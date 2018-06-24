package challenges.com.challenges.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import challenges.com.challenges.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderSolicitacao extends RecyclerView.ViewHolder{

    public TextView titulo;
    public CircleImageView imagem;
    public Button aceitar;

    public ViewHolderSolicitacao(View view) {
        super(view);
        imagem = itemView.findViewById(R.id.circleImageViewCrianca2);
        titulo = itemView.findViewById(R.id.title_notificacao);
        aceitar = itemView.findViewById(R.id.button_solicitar);

    }
}
