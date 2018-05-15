package challenges.com.challenges.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Crianca extends Usuario implements Serializable {

    private CollectionReference amigos;
    private DocumentReference responsavel;
    private int tipo = 1;
    private int pontos;
    private String foto;
    private int recompensa = 0;

    public Crianca() {
    }

    public int getTipo() {
        return tipo;
    }

    public CollectionReference getAmigos() {
        return amigos;
    }

    public void setAmigos(CollectionReference amigos) {
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

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public Map construirHash() {
        Map<String, Object> hashMap = new HashMap<String, Object>();

        //seta o tipo do usuario
        hashMap.put("tipo", getTipo());
        if (getNome() != null) hashMap.put("nome", getNome());
        if (getEmail() != null) hashMap.put("email", getEmail());
        if (getFoto() != null) hashMap.put("foto", getFoto());
        if (getPontos() != 0) hashMap.put("pontos", getPontos());
        if (getResponsavel() != null) hashMap.put("responsavel", getResponsavel());
        if (getAmigos() != null) hashMap.put("amigos", getAmigos());
        if (getRecompensa() != 0) hashMap.put("recompensas", getRecompensa());

        return hashMap;
    }

}
