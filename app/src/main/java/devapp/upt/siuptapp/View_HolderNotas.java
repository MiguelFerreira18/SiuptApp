package devapp.upt.siuptapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_HolderNotas extends RecyclerView.ViewHolder {
    public TextView textViewUC;
    public TextView textViewNota;

    public View_HolderNotas(@NonNull View itemView) {
        super(itemView);
        textViewUC = itemView.findViewById(R.id.textViewUC);
        textViewNota = itemView.findViewById(R.id.textViewNota);
    }
}
