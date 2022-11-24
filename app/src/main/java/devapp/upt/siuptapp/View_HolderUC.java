package devapp.upt.siuptapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class View_HolderUC extends RecyclerView.ViewHolder{
    public TextView textViewUC;

    public View_HolderUC (@NonNull View itemView){
        super(itemView);
        textViewUC = itemView.findViewById(R.id.textViewUCs);
    }

}
