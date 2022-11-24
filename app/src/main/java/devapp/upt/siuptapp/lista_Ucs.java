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

import devapp.upt.siuptapp.adapters.AdapterUCs;

public class lista_Ucs extends AppCompatActivity {

    Db_handler dbHandler;
    RequestQueue queue;
    Intent i;
    String token;
    AdapterUCs myadapter;
    RecyclerView recyclerViewUCs;
    ArrayList<String> ListaUCs;
    ArrayList<Uc> ListaUCsBD;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ucs);
        ListaUCs = new ArrayList<>();

        i = getIntent();
        token = i.getStringExtra(Menu.tokenS);
        dbHandler = new Db_handler(this);

        if (isConnected()) {
            getUCs();
        } else {
            getUCsBD();
        }

        myadapter = new AdapterUCs(ListaUCs);
        recyclerViewUCs = findViewById(R.id.recyclerViewUCs);
        recyclerViewUCs.setAdapter(myadapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerViewUCs.setLayoutManager(layoutManager);
    }

    public void getUCs() {
        queue = Volley.newRequestQueue(lista_Ucs.this);
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=horario&token=" + token;
        JsonObjectRequest jObeject = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ucs = response.getJSONArray("horario");
                    for (int i = 0; i < ucs.length(); i++) {
                        JSONObject ucObject = ucs.getJSONObject(i);
                        getUc(ucObject.getInt("codigoUC"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                if (ListaUCs.contains(uc)) {
                                } else {
                                    ListaUCs.add(uc);
                                }
                                myadapter.notifyDataSetChanged();
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
                Toast.makeText(lista_Ucs.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jObeject);
    }

    public void getUCsBD() {
        ListaUCsBD = (dbHandler.getUcs(token));
        for (int i = 0; i < ListaUCsBD.size(); i++) {
            ListaUCs.add(ListaUCsBD.get(i).getNome());
        }
    }

    public void getUc(int uc, final ICallBack callback) {
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc&codigo=" + uc;
        Log.d("cenas", "getUc: " + myUrl);
        //Background work here
        StringRequest sr = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(lista_Ucs.this, "done", Toast.LENGTH_SHORT).show();
                callback.onSuccess(response.replace("\n", ""));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(lista_Ucs.this, "Erro" + error, Toast.LENGTH_SHORT).show();

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