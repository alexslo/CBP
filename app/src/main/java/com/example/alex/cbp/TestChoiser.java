package com.example.alex.cbp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class TestChoiser extends Activity {

    Button startTest;
    CheckBox staticTest, dynamicTest;
    TextView deviceText, androidVerText, cameraMPText, cameraFocusText;

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
                if (staticTest.isChecked())
                {
                    Intent intent = new Intent(TestChoiser.this, StaticTestResult.class);
                    startActivity(intent);
                }

            }
        });

        deviceText = (TextView) findViewById(R.id.dev_text);
        androidVerText = (TextView) findViewById(R.id.android_ver_text);
        cameraMPText = (TextView) findViewById(R.id.cam_mp_text);
        cameraFocusText = (TextView) findViewById(R.id.cam_focus_text);

        deviceText.append(" " + android.os.Build.MODEL + "(" + Build.DEVICE + ")");
        androidVerText.append(" " + Build.VERSION.RELEASE);
        CameraModel mCameraModel = new CameraModel(1);
        cameraMPText.append(" " + mCameraModel.getCameraMP() + "MP");
        cameraFocusText.append(" " + mCameraModel.getCameraFocusSize() +"mm");

    }
}
