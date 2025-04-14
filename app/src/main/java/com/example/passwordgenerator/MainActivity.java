package com.example.passwordgenerator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvCopyright = findViewById(R.id.tv_copyright);
//        tvCopyright.setText(Html.fromHtml("© 2025 <a href='https://nadjib-chacha.vercel.app/'>MED DEV</a> - All Rights Reserved"));
//        tvCopyright.setMovementMethod(LinkMovementMethod.getInstance());
        Button btn_gene =(Button)findViewById(R.id.btn_generate);
        btn_gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_password_page = new Intent(MainActivity.this,AddPassword.class);
                startActivity(add_password_page);
            }
        });
        TextView tvcopy =(TextView) findViewById(R.id.tv_copyright);
        tvcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_password_page = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nadjib-chacha.vercel.app/"));
                startActivity(add_password_page);
            }
        });
    }

}