package challenges.com.challenges.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Desafio;

public class DesafioDetalhesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titulo;
    private TextView frequencia;
    private TextView habilidade;
    private TextView pontos;
    private TextView recompensa;
    private TextView totalPontos;
    private TextView totalAjuda;
    private TextView totalNaoFeito;
    private TextView totalRecompensas;
    private ImageView imagemHabilidade;
    private Button primeiroBotao;
    private Button segundoBotao;
    private Desafio desafio;
    /**TIPO vai ter três para poder saber qual activity será aberta
     * responsavel que é a padrão,
     * crianca,
     * ajuda,
     * completo(completar Desafio)
     * aceitar (aceitar desafio)
     * **/
    private String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_detalhes);

        toolbar = findViewById(R.id.toolbarDesafio);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titulo = findViewById(R.id.textViewTituloDesafioDetalhes);
        frequencia = findViewById(R.id.textViewFrequenciaDesafioDetalhes);
        habilidade = findViewById(R.id.textViewHabilidadeDesafioDetalhes);
        pontos = findViewById(R.id.textViewPontosDesafioDetalhes);
        recompensa = findViewById(R.id.textViewHabilidadeDesafioDetalhes3);
        totalPontos = findViewById(R.id.textViewPontuacaoDetalhesDesafio);
        totalAjuda = findViewById(R.id.textViewAjudaDetalhesDesafio);
        totalNaoFeito = findViewById(R.id.textViewNaoFeitoDetalhesDesafio);
        totalRecompensas = findViewById(R.id.textViewRecompensasDetalhesDesafio);
        imagemHabilidade = findViewById(R.id.imageViewHabilidadeDetalhesDesafio);
        primeiroBotao = findViewById(R.id.buttonPrimeiro);
        segundoBotao = findViewById(R.id.buttonSegundo);

        //recuperar as intents
        desafio = (Desafio) getIntent().getSerializableExtra("desafio");
        tipo = getIntent().getStringExtra("tipo");

        /**
         * Intent intent = new Intent(getActivity(), DesafioDetalhesActivity.class);
         * intent.putExtra("desafio", desafio);
         * intent.putExtra("tipo", "aceitar");
         * startActivity(intent);
         * **/


        //seta os textview na acitivity
        if (desafio != null){
            if (desafio.getTitulo() != null){
                titulo.setText(desafio.getTitulo());
            }
            if (desafio.getRecompensa() != null){
                recompensa.setText("Recompensa: " + desafio.getRecompensa());
            }
            if (desafio.getHabilidade() != null){
                switch (desafio.getHabilidade()){
                    case "fisica":
                        imagemHabilidade.setImageResource(R.drawable.habilidade_fisica);
                        habilidade.setText("Física");
                        break;
                    case "intelectual":
                        imagemHabilidade.setImageResource(R.drawable.habilidade_inteligencia);
                        habilidade.setText("Intelectual");
                        break;
                    case "criatividade":
                        imagemHabilidade.setImageResource(R.drawable.habilidade_criatividade);
                        habilidade.setText("Criatividade");
                        break;
                    case "social":
                        imagemHabilidade.setImageResource(R.drawable.habilidade_social);
                        habilidade.setText("Social");
                        break;
                }
            }
            if (desafio.getPontos() != 0){
                pontos.setText("Pontos: " + desafio.getPontos());
            }
            if (desafio.getFrequencia() != null){
                switch (desafio.getFrequencia()){
                    case "unica vez":
                        frequencia.setText("Única vez");
                        break;
                    case "diario":
                        frequencia.setText("Diário");
                        break;
                    case "semanal":
                        frequencia.setText("Semanal");
                        break;
                    case "mensal":
                        frequencia.setText("Mensal");
                        break;
                }
            }
            /**
             *
             * AINDA TEM QUE SETAR O PROGRESSO DO USUÁRIO PORQUE NAO TEM INFORMAÇÃO AINDA
             *
             * */
        }

        if (tipo.equals("responsavel")){
            primeiroBotao.setText("Editar");
            primeiroBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_azul_grande));
            primeiroBotao.setVisibility(View.VISIBLE);
            segundoBotao.setText("Excluir");
            segundoBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_vermelho_grande));
            segundoBotao.setVisibility(View.VISIBLE);
        }else if (tipo.equals("crianca")){
            primeiroBotao.setText("Completar Desafio");
            primeiroBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_verde_grande));
            primeiroBotao.setVisibility(View.VISIBLE);
            segundoBotao.setText("Pedir ajuda");
            segundoBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_azul_grande));
            segundoBotao.setVisibility(View.VISIBLE);
        }else if(tipo.equals("ajuda")){
            primeiroBotao.setText("Aceitar pedido de ajuda");
            primeiroBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_verde_grande));
            primeiroBotao.setVisibility(View.VISIBLE);
            segundoBotao.setText("Recusar pedido de ajuda");
            segundoBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_vermelho_grande));
            segundoBotao.setVisibility(View.VISIBLE);
        }else if (tipo.equals("completo")){
            primeiroBotao.setText("Completar desafio");
            primeiroBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_verde_grande));
            segundoBotao.setVisibility(View.INVISIBLE);
        }else if (tipo.equals("aceitar")){
            primeiroBotao.setText("Aceitar desafio");
            primeiroBotao.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fundo_botao_azul_grande));
            segundoBotao.setVisibility(View.INVISIBLE);
        }

        primeiroBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo.equals("responsavel")){
                    //EDITAR O DESAFIO
                    Intent intent = new Intent(DesafioDetalhesActivity.this, CadastrarDesafioActivity.class);
                    intent.putExtra("desafio", desafio);
                    intent.putExtra("tipo", "editar");
                    startActivity(intent);

                }else if (tipo.equals("crianca")){


                }else if(tipo.equals("ajuda")){


                }else if (tipo.equals("completo")){


                }else if (tipo.equals("aceitar")){


                }
            }
        });

        segundoBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo.equals("responsavel")){
                    //EXCLUIR O DESAFIO
                    DocumentReference reference = ConfiguracaoFirebase.getFirestore().collection("Desafios").document(desafio.getId());
                    reference.delete().addOnCompleteListener(DesafioDetalhesActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(DesafioDetalhesActivity.this, "Desafio deletado com sucesso!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DesafioDetalhesActivity.this, HomeResponsavelActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }else if (tipo.equals("crianca")){


                }else if(tipo.equals("ajuda")){


                }else if (tipo.equals("completo")){


                }else if (tipo.equals("aceitar")){


                }
            }
        });



    }
}
