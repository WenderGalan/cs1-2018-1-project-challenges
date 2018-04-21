package challenges.com.challenges.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import challenges.com.challenges.R;

public class CadastrarDesafioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText titulo;
    private EditText recompensa;
    private EditText pontos;
    private EditText observacoes;
    private Button salvar;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_desafio);

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

        //botao salvar
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos()){

                }
            }
        });




    }

    private boolean validarCampos() {



        return true;
    }


}
