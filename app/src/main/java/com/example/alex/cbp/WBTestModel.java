package com.example.alex.cbp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;

/**
 * Created by alex on 17.10.2014.
 */
public class WBTestModel {
    private String photoPatchTempl;
    private int testPoints[] ={0,0,0};
    private ProgressDialog pDialog;

    public WBTestModel(ProgressDialog _pDialog) {
        photoPatchTempl = CameraWBTest.saveFolderPatch + CameraWBTest.testPictureName;
        pDialog = _pDialog;
    }
    public int[] getWBTestPoints() {
        CalculateTestPoints mCalculateTestPoints = new CalculateTestPoints();
        mCalculateTestPoints.execute();
        return testPoints;



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

        for (int pictureCounter =1;pictureCounter<4;pictureCounter++) {

            String patch = photoPatchTempl + pictureCounter + ".jpg";
            File file = new File(patch);
            if (file.exists())
            {
                testPoints[pictureCounter -1] = getPhotoPoints(patch);
            }
            else testPoints[pictureCounter -1] =0;
        }
            return null;
        }

        /**
         * После оконачния скрываем прогресс диалог
         **/
        protected void onPostExecute() {
            pDialog.dismiss();
        }

        private int getPhotoPoints(String _photoPatch) {
            Bitmap fullBitMap = BitmapFactory.decodeFile(_photoPatch);
            // smallBitMap = 1x1; TODO revert to 10x10
            int cutBitMapWidth = (fullBitMap.getWidth() - 1);
            int cutBitMapHeight = (fullBitMap.getHeight() - 1);
            Bitmap smallBitMap = Bitmap.createBitmap(fullBitMap, cutBitMapWidth/2, cutBitMapHeight/2, fullBitMap.getWidth() - cutBitMapWidth, fullBitMap.getHeight() - cutBitMapHeight);

            return smallBitMap.getPixel(0,0);

        }
    }


}
