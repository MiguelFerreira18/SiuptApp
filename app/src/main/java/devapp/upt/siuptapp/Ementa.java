package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

import devapp.upt.siuptapp.adapters.AdapterEmenta;
import es.dmoral.toasty.Toasty;

public class Ementa extends AppCompatActivity {
    Spinner diaSpinner;
    RecyclerView ementasRecycle;
    AdapterEmenta ementaAdapter;
    RequestQueue queue;
    ArrayList<EmentaModel> ementas;
    ConstraintLayout cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ementa);

        queue = Volley.newRequestQueue(this);

        ementas = new ArrayList<>();

        diaSpinner = findViewById(R.id.diaSpinner);
        populateSpinner();

        ementasRecycle = findViewById(R.id.ementaRecycle);
        ementaAdapter = new AdapterEmenta(this, ementas);
        ementasRecycle.setAdapter(ementaAdapter);
        ementasRecycle.setLayoutManager(new LinearLayoutManager(this));


        apiRequestEmenta();

        diaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toasty.info(Ementa.this, "A carregar "+ diaSpinner.getSelectedItem().toString(), Toasty.LENGTH_SHORT).show();
                apiRequestEmenta();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cl = findViewById(R.id.finishBtnEmenta);
        cl.setOnClickListener(this::onClick);
    }

    public void onClick(View v)
    {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }



    public void populateSpinner() {
        String[] dias = {"Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dias);
        diaSpinner.setAdapter(adapterSpinner);
    }

    public void apiRequestEmenta() {
        String url = "https://alunos.upt.pt/~abilioc/dam.php?func=ementa";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ementa = response.getJSONArray("ementa");
                    if (ementas != null) {
                        ementas.clear();
                    }
                    for (int i = 0; i < ementa.length(); i++) {
                        JSONObject ementaObject = ementa.getJSONObject(i);
                        int dia = diaSemana(diaSpinner.getSelectedItem().toString());
                        if (ementaObject.getInt("diaSemana") == dia ) {
                            ementas.add(new EmentaModel(ementaObject.getString("sopa"), ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("pratoCarne"), ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("pratoPeixe"), ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("Sobremesa"), ementaObject.getInt("diaSemana")));
                            ementaAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-------errorResponse---------L125Ementa");
            }
        });
        queue.add(jsonObjectRequest);
    }
    public int diaSemana(String dia){
        switch(dia){
            case "Segunda-Feira":
                return 2;
            case "Terça-Feira":
                return 3;
            case "Quarta-Feira":
                return 4;
            case "Quinta-Feira":
                return 5;
            case "Sexta-Feira":
                return 6;
        }
        return -1;
    }
}