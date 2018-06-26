package challenges.com.challenges.model;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Responsavel extends Usuario implements Serializable{

    private ArrayList<DocumentReference> criancas;
    private int tipo = 0; //tipo responsavel
    private boolean permiteSocial = true;

    public Responsavel() {
    }

    public boolean isPermiteSocial() {
        return permiteSocial;
    }

    public void setPermiteSocial(boolean permiteSocial) {
        this.permiteSocial = permiteSocial;
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
        hashMap.put("tipo", getTipo());
        if (getNome() != null) hashMap.put("nome", getNome());
        if (getEmail() != null) hashMap.put("email", getEmail());
        if (getCriancas() != null) hashMap.put("criancas", getCriancas());
        if (isPermiteSocial()) hashMap.put("permiteSocial", isPermiteSocial());
        return hashMap;
    }

}
