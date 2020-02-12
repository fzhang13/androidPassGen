package com.felixzhang.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvPassword;
    private Button btnGenerate;
    Spinner passwordSpinner;
    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPassword = findViewById(R.id.tv_password);
        btnGenerate = findViewById(R.id.btn_generate);
        passwordSpinner = findViewById(R.id.passSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.password_length,
                android.R.layout.simple_spinner_item);

        passwordSpinner.setAdapter(adapter);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = passwordSpinner.getSelectedItem().toString();
                int length = Integer.parseInt(str);
                tvPassword.setText(getPassword(length));
            }
        });

        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copiedPassword = tvPassword.getText().toString();
                clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password", copiedPassword);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Password Copied to Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getPassword(final int length){
        final RadioButton r1 = findViewById(R.id.strong);
        final RadioButton r2 = findViewById(R.id.medium);
        final RadioButton r3 = findViewById(R.id.weak);

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)(}{][,:;";

        final String low = upperCase + lowerCase;
        final String medium = upperCase + lowerCase + numbers;
        final String strong = upperCase + lowerCase + numbers + symbols;

        final StringBuilder stringBuilder = new StringBuilder();

        final Random rand = new Random();

        if(r1.isChecked()){
            for(int i = 0; i < length; i++) {
            char c = strong.charAt(rand.nextInt(strong.length()));
            stringBuilder.append(c);
            }
        }
        if(r2.isChecked()){
            for(int i = 0; i < length; i++) {
                char c = medium.charAt(rand.nextInt(medium.length()));
                stringBuilder.append(c);
            }
        }
        if(r3.isChecked()){
            for(int i = 0; i < length; i++) {
                char c = low.charAt(rand.nextInt(low.length()));
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

}
