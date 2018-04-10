package challenges.com.challenges.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import challenges.com.challenges.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderCrianca extends RecyclerView.ViewHolder {

    public TextView nome;
    public CircleImageView imagem;

    public ViewHolderCrianca(View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.textViewNomeCrianca);
        imagem = itemView.findViewById(R.id.circleImageViewCrianca);
    }
}
