package challenges.com.challenges.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilCriancaFragment extends Fragment {

    private CircleImageView fotoCrianca;
    private FirebaseAuth autenticacao;
    private Crianca crianca;

    private TextView nomeCrianca;
    private TextView inteligenciaPrincipalCrianca;
    private TextView qntDesafios;
    private TextView qntPontos;
    private TextView qntAmigos;
    private TextView qntRecompensas;

    public PerfilCriancaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_perfil_crianca, container, false);
        fotoCrianca = view.findViewById(R.id.foto_perfil);
        nomeCrianca = view.findViewById(R.id.nome_crianca);
        inteligenciaPrincipalCrianca = view.findViewById(R.id.habilidade_principal);
        qntDesafios = view.findViewById(R.id.qnt_desafios);
        qntPontos = view.findViewById(R.id.qnt_pontos);
        qntAmigos = view.findViewById(R.id.qnt_amigos);
        qntRecompensas = view.findViewById(R.id.qnt_recompensas);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuarioAtual = autenticacao.getCurrentUser().getUid();

        try {
            ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        crianca = documentSnapshot.toObject(Crianca.class);
                        nomeCrianca.setText(crianca.getNome());
                        System.out.println(crianca.getFoto());
                        Picasso.get().load(crianca.getFoto()).into(fotoCrianca);

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("caiu excecao");
        }

        return view;
    }
}
