package com.example.mvmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mobileNumber;
    Button continueBtn;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mobileNumber=findViewById(R.id.mobile);
        continueBtn=findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);
        linearLayout=findViewById(R.id.backLogo);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(backIntent);

            }
        });

    }

    @Override
    public void onClick(View v) {

            Intent intent=new Intent(ForgetPasswordActivity.this,VerifyOtpActivity.class);
            startActivity(intent);
        }

    }
