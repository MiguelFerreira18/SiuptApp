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
import android.widget.ImageView;

import es.dmoral.toasty.Toasty;

public class Menu extends AppCompatActivity {
    public static final String tokenS = "token";
    String token;
    Intent i;
    ImageView timeTablechange, notasChange,ementaChange, UcsChange;
    ConstraintLayout cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //TOASTY DE BOAS VINDAS
        Toasty.success(this, "Bem vindo à app do Siupts", Toasty.LENGTH_LONG).show();

        //INTENT11
        i = getIntent();
        token = i.getStringExtra(MainActivity.tokenA);

        //IDS
        notasChange = findViewById(R.id.NotasMenu);
        timeTablechange = findViewById(R.id.TimeTableMenu);
        ementaChange = findViewById(R.id.Ementa);
        UcsChange = findViewById(R.id.Incricao);
        cl = findViewById(R.id.Voltarbtn);


        //ONCLICK
        notasChange.setOnClickListener(this::onClick);
        timeTablechange.setOnClickListener(this::onClick);
        ementaChange.setOnClickListener(this::onClick);
        UcsChange.setOnClickListener(this::onClick);
        cl.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.NotasMenu:
                Intent intent = new Intent(this, ListaNotasActivity.class);
                intent.putExtra(tokenS, token);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.TimeTableMenu:
                intent = new Intent(this, TimeTable.class);
                intent.putExtra(tokenS, token);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.Ementa:
                if (isConnected()){
                intent = new Intent(this, Ementa.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                Toasty.info(this, "Só pode aceder à ementa com internet", Toasty.LENGTH_LONG).show();
                break;
            case R.id.Incricao:
                intent = new Intent(this, lista_Ucs.class);
                intent.putExtra(tokenS, token);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.Voltarbtn:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

        }
    }

    /**
     * verifica se existe uma conexão com a internet/wifi
     *
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


}