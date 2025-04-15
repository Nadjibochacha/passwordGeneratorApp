package com.example.passwordgenerator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
        loadPasswords();
    }
    private void loadPasswords() {
        TableLayout tableLayout = findViewById(R.id.table_layout);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getAllPasswords();

        // Clear existing rows except header
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }

        if (cursor.moveToFirst()) {
            do {
                String account = cursor.getString(cursor.getColumnIndexOrThrow("account"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                TableRow row = new TableRow(this);

                // Account text
                TextView tvAccount = new TextView(this);
                tvAccount.setText(account);
                tvAccount.setPadding(8, 8, 8, 8);
                tvAccount.setTextSize(12);
                tvAccount.setTextColor(Color.BLACK);

                // Password text
                TextView tvPassword = new TextView(this);
                tvPassword.setText(password);
                tvPassword.setPadding(8, 8, 8, 8);
                tvPassword.setTextColor(Color.BLACK);
                tvPassword.setTextSize(10);
                tvPassword.setSingleLine(false);
                tvPassword.setMaxLines(10);
                tvPassword.setEllipsize(null);

                // Set fixed width in pixels
                int fixedWidthInPx = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        178, // 200 dp width
                        getResources().getDisplayMetrics()
                );
                tvPassword.setLayoutParams(new TableRow.LayoutParams(fixedWidthInPx, TableRow.LayoutParams.WRAP_CONTENT));

                // Menu button
                ImageButton menuBtn = new ImageButton(this);
                menuBtn.setImageResource(R.drawable.dots); // 3-dot icon
                menuBtn.setBackgroundColor(Color.TRANSPARENT);
                menuBtn.setPadding(0, 4, 4, 4);

                menuBtn.setOnClickListener(v -> {
                    PopupMenu popup = new PopupMenu(this, v);
                    popup.getMenuInflater().inflate(R.menu.password_item_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.action_copy) {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Password", password);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(this, "Password copied!", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (item.getItemId() == R.id.action_delete) {
                            dbHelper.deletePassword(account);
                            loadPasswords(); // Refresh
                            return true;
                        }
                        return false;
                    });

                    popup.show();
                });

                row.addView(tvAccount);
                row.addView(tvPassword);
                row.addView(menuBtn);

                tableLayout.addView(row);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
    protected void onResume() {
        super.onResume();
        loadPasswords(); // Reload data when coming back from AddPassword
    }
}