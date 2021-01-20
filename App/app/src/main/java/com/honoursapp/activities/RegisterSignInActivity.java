package com.honoursapp.activities;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.honoursapp.R;

public class RegisterSignInActivity extends AppCompatActivity {

    //Edit texts
    EditText etEmail, etPassword;

    //Buttons
    Button btnRegister, btnSignIn;

    //Firebase authentication (to be added to gradle (app): (implementation 'com.google.firebase:firebase-auth:18.0.0'))
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_in);

        //Initialise the aut
        auth = FirebaseAuth.getInstance();

        //Find the Edit texts
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPass);

        //Find the buttons
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    etEmail.setText("");
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setText("");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Record the values that have been input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                }else if(password.length() < 7){
                    Toast.makeText(getApplicationContext(),"Enter a password which has 7 characters",Toast.LENGTH_LONG).show();
                }else{
                    register(email, password);
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Record the values that have been input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                }else{
                    signIn(email, password);
                }
            }
        });

    }

    private void register(String email, String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterSignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Successfully added!",Toast.LENGTH_SHORT).show();
                    //Sign them in and send them back
                    signIn(email, password);
                }else{
                    Toast.makeText(getApplicationContext(),"Error.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signIn(String email, String password){

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Tell them that they are logged in and redirect them to the main screen
                    Toast.makeText(getApplicationContext(), "Logged in!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else{
                    //Inform them to try again
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}