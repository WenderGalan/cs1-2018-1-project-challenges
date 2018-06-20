package challenges.com.challenges.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.NotificacaoAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Notificacao;

public class NotificacoesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerViewNotificacoes;
    private ArrayList<Notificacao> notificacoesDesafio = new ArrayList<>();
    private FirebaseAuth autenticacao;
    private NotificacaoAdapter notificacaoAdapter;
    private ArrayList<Desafio> desafioArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        recyclerViewNotificacoes = findViewById(R.id.notificacao_desafio);
        acharDesafio();

        LinearLayoutManager linearLayout = new LinearLayoutManager(NotificacoesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNotificacoes.setLayoutManager(linearLayout);

        toolbar = findViewById(R.id.toolbarNotificacoes);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void carregarNotificacoes() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        final String idUsuarioAtual = autenticacao.getCurrentUser().getUid();

        Query query = ConfiguracaoFirebase.getFirestore().collection("NotificacoesDesafioCompleto").whereEqualTo("responsavel", idUsuarioAtual);
        query.addSnapshotListener(NotificacoesActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                notificacoesDesafio.clear();

                if (documentSnapshots != null) {
                    for (DocumentSnapshot documentSnapshot: documentSnapshots) {
                        Notificacao notificacao = documentSnapshot.toObject(Notificacao.class);
                        notificacao.setId(documentSnapshot.getId());

                        for (Desafio desafio : desafioArrayList) {

                            if (notificacao.getDesafio().getId().equals(desafio.getId())) {
                                notificacao.setDesafioObject(desafio);
                                break;
                            }

                        }
                        notificacoesDesafio.add(notificacao);
                    }
                }
                notificacaoAdapter = new NotificacaoAdapter(notificacoesDesafio, NotificacoesActivity.this);
                recyclerViewNotificacoes.setAdapter(notificacaoAdapter);
            }

        });
    }

    private void acharDesafio() {
        Task<QuerySnapshot> query = ConfiguracaoFirebase.getFirestore().collection("Desafios").get();
        query.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Desafio desafio = documentSnapshot.toObject(Desafio.class);
                        desafio.setId(documentSnapshot.getId());
                        desafioArrayList.add(desafio);
                    }
                }
                carregarNotificacoes();
            }
        });
    }
}