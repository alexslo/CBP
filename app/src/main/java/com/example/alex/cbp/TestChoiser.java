package com.example.alex.cbp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class TestChoiser extends Activity {

    Button startTest;
    CheckBox staticTest, dynamicTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_choiser);
        startTest = (Button) findViewById(R.id.startTestButton);
        staticTest = (CheckBox) findViewById(R.id.stat_test);
        dynamicTest = (CheckBox) findViewById(R.id.dyn_test);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicTest.isChecked())
                {
                    Intent intent = new Intent(TestChoiser.this, CameraWBTest.class);
                    startActivity(intent);
                }

            }
        });

    }
}