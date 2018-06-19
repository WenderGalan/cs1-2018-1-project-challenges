package challenges.com.challenges.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import challenges.com.challenges.R;

public class ViewHolderNotificacao extends RecyclerView.ViewHolder {

    public TextView titulo;
    public ImageView imagem;
    public Button aceitar;
    public Button recusar;

    public ViewHolderNotificacao(View itemView) {
        super(itemView);
        imagem = itemView.findViewById(R.id.imagem_notificacao);
        titulo = itemView.findViewById(R.id.titulo_notificacao);
        aceitar = itemView.findViewById(R.id.button_confirmar);
        recusar = itemView.findViewById(R.id.button_recusar_item);
    }
}
