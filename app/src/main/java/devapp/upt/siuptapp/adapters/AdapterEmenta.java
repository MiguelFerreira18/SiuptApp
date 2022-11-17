package devapp.upt.siuptapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import devapp.upt.siuptapp.Ementa;
import devapp.upt.siuptapp.EmentaModel;
import devapp.upt.siuptapp.R;

public class AdapterEmenta extends RecyclerView.Adapter<AdapterEmenta.MyEmentaHolder> {
    Context ct;
    ArrayList<EmentaModel> ementas;

    public AdapterEmenta(Context ct, ArrayList<EmentaModel> ementas) {
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
        switch (position) {
            case 0:
                Glide.with(ct).load("https://img.myloview.com.br/posters/bowl-of-soup-vector-illustration-isolated-on-green-background-linear-color-style-of-soup-icon-400-245969672.jpg").into(holder.im);
                break;
            case 1:
                Glide.with(ct).load("https://cdn.xxl.thumbs.canstockphoto.com/chicken-leg-sign-white-icon-on-red-background-illustration_csp37424323.jpg").into(holder.im);
                break;
            case 2:
                Glide.with(ct).load("https://thumbs.dreamstime.com/b/silver-fish-icon-isolated-blue-background-vector-192250320.jpg").into(holder.im);
                break;
            case 3:
                Glide.with(ct).load("https://img.freepik.com/premium-vector/cupcake-icon-pink-background_24911-14091.jpg").into(holder.im);
                break;
            default:
                break;
        }
        holder.tv.setText(ementas.get(position).getPrato());


    }

    @Override
    public int getItemCount() {
        return ementas.size();
    }

    class MyEmentaHolder extends RecyclerView.ViewHolder {
        ImageView im;
        TextView tv;

        public MyEmentaHolder(@NonNull View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.iconImage);
            tv = itemView.findViewById(R.id.prato);
        }
    }

}
