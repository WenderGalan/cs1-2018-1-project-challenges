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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import challenges.com.challenges.R;
import challenges.com.challenges.adapter.NotificacaoAdapter;
import challenges.com.challenges.adapter.NotificacaoAmizadeAdapter;
import challenges.com.challenges.adapter.NotificacaoDesafioAppAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.DesafioApp;
import challenges.com.challenges.model.Notificacao;
import challenges.com.challenges.model.NotificacaoAmizade;
import challenges.com.challenges.model.NotificacaoDesafioApps;

public class NotificacoesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerViewNotificacoes;
    private RecyclerView recyclerViewNotificacoesApp;
    private ArrayList<Notificacao> notificacoesDesafio = new ArrayList<>();
    private ArrayList<NotificacaoDesafioApps> notificacoesDesafioApp = new ArrayList<>();
    private FirebaseAuth autenticacao;
    private NotificacaoAdapter notificacaoAdapter;
    private NotificacaoDesafioAppAdapter notificacaoDesafioAdapter;
    private ArrayList<Desafio> desafioArrayList = new ArrayList<>();
    private ArrayList<DesafioApp> desafioAppArrayList = new ArrayList<>();
    private RecyclerView recyclerViewNotificacoesAmizade;
    private ArrayList<NotificacaoAmizade> notificacaoAmizades = new ArrayList<>();
    private NotificacaoAmizadeAdapter adapterNotificacaoAmizade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        recyclerViewNotificacoes = findViewById(R.id.notificacao_desafio);
        recyclerViewNotificacoesApp = findViewById(R.id.notificacao_desafioApp);
        recyclerViewNotificacoesAmizade = findViewById(R.id.notificacao_amizade);
        acharDesafio();
        acharDesafiosApp();
        acharNotificacoesAmizade();

        LinearLayoutManager linearLayout = new LinearLayoutManager(NotificacoesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNotificacoes.setLayoutManager(linearLayout);

        LinearLayoutManager linearLayout2 = new LinearLayoutManager(NotificacoesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNotificacoesApp.setLayoutManager(linearLayout2);

        LinearLayoutManager linearLayout3 = new LinearLayoutManager(NotificacoesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNotificacoesAmizade.setLayoutManager(linearLayout3);

        toolbar = findViewById(R.id.toolbarNotificacoes);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void acharNotificacoesAmizade() {
        String idUsuario = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser().getUid();
        DocumentReference here = ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuario);
        Query query  = ConfiguracaoFirebase.getFirestore().collection("NotificacaoAmizade").whereEqualTo("amigo", here);

        query.addSnapshotListener(NotificacoesActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                notificacaoAmizades.clear();
                if (documentSnapshots.getDocuments() != null){
                    for (DocumentSnapshot document : documentSnapshots.getDocuments()){
                        if (document.exists()){
                            NotificacaoAmizade notificacaoAmizade = document.toObject(NotificacaoAmizade.class);
                            notificacaoAmizade.setId(document.getId());
                            notificacaoAmizades.add(notificacaoAmizade);
                        }
                    }
                    //chamar o adapter e a view
                    adapterNotificacaoAmizade = new NotificacaoAmizadeAdapter(notificacaoAmizades, NotificacoesActivity.this);
                    recyclerViewNotificacoesAmizade.setAdapter(adapterNotificacaoAmizade);
                    adapterNotificacaoAmizade.notifyDataSetChanged();
                }
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

    private void carregarNotificacoesDesafioApp() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        final String idUsuarioAtual = autenticacao.getCurrentUser().getUid();

        Query query = ConfiguracaoFirebase.getFirestore().collection("NotificacaoDesafioApps").whereEqualTo("responsavel", idUsuarioAtual);
        query.addSnapshotListener(NotificacoesActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                notificacoesDesafioApp.clear();

                int total = documentSnapshots.size();
                if (documentSnapshots != null) {
                    for (DocumentSnapshot documentSnapshot: documentSnapshots) {
                        NotificacaoDesafioApps notificacao = documentSnapshot.toObject(NotificacaoDesafioApps.class);
                        notificacao.setId(documentSnapshot.getId());

                        for (DesafioApp desafio : desafioAppArrayList) {

                            if (notificacao.getDesafio().getId().equals(desafio.getId())) {
                                notificacao.setDesafioObject(desafio);
                                break;
                            }

                        }
                        notificacoesDesafioApp.add(notificacao);
                    }
                }
                notificacaoDesafioAdapter = new NotificacaoDesafioAppAdapter(notificacoesDesafioApp, NotificacoesActivity.this);
                recyclerViewNotificacoesApp.setAdapter(notificacaoDesafioAdapter);
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

    private void acharDesafiosApp() {
        Task<QuerySnapshot> query = ConfiguracaoFirebase.getFirestore().collection("DesafioApp").get();
        query.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        DesafioApp desafio = documentSnapshot.toObject(DesafioApp.class);
                        desafio.setId(documentSnapshot.getId());
                        desafioAppArrayList.add(desafio);
                    }
                }
                carregarNotificacoesDesafioApp();
            }
        });
    }
}