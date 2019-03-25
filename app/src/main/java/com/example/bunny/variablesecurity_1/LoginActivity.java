package com.example.bunny.variablesecurity_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.auth.userpools.SignUpActivity;

public class LoginActivity extends AppCompatActivity {


    EditText id, password;
    Button loginBtn, signupBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.loginID);
        password = findViewById(R.id.password);
        loginBtn =  findViewById(R.id.logInBtn);
        signupBtn =  findViewById(R.id.signUpBtn);

        final com.example.myappname.TinyDB tinydb = new com.example.myappname.TinyDB(this);



        if(!tinydb.getBoolean("loginState")){
            Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
        }else {

            Intent intent =  new Intent(LoginActivity.this , HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id.getText().toString().equals("variable") && password.getText().toString().equals("variable")){

                    Intent intent =  new Intent(LoginActivity.this , HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    tinydb.putBoolean("loginState" , true);

                }else{
                    Toast.makeText(LoginActivity.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LoginActivity.this, "To be implemented!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
