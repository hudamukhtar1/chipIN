package com.uofthacksvi.uofthacksvi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button registerButton;
    private TextView tvSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //User is already logged in
            //Start MainActivity
            startActivity(new Intent(SignUpActivity.this, GroupsActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);

        registerButton.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);

    }

    private void registerUser(){

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //username is empty
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            //password is empty
            // stopping the function execution further
            return;
        }

        //if username and password are entered, a progress dialog if first shown

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            //we can start GroupActivity
                            //for now we display a toast
                            Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, GroupsActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Could not register User, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == registerButton){
            registerUser();
        }
        if(view == tvSignIn);
        //will open LoginActivity

    }
}
