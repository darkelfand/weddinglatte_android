package org.redborn.weddinglatte.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.redborn.weddinglatte.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TermArgeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_argee);

        TextView termAgreeText = (TextView) findViewById(R.id.termAgree);
        termAgreeText.setText(readText());

        findViewById(R.id.main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(TermArgeeActivity.this, MainActivity.class);
                Intent intent = new Intent(TermArgeeActivity.this, JoinActivity.class);
                //intent.putExtra("gubun", "mainBoard");
                startActivity(intent);
            }
        });

    }
    private String readText() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.termagreecontent);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
