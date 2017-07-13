package cn.zilin.secretdiary.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.MoodInstance;
import cn.zilin.secretdiary.util.AdUtil;
import cn.zilin.secretdiary.util.MyUtil;
import cn.zilin.secretdiary.util.PreferencesUtil;

public class ToSdActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ImageView okIv;
	private EditText pwEt;

	private ProgressDialog pd;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (pd == null) {
					pd = new ProgressDialog(ToSdActivity.this);
					pd.setMessage("正在导出日记中...");
					pd.setCancelable(false);
				}
				pd.show();
				break;
			case 1:
				if (pd != null) {
					pd.dismiss();
				}
				Toast.makeText(ToSdActivity.this, "导出日记成功", 0).show();
				finish();
				break;
			case 2:
				if (pd != null) {
					pd.dismiss();
				}
				Toast.makeText(ToSdActivity.this, "导出日记失败", 0).show();
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
		setContentView(R.layout.tosd);

		backLayout = (LinearLayout) this.findViewById(R.id.tosd_layout_back);
		okIv = (ImageView) this.findViewById(R.id.tosd_iv_ok);
		pwEt = (EditText) this.findViewById(R.id.tosd_et_password);

		backLayout.setOnClickListener(this);
		okIv.setOnClickListener(this);

		AdUtil.initAd(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tosd_layout_back:
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		case R.id.tosd_iv_ok:
			if (PreferencesUtil.checkPassword(this, pwEt.getText().toString())) {
				saveFile();
			} else {
				Toast.makeText(this, "密码不正确", 0).show();
			}
			break;
		}
	}

	private void saveImg(File diaryFile) throws Exception {
		InputStream is = null;
		FileOutputStream fos = null;
		for (String mood : MoodInstance.getInstance().getMoodList()) {
			is = getAssets().open(mood);
			fos = new FileOutputStream(diaryFile + File.separator + mood);
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = is.read(buff)) != -1) {
				fos.write(buff, 0, len);
				fos.flush();
			}
		}
		if (is != null)
			is.close();
		if (fos != null)
			fos.close();
	}

	private void saveDiary(File diaryFile) throws Exception {
		ArrayList<DiaryBean> diaryList = new DiaryManage(this).selectAllDiary();
		StringBuilder sb = new StringBuilder();
		for (DiaryBean diary : diaryList) {
			sb.append("<br /><br /><a>" + MyUtil.convertTime(diary.getTime())
					+ "</a><br /><br /><div><img src='" + diary.getMood()
					+ "' /><h2>" + diary.getTitle() + "</h2><p>"
					+ diary.getContent()
					+ "</p><div><br /><br /><br /><br /><br /><hr />");
		}
		String content = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"zh-CN\"><head><title>"
				+ MyUtil.getTime()
				+ "</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><style type=\"text/css\">body{margin:10px; font-size: 1.1em;}p{text-indent:2em; line-height:1.5em; margin-top:0; margin-bottom:0;}</style></head><body>"
				+ sb.toString() + "</body></html>";

		PrintWriter out = new PrintWriter(new FileWriter(new File(diaryFile,
				MyUtil.getTime() + ".html")));
		out.write(content);
		out.flush();
	}

	private void saveFile() {

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (!sdCardExist) {
			Toast.makeText(this, "sd卡不存在", 0).show();
			return;
		}

		final File diaryFile = new File(Environment.getExternalStorageDirectory()
				.toString() + "/diary");
		if (!diaryFile.isDirectory()) {
			diaryFile.mkdirs();
		}

		mHandler.sendEmptyMessage(0);
		new Thread(){
			public void run() {
				
				try {
					saveImg(diaryFile);
					saveDiary(diaryFile);
					mHandler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(2);
				}
			};
		}.start();

	}

}