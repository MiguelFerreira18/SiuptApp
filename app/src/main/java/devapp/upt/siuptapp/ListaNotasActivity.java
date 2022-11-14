package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaNotasActivity extends AppCompatActivity {

    Db_handler dbHandler;
    RequestQueue queue;
    Intent i;
    String token;
    ArrayList<UcNotas> ListaNotas;
    ArrayList<Nota> Notas;
    ArrayList<Uc> ListaUcs;
    My_Adapter myadapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_notas);

        i = getIntent();
        token = i.getStringExtra(Menu.tokenS);
        dbHandler = new Db_handler(this);

        if (isConnected()){
            getNotas();
            if (ListaNotas.size() > 0) {
                myadapter = new My_Adapter(ListaNotas);
            }
        }
        else{
            getNotasBd();
            if (ListaNotas.size() > 0) {
                myadapter = new My_Adapter(ListaNotas);
            }
        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(myadapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void getNotas(){
        queue = Volley.newRequestQueue(ListaNotasActivity.this);
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=classificacao&token=" + token;
        JsonObjectRequest jObeject = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ucs = response.getJSONArray("classificacao");
                    for (int i = 0; i < ucs.length(); i++) {
                        JSONObject ucObject = ucs.getJSONObject(i);
                        getUc(ucObject.getInt("uc"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                try {
                                    int ucNota = ucObject.getInt("nota");
                                    ListaNotas.add(new UcNotas(uc, ucNota));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaNotasActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jObeject);
    }


    public void getNotasBd(){
        Notas = dbHandler.getNotas(token);
        for (int i = 0; i < Notas.size(); i++){
            ListaUcs.add(dbHandler.getUc(Notas.get(i).getCodUC()));
        }
        for (int i = 0; i < Notas.size(); i++){
            ListaNotas.add(new UcNotas(ListaUcs.get(i).getNome(), Notas.get(i).getNota()));
        }
    }

    public void getUc(int uc, final ICallBack callback) {
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc&codigo=" + uc;
        Log.d("cenas", "getUc: " + myUrl);
        //Background work here
        StringRequest sr = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ListaNotasActivity.this, "done", Toast.LENGTH_SHORT).show();
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaNotasActivity.this, "Erro" + error, Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(sr);
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    return true;
                }
            }
        }
        return false;
    }
}