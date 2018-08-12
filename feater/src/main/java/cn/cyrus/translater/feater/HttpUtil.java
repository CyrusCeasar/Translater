package cn.cyrus.translater.feater;


import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {


    public static String get(String path) {


        if (TextUtils.isEmpty(path))
            return "";
        try {

            URL url = new URL(path.trim());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(200 != urlConnection.getResponseCode())
                return "";


            InputStream is = urlConnection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while(-1 != (len = is.read(buffer))){
                baos.write(buffer,0,len);
                baos.flush();
            }
            return baos.toString("utf-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
