package com.example.alex.cbp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by alex on 05.11.2014.
 */
public class StaticTestResult extends Activity {

    private TextView  resultStaticFrontCamText, resultStaticBackCamText, resultStaticSumText;

    public int FullPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_stat);
        resultStaticFrontCamText = (TextView)findViewById(R.id.stat_rez_front);
        resultStaticBackCamText = (TextView)findViewById(R.id.stat_rez_back);
        resultStaticSumText = (TextView)findViewById(R.id.stat_rezults);

        StaticTestModel mStaticTestModel = new StaticTestModel();

        int testPointBuf;

        mStaticTestModel.RunStaticTests(1);
        testPointBuf = mStaticTestModel.GetFinalPoints();
        resultStaticBackCamText.setText(" " + testPointBuf);
        FullPoints += testPointBuf;

        mStaticTestModel.RunStaticTests(2);
        testPointBuf = mStaticTestModel.GetFinalPoints();
        resultStaticFrontCamText.setText(" " + testPointBuf);
        FullPoints += testPointBuf;

        resultStaticSumText.setText(" " + FullPoints);

        // Поиск AdView как ресурса и отправка запроса.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
