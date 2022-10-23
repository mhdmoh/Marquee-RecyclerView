package mhdmoh.marquee_recyclerview;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Arrays;

public class Utils {
    static boolean forceRtl = false;

    public static boolean isAr(Context context) {
        if (forceRtl) return true;
        String[] rtlLanguageCodes = context.getResources().getStringArray(R.array.rtl_language_codes);
        Configuration configuration = context.getResources().getConfiguration();
        Log.d("Utils", "isAr: " + configuration.locale.getLanguage());
        return Arrays.asList(rtlLanguageCodes).contains(configuration.locale.getLanguage());
    }
}
