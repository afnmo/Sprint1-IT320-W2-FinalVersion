package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



    public class SignUp extends AppCompatActivity {
        EditText username, passwordd, confirmPPassword, emaill;
        Button signup;

        TextView signInRedirect;
        DBHelper db ;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);
            username = findViewById(R.id.signup_username);
            passwordd = findViewById(R.id.signup_password);
            signup = findViewById(R.id.signup_button);
            confirmPPassword = findViewById(R.id.signup_confirm);
            emaill = findViewById(R.id.signup_email);
            signInRedirect = findViewById(R.id.loginRedirectText);
            db = new DBHelper(this);
            signup.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String userName = username.getText().toString();
                    String email = emaill.getText().toString();
                    String password = passwordd.getText().toString();
                    String confirmPassword = confirmPPassword.getText().toString();

                    if(userName.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                        Toast.makeText(SignUp.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    else{
                        if(password.equals(confirmPassword)){
                            Boolean checkUserUserName = db.checkPassWord(userName);

                            if(checkUserUserName == false){
                                Boolean insert = db.insertData(userName,email,password);
                                //lint to login page
                                if(insert == true){
                                    Toast.makeText(SignUp.this,"SignUp Successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(SignUp.this,"SignUp Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(SignUp.this,"User already exists, Please Login",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignUp.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            signInRedirect.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                }
            });
        }
    }
