package challenges.com.challenges.model;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import challenges.com.challenges.activities.CadastroActivity;
import challenges.com.challenges.config.ConfiguracaoFirebase;

/**
 * Created by Wender Galan Gamer on 25/03/2018.
 */

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private DocumentReference responsavel;
    private DocumentReference habilidade;
    private int pontos;
    private int tipo; // 1 => é criança  e 0 => é responsável
    private DocumentReference amigos[];
    private DocumentReference criancas[];
    private String senha;
    private String foto;

    public Usuario() {
    }


    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    @com.google.firebase.firestore.Exclude
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @com.google.firebase.firestore.Exclude
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
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
    public int getPontos() {
        return pontos;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public DocumentReference getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(DocumentReference responsavel) {
        this.responsavel = responsavel;
    }
    public DocumentReference getHabilidade() {
        return habilidade;
    }
    public void setHabilidade(DocumentReference habilidade) {
        this.habilidade = habilidade;
    }
    public DocumentReference[] getAmigos() {
        return amigos;
    }
    public void setAmigos(DocumentReference[] amigos) {
        this.amigos = amigos;
    }
    public DocumentReference[] getCriancas() {
        return criancas;
    }
    public void setCriancas(DocumentReference[] criancas) {
        this.criancas = criancas;
    }

    public void salvar(){
        /*CollectionReference referencia = ConfiguracaoFirebase.getFirestore().collection("Usuarios");
        referencia.document(getId()).set(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("DEBUG", "SALVO COM SUCESSO");
            }
        });*/
    }

}
