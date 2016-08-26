package com.payvand.jahandideh.payvand;
import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class FontDastnevis extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/BYekan+.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}