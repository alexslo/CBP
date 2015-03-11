package com.example.alex.cbp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by alex on 17.10.2014.
 */
public class DynTestResult extends Activity {

    private TextView resultDynText_1, resultDynText_2, resultDynText_3, resultDynText_4, resultDynTextSum;
    private ProgressDialog pDialog;
    private SharedPreferences prefData;

    private String photoPatchTempl;
    private final int ariaSize =100;
    int picturesNum = 3;

    //Match
    private double G;
    private int finalPoints;

    private TextView testTextView;
    String testStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_dyn);

        resultDynText_1 = (TextView)findViewById(R.id.dyn_rez_1);
        resultDynText_2 = (TextView)findViewById(R.id.dyn_rez_2);
        resultDynText_3 = (TextView)findViewById(R.id.dyn_rez_3);
        resultDynText_4 = (TextView)findViewById(R.id.dyn_rez_4);
        resultDynTextSum = (TextView)findViewById(R.id.dyn_rezults);
        testTextView = (TextView)findViewById(R.id.testTextView);

        photoPatchTempl = CameraWBTest.saveFolderPatch + CameraWBTest.testPictureName;

        pDialog = new ProgressDialog(DynTestResult.this,ProgressDialog.THEME_HOLO_DARK);
        prefData = getApplicationContext().getSharedPreferences("CBP_DATA", MODE_PRIVATE);
        CalculateTestPoints mCalculateTestPoints = new CalculateTestPoints();

        // Поиск AdView как ресурса и отправка запроса.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

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
            pDialog.setMessage(getResources().getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected Void doInBackground(Void... args) {
            /*
            //Match variable:
            int Rfull[] = new int [picturesNum];
            int Gfull[] = new int [picturesNum];
            int Bfull[] = new int [picturesNum];
            int Rrez =0, Grez =0, Brez =0;
            double Gr, Gg, Gb;

            for (int pictureCounter =0;pictureCounter<picturesNum;pictureCounter++) {

                String patch = photoPatchTempl + pictureCounter + ".jpg";
                File file = new File(patch);
                if (file.exists())
                {

                    int [] photoPixels = getPhotoPoints(patch);
                    for (int photoPixel : photoPixels)
                    {
                        Rfull[pictureCounter] += Color.red(photoPixel);
                        Gfull[pictureCounter] += Color.green(photoPixel);
                        Bfull[pictureCounter] += Color.blue(photoPixel);

                    }

                    Rfull[pictureCounter] =  Rfull[pictureCounter]/ariaSize;
                    Gfull[pictureCounter] =  Gfull[pictureCounter]/ariaSize;
                    Bfull[pictureCounter] =  Bfull[pictureCounter]/ariaSize;
                }
                else return null;
            }

            for (int pictureCounter =0;pictureCounter<picturesNum;pictureCounter++) {
                Rrez += Rfull[pictureCounter];
                Grez += Gfull[pictureCounter];
                Brez += Bfull[pictureCounter];
            }
            Rrez = Rrez/picturesNum;
            Grez = Rrez/picturesNum;
            Brez = Rrez/picturesNum;

            //Test N1
            Gr = getFirstTestPoints(Rfull, Rrez);
            Gg = getFirstTestPoints(Gfull, Grez);
            Gb = getFirstTestPoints(Bfull, Brez);

            G = (((Gr + Gg + Gb)/3)/255) * 100;

            */

            testStr += "MPixels: " +  prefData.getString("MPixels","0.1") +"\n" +
                       "MFocus: " +  prefData.getString("MFocus", "0") +"\n" +
                       "MTime: " +  Math.abs(prefData.getLong("timeTestP", 1)) +"\n" +
                       "   Photo white:" + "/n" + getExifPhotoPoints(photoPatchTempl + "0.jpg") + "\n" +
                       "   Photo black:"+  "/n" + getExifPhotoPoints(photoPatchTempl + "1.jpg");

            //resultDynTextSum (Sergei algorithm implementation)
            double MP = Double.parseDouble(prefData.getString("MPixels", "0.1"));
            double MF = Double.parseDouble(prefData.getString("MFocus", "0"));
            int MT = (int) Math.abs(prefData.getLong("timeTestP", 1));

            //parBuf = "TAG_MAKE: " + exif.getAttribute(ExifInterface.TAG_MAKE) + '\n' +
            String stringPhotoWhiteBuf = getExifPhotoPoints(photoPatchTempl + "0.jpg");

            //TODO:AddTry

            double WET = Double.parseDouble(stringPhotoWhiteBuf.split("\n")[1].split(": ")[1]);
            double WA = Double.parseDouble(stringPhotoWhiteBuf.split("\n")[2].split(": ")[1]);
            int WISO = Integer.parseInt(stringPhotoWhiteBuf.split("\n")[3].split(": ")[1]);
            int ISO = 0;
            if (WISO <= 800)
                ISO = 200;
            else if ((WISO > 800) && (WISO <=1600))
                ISO = 700;
            else if ((WISO > 1600) && (WISO <=3200))
                ISO = 1000;
            else if ((WISO > 3200) && (WISO <=6400))
                ISO = 1500;
            if (WISO > 6400)
                ISO = 200;

            finalPoints = (int) (220*MP) + (int) (Math.pow((MF +5*MF),2)) +  (int) (10000 / Math.sqrt(MT)) + (int) (10/Math.tan(WET + 0.01)) + (int) (1000/Math.pow(WA,2)) +ISO;
            return null;
        }

        private int[] getPhotoPoints(String _photoPatch) {
            Bitmap fullBitMap = BitmapFactory.decodeFile(_photoPatch);
            // if smallBitMap = 10x10 (100); aSize = 10
            int aSize= (int) Math.sqrt(ariaSize);
            int cutBitMapWidth = (fullBitMap.getWidth() - aSize);
            int cutBitMapHeight = (fullBitMap.getHeight() - aSize);
            Bitmap smallBitMap = Bitmap.createBitmap(fullBitMap, cutBitMapWidth/2, cutBitMapHeight/2, fullBitMap.getWidth() - cutBitMapWidth, fullBitMap.getHeight() - cutBitMapHeight);
            //clear mem
            fullBitMap.recycle();

            int[] pixels = new int[ariaSize]; // 10x10
            //TODO revert to work with fullBitMap, without smallBitMap
            smallBitMap.getPixels(pixels, 0, smallBitMap.getWidth(), 0, 0, smallBitMap.getWidth(), smallBitMap.getHeight());

            //remove photo
            //File file = new File(_photoPatch);
            //file.delete();


            //clear mem
            smallBitMap.recycle();

            return pixels;

        }

        private double getFirstTestPoints(int PhotoFullColor[], int rezColor) {
            double BufPoints = 0;
            for (int i = 0; i <picturesNum; i++)
            {
                BufPoints += Math.pow(PhotoFullColor[i] - rezColor, 2);
            }
            BufPoints = Math.sqrt( BufPoints/picturesNum );
            return BufPoints;
        }


        private String getExifPhotoPoints(String patch) {
            String parBuf ="";
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(patch);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (exif != null) {
                parBuf = "TAG_MAKE: " + exif.getAttribute(ExifInterface.TAG_MAKE) + '\n' +
                         "TAG_EXPOSURE_TIME: " + exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME) + '\n' +
                         "TAG_APERTURE: " + exif.getAttribute(ExifInterface.TAG_APERTURE) + '\n' +
                         "TAG_ISO: " + exif.getAttribute(ExifInterface.TAG_ISO) + '\n';
            }
            return parBuf;
        }


        protected void onPostExecute(Void arg) {
            //String testText ="1 Test: " + G +'\n';
            //testText +="2 Test:" + '\n' + H1 +'\n' + H2 + '\n' + H3 +'\n';
            //testText +="3 Test:" + '\n' + Y1 +'\n' + Y2 + '\n' + Y3 +'\n';
            //resultText.setText(testText);

            //resultDynText_1.setText( Double.toString(G) + " %");
            //resultDynText_2.setText( Double.toString(H) + " %");
            //resultDynText_3.setText( Double.toString(Y)+ " %");
            //resultDynText_4.setText( Double.toString(YY) + " %");
            //resultDynTextSum.setText(Double.toString(F));

            resultDynTextSum.setText(Integer.toString(finalPoints));

            testTextView.setText(testStr);
            pDialog.dismiss();
        }


    }

}