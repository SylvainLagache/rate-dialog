package com.lagache.sylvain.AppRateDialog.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by user on 29/01/2016.
 */
public class IntentHelper {

    public static void openEmailIntent(Context context, String emailAddress, String emailObject){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailObject);
        Intent chooserIntent = Intent.createChooser(emailIntent, null);
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooserIntent);
    }

    public static void openPlayStoreIntent(Context context, String appPackage){
        try {
            Intent storeIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackage));
            storeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(storeIntent);
        } catch (android.content.ActivityNotFoundException anfe) {
            Intent storeIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackage));
            storeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(storeIntent);
        }
    }

}
