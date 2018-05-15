package challenges.com.challenges.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.Notificacao;

public class NotificacoesActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerViewNotificacoes;
    private ArrayList<Notificacao> notificacoesDesafio = new ArrayList<>();
    private FirebaseAuth autenticacao;
    private NotificacaoAdapter notificacaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);
        recyclerViewNotificacoes = findViewById(R.id.notificacao_desafio);

        LinearLayoutManager linearLayout = new LinearLayoutManager(NotificacoesActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewNotificacoes.setLayoutManager(linearLayout);

        carregarNotificacoes();

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
        query.get().addOnSuccessListener(NotificacoesActivity.this, new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for(final DocumentSnapshot document : documentSnapshots){
                    final Notificacao notificacao = document.toObject(Notificacao.class);
                    if (notificacao != null){
                        notificacao.setId(document.getId());
                        DocumentReference reference = notificacao.getDesafio();
                        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                if (documentSnapshot.exists()){
                                    Desafio desafio = documentSnapshot.toObject(Desafio.class);
                                    desafio.setId(documentSnapshot.getId());
                                    notificacao.desafioObject = desafio;
                                    notificacoesDesafio.add(notificacao);
                                }else {
                                    notificacoesDesafio.add(notificacao);
                                }
                            }
                        });
                    }

                }
                notificacaoAdapter = new NotificacaoAdapter(notificacoesDesafio);
                recyclerViewNotificacoes.setAdapter(notificacaoAdapter);
            }
        });
    }
}