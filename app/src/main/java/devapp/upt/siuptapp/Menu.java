package devapp.upt.siuptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Menu extends AppCompatActivity {
public static final String tokenS = "token";
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        i = getIntent();
        //PRECISO DO TOKEN DO LOGIN
    }



}