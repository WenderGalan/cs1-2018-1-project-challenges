package challenges.com.challenges.model;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

import challenges.com.challenges.config.ConfiguracaoFirebase;

/**
 * Created by Wender Galan Gamer on 25/03/2018.
 */

public class Usuario implements Serializable{

    private String id;
    private String nome;
    private String email;
    private String senha;
    private int tipo;

    public Usuario() {
    }

    @com.google.firebase.firestore.Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @com.google.firebase.firestore.Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo;
    }
}
