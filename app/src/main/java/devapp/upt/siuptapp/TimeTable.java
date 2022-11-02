package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimeTable extends AppCompatActivity {
    Intent i;
    String token, myUc;
    ArrayList<Schedule> horarios;
    TimetableView myTimeTable;
    RequestQueue queue;
    Db_handler db;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        horarios = new ArrayList<>();
        myTimeTable = findViewById(R.id.myTimeTable);
        i = getIntent();
        token = i.getStringExtra(Menu.tokenS);
        db = new Db_handler(this.getApplicationContext());
        /*if(connected){
        getHorario();
        }else {*/
            getHorarioBd();
//        }
    }



    /**
     * arranja o horario do aluno  pelo webservice
     */
    public void getHorario() {
        queue = Volley.newRequestQueue(TimeTable.this);
        String myUrl = "https://alunos.upt.pt/~abilioc/dam.php?func=horario&token=" + token;
        Toast.makeText(TimeTable.this, myUrl, Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray myArray = response.getJSONArray("horario");
                    Toast.makeText(TimeTable.this, " " + myArray, Toast.LENGTH_LONG).show();
                    for (int i = 0; i < myArray.length(); i++) {
                        JSONObject jo = myArray.getJSONObject(i);
                        Schedule s = new Schedule();
                        getUc(jo.getInt("codigoUC"), new ICallBack() {
                            @Override
                            public void onSuccess(String uc) {
                                try {
                                    JSONArray myArray = response.getJSONArray("horario");

                                    myUc = uc;
                                    s.setClassTitle(myUc);
                                    s.setProfessorName(jo.getString("tipoAula"));
                                    s.setStartTime(new Time(jo.getInt("horaInicio"), 0));
                                    s.setEndTime(new Time(jo.getInt("horaFim"), 0));
                                    s.setDay(jo.getInt("diaSemana") - 2);
                                    horarios.add(s);
                                    myTimeTable.add(horarios);
                                    Toast.makeText(TimeTable.this, " " + horarios, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TimeTable.this, "Erro" + error, Toast.LENGTH_SHORT).show();
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
                myUc = response;
                Toast.makeText(TimeTable.this, "done", Toast.LENGTH_SHORT).show();
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimeTable.this, "Erro" + error, Toast.LENGTH_SHORT).show();
                myUc = null;
            }
        });
        queue.add(sr);
    }//fim do getUc

    public void getHorarioBd(){
        Log.d("entrou", "getHorarioBd: inicio do horario");
        token = token.replace("\n","");
        ArrayList<Horario> myHorarios = db.getHorarioAluno(token);
        for (Horario h: myHorarios) {
            Schedule s = new Schedule();
            s.setClassTitle(db.getUc(h.getCodUC()).getNome());
            s.setProfessorName(h.getTipo());
            s.setStartTime(new Time(h.getHoraInicio(), 0));
            s.setEndTime(new Time(h.getHoraFim(), 0));
            s.setDay(h.getDia());
            horarios.add(s);
            Log.d("o meu horario", s.getDay() + "  cenas " + h.getDia());
            myTimeTable.add(horarios);
        }
        Log.d("a minha time table", myTimeTable.getAllSchedulesInStickers().size() +"");
    }
}