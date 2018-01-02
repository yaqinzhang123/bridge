package com.icardreader;


import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.Callback;
import com.rsk.api.ICard;
import com.rsk.api.ICardInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.util.Base64;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rsk.api.RskApi;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by jeffddjt on 2017/12/1.
 */

public class ICToastModule extends ReactContextBaseJavaModule {

    public ICToastModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        // DyEvent.myContext=reactContext;
        try {
            //ICard.Open();

           //RskApi.PrintOpen();
        } catch (Exception e) {
            Toast.makeText(getReactApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public String getName() {
        return "ICToastAndroid";
    }

    @ReactMethod
    public void read(Callback callback) {
            ICard.Open();
            ICardInfo info = new ICardInfo();
            int nResult = ICard.Read(info);
            try {
                if (nResult == 0) {
                    String str = JSON.toJSONString(info);
                    if(callback!=null)
                        callback.invoke(str);
                }
            } catch (Exception e) {
                Toast.makeText(getReactApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
           ICard.Close();
    }

    private Bitmap getImg(){
            Bitmap bitmap=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/test.png"));
            return bitmap;
    }
    @ReactMethod
    public void print(String str1,String str2) {
        try{
            RskApi.ZGOpenPower();
            Thread.sleep(2000);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String time = formatter.format(curDate);
            RskApi.PrintOpen();
            RskApi.PrintChars("  秦皇岛市北戴河区交通运输局\r\n");
            str1=str1.replace("*","  ");
            String[] str=str1.split("<br/>");
            for(int i=0;i<str.length;i++) {
                RskApi.PrintChars(str[i]);
                RskApi.PrintLine();
                Thread.sleep(200);
            }
                Bitmap bitmap = getImg();
                RskApi.PrintBitmap(bitmap);
                Thread.sleep(200);
            RskApi.PrintChars("当事人签名：\r\n");

            if(str2.length()>5){
                String imageData1 = str2.substring(str2.indexOf(",") + 1);
                if(imageData1!=""){
                Bitmap bmap = rotateBitmap(imageData1,150,75);
                RskApi.PrintBitmap(bmap);
                Thread.sleep(200);
            	}
            }
            else{
            	Bitmap bp=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/empty.png"));
                RskApi.PrintBitmap(bp);
                Thread.sleep(200);
            }
            RskApi.PrintChars("打印时间："+time);
            Thread.sleep(200);
            byte[] Byte1="\r\n\r\n\r\n\r\n\r\n\n\n".getBytes("GBK");
            RskApi.PrintChars(Byte1, Byte1.length);
        } catch (Exception e) {
            Toast.makeText(getReactApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        waitPrint();
        Toast.makeText(getReactApplicationContext(), "打印完毕...", Toast.LENGTH_LONG).show();
    }

    private Bitmap rotateBitmap(String string,int w,int h) {
        byte[] buf = Base64.decode(string, Base64.DEFAULT);
        Bitmap BitmapOrg =BitmapFactory.decodeByteArray(buf, 0, buf.length);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }
    private void waitPrint(){
        byte[] versionNo = new byte[50];
        int[] versionNoLen = new int[1];
        while(true){
            int nResult = RskApi.ZGGetVersion(versionNo, versionNoLen);
            if (nResult == 0){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}


