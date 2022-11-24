package devapp.upt.siuptapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import devapp.upt.siuptapp.R;
import devapp.upt.siuptapp.View_HolderUC;

public class AdapterUCs extends RecyclerView.Adapter<View_HolderUC>{
    ArrayList<String> ucs;
    View_HolderUC myViewHolder;

    public AdapterUCs(ArrayList<String> ListaUCs) {
        ucs = ListaUCs;
    }

    @NonNull
    @Override
    public View_HolderUC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_ucs, parent, false);

        myViewHolder = new View_HolderUC(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull View_HolderUC holder, int position) {
        myViewHolder.textViewUC.setText(ucs.get(position));
    }

    @Override
    public int getItemCount() {
        return ucs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


