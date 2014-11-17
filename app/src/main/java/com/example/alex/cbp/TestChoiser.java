package com.example.alex.cbp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


public class TestChoiser extends Activity {

    Button startTest;
    RadioGroup testsRadioGroup;
    TextView deviceText, androidVerText, cameraMPText, cameraFocusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_choiser);
        startTest = (Button) findViewById(R.id.startTestButton);
        testsRadioGroup = (RadioGroup) findViewById(R.id.radiotests);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testsRadioGroup.getCheckedRadioButtonId()==R.id.dyn_test)
                {
                    Intent intent = new Intent(TestChoiser.this, CameraWBTest.class);
                    startActivity(intent);
                }
                if (testsRadioGroup.getCheckedRadioButtonId()==R.id.stat_test)
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
