package challenges.com.challenges.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.CriancaAdapter;
import challenges.com.challenges.adapter.DesafioAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Responsavel;

public class HomeResponsavelActivity extends AppCompatActivity {

    private ImageView editarUsuario;
    private TextView nome;
    private ImageView adicionarCrianca;
    private ImageView adicionarDesafio;
    private ImageView verNotificacoes;
    private RecyclerView recyclerViewCrianca;
    private RecyclerView recyclerViewDesafio;
    private Switch switchSocial;

    private DocumentReference usuario;
    private String usuarioUID;
    private Responsavel responsavel;
    private ArrayList<DocumentReference> criancas;
    private ArrayList<Crianca> criancasObject;
    private CriancaAdapter adapterCrianca;
    private DesafioAdapter adapterDesafio;

    private ArrayList<DocumentReference> criancasObject2;
    private List<String> listaCriancas;
    private ArrayList<Desafio> desafiosResult = new ArrayList<Desafio>();

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_responsavel);

        nome = findViewById(R.id.textViewNomeResponsavel);
        editarUsuario = findViewById(R.id.imageViewEditarUsuario);
        adicionarCrianca = findViewById(R.id.imageViewAddCrianca);
        adicionarDesafio = findViewById(R.id.imageViewAddDesafio);
        recyclerViewCrianca = findViewById(R.id.recyclerViewCriancas);
        recyclerViewDesafio = findViewById(R.id.recyclerViewDesafios);
        verNotificacoes = findViewById(R.id.imageViewNotificacoes);
        switchSocial = findViewById(R.id.switchAddCrianças);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        usuarioUID = autenticacao.getCurrentUser().getUid();
        usuario = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID);
        criancasObject = new ArrayList<Crianca>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeResponsavelActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCrianca.setLayoutManager(layoutManager);


        GridLayoutManager layoutManager2 = new GridLayoutManager(HomeResponsavelActivity.this, 2);
        recyclerViewDesafio.setLayoutManager(layoutManager2);


        //recupera o usuario
        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID).addSnapshotListener(HomeResponsavelActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                responsavel = documentSnapshot.toObject(Responsavel.class);
                /**SETA TODAS AS INFORMACOES DA ACTIVITY PARA NAO OCORRER ERROS DE NULO**/
                nome.setText(responsavel.getNome().toUpperCase());
                switchSocial.setChecked(responsavel.isPermiteSocial());
                //recupera as criancas

                criancas = responsavel.getCriancas();
                criancasObject.clear();

                if (criancas != null) {
                    //buscar as criancas no banco de dados
                    for (int i = 0; i < criancas.size(); i++) {
                        criancas.get(i).get().addOnSuccessListener(HomeResponsavelActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Crianca crianca = documentSnapshot.toObject(Crianca.class);
                                crianca.setId(documentSnapshot.getId());
                                criancasObject.add(crianca);
                                adapterCrianca = new CriancaAdapter(criancasObject, "crianca");
                                recyclerViewCrianca.setAdapter(adapterCrianca);
                            }
                        });
                    }
                }
            }
        });

        //carrega os desafios da view
        carregarDesafios();

        editarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirEditarUsuario();
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
                if (criancasObject == null || criancasObject.size() == 0) {
                    final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(HomeResponsavelActivity.this);
                    alertDialog.setMessage("Você precisa ter um filho cadastrado para poder criar um desafio!");
                    alertDialog.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    alertDialog.show();
                } else {
                    abrirAdicionarDesafio();
                }
            }
        });

        verNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNotificacoes();
            }
        });

        switchSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                responsavel.setPermiteSocial(b);
                Map<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("permiteSocial", b);
                DocumentReference reference = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(usuarioUID);
                reference.update(hashMap).addOnSuccessListener(HomeResponsavelActivity.this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                    }
                });
            }
        });

    }

    private void carregarDesafios() {
        Query query = ConfiguracaoFirebase.getFirestore().collection("Desafios").whereEqualTo("responsavel", usuarioUID);
        query.addSnapshotListener(HomeResponsavelActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                desafiosResult.clear();
                for (DocumentSnapshot document : documentSnapshots){
                    Desafio desafio = document.toObject(Desafio.class);
                    desafio.setId(document.getId());
                    desafiosResult.add(desafio);
                }
                adapterDesafio = new DesafioAdapter(desafiosResult, "responsavel");
                recyclerViewDesafio.setAdapter(adapterDesafio);
            }
        });
    }


    private void abrirNotificacoes() {
        Intent intent = new Intent(HomeResponsavelActivity.this, NotificacoesActivity.class);
        startActivity(intent);
    }

    private void abrirAdicionarDesafio() {
        Intent intent = new Intent(HomeResponsavelActivity.this, CadastrarDesafioActivity.class);
        List<String> nomes = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < criancasObject.size(); i++) {
            nomes.add(criancasObject.get(i).getNome());
        }
        for (int i = 0; i < criancas.size(); i++) {
            ids.add(String.valueOf(criancas.get(i)));
        }

        intent.putStringArrayListExtra("nomes", (ArrayList<String>) nomes);
        intent.putStringArrayListExtra("ids", (ArrayList<String>) ids);
        intent.putExtra("tipo", "cadastrar");
        startActivity(intent);
    }

    private void abrirAdicionarCrianca() {
        Intent intent = new Intent(HomeResponsavelActivity.this, CadastroCriancaActivity.class);
        intent.putExtra("tipo", "cadastrar");
        intent.putExtra("responsavel", usuarioUID);
        intent.putExtra("segundoCadastro", "verdade");
        startActivity(intent);
    }

    private void abrirEditarUsuario() {
        Intent intent = new Intent(HomeResponsavelActivity.this, CadastroActivity.class);
        intent.putExtra("tipo", "editar");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void sairDaConta(View view) {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        Intent intent = new Intent(HomeResponsavelActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
