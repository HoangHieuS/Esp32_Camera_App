package com.example.esp32app.Common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.example.esp32app.R;


public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
    }


    public void callLoginScreen(View view) {

        Intent intent = new Intent(this,LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
        startActivity(intent,options.toBundle());

    }


    public void callSignupScreen(View view) {

        Intent intent = new Intent(this,RegisActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.sign_btn),"transition_login");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StartScreen.this, pairs);
        startActivity(intent,options.toBundle());
    }

    public void callAboutUsScreen(View view) {
    }
}