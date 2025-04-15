package com.example.passwordgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class AddPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.addpassword);
        Button go_back = findViewById(R.id.btn_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_intent = new Intent(AddPassword.this, MainActivity.class);
                startActivity(main_intent);
            }
        });
        Button generateBtn = findViewById(R.id.generate_pass);
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_password();
            }
        });
    }

    protected void add_password() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText accountField = findViewById(R.id.edit_account);
        EditText numCharsField = findViewById(R.id.edit_num_chars);

        String account = accountField.getText().toString().trim();
        String numCharsStr = numCharsField.getText().toString().trim();
        // Validate input
        if (account.isEmpty() || numCharsStr.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int numChars = Integer.parseInt(numCharsStr);
        String password = PasswordGenerator.generatePassword(numChars);
        System.out.println("Account: " + account + ", Password: " + password + "num: "+ numChars);
        boolean inserted = dbHelper.insertPassword(account, password);
        if (inserted) {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            System.out.println("Account: " + account + ", Password: " + password);
        } else {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }
}
