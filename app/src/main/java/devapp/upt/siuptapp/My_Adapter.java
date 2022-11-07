package devapp.upt.siuptapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class My_Adapter extends RecyclerView.Adapter<View_Holder>{

    ListaNotasActivity activity;
    ArrayList<Nota> notas;
    ArrayList<Uc> UC;
    String token_aluno;
    View_Holder myViewHolder;
    MainActivity mainActivity;
    boolean connected = false;

    public My_Adapter(ArrayList<Nota> listaNotas, ArrayList<Uc> ListaUcs, String token) {
        notas = listaNotas;
        UC = ListaUcs;
        token_aluno = token;

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
        if (mainActivity.isConnected()) {
            activity.addUcs(myViewHolder);
            activity.addNotas(myViewHolder);
        }
        else {
            myViewHolder.textViewUC.setText(UC.get(position).getNome());
            myViewHolder.textViewNota.setText(notas.get(position).getNota());
        }
    }



    @Override
    public int getItemCount() {
        return notas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

