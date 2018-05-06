package challenges.com.challenges.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.HintAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Responsavel;
import challenges.com.challenges.util.Validator;

public class CadastrarDesafioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText titulo;
    private EditText recompensa;
    private EditText pontos;
    private EditText observacoes;
    private EditText qtdRepeticoes;
    private Spinner spinnerCriancas;
    private Spinner spinnerFrequencia;
    private Button salvar;
    private Spinner spinnerHabilidade;
    private List<String> listaHabilidade;
    private List<String> listaFrequencia;
    private boolean habilidadeSelecionada = false;
    private boolean frequenciaSelecionada = false;
    private boolean criancaSelecionada = false;
    private Desafio desafio;
    private String usuarioUID;
    private ArrayList<DocumentReference> criancasReferencia;
    private List<String> nomes;
    private ArrayList<DocumentReference> criancas;
    private DocumentReference responsavelReference;
    private String tipo;
    private Desafio desafioEditar;
    private Crianca crianca;
    private String nomeCrianca;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_desafio);

        spinnerHabilidade = findViewById(R.id.spinnerHabilidades);
        qtdRepeticoes = findViewById(R.id.editTextRepeticoesDesafio);
        spinnerCriancas = findViewById(R.id.spinnerCriancas);
        spinnerFrequencia = findViewById(R.id.spinnerFrequencia);
        titulo = findViewById(R.id.editTextTituloDesafio);
        recompensa = findViewById(R.id.editTextRecompensaDesafio);
        pontos = findViewById(R.id.editTextPontosDesafio);
        observacoes = findViewById(R.id.editTextObservacoesDesafio);
        salvar = findViewById(R.id.buttonSalvarDesafio);
        toolbar = findViewById(R.id.toolbarCadastroDesafio);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        desafio = new Desafio();
        criancasReferencia = new ArrayList<DocumentReference>();
        nomes = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        desafioEditar = new Desafio();

        tipo = getIntent().getStringExtra("tipo");


        if (tipo.equals("editar")) {
            desafioEditar = (Desafio) getIntent().getSerializableExtra("desafio");
            titulo.setText(desafioEditar.getTitulo());
            recompensa.setText(desafioEditar.getRecompensa());
            pontos.setText(String.valueOf(desafioEditar.getPontos()));
            observacoes.setText(desafioEditar.getObservacoes());
            qtdRepeticoes.setText(String.valueOf(desafioEditar.getRepeticoes()));


        } else if (tipo.equals("cadastrar")) {
            nomes = getIntent().getStringArrayListExtra("nomes");
            ids = getIntent().getStringArrayListExtra("ids");
            nomes.add("Crianças");
        }

        usuarioUID = ConfiguracaoFirebase.getFirebaseAutenticacao().getUid();
        desafio.setResponsavel(usuarioUID);

        //seta os spinners
        setarSpinners();

        if (tipo.equals("editar")) {
            switch (desafioEditar.getHabilidade()) {
                case "fisica":
                    spinnerHabilidade.setSelection(0);
                    break;
                case "intelectual":
                    spinnerHabilidade.setSelection(1);
                    break;
                case "criatividade":
                    spinnerHabilidade.setSelection(2);
                    break;
                case "social":
                    spinnerHabilidade.setSelection(3);
                    break;
            }

            switch (desafioEditar.getFrequencia()) {
                case "unica vez":
                    spinnerFrequencia.setSelection(0);
                    break;
                case "diario":
                    spinnerFrequencia.setSelection(1);
                    break;
                case "semanal":
                    spinnerFrequencia.setSelection(2);
                    break;
                case "mensal":
                    spinnerFrequencia.setSelection(3);
                    break;
            }

            Log.i("DEBUG", "ID: " + desafioEditar.getCrianca());
            DocumentReference reference = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(desafioEditar.getCrianca());

            reference.get().addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        crianca = documentSnapshot.toObject(Crianca.class);
                        Log.i("DEBUG", "getNome: " + crianca.getNome());
                        nomeCrianca = crianca.getNome();

                        //seta o spinner da crianca
                        List<String> crianca = new ArrayList<String>();
                        crianca.add(nomeCrianca);
                        crianca.add(nomeCrianca);
                        HintAdapter adapterCriancas = new HintAdapter(CadastrarDesafioActivity.this, R.layout.spinner_item, crianca);
                        adapterCriancas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCriancas.setAdapter(adapterCriancas);
                        spinnerCriancas.setSelection(adapterCriancas.getCount());
                        spinnerCriancas.setClickable(false);
                        spinnerCriancas.setFocusable(false);

                        final int sdk = android.os.Build.VERSION.SDK_INT;

                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            spinnerCriancas.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        } else {
                            spinnerCriancas.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_cinza));
                        }

                    }
                }
            });


        }


        //botao salvar
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo.equals("cadastrar")) {
                    if (validarCampos()) {
                        //seta o titulo
                        desafio.setTitulo(titulo.getText().toString());
                        //seta a recompensa se for diferente de vazio
                        if (!recompensa.getText().toString().equals("")) {
                            desafio.setRecompensa(recompensa.getText().toString());
                        }
                        desafio.setPontos(Integer.parseInt(pontos.getText().toString()));
                        if (!observacoes.getText().toString().equals("")) {
                            desafio.setObservacoes(observacoes.getText().toString());
                        }
                        /**SETAR A HORA E A DATA ATUAL**/
                        Calendar c = Calendar.getInstance();
                        int ano = c.get(Calendar.YEAR);
                        int mes = c.get(Calendar.MONTH) + 1;
                        int dia = c.get(Calendar.DAY_OF_MONTH);
                        int hora = c.get(Calendar.HOUR_OF_DAY);
                        int minuto = c.get(Calendar.MINUTE);

                        desafio.setData(dia + "/" + mes + "/" + ano);
                        desafio.setHora(hora + ":" + minuto);

                        ConfiguracaoFirebase.getFirestore().collection("Desafios").document().set(desafio.construirHash()).addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(CadastrarDesafioActivity.this, "Desafio inserido com sucesso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CadastrarDesafioActivity.this, HomeResponsavelActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                    }
                } else if (tipo.equals("editar")) {
                    desafioEditar.setTitulo(titulo.getText().toString());
                    desafioEditar.setRecompensa(recompensa.getText().toString());
                    desafioEditar.setObservacoes(observacoes.getText().toString());
                    desafioEditar.setRepeticoes(Integer.parseInt(qtdRepeticoes.getText().toString()));
                    desafioEditar.setPontos(Integer.parseInt(pontos.getText().toString()));
                    DocumentReference referencia = ConfiguracaoFirebase.getFirestore().collection("Desafios").document(desafioEditar.getId());
                    referencia.update(desafioEditar.construirHash()).addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(CadastrarDesafioActivity.this, "Desafio atualizado com sucesso!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CadastrarDesafioActivity.this, HomeResponsavelActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

    private void setarSpinners() {
        HintAdapter adapterCriancas = new HintAdapter(this, R.layout.spinner_item, nomes);
        adapterCriancas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCriancas.setAdapter(adapterCriancas);
        spinnerHabilidade.setSelection(adapterCriancas.getCount());

        if (tipo.equals("cadastrar")) {
            spinnerCriancas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                    String resultado = nomes.get(i).toString();
                    if (resultado.equals("Crianças")) {
                        criancaSelecionada = true;
                    } else {
                        responsavelReference = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID);
                        responsavelReference.get().addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Responsavel responsavel = documentSnapshot.toObject(Responsavel.class);
                                    criancas = responsavel.getCriancas();
                                    desafio.setCrianca(criancas.get(i).getId());
                                }
                            }
                        });
                        criancaSelecionada = false;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        //seta as listas
        listaHabilidade = new ArrayList<String>();
        listaHabilidade.add("Física");
        listaHabilidade.add("Intelectual");
        listaHabilidade.add("Criatividade");
        listaHabilidade.add("Social");
        listaHabilidade.add("Habilidade");

        //seta o adapter para consumir toda a lista menos o último item porque o mesmo é o hint da caixa de seleção
        HintAdapter adapterHabilidade = new HintAdapter(this, R.layout.spinner_item, listaHabilidade);
        adapterHabilidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHabilidade.setAdapter(adapterHabilidade);
        spinnerHabilidade.setSelection(adapterHabilidade.getCount());

        spinnerHabilidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String resultado = listaHabilidade.get(i).toString();
                if (resultado.equals("Habilidade")) {
                    habilidadeSelecionada = true;
                } else if (resultado.equals("Física")) {
                    desafio.setHabilidade("fisica");
                    desafioEditar.setHabilidade("fisica");
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Intelectual")) {
                    desafio.setHabilidade("intelectual");
                    desafioEditar.setHabilidade("intelectual");
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Criatividade")) {
                    desafio.setHabilidade("criatividade");
                    desafioEditar.setHabilidade("criatividade");
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Social")) {
                    desafio.setHabilidade("social");
                    desafioEditar.setHabilidade("social");
                    habilidadeSelecionada = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //seta a lista
        listaFrequencia = new ArrayList<String>();
        listaFrequencia.add("Única vez");
        listaFrequencia.add("Diário");
        listaFrequencia.add("Semanal");
        listaFrequencia.add("Mensal");
        listaFrequencia.add("Frequência");

        HintAdapter adapterFrequencia = new HintAdapter(this, R.layout.spinner_item, listaFrequencia);
        adapterFrequencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequencia.setAdapter(adapterFrequencia);
        spinnerFrequencia.setSelection(adapterFrequencia.getCount());

        spinnerFrequencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String resultado = listaFrequencia.get(i).toString();
                if (resultado.equals("Frequência")) {
                    frequenciaSelecionada = true;
                } else if (resultado.equals("Única vez")) {
                    desafio.setFrequencia("unica vez");
                    desafioEditar.setFrequencia("unica vez");
                    qtdRepeticoes.setText("1");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Diário")) {
                    desafio.setFrequencia("diario");
                    desafioEditar.setFrequencia("diario");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Semanal")) {
                    desafio.setFrequencia("semanal");
                    desafioEditar.setFrequencia("semanal");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Mensal")) {
                    desafio.setFrequencia("Mensal");
                    desafioEditar.setFrequencia("Mensal");
                    frequenciaSelecionada = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private boolean validarCampos() {
        Validator validator = new Validator();
        boolean retorno = true;
        if (!validator.validateNotNull(titulo)) {
            titulo.setError("Título vazio");
            titulo.setFocusable(true);
            titulo.requestFocus();
            retorno = false;
        }
        if (!validator.validateNotNull(pontos)) {
            pontos.setError("Pontos vazio");
            pontos.setFocusable(true);
            pontos.requestFocus();
            retorno = false;
        }
        if (habilidadeSelecionada) {
            TextView errorText3 = (TextView) spinnerHabilidade.getSelectedView();
            errorText3.setError("Habilidade não selecionada");
            errorText3.setTextColor(Color.RED);//just to highlight that this is an error
            errorText3.setText("Habilidade não selecionada");//changes the selected item text to this
            retorno = false;
        }
        if (frequenciaSelecionada) {
            TextView errorText3 = (TextView) spinnerFrequencia.getSelectedView();
            errorText3.setError("Frequência não selecionada");
            errorText3.setTextColor(Color.RED);//just to highlight that this is an error
            errorText3.setText("Frequência não selecionada");//changes the selected item text to this
            retorno = false;
        }
        if (criancaSelecionada) {
            TextView errorText3 = (TextView) spinnerCriancas.getSelectedView();
            errorText3.setError("Criança não selecionada");
            errorText3.setTextColor(Color.RED);//just to highlight that this is an error
            errorText3.setText("Criança não selecionada");//changes the selected item text to this
            retorno = false;
        }
        return retorno;
    }


}
