package com.example.mvmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity{

    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    EditText nameEdt,emailEdt,mobileEdt,passwordEdt;
    Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("We're creating new account...");

        nameEdt=findViewById(R.id.name);
        emailEdt=findViewById(R.id.email);
        mobileEdt=findViewById(R.id.mobile);
        passwordEdt=findViewById(R.id.password);
        signUp=findViewById(R.id.register);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lemail, lpassword, lname, lmobile;
                lname = nameEdt.getText().toString();
                lemail = emailEdt.getText().toString();
                lpassword = passwordEdt.getText().toString();

                lmobile =mobileEdt.getText().toString();
               Map<String,Object> user=new HashMap<>();
               user.put("name",lname);
               user.put("email",lemail);
               user.put("mobile",lmobile);
               user.put("password",lpassword);

                dialog.show();
                auth.createUserWithEmailAndPassword(lemail, lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();

                            database
                                    .collection("users")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


}