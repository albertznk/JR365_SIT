package com.goldenictsolutions.win.goldenictjob365.Employee.LanguageHandler;

import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

/**
 * Created by Win on 1/11/2017.
 */

public class LanguageHandler {
    Locale myLocale;
   public static  void changeLocale(Resources res, String locale){
        Configuration config;
        config = new Configuration(res.getConfiguration());

        switch (locale){

            case "en":
                config.locale = Locale.ENGLISH;
                break;
            case "my":
                config.locale = new Locale("my");
                break;

            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
    }



}
