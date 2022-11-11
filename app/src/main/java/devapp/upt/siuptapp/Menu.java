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
    ImageView timeTablechange, notasChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        i = getIntent();
        token = i.getStringExtra(MainActivity.tokenA);
        notasChange = findViewById(R.id.NotasMenu);
        timeTablechange = findViewById(R.id.TimeTableMenu);
        notasChange.setOnClickListener(this::onClick);
        timeTablechange.setOnClickListener(this::onClick);
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
        }
    }


}