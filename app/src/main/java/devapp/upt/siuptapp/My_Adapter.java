package devapp.upt.siuptapp;

import android.util.Log;
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
        Log.d("cenas notas", "onBindViewHolder: "+ myViewHolder.textViewUC);
        Log.d("cenas notas2", "onBindViewHolder: " + holder.textViewUC);
        Log.d("cenas notas3", "onBindViewHolder: "+ notas.get(position).getUC());
        myViewHolder.textViewUC.setText(notas.get(position).getUC());
        Log.d("cenas notas4", "onBindViewHolder: "+ myViewHolder.textViewNota);
        Log.d("cenas notas5", "onBindViewHolder: " + holder.textViewNota);
        Log.d("cenas notas6", "onBindViewHolder: "+ notas.get(position).getNota());
        myViewHolder.textViewNota.setText(String.valueOf( notas.get(position).getNota()));
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

