package com.example.cs125finalproject_wordsearchsolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

// CREDIT: API implementation based off https://www.thecodecity.com/2016/09/creating-ocr-android-app-using-tesseract.html

public class MyTessOCR {
    private String datapath;
    private TessBaseAPI mTess;    Context context;
    public MyTessOCR(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        datapath = Environment.getExternalStorageDirectory().toString() + "/ocrctz/";
        System.out.println(datapath);
        File dir = new File(datapath + "/tessdata/");
        File file = new File(datapath + "/tessdata/" + "eng.traineddata");

        if (!file.exists()) {
            Log.d("mylog", "in file doesn't exist");
            dir.mkdirs();
            copyFile(context);
        }
        Log.d("debug", "API initialization");
        mTess = new TessBaseAPI();
        String language = "eng";
        Log.d("debug", "before init");
        mTess.init(datapath, language);
        Log.d("debug", "after init");
        //Auto only
        mTess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_ONLY);
    }

    public void stopRecognition() {
        mTess.stop();
    }

    public String getOCRResult(Bitmap bitmap) {
        Log.d("debug", "before set image");
        mTess.setImage(bitmap);
        Log.d("debug", "after set image");
        String result = mTess.getUTF8Text();
        mTess.end();
        Log.d("debug", "after getUTF8Text");
        return result;
    }

    public void onDestroy() {
        if (mTess != null)
            mTess.end();
    }

    private void copyFile(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            Log.d("debug", "copyFile");
            InputStream in = assetManager.open("eng.traineddata");
            OutputStream out = new FileOutputStream(datapath + "/tessdata/" + "eng.traineddata");                    byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            while (read != -1) {
                out.write(buffer, 0, read);
                read = in.read(buffer);
            }
        } catch (Exception e) {
            Log.d("mylog", "couldn't copy with the following error : "+e.toString());
        }
    }
}