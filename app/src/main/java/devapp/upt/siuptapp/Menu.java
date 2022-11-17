package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class Menu extends AppCompatActivity {
    public static final String tokenS = "token";
    String token;
    Intent i;
    ImageView timeTablechange, notasChange,ementaChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //INTENT11
        i = getIntent();
        token = i.getStringExtra(MainActivity.tokenA);

        //IDS
        notasChange = findViewById(R.id.NotasMenu);
        timeTablechange = findViewById(R.id.TimeTableMenu);
        ementaChange = findViewById(R.id.Ementa);

        //ONCLICK
        notasChange.setOnClickListener(this::onClick);
        timeTablechange.setOnClickListener(this::onClick);
        ementaChange.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.NotasMenu:
                Intent intent = new Intent(this, ListaNotasActivity.class);
                intent.putExtra(tokenS, token);
                startActivity(intent);
                break;
            case R.id.TimeTableMenu:
                intent = new Intent(this, TimeTable.class);
                Log.d("Cenas", "onClick: " + i.getStringExtra(MainActivity.tokenA));
                intent.putExtra(tokenS, token);
                startActivity(intent);
                break;
            case R.id.Ementa:
                intent = new Intent(this, Ementa.class);
                startActivity(intent);
                break;
        }
    }


}