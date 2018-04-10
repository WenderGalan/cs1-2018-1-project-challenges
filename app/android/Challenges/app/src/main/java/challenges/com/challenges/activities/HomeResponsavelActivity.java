package challenges.com.challenges.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.CriancaAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Responsavel;

public class HomeResponsavelActivity extends AppCompatActivity {

    private ImageView configuracoes;
    private TextView nome;
    private ImageView adicionarCrianca;
    private ImageView adicionarDesafio;
    private RecyclerView recyclerView;
    private GridView gridView;

    private DocumentReference usuario;
    private String usuarioUID;
    private Responsavel responsavel;
    private ArrayList<DocumentReference> criancas;
    private ArrayList<Crianca> criancasObject;
    private CriancaAdapter adapter;


    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_responsavel);

        nome = findViewById(R.id.textViewNomeResponsavel);
        configuracoes = findViewById(R.id.imageViewConfiguracoes);
        adicionarCrianca = findViewById(R.id.imageViewAddCrianca);
        adicionarDesafio = findViewById(R.id.imageViewAddDesafio);
        recyclerView = findViewById(R.id.recyclerViewCriancas);
        gridView = findViewById(R.id.gridViewDesafios);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        usuarioUID = autenticacao.getCurrentUser().getUid();
        usuario = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID);
        criancasObject = new ArrayList<>();


        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeResponsavelActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CriancaAdapter(criancasObject);

        recyclerView.setAdapter(adapter);

        //recupera o usuario
        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID).get().addOnSuccessListener(HomeResponsavelActivity.this, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                responsavel = documentSnapshot.toObject(Responsavel.class);
                /**SETA TODAS AS INFORMACOES DA ACTIVITY PARA NAO OCORRER ERROS DE NULO**/
                nome.setText(responsavel.getNome().toUpperCase());
                //recupera as criancas
                criancas = responsavel.getCriancas();
                if (criancas != null){
                    //buscar as criancas no banco de dados
                    for (int i = 0 ; i < criancas.size() ; i++){
                        criancas.get(i).get().addOnSuccessListener(HomeResponsavelActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Crianca crianca = documentSnapshot.toObject(Crianca.class);
                                criancasObject.add(crianca);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

        configuracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirConfiguracoes();
            }
        });

        adicionarCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAdicionarCrianca();
            }
        });

        adicionarDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAdicionarDesafio();
            }
        });






    }

    private void abrirAdicionarDesafio() {
        Intent intent = new Intent(HomeResponsavelActivity.this, CadastrarDesafioActivity.class);
        startActivity(intent);
    }

    private void abrirAdicionarCrianca() {
        Intent intent = new Intent(HomeResponsavelActivity.this, CadastroCriancaActivity.class);
        intent.putExtra("responsavel", usuarioUID);
        intent.putExtra("segundoCadastro", "verdade");
        startActivity(intent);
    }

    private void abrirConfiguracoes() {
        Intent intent = new Intent(HomeResponsavelActivity.this, ConfiguracoesResponsavelActivity.class);
        startActivity(intent);
    }
}
