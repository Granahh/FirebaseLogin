package com.diegohermosomourinov.firebaselogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG ="LoginActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edtEmail        = findViewById(R.id.edtLogin);
        edtPassword     = findViewById(R.id.edtPassword);
        btnLogin        = findViewById(R.id.btnLogin);

        startFirebase();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty() ||edtPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Rellene todos los campos",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Login(edtEmail.getText().toString(),edtPassword.getText().toString());



                }
            }
        });



    }
    private void startFirebase()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                if (firebaseAuth != null)
                {

                    Log.w(TAG,"Sesion Iniciada");

                }
                else
                {
                    Log.w(TAG,"Cerro la sesion");
                }
            }
        };
    }

    private void Login (String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this,"Login establecido",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Error en el login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
       firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
