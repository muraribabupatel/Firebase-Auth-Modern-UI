package com.example.mvmart;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button Login;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    EditText Email, Password;
 private FirebaseAuth firebaseAuth;
    TextView Forget, AccountNew;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.lEmail);
        Password = findViewById(R.id.lPassword);
        Forget = findViewById(R.id.lForget);
          firebaseAuth=FirebaseAuth.getInstance();
          progressBar=findViewById(R.id.progressBar);

        AccountNew = findViewById(R.id.acReg);
        Login = findViewById(R.id.login);
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();


                if (Email.getText().toString().matches(emailPattern)){
                    if (password.length() >=8){
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility( View.INVISIBLE);
                                }
                            }
                        });
                    }


                }else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }//else {
            //Toast.makeText(LoginActivity.this, "Incorrect email or passowrd", Toast.LENGTH_SHORT).show();





            // }



        });

        AccountNew.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(Email.getText()))
            if (!TextUtils.isEmpty(Password.getText())) {

            }

    }
}