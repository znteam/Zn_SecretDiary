package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.BackgroundThread;

public class MoveDataActivity extends Activity implements OnClickListener {

    private LinearLayout backLayout;
    private Button inputBtn, outputBtn;

    private ProgressDialog pd;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (pd == null) {
                        pd = new ProgressDialog(MoveDataActivity.this);
                        pd.setMessage("正在导出日记中...");
                        pd.setCancelable(false);
                    }
                    pd.show();
                    break;
                case 1:
                    if (pd != null) {
                        pd.dismiss();
                    }
                    Toast.makeText(MoveDataActivity.this, "导出成功", 0).show();
                    break;
                case 2:
                    if (pd != null) {
                        pd.dismiss();
                    }
                    Toast.makeText(MoveDataActivity.this, "导出失败", 0).show();
                    break;
                case 3:
                    if (pd != null) {
                        pd.dismiss();
                    }
                    Toast.makeText(MoveDataActivity.this, "导入成功", 0).show();
                    break;
                case 4:
                    if (pd != null) {
                        pd.dismiss();
                    }
                    Toast.makeText(MoveDataActivity.this, "导入失败", 0).show();
                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置为无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置为全屏模式
        /*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_move_data);

        backLayout = (LinearLayout) this.findViewById(R.id.move_data_layout_back);
        inputBtn = (Button) this.findViewById(R.id.move_data_btn_input);
        outputBtn = (Button) this.findViewById(R.id.move_data_btn_output);

        backLayout.setOnClickListener(this);
        inputBtn.setOnClickListener(this);
        outputBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_data_layout_back:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            case R.id.move_data_btn_output:
                saveFile();
                break;
            case R.id.move_data_btn_input:
                saveData();
                break;
        }
    }

    private void saveData() {
        final File file = new File(Environment.getExternalStorageDirectory() + "/sceretdiary.db");
        if (!file.exists() || !file.isFile()) {
            Toast.makeText(this, "SD卡根目录找不到sceretdiary.db，请先复制后再执行！", Toast.LENGTH_SHORT).show();
            return;
        }
        mHandler.sendEmptyMessage(0);
        BackgroundThread.post(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String readline = "";
                    StringBuffer sb = new StringBuffer();
                    while ((readline = br.readLine()) != null) {
                        System.out.println("readline:" + readline);
                        sb.append(readline);
                    }
                    br.close();
                    DiaryManage diaryManage = new DiaryManage(MoveDataActivity.this);
                    Gson gson = new Gson();
                    List<DiaryBean> diaryBeanList = gson.fromJson(sb.toString(), new TypeToken<List<DiaryBean>>() {
                    }.getType());
                    boolean flag = diaryManage.insertDiaryList(diaryBeanList);
                    mHandler.sendEmptyMessage(flag ? 3 : 4);
                    Log.i("zning", "read>>>" + sb.toString());
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(4);
                    e.printStackTrace();
                }
            }
        });

    }

    private void saveDiary(File diaryFile) throws Exception {
        ArrayList<DiaryBean> diaryList = new DiaryManage(this).selectAllDiary();
        Gson gson = new Gson();
        String gsonStr = gson.toJson(diaryList);
        PrintWriter out = new PrintWriter(new FileWriter(new File(diaryFile,
                "sceretdiary.db")));
        out.write(gsonStr);
        out.flush();
    }

    private void saveFile() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Toast.makeText(this, "sd卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        final File diaryFile = new File(Environment.getExternalStorageDirectory()
                .toString());
        if (!diaryFile.isDirectory()) {
            diaryFile.mkdirs();
        }
        mHandler.sendEmptyMessage(0);
        new Thread() {
            public void run() {

                try {
                    saveDiary(diaryFile);
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(2);
                }
            }

            ;
        }.start();

    }

}