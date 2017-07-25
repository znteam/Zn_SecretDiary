package cn.zilin.secretdiary.ui;

import android.widget.Toast;

/**
 * Created by Zning on 2017/7/25.
 */

public class ToastUtils {
    public static void toToast(String msg) {
        Toast.makeText(ZnApp.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
