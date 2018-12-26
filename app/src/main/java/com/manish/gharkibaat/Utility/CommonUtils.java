package com.manish.gharkibaat.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.manish.gharkibaat.BuildConfig;
import com.manish.gharkibaat.R;

/**********************************
 * Created by Manish on 01-Oct-18
 ***********************************/
public class CommonUtils {

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static void printErrorLog(String tag, String message) {
        if (BuildConfig.DEBUG && message != null) {
            Log.e(tag, message);
        }
    }

    public static void showToastMessage(String message, Context context) {
        if (message != null && context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static Dialog showLoadingBar(Context context) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void hideLoadingBar(Dialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
