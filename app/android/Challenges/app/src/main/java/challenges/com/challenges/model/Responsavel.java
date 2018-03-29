package challenges.com.challenges.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class Responsavel extends Usuario implements Serializable{

    private ArrayList<DocumentReference> criancas;
    private int tipo = 0; //tipo responsavel

    public Responsavel() {
    }

    public int getTipo() {
        return tipo;
    }

    public ArrayList<DocumentReference> getCriancas() {
        return criancas;
    }

    public void setCriancas(ArrayList<DocumentReference> criancas) {
        this.criancas = criancas;
    }

    public void salvar(){
        DocumentReference responsavel = FirebaseFirestore.getInstance().collection("usuarios").document(getId());
        responsavel.set(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i("DEBUG", "Responsável salvo com sucesso!");
                }
            }
        });
    }
}
