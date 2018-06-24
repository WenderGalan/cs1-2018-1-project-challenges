package challenges.com.challenges.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matheus on 20/06/18.
 */

public class NotificacaoAmizade {

    private DocumentReference criancaRemetente;
    private DocumentReference criancaDestinatario;
    private DocumentReference responsavel;

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("criancaRemetente", getCriancaRemetente());
        hashMap.put("criancaDestinatario", getCriancaDestinatario());
        hashMap.put("responsavel", getResponsavel());

        return hashMap;
    }

    public DocumentReference getCriancaRemetente() {
        return criancaRemetente;
    }

    public void setCriancaRemetente(DocumentReference criancaRemetente) {
        this.criancaRemetente = criancaRemetente;
    }

    public DocumentReference getCriancaDestinatario() {
        return criancaDestinatario;
    }

    public void setCriancaDestinatario(DocumentReference criancaDestinatario) {
        this.criancaDestinatario = criancaDestinatario;
    }

    public DocumentReference getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(DocumentReference responsavel) {
        this.responsavel = responsavel;
    }
}
