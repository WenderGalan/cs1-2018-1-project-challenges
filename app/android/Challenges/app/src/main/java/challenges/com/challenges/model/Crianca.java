package challenges.com.challenges.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class Crianca extends Usuario implements Serializable{

    private DocumentReference habilidade;
    private ArrayList<DocumentReference> amigos;
    private DocumentReference responsavel;
    private int tipo = 1;
    private int pontos;
    private String foto;

    public Crianca() {
    }

    public int getTipo() {
        return tipo;
    }

    public DocumentReference getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(DocumentReference habilidade) {
        this.habilidade = habilidade;
    }

    public ArrayList<DocumentReference> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<DocumentReference> amigos) {
        this.amigos = amigos;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public DocumentReference getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(DocumentReference responsavel) {
        this.responsavel = responsavel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void salvar(){
        DocumentReference responsavel = FirebaseFirestore.getInstance().collection("usuarios").document(getId());
        responsavel.set(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i("DEBUG", "Criança salva com sucesso!");
                }
            }
        });
    }


}
