package challenges.com.challenges.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.HomeResponsavelActivity;
import challenges.com.challenges.activities.NotificacoesActivity;
import challenges.com.challenges.adapter.DesafioAdapter;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Desafio;

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
        recyclerViewDesafioCrianca.setLayoutManager(layoutManager);
//        recyclerViewSugestoesDesafioCrianca.setLayoutManager(layoutManager);

        Query query = ConfiguracaoFirebase.getFirestore().collection("Desafios").whereEqualTo("crianca", idUsuarioAtual);
        query.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                List<Desafio> desafiosResult = documentSnapshots.toObjects(Desafio.class);
                desafioAdapter = new DesafioAdapter((ArrayList<Desafio>) desafiosResult, "crianca");
                recyclerViewDesafioCrianca.setAdapter(desafioAdapter);
            }
        });

        notificacaoCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificacoesActivity.class);
                startActivity(intent);
            }
        });

//        sair.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //desloga o usuario e volta para a tela incial de Login
//                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
//                autenticacao.signOut();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });

        return view;
    }

}
