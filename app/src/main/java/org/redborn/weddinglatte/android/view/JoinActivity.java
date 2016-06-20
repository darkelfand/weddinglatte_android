package org.redborn.weddinglatte.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.redborn.weddinglatte.R;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

      /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/

        findViewById(R.id.join_confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                intent.putExtra("gubun", "mainBoard");
                startActivity(intent);
            }
        });
    }
    /*@OnClick(R.id.join_confirm_btn)
    void click(View v) {
        Intent intent = new Intent(JoinActivity.this, TermArgeeActivity.class);
        startActivity(intent);
    }*/
}
