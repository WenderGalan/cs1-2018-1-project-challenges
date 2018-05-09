package challenges.com.challenges.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<String, Object>();

        //seta o tipo do usuario
        hashMap.put("tipo", getTipo());
        if (getNome() != null) hashMap.put("nome", getNome());
        if (getEmail() != null) hashMap.put("email", getEmail());
        if (getCriancas() != null) hashMap.put("criancas", getCriancas());

        return hashMap;
    }

}
