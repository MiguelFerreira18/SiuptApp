package devapp.upt.siuptapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_Holder extends RecyclerView.ViewHolder {
    TextView textViewUC;
    TextView textViewNota;

    public View_Holder(@NonNull View itemView) {
        super(itemView);
        textViewUC = itemView.findViewById(R.id.textViewUC);
        textViewNota = itemView.findViewById(R.id.textViewNota);
    }
}
