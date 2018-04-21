package challenges.com.challenges.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import challenges.com.challenges.R;

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

    }
}
