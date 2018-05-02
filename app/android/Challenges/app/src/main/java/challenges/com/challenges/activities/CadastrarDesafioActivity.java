package challenges.com.challenges.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        usuarioUID = ConfiguracaoFirebase.getFirebaseAutenticacao().getUid();
        desafio.setResponsavel(usuarioUID);

        nomes = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();

        nomes = getIntent().getStringArrayListExtra("nomes");
        ids = getIntent().getStringArrayListExtra("ids");

        nomes.add("Crianças");

        HintAdapter adapterCriancas = new HintAdapter(this, R.layout.spinner_item, nomes);
        adapterCriancas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCriancas.setAdapter(adapterCriancas);
        spinnerHabilidade.setSelection(adapterCriancas.getCount());

        spinnerCriancas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                String resultado = nomes.get(i).toString();
                if (resultado.equals("Crianças")){
                    criancaSelecionada = true;
                }else{
                    responsavelReference = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID);
                    responsavelReference.get().addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
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
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Intelectual")) {
                    desafio.setHabilidade("intelectual");
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Criatividade")) {
                    desafio.setHabilidade("criatividade");
                    habilidadeSelecionada = false;
                } else if (resultado.equals("Social")) {
                    desafio.setHabilidade("social");
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
                    qtdRepeticoes.setText("1");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Diário")) {
                    desafio.setFrequencia("diario");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Semanal")) {
                    desafio.setFrequencia("semanal");
                    frequenciaSelecionada = false;
                } else if (resultado.equals("Mensal")) {
                    desafio.setFrequencia("Mensal");
                    frequenciaSelecionada = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //botao salvar
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    int mes = c.get(Calendar.MONTH)+1;
                    int dia = c.get(Calendar.DAY_OF_MONTH);
                    int hora = c.get(Calendar.HOUR_OF_DAY);
                    int minuto = c.get(Calendar.MINUTE);

                    desafio.setData(dia + "/" + mes + "/" + ano);
                    desafio.setHora(hora + ":" + minuto);

                    ConfiguracaoFirebase.getFirestore().collection("Desafios").document().set(desafio.construirHash()).addOnSuccessListener(CadastrarDesafioActivity.this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            Toast.makeText(CadastrarDesafioActivity.this, "Desafio inserido com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }
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
