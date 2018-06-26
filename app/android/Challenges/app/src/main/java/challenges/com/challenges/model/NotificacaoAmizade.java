package challenges.com.challenges.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matheus on 20/06/18.
 */

public class NotificacaoAmizade {

    private String id;
    private DocumentReference amigo;
    private DocumentReference crianca;

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crianca", getCrianca());
        hashMap.put("amigo", getAmigo());

        return hashMap;
    }

    @com.google.firebase.firestore.Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentReference getAmigo() {
        return amigo;
    }

    public void setAmigo(DocumentReference amigo) {
        this.amigo = amigo;
    }

    public DocumentReference getCrianca() {
        return crianca;
    }

    public void setCrianca(DocumentReference crianca) {
        this.crianca = crianca;
    }
}
