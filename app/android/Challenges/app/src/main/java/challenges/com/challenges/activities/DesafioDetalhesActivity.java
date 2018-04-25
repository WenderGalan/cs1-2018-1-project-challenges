package challenges.com.challenges.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import challenges.com.challenges.R;
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
                habilidade.setText(desafio.getHabilidade());
            }
            if (desafio.getPontos() != 0){
                pontos.setText("Pontos: " + desafio.getPontos());
            }
            /**TEM QUE SETAR A IMAGEM TAMBEM**/
            /**SETAR A FREQUENCIA e BUSCAR O PROGRESSO E SETAR AQUI**/

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


                }else if (tipo.equals("crianca")){


                }else if(tipo.equals("ajuda")){


                }else if (tipo.equals("completo")){


                }else if (tipo.equals("aceitar")){


                }
            }
        });



    }
}
