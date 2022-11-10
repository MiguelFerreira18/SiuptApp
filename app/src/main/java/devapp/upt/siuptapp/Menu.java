package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    public static final String tokenS = "token";
    Intent i;
    String token;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        i = getIntent();
        token = i.getStringExtra(MainActivity.tokenA);
        bt1 = findViewById(R.id.myBtn);
        bt1.setOnClickListener(this::onClick);
    }
    public void onClick(View v){
        Intent i = new Intent(this, TimeTable.class);
        i.putExtra(tokenS, token);
        startActivity(i);
    }


}