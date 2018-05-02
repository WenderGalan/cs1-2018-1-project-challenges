package challenges.com.challenges.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import challenges.com.challenges.R;

public class ViewHolderDesafio extends RecyclerView.ViewHolder {

    public TextView titulo;
    public ImageView imagem;

    public ViewHolderDesafio(View itemView) {
        super(itemView);
        imagem = itemView.findViewById(R.id.imageViewDesafio);
        titulo = itemView.findViewById(R.id.textViewDesafio);
    }
}
