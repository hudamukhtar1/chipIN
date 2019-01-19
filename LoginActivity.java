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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button loginButton;
    private TextView tvSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //User is already logged in
            //Start MainActivity
            startActivity(new Intent(LoginActivity.this, GroupsActivity.class));
        }

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void userLogin(){
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

        progressDialog.setMessage("Signing In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //user is successfully logged in
                            //we can start ProfileActivity
                            //for now we display a toast
                            Toast.makeText(LoginActivity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, GroupsActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Could not sign in, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            userLogin();
        }
        if (view == tvSignUp){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }
}
