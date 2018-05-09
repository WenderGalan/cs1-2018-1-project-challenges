package challenges.com.challenges.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import challenges.com.challenges.R;
import challenges.com.challenges.config.ConfiguracaoFirebase;
import challenges.com.challenges.model.Usuario;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       /* //INICIALIZA UMA THREAD PARA DEIXAR O SPLASH POR 1 SEGUNDO NA TELA DO USUARIO
        Thread timerThread = new Thread() {

            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();*/
        verificarUsuarioLogado();
    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            destinarUsuario();
        }else{
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void destinarUsuario(){
        String idUsuarioAtual = autenticacao.getCurrentUser().getUid();
        ConfiguracaoFirebase.getFirestore().collection("Usuarios").document(idUsuarioAtual).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                    if (usuario.getTipo() == 0){
                        //responsavel
                        abrirHomeResponsavel();
                    }else if (usuario.getTipo() == 1){
                        //crianca
                        abrirHomeCrianca();
                    }
                }else{
                    Log.i("DEBUG", "Usuário não encontrado!");
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void abrirHomeResponsavel() {
        Intent intent = new Intent(SplashScreenActivity.this, HomeResponsavelActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirHomeCrianca() {
        Intent intent = new Intent(SplashScreenActivity.this, HomeCriancaActivity.class);
        startActivity(intent);
        finish();
    }

}

