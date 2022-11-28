package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimeTable extends AppCompatActivity {
    Intent i;
    String token;

    List<ArrayList<Schedule>> disciplinas;
    TimetableView myTimeTable;
    RequestQueue queue;
    Db_handler db;
    ConstraintLayout cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        inicializables();
        cl = findViewById(R.id.finishBtnTimeTable);
        cl.setOnClickListener(this::onClick);

        if (isConnected()) {
            getHorario();
        } else {
            getHorarioBd();
        }
    }

    public void onClick(View v)
    {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    /**
     *
     */
    private void inicializables() {
        disciplinas = new ArrayList<>();

        myTimeTable = findViewById(R.id.myTimeTable);
        i = getIntent();
        token = i.getStringExtra(Menu.tokenS);
        db = new Db_handler(this.getApplicationContext());
    }

    /**
     * arranja o horario do aluno  pelo webservice
     */
    public void getHorario() {
        queue = Volley.newRequestQueue(TimeTable.this);
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=horario&token=" + token;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("horario");
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject jo = myArray.getJSONObject(i);
                        getUc(jo.getInt("codigoUC"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                try {
                                    if (disciplinas.size() == 0) {
                                        ArrayList<Schedule> l = new ArrayList<>();
                                        disciplinas.add(l);
                                        disciplinas.get(0).add(getScheduleWeb(uc, jo.getString("tipoAula"), new Time(jo.getInt("horaInicio"), 0), new Time(jo.getInt("horaFim"), 0), jo.getInt("diaSemana") - 2));
                                    } else {
                                        Boolean hasFound = false;
                                        for (int j = 0; j < disciplinas.size(); j++) {
                                            if (uc.equals(disciplinas.get(j).get(0).getClassTitle())) {
                                                disciplinas.get(j).add(getScheduleWeb(uc, jo.getString("tipoAula"), new Time(jo.getInt("horaInicio"), 0), new Time(jo.getInt("horaFim"), 0), jo.getInt("diaSemana") - 2));
                                                hasFound = true;
                                            }
                                        }
                                        if (!hasFound) {
                                            ArrayList<Schedule> l = new ArrayList<>();
                                            disciplinas.add(l);
                                            disciplinas.get(disciplinas.size() - 1).add(getScheduleWeb(uc, jo.getString("tipoAula"), new Time(jo.getInt("horaInicio"), 0), new Time(jo.getInt("horaFim"), 0), jo.getInt("diaSemana") - 2));
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iterateDisciplina();
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
                System.out.println("---------errorResponse-----------L125TimeTable");
            }
        });
        queue.add(jsonObjectRequest);
    }//fim do getHorario

    /**
     * vai buscar o nome da uc
     *
     * @param uc
     * @param callback
     */
    public void getUc(int uc, final ICallBack callback) {
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=uc&codigo=" + uc;
        Log.d("cenas", "getUc: " + myUrl);
        //Background work here
        StringRequest sr = new StringRequest(Request.Method.GET, myUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("---------errorResponse-----------L152TimeTable" );

            }
        });
        queue.add(sr);
    }//fim do getUc

    /**
     *
     */
    public void getHorarioBd() {
        Log.d("entrou", "getHorarioBd: inicio do horario");
        token = token.replace("\n", "");
        ArrayList<Horario> myHorarios = db.getHorarioAluno(token);
        for (Horario h : myHorarios) {
            Schedule s = getSchedureDb(h);
            //adiciona se a lista de disciplinas for vazia
            if (disciplinas.size() == 0) {
                ArrayList<Schedule> l = new ArrayList<>();
                disciplinas.add(l);
                disciplinas.get(0).add(s);
            } else {//se nao for vazia verifica duas situações 1ª situação encontra uma disciplina com o mesmo nome , 2ª situação não encontra por isso cria uma nova lista e adiciona nessa lsita
                Boolean hasFound = false;
                for (int j = 0; j < disciplinas.size(); j++) {
                    if (s.getClassTitle().equals(disciplinas.get(j).get(0).getClassTitle())) {
                        disciplinas.get(j).add(s);
                        hasFound = true;
                    }
                }
                if (!hasFound) {
                    ArrayList<Schedule> l = new ArrayList<>();
                    disciplinas.add(l);
                    disciplinas.get(disciplinas.size() - 1).add(s);
                }
            }

        }
        iterateDisciplina();
        Log.d("a minha time table", myTimeTable.getAllSchedulesInStickers().size() + "");
    }

    /**
     *
     */
    public void iterateDisciplina() {
        for (int j = 0; j < disciplinas.size(); j++) {
            myTimeTable.add(disciplinas.get(j));
        }
    }

    public Schedule getSchedureDb(Horario h) {
        Schedule s = new Schedule();
        s.setClassTitle(db.getUc(h.getCodUC()).getNome());
        s.setProfessorName(h.getTipo());
        s.setStartTime(new Time(h.getHoraInicio(), 0));
        s.setEndTime(new Time(h.getHoraFim(), 0));
        s.setDay(h.getDia());
        return s;
    }

    public Schedule getScheduleWeb(String uc, String tipo, Time tStart, Time tEnd, int day) {
        Schedule s = new Schedule();
        s.setClassTitle(uc);
        s.setProfessorName(tipo);
        s.setStartTime(tStart);
        s.setEndTime(tEnd);
        s.setDay(day);
        return s;
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