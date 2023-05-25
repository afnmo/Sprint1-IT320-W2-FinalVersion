package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText username, password;
    Button signin;
    DatabaseHelper DB;
    //hello
    TextView signUpRedirect;

    private String user;
    private String pass;

//    Menu menu;
//    MainActivity mainActivity;
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        signin = findViewById(R.id.btnsignin1);
        DB = new DatabaseHelper(this);
        signUpRedirect = findViewById(R.id.signUpRedirectText);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
//                    Boolean check = DB.checkUsernamePassword(user, pass);
                    Boolean check = isLogin(user,pass);
                    if (check){
                        Toast.makeText(login.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
//                        for retrieving the username of current user
                        DB.saveUsername(user);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);


                    } else {
                        Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signUpRedirect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    public boolean isLogin(String user1, String pass1){
        if(DB.checkUsernamePassword(user1, pass1)){
            flag = true;
            return true;
        }
        return false;
    }

    public boolean getFlag(){
        return flag;
    }

}
