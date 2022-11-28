package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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
                intent = new Intent(this, Ementa.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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


}