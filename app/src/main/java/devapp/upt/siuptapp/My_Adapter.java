package devapp.upt.siuptapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My_Adapter extends RecyclerView.Adapter<View_Holder>{

    ArrayList<UcNotas> notas;
    View_Holder myViewHolder;


    public My_Adapter(ArrayList<UcNotas> listaNotas) {
        notas = listaNotas;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_notas, parent, false);

        myViewHolder = new View_Holder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        myViewHolder.textViewUC.setText(notas.get(position).getUC());
        myViewHolder.textViewNota.setText(notas.get(position).getNota());
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }


}

