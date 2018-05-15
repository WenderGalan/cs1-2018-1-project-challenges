package challenges.com.challenges.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notificacao implements Serializable {

    private DocumentReference crianca;
    private DocumentReference desafio;
    private String responsavel;
    private String id;
    public Desafio desafioObject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Notificacao() {

    }

    public DocumentReference getCrianca() {
        return crianca;
    }

    public void setCrianca(DocumentReference crianca) {
        this.crianca = crianca;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public DocumentReference getDesafio() {
        return desafio;
    }

    public void setDesafio(DocumentReference desafio) {
        this.desafio = desafio;
    }

    public Map construirHash() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crianca",getCrianca());
        hashMap.put("desafio", getDesafio());
        hashMap.put("responsavel",getResponsavel());
        return hashMap;
    }

}