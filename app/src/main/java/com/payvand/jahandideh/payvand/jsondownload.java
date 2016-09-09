package com.payvand.jahandideh.payvand;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class jsondownload {


    public   String downloadurl(String strurl){

        InputStream stream;
        String data="";
            try {
                String dat= "";
                URL ur = new URL(strurl);

                HttpURLConnection uc = (HttpURLConnection) ur.openConnection();

                uc.setReadTimeout(10000);

                uc.setConnectTimeout(15000);

                uc.setRequestMethod("POST");

                uc.setDoInput(true);
                uc.connect();
                OutputStreamWriter wy= new OutputStreamWriter(uc.getOutputStream());
                wy.write(dat);
                wy.close();

                uc.connect();

                stream = uc.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(stream));

                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = br.readLine()) != null) {

                    sb.append(line);

                }
                data=sb.toString();

                stream.close();

                br.close();

                uc.disconnect();

            } catch (Exception e) {

                Log.i("matis", "error in json download()-->" + e.toString());

            }

        return (data);
        }

    }
