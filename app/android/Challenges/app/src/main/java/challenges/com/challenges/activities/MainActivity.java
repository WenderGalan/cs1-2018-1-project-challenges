package challenges.com.challenges.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Crianca;
import challenges.com.challenges.model.Responsavel;
import challenges.com.challenges.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sair = findViewById(R.id.buttonSair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //desloga o usuario e volta para a tela incial de Login
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        /*//busca pelo ID e coloca um listener nesse nó, tudo que mudar ele avisa aqui
        ConfiguracaoFirebase.getFirestore().collection("usuarios").document("AvIdYPSvPJalkM4RUrRiXIwzfqR2").addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                Responsavel responsavel = documentSnapshot.toObject(Responsavel.class);
                Log.i("DEBUG", "Nome: " + responsavel.getNome());
                Log.i("DEBUG", "Email: " + responsavel.getEmail());
                Log.i("DEBUG", "Tipo: " + responsavel.getTipo());
                Log.i("DEBUG", "Criança: " + responsavel.getCriancas().get(0));
            }
        });

        //busca pelo ID e apenas recebe um objeto, aqui nao existe listener
        ConfiguracaoFirebase.getFirestore().collection("usuarios").document("7dZjCJPgnTPUo0cuQ7odSN0ElME2").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Crianca crianca = documentSnapshot.toObject(Crianca.class);
                Log.i("DEBUG", "Nome: " + crianca.getNome());
                Log.i("DEBUG", "Email: " + crianca.getEmail());
                Log.i("DEBUG", "Foto: " + crianca.getFoto());
                Log.i("DEBUG", "Tipo: " + crianca.getTipo());
                Log.i("DEBUG", "Responsável: " + crianca.getResponsavel());
                Log.i("DEBUG", "Pontos: " + crianca.getPontos());
                Log.i("DEBUG", "Habilidade: " + crianca.getHabilidade());

            }
        });*/

        /*CollectionReference ref = ConfiguracaoFirebase.getFirestore().collection("usuarios");
        Query query = ref.whereEqualTo("tipo", 0);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                List<Responsavel> responsavel = documentSnapshots.toObjects(Responsavel.class);
                Log.i("DEBUG", "Nome: " + responsavel.get(0).getNome());
                Log.i("DEBUG", "Email: " + responsavel.get(0).getEmail());

                Log.i("DEBUG", "Nome: " + responsavel.get(1).getNome());
                Log.i("DEBUG", "Email: " + responsavel.get(1).getEmail());
            }
        });*/



    }
}
