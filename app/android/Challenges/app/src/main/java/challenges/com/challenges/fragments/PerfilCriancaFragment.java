package challenges.com.challenges.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import challenges.com.challenges.R;
import challenges.com.challenges.activities.AdicionarAmigoActivity;
import challenges.com.challenges.activities.LoginActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilCriancaFragment extends Fragment {

    private CircleImageView fotoCrianca;
    private FirebaseAuth autenticacao;
    private Crianca crianca;
    private ImageView adicionarAmigo;
    private TextView sair;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_perfil_crianca, container, false);
        fotoCrianca = view.findViewById(R.id.foto_perfil);
        nomeCrianca = view.findViewById(R.id.NomeCrianca);
        inteligenciaPrincipalCrianca = view.findViewById(R.id.habilidade_principal);
        qntDesafios = view.findViewById(R.id.qnt_desafios);
        qntPontos = view.findViewById(R.id.qnt_pontos);
        qntAmigos = view.findViewById(R.id.qnt_amigos);
        qntRecompensas = view.findViewById(R.id.qnt_recompensas);
        adicionarAmigo = view.findViewById(R.id.adc_amigo);
        sair = view.findViewById(R.id.button_sair);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuarioAtual = autenticacao.getCurrentUser().getUid();

        //coloquei um listener para ficar escutando as alteraçoes do banco porque é usuário...
        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual)
                .addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    crianca = documentSnapshot.toObject(Crianca.class);
                    nomeCrianca.setText(crianca.getNome().toUpperCase());
                    if (crianca.getFoto() != null){
                        Picasso.get().load(crianca.getFoto()).into(fotoCrianca);
                    }

                    if (crianca.getRecompensa() > 0) {
                        qntRecompensas.setText(String.valueOf(crianca.getRecompensa()));
                    }

                    if (crianca.getPontos() > 0) {
                        qntPontos.setText(String.valueOf(crianca.getPontos()));
                    }

                    if (crianca.getDesafios() > 0) {
                        qntDesafios.setText(String.valueOf(crianca.getDesafios()));
                    }
                }
            }
        });
        adicionarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vie) {
                Intent intent = new Intent(getActivity(), AdicionarAmigoActivity.class);
                startActivity(intent);
            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //desloga o usuario e volta para a tela incial de Login
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}
