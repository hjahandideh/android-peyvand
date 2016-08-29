package com.payvand.jahandideh.payvand;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class class_login extends AsyncTask {

    private String Link;
    private String User;
    private String Password;

    public class_login(String link, String user, String password) {
        Link = link;
        User = user;
        Password = password;
    }

    @Override
    protected Object doInBackground(Object... arg0) {
        try {
            String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(User, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");
            URL con = new URL(Link);
            URLConnection connect = con.openConnection();
            connect.setConnectTimeout(15000);
            connect.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connect.getOutputStream());
            wr.write(data);
            wr.close();
            BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = read.readLine()) != null) {
                sb.append(line);
            }
            MainActivity.r = sb.toString();
        } catch (Exception e) {
            Log.i("matis", "error in login()-->" + e.toString());
        }
        return null;
    }
}
