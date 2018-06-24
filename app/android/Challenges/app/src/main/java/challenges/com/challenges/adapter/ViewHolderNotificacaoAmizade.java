package challenges.com.challenges.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import challenges.com.challenges.R;

public class ViewHolderNotificacaoAmizade extends RecyclerView.ViewHolder {

    public TextView nome;
    public Button confirmar;
    public Button recusar;
    public ImageView imagem;

    public ViewHolderNotificacaoAmizade(View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.titulo_notificacao);
        confirmar = itemView.findViewById(R.id.button_confirmar);
        recusar = itemView.findViewById(R.id.button_recusar_item);
        imagem = itemView.findViewById(R.id.imagem_notificacao);
    }

}
