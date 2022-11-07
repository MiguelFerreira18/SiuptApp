package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class ListaNotasActivity extends AppCompatActivity {

    Db_handler dbHandler;
    MainActivity login;
    RequestQueue queue;
    Intent i;
    String token;
    ArrayList<Nota> ListaNotas;
    ArrayList<Uc> ListaUcs;
    My_Adapter myadapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_notas);

        i = getIntent();
        token = i.getStringExtra(Menu.tokenS);

        ListaNotas = dbHandler.getNotas(token);
        ListaUcs = dbHandler.getUcs(token);

        myadapter = new My_Adapter(ListaNotas, ListaUcs, token);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(myadapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * adiciona as ucs na recycle view
     */
    public void addUcs(View_Holder myViewHolder) {
        //adiciona as ucs into db
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc_inscrito&token=" + token;
        queue = Volley.newRequestQueue(ListaNotasActivity.this);
        JsonObjectRequest jObeject = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ucs = response.getJSONArray("inscrito");
                    for (int i = 0; i < ucs.length(); i++) {
                        JSONObject ucObject = ucs.getJSONObject(i);
                        login.getUc(ucObject.getInt("uc"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                try {
                                    int ucCod = ucObject.getInt("uc");
                                    myViewHolder.textViewUC.setText(uc);

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


    /**
     * adiciona as notas na recycle view
     */
    public void addNotas(View_Holder myViewHolder) {
        queue = Volley.newRequestQueue(ListaNotasActivity.this);

        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=classificacao&token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("classificacao");
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject jo = myArray.getJSONObject(i);
                        myViewHolder.textViewNota.setText(jo.getInt("nota"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaNotasActivity.this, "Error in notas", Toast.LENGTH_SHORT).show();
                Log.d("adicionadas||||||||||||", "onResponse:not added Notas ");
            }
        });
        queue.add(jsonObjectRequest);
    }

}