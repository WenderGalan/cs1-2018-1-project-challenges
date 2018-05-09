package challenges.com.challenges.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import de.hdodenhof.circleimageview.CircleImageView;

public class CriancaDetalhesActivity extends AppCompatActivity {

    private TextView nome;
    private TextView habilidade;
    private TextView qtdDesafios;
    private TextView qtdPontos;
    private TextView qtdAmigos;
    private TextView qtdRecompensas;
    private CircleImageView imagem;
    private Crianca crianca;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crianca_detalhes);

        nome = findViewById(R.id.labelNomeCrianca);
        habilidade = findViewById(R.id.label_habilidade_principal);
        imagem = findViewById(R.id.foto_perfil_image);
        qtdDesafios = findViewById(R.id.label_qnt_desafios);
        qtdPontos = findViewById(R.id.label_qnt_pontos);
        qtdAmigos = findViewById(R.id.label_qnt_amigos);
        qtdRecompensas = findViewById(R.id.label_qnt_recompensas);

        id = getIntent().getStringExtra("id");

        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(id).get().addOnSuccessListener(CriancaDetalhesActivity.this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                crianca = documentSnapshot.toObject(Crianca.class);
                if (crianca != null){
                    if (crianca.getNome() != null) { nome.setText(crianca.getNome().toUpperCase());}
                    if (crianca.getFoto() != null){ Picasso.get().load(crianca.getFoto()).into(imagem);}
                    if (crianca.getPontos() != 0) { qtdPontos.setText(crianca.getPontos());}
                }
            }
        });


        imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CriancaDetalhesActivity.this, CadastroCriancaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("tipo", "editar");
                startActivity(intent);
            }
        });







    }
}
