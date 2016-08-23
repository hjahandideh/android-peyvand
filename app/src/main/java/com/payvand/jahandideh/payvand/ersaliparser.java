package com.payvand.jahandideh.payvand;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ersaliparser {

    public  List<HashMap<String,Object>> prser(String json){
        List<HashMap<String,Object>> all_nameh =new ArrayList<HashMap<String, Object>>();
        try {

            JSONObject jo=new JSONObject(json);

            JSONArray ja=jo.getJSONArray("nameh");
            for (int i=0;i<ja.length();i++)
            {   HashMap<String,Object> cat=new HashMap<>();

                JSONObject temp=ja.getJSONObject(i);
                cat.put("nnameh",temp.getString("nnameh"));
                cat.put("mnameh",temp.getString("mnameh"));
                cat.put("recive",temp.getString("recive"));
                all_nameh.add(cat);

            }



        }catch (Exception e){
            Log.i("matis","error in ersaliparser()-->"+e.toString());
        }
        return all_nameh;

    }

}

