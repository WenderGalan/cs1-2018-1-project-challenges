package challenges.com.challenges.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;

import challenges.com.challenges.activities.LoginActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;

import challenges.com.challenges.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Button sair;
    private Button detalheDesafio;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        sair = view.findViewById(R.id.button2);
        detalheDesafio = view.findViewById(R.id.button5);

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

//        detalheDesafio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), DetalheDesafioActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

}
