package com.example.alex.cbp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

/**
 * Created by alex on 17.10.2014.
 */
public class WBTestResult extends Activity {

    private TextView resultText;
    private ProgressDialog pDialog;

    private String photoPatchTempl;
    private int testPoints[] ={0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_result);

        resultText = (TextView)findViewById(R.id.resultTextView);

        photoPatchTempl = CameraWBTest.saveFolderPatch + CameraWBTest.testPictureName;

        pDialog = new ProgressDialog(WBTestResult.this);
        CalculateTestPoints mCalculateTestPoints = new CalculateTestPoints();

        mCalculateTestPoints.execute();

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

    class CalculateTestPoints extends AsyncTask<Void, Void, Void> {
        /**
         * Перед согданием в фоновом потоке показываем прогресс диалог
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Calculation...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected Void doInBackground(Void... args) {


            for (int pictureCounter =0;pictureCounter<3;pictureCounter++) {

                String patch = photoPatchTempl + pictureCounter + ".jpg";
                File file = new File(patch);
                if (file.exists())
                {
                    testPoints[pictureCounter] = getPhotoPoints(patch);
                }
                else
                {
                    testPoints[pictureCounter] = 0;
                }
            }
            return null;
        }
        private int getPhotoPoints(String _photoPatch) {
            Bitmap fullBitMap = BitmapFactory.decodeFile(_photoPatch);
            // smallBitMap = 1x1; TODO revert to 10x10
            int cutBitMapWidth = (fullBitMap.getWidth() - 1);
            int cutBitMapHeight = (fullBitMap.getHeight() - 1);
            Bitmap smallBitMap = Bitmap.createBitmap(fullBitMap, cutBitMapWidth/2, cutBitMapHeight/2, fullBitMap.getWidth() - cutBitMapWidth, fullBitMap.getHeight() - cutBitMapHeight);
            //remove photo
            File file = new File(_photoPatch);
            file.delete();

            return smallBitMap.getPixel(0,0);

        }

        protected void onPostExecute(Void arg) {
            String testText ="";
            for (int i = 0; i<3; i++) {
                testText += "\n Photo N" +i;
                testText += " Red: "+ Color.red(testPoints[i]);
                testText += " Blue: "+ Color.blue(testPoints[i]);
                testText += " Green: "+ Color.green(testPoints[i]);
                testText += " Alpha: "+ Color.alpha(testPoints[i]);
            }
            resultText.setText(testText);

            pDialog.dismiss();
        }


    }

}