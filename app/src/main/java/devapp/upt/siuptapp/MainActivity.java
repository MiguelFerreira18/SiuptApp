package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    String token, myUc;
    Db_handler db;

    TextView numero;
    TextView nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero = findViewById(R.id.editTextTextPersonName);
        nome = findViewById(R.id.editTextTextPersonName2);
    }

    public void login(View v)
    {

    }







    //**********************************************************************************************************************
    //**********************************************************************************************************************
    //**************************************************ADICIONA TUDO NA BASE DE DADOS**************************************
    //**********************************************************************************************************************
    //**********************************************************************************************************************


    /**
     * verifica se existe uma conexão com a internet/wifi
     * @return
     */
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

    /**
     * adiciona valores na base de dados dado os dados de um aluno
     * só acontece uma vez por aluno novo
     * @param number
     * @param password
     * @param token
     */
    public void addToDataBase(int number, String password, String token) {
        //adiciona o aluno
        addAluno(new Aluno(number, "1", password, token));
        //adiciona as ucs
        addUcs();
        //adiciona os horarios
        addHorario();
        //adiciona as notas
        addNotas();

    }

    /**
     * adiciona um aluno à base de dados
     * @param a
     */
    private void addAluno(Aluno a) {
        //adiciona o aluno into db
        db.addAluno(a);
        Toast.makeText(MainActivity.this, "al Add", Toast.LENGTH_SHORT).show();
    }


    /**
     * adiciona as ucs de um aluno à base de dados
     */
    private void addUcs() {
        //adiciona as ucs into db
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc_inscrito&token=" + token;
        queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jObeject = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ucs = response.getJSONArray("inscrito");
                    for (int i = 0; i < ucs.length(); i++) {
                        JSONObject ucObject = ucs.getJSONObject(i);
                        getUc(ucObject.getInt("uc"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                try {
                                    int ucCod = ucObject.getInt("uc");
                                    Uc u = new Uc(ucCod, uc);
                                    db.addUc(u);

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
                Toast.makeText(MainActivity.this, "Failed to get Response", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jObeject);
    }

    /**
     * adiciona os horario de um aluno à base de dados
     */
    private void addHorario() {
        queue = Volley.newRequestQueue(MainActivity.this);
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=horario&token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("horario");
                    Toast.makeText(MainActivity.this, " " + myArray, Toast.LENGTH_LONG).show();
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject jo = myArray.getJSONObject(i);
                        Horario s = new Horario();
                        s.setCodUC(jo.getInt("codigoUC"));
                        s.setTipo(jo.getString("tipoAula"));
                        s.setHoraInicio(jo.getInt("horaInicio"));
                        s.setHoraFim(jo.getInt("horaFim"));
                        s.setDia(jo.getInt("diaSemana") - 2);
                        db.addHorario(s);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "h Add", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * adiciona as notas de um aluno à base de dados
     */
    private void addNotas() {
        queue = Volley.newRequestQueue(MainActivity.this);

        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=classificacao&token=" + token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("classificacao");
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject jo = myArray.getJSONObject(i);
                        Nota n = new Nota();
                        n.setCodUC(jo.getInt("uc"));
                        n.setNota(jo.getInt("nota"));
                        n.setNumAluno(db.checkToken(token));
                        db.addNota(n);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error in notas", Toast.LENGTH_SHORT).show();
                Log.d("adicionadas||||||||||||", "onResponse:not added Notas ");
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * devolve o nome da uc dado o seu codigo
     * @param uc
     * @param callback
     */
    private void getUc(int uc, final ICallBack callback) {

        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc&codigo=" + uc;
        Log.d("cenas", "getUc: " + myUrl);
        //Background work here
        StringRequest sr = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myUc = response;
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                myUc = null;
                Toast.makeText(MainActivity.this, "Error in ucs", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(sr);
    }


}