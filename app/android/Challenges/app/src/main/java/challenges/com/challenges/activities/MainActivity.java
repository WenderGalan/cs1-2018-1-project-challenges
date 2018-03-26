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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
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


       /* Usuario usuario = new Usuario();

        usuario.setNome("Wender");
        usuario.setSenha("a5t7l4h6");
        usuario.setEmail("wendergalan2014@hotmail.com");
        usuario.setTipo(0);
        usuario.setId("fsdfsdfsdfsdfsdf3123s32fd312123231");
        usuario.salvar();*/


            /*CollectionReference db = FirebaseFirestore.getInstance().collection("usuarios");
            Usuario usuario = new Usuario();
            usuario.setEmail("wendergalan@2014.com");
            DocumentReference responsavel = FirebaseFirestore.getInstance().collection("usuarios").document("wender");
            usuario.setResponsavel(responsavel);
            db.document("wender").set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i("DEBUG", "Deu certo!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("DEBUG", "Erro: " + e.getMessage());
                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("DEBUG", "Erro: " + task.getException());
                }
            });*/

    }
}
