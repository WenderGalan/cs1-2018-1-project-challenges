package challenges.com.challenges.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.HintAdapter;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.util.Validator;

public class CadastrarDesafioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText titulo;
    private EditText recompensa;
    private EditText pontos;
    private EditText observacoes;
    private Button salvar;
    private Spinner spinnerHabilidade;
    private List<String> listaHabilidade;
    private boolean habilidadeSelecionada = false;
    private Desafio desafio;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_desafio);

        spinnerHabilidade = findViewById(R.id.spinnerHabilidades);
        titulo = findViewById(R.id.editTextTituloDesafio);
        recompensa = findViewById(R.id.editTextRecompensaDesafio);
        pontos = findViewById(R.id.editTextPontosDesafio);
        observacoes = findViewById(R.id.editTextObservacoesDesafio);
        salvar = findViewById(R.id.buttonSalvarDesafio);
        toolbar = findViewById(R.id.toolbarCadastroDesafio);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cadastrar Desafio");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        desafio = new Desafio();

        //botao salvar
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){
                    //seta o titulo
                    desafio.setTitulo(titulo.getText().toString());
                    //seta a recompensa se for diferente de vazio
                    if (!recompensa.getText().toString().equals("")){
                        desafio.setRecompensa(recompensa.getText().toString());
                    }
                    desafio.setPontos(Integer.parseInt(pontos.getText().toString()));
                    if (!observacoes.getText().toString().equals("")){
                        desafio.setObservacoes(observacoes.getText().toString());
                    }

                    /**
                     * AGORA É SO CHAMAR O CONSTRUIR HASH E SALVAR O DESAFIO NO BANCO DE DADOS
                     * */
                    desafio.construirHash();

                }
            }
        });

        listaHabilidade = new ArrayList<String>();
        listaHabilidade.add("Física");
        listaHabilidade.add("Intelectual");
        listaHabilidade.add("Criatividade");
        listaHabilidade.add("Social");
        listaHabilidade.add("Habilidade");
        //seta o adapter para consumir toda a lista menos o último item porque o mesmo é o hint da caixa de seleção
        HintAdapter adapterCategoria = new HintAdapter(this, R.layout.spinner_item, listaHabilidade);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHabilidade.setAdapter(adapterCategoria);
        spinnerHabilidade.setSelection(adapterCategoria.getCount());

        spinnerHabilidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String resultado = listaHabilidade.get(i).toString();
                if (resultado.equals("Habilidade")){
                    habilidadeSelecionada = true;
                }else if (resultado.equals("Física")){
                    desafio.setHabilidade("Física");
                    habilidadeSelecionada = false;
                }else if (resultado.equals("Intelectual")){
                    desafio.setHabilidade("Intelectual");
                    habilidadeSelecionada = false;
                }else if (resultado.equals("Criatividade")){
                    desafio.setHabilidade("Criatividade");
                    habilidadeSelecionada = false;
                }else if (resultado.equals("Social")){
                    desafio.setHabilidade("Social");
                    habilidadeSelecionada = false;
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
        return retorno;
    }


}
