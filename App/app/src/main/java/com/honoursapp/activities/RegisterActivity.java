package com.honoursapp.activities;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.honoursapp.R;

public class RegisterActivity extends AppCompatActivity {

    //Buttons
    Button btnRegister;

    //Edit texts
    EditText etEmail, etPass, etPassConf;

    //Firebase authentication (to be added to gradle (app): (implementation 'com.google.firebase:firebase-auth:18.0.0'))
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialise the aut
        auth = FirebaseAuth.getInstance();

        //Pair Buttons
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Pair edit texts
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etPassConf = (EditText) findViewById(R.id.etPassConf);

        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    etEmail.setText("");
                }
            }
        });

        etPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPass.setText("");
                }
            }
        });

        etPassConf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    etPassConf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassConf.setText("");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();
                String passwordConf = etPassConf.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConf)){
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                }else if(password.length() < 7){
                    Toast.makeText(getApplicationContext(),"Enter a password which has 7 characters",Toast.LENGTH_LONG).show();
                }else if(!password.equals(passwordConf)){
                    Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_LONG).show();
                }else{
                    // TODO: Make this add the name to the account too!
                    register(email, password);
                }
            }
        });

    }

    private void register(String email, String password){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
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