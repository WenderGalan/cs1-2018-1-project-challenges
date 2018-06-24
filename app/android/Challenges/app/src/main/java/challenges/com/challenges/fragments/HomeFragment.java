package challenges.com.challenges.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.NotificacoesActivity;
import challenges.com.challenges.adapter.DesafioAdapter;
import challenges.com.challenges.adapter.DesafioAppAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Desafio;
import challenges.com.challenges.model.DesafioApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewDesafioCrianca;
    private RecyclerView recyclerViewSugestoesDesafioCrianca;
    private ImageView notificacaoCrianca;

    private FirebaseAuth autenticacao;
    private Crianca crianca;
    private DesafioAdapter desafioAdapter;
    private List<Desafio> desafiosResult = new ArrayList<>();
    private List<DesafioApp> desafiosAppResult = new ArrayList<>();
    private DesafioAppAdapter desafioAppAdapter;

    private String UID;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);


        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuarioAtual = autenticacao.getCurrentUser().getUid();


        recyclerViewDesafioCrianca = view.findViewById(R.id.recyclerViewDesafios);
        recyclerViewSugestoesDesafioCrianca = view.findViewById(R.id.recyclerViewSugestoesDesafios);
        notificacaoCrianca = view.findViewById(R.id.notificacaoCrianca);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 2);
        recyclerViewDesafioCrianca.setLayoutManager(layoutManager);
        recyclerViewSugestoesDesafioCrianca.setLayoutManager(layoutManager2);

        Query query = ConfiguracaoFirebase.getFirestore().collection("Desafios").whereEqualTo("crianca", idUsuarioAtual);
        query.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                desafiosResult.clear();
                for (DocumentSnapshot document : documentSnapshots){
                    Desafio desafio = document.toObject(Desafio.class);
                    if (desafio.isCompletado() == false){
                        desafio.setId(document.getId());
                        desafiosResult.add(desafio);
                    }
                }
                desafioAdapter = new DesafioAdapter((ArrayList<Desafio>) desafiosResult, "crianca");
                recyclerViewDesafioCrianca.setAdapter(desafioAdapter);
            }
        });

        Task<QuerySnapshot> query1 = ConfiguracaoFirebase.getFirestore().collection("DesafioApp").get();
        query1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        final DesafioApp desafio = documentSnapshot.toObject(DesafioApp.class);
                        desafio.setId(documentSnapshot.getId());
                        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                        String idUsuarioAtual = autenticacao.getCurrentUser().getUid();

                        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual)
                                .addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {

                                            @Override
                                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                                                if (documentSnapshot.exists()) {
                                                    Crianca criancaAux = documentSnapshot.toObject(Crianca.class);
                                                    desafio.setResponsavel(criancaAux.getResponsavel().getId());
                                                }
                                            }
                                        });

                        desafiosAppResult.add(desafio);
                    }
                    desafioAppAdapter = new DesafioAppAdapter((ArrayList<DesafioApp>) desafiosAppResult);
                    recyclerViewSugestoesDesafioCrianca.setAdapter(desafioAppAdapter);
                }
            }
        });
        notificacaoCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificacoesActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
