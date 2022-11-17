package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import devapp.upt.siuptapp.adapters.AdapterEmenta;

public class Ementa extends AppCompatActivity {
    Spinner diaSpinner;
    RecyclerView ementasRecycle;
    AdapterEmenta ementaAdapter;
    ArrayList<EmentaModel> ementas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ementa);

        ementas = new ArrayList<>();

        diaSpinner = findViewById(R.id.diaSpinner);
        populateSpinner();

        ementasRecycle = findViewById(R.id.ementaRecycle);
        ementaAdapter = new AdapterEmenta(this,ementas);
        ementasRecycle.setAdapter(ementaAdapter);
        ementasRecycle.setLayoutManager(new LinearLayoutManager(this));


        apiRequestEmenta();

        diaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void populateSpinner() {
        String[] dias = {"2", "3", "4", "5", "6"};
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
                    if(ementas!=null) {
                        ementas.clear();
                    }
                    for (int i = 0; i < ementa.length(); i++) {
                        JSONObject ementaObject = ementa.getJSONObject(i);
                        if(ementaObject.getInt("diaSemana")==Integer.parseInt(diaSpinner.getSelectedItem().toString())){
                            ementas.add(new EmentaModel(ementaObject.getString("sopa"),ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("pratoCarne"),ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("pratoPeixe"),ementaObject.getInt("diaSemana")));
                            ementas.add(new EmentaModel(ementaObject.getString("Sobremesa"),ementaObject.getInt("diaSemana")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ementaAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Ementa.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}