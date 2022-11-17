package devapp.upt.siuptapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import devapp.upt.siuptapp.Ementa;
import devapp.upt.siuptapp.R;

public class AdapterEmenta extends RecyclerView.Adapter<AdapterEmenta.MyEmentaHolder> {
    Context ct;
    ArrayList<Ementa> ementas;

    public AdapterEmenta(Context ct, ArrayList<Ementa> ementas) {
        this.ct = ct;
        this.ementas = ementas;
    }

    @NonNull
    @Override
    public MyEmentaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View v = inflater.inflate(R.layout.card_ementa, parent, false);
        return new MyEmentaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEmentaHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ementas.size();
    }

    class MyEmentaHolder extends RecyclerView.ViewHolder {


        public MyEmentaHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
