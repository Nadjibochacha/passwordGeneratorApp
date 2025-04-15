package com.example.passwordgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.addpassword);
        TextView tvCopyright = findViewById(R.id.tv_copyright);
        tvCopyright.setText(HtmlCompat.fromHtml("© 2025 <a href='https://nadjib-chacha.vercel.app/'>MED DEV</a> - All Rights Reserved", HtmlCompat.FROM_HTML_MODE_LEGACY));
        tvCopyright.setMovementMethod(LinkMovementMethod.getInstance());
        Button go_back = (Button) findViewById(R.id.btn_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_intent = new Intent(AddPassword.this, MainActivity.class);
                startActivity(main_intent);
            }
        });
    }
}
