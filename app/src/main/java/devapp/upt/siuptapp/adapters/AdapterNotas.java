package devapp.upt.siuptapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import devapp.upt.siuptapp.R;
import devapp.upt.siuptapp.UcNotas;
import devapp.upt.siuptapp.View_HolderNotas;

public class AdapterNotas extends RecyclerView.Adapter<View_HolderNotas>{

    ArrayList<UcNotas> notas;
    View_HolderNotas myViewHolder;


    public AdapterNotas(ArrayList<UcNotas> listaNotas) {
        notas = listaNotas;
    }

    @NonNull
    @Override
    public View_HolderNotas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_notas, parent, false);

        myViewHolder = new View_HolderNotas(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull View_HolderNotas holder, int position) {
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

