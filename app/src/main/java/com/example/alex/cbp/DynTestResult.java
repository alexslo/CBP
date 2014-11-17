package com.example.alex.cbp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by alex on 17.10.2014.
 */
public class DynTestResult extends Activity {

    private TextView resultDynText_1, resultDynText_2, resultDynText_3, resultDynText_4, resultDynTextSum;
    private ProgressDialog pDialog;

    private String photoPatchTempl;
    private final int ariaSize =10000;

    //Match
    private double G;
    private double H;
    private double Y;
    private double YY;
    private int F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_dyn);

        resultDynText_1 = (TextView)findViewById(R.id.dyn_rez_1);
        resultDynText_2 = (TextView)findViewById(R.id.dyn_rez_2);
        resultDynText_3 = (TextView)findViewById(R.id.dyn_rez_3);
        resultDynText_4 = (TextView)findViewById(R.id.dyn_rez_4);
        resultDynTextSum = (TextView)findViewById(R.id.dyn_rezults);

        photoPatchTempl = CameraWBTest.saveFolderPatch + CameraWBTest.testPictureName;

        pDialog = new ProgressDialog(DynTestResult.this,ProgressDialog.THEME_HOLO_DARK);
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
            pDialog.setMessage(getResources().getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Создание продукта
         **/
        protected Void doInBackground(Void... args) {

            //Match variable:
            int Rfull[] = {0,0,0};
            int Gfull[] = {0,0,0};
            int Bfull[] = {0,0,0};
            int Rrez, Grez, Brez;
            double Gr, Gg, Gb;
            double H1, H2, H3, Hfull;
            double Y1, Y2, Y3, Yfull;

            for (int pictureCounter =0;pictureCounter<3;pictureCounter++) {

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

            Rrez = (Rfull[0] + Rfull[1] + Rfull[2])/3;
            Grez = (Gfull[0] + Gfull[1] + Gfull[2])/3;
            Brez = (Bfull[0] + Bfull[1] + Bfull[2])/3;
            //Test N1
            Gr = getFirstTestPoints(Rfull[0], Rfull[1], Rfull[2], Rrez);
            Gg = getFirstTestPoints(Gfull[0], Gfull[1], Gfull[2], Grez);
            Gb = getFirstTestPoints(Bfull[0], Bfull[1], Bfull[2], Brez);

            G = (((Gr + Gg + Gb)/3)/255) * 100;

            //Test N2
            H1=  getSecondTestPoints(Rfull[0], Gfull[0], Bfull[0]);
            H2=  getSecondTestPoints(Rfull[1], Gfull[1], Bfull[1]);
            H3=  getSecondTestPoints(Rfull[2], Gfull[2], Bfull[2]);


            Hfull = (H1 + H2 + H3)/3;

            H =  Math.sqrt((Math.pow(H1-Hfull, 2) + Math.pow(H2-Hfull,2) + Math.pow(H3-Hfull,2))/2) ;
            H = (H/Hfull) *100;

            //Test N3
            Y1=  getThirdTestPoints(Rfull[0], Gfull[0], Bfull[0]);
            Y2=  getThirdTestPoints(Rfull[1], Gfull[1], Bfull[1]);
            Y3=  getThirdTestPoints(Rfull[2], Gfull[2], Bfull[2]);

            Yfull = (Y1 + Y2 + Y3)/3;

            Y = Yfull;
            Y = (Math.abs(Y -1.5)/0.5)*100;

            //Test 4
            YY = Math.sqrt((Math.pow(Y1-Yfull, 2) + Math.pow(Y2-Yfull,2) + Math.pow(Y3-Yfull,2))/2);

            YY = (YY/Yfull) *100;

            F = (int) (2500/G + 2500/H +2500/Y +2500/YY);

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
            File file = new File(_photoPatch);
            file.delete();

            //clear mem
            smallBitMap.recycle();

            return pixels;

        }

        private double getFirstTestPoints(int FirstPhotoFullColor, int SecondPhotoFullColor, int thirdPhotoFullColor, int rezColor) {
            return Math.sqrt(  0.3333D* (Math.pow(FirstPhotoFullColor - rezColor, 2) + Math.pow(SecondPhotoFullColor - rezColor, 2) + Math.pow(thirdPhotoFullColor - rezColor, 2)) );
        }
        private double getSecondTestPoints(int R, int G, int B) {
            double H = 0;
            int MAX=0,MIN=0;
            //Find min,max
            MAX = Math.max(R,G);
            MAX = Math.max(MAX,B);
            MIN = Math.min(R,G);
            MIN = Math.min(MIN,B);

            //Algoritm
            if (MAX == MIN)
            {
                H = 0;
            }
            else if ((MAX == R)&&(G >= B))
            {
                H = 60*( (double) (G - B)/(MAX-MIN) );
            }
            else if ((MAX == R)&&(G < B))
            {
                H = (60*( (double) (G - B)/(MAX-MIN) ) )+ 360;
            }
            else if ((MAX == G))
            {
                H = (60*( (double) (B - R)/(MAX-MIN) ) )+ 120;
            }
            else if ((MAX == B))
            {
                H = (60*( (double) (R - G)/(MAX-MIN) ) )+ 240;
            }
            else if(MAX == 0)
            {
                H = 0;
            }
            return H;
        }
        private double getThirdTestPoints(int R, int G, int B) {
            double Y;
            int MAX, MIN;
            int HLSMAX = 240, RGBMAX = 255;
            //Find min,max
            MAX = Math.max(R, G);
            MAX = Math.max(MAX, B);
            MIN = Math.min(R, G);
            MIN = Math.min(MIN, B);

            Y = (double) (MAX + MIN + HLSMAX + RGBMAX)/( 2*RGBMAX);
            return Y;
        }

        protected void onPostExecute(Void arg) {
            //String testText ="1 Test: " + G +'\n';
            //testText +="2 Test:" + '\n' + H1 +'\n' + H2 + '\n' + H3 +'\n';
            //testText +="3 Test:" + '\n' + Y1 +'\n' + Y2 + '\n' + Y3 +'\n';
            //resultText.setText(testText);

            resultDynText_1.setText( Double.toString(G) + " %");
            resultDynText_2.setText( Double.toString(H) + " %");
            resultDynText_3.setText( Double.toString(Y)+ " %");
            resultDynText_4.setText( Double.toString(YY) + " %");
            resultDynTextSum.setText(Double.toString(F));

            pDialog.dismiss();
        }


    }

}