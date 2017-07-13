package cn.zilin.secretdiary.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.MoodInstance;
import cn.zilin.secretdiary.util.ImageUtil;

public class WriteActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ImageView okIv;
	private ImageView moodIv;
	private ImageView signIv;
	private EditText titleEt;
	private EditText contentEt;

	private LinearLayout moodLayout;

	private String moodPicName;
	private String signStatus = "0";

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
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.write);

		init();

	}

	private void init() {
		backLayout = (LinearLayout) this.findViewById(R.id.write_layout_back);
		okIv = (ImageView) this.findViewById(R.id.write_iv_ok);
		signIv = (ImageView) this.findViewById(R.id.write_iv_sign);
		moodIv = (ImageView) this.findViewById(R.id.write_iv_mood);
		titleEt = (EditText) this.findViewById(R.id.write_et_title);
		contentEt = (EditText) this.findViewById(R.id.write_et_content);
		moodLayout = (LinearLayout) this.findViewById(R.id.write_layout_mood);

		initMoodLayout();

		backLayout.setOnClickListener(this);
		okIv.setOnClickListener(this);
		signIv.setOnClickListener(this);
		moodIv.setOnClickListener(this);

		contentEt.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				moodLayout.setVisibility(View.GONE);
				return false;
			}
		});
	}

	private void initMoodLayout() {
		ArrayList<String> moodList = MoodInstance.getInstance().getMoodList();
		for (String picName : moodList) {
			moodLayout.addView(getMoodView(this, picName));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.write_layout_back:
				back();
				break;
			case R.id.write_iv_sign:

				if (signIv.isSelected()) {
					signIv.setSelected(false);
					Toast.makeText(this, "~O 平凡的一天 O~", 0).show();
					signStatus = "0";
				} else {
					signIv.setSelected(true);
					Toast.makeText(this, "~O 重要的一天 O~", 0).show();
					signStatus = "1";
				}

				break;
			case R.id.write_iv_ok:

				if (checkDiary()) {
					DiaryBean diary = new DiaryBean();
					diary.setMood(moodPicName);
					diary.setTitle(titleEt.getText().toString());
					diary.setContent(contentEt.getText().toString());
					diary.setTape(null);
					diary.setSign(signStatus);
					diary.setTime(System.currentTimeMillis() + "");

					if (new DiaryManage(this).insertDiary(diary)) {
						Toast.makeText(this, "保存日记成功", 0).show();
						finish();
						overridePendingTransition(R.anim.left_in, R.anim.right_out);
					} else {
						Toast.makeText(this, "保存失败，请重新保存", 0).show();
					}
				}

				break;
			case R.id.write_iv_mood:
				if (moodLayout.getVisibility() == View.VISIBLE) {
					moodLayout.setVisibility(View.GONE);
				} else {
					moodLayout.setVisibility(View.VISIBLE);
					Toast.makeText(WriteActivity.this, "左右滚动，选择心情", 0).show();
				}
				break;
		}
	}

	private boolean checkDiary() {
		if (moodPicName == null || "".equals(moodPicName.trim())) {
			Toast.makeText(this, "亲，点击心情->选择心情", 0).show();
			return false;
		} else if ("".equals(titleEt.getText().toString().trim())) {
			Toast.makeText(this, "亲，还没写标题", 0).show();
			return false;
		} else if ("".equals(contentEt.getText().toString().trim())) {
			Toast.makeText(this, "亲，记得写内容", 0).show();
			return false;
		} else if (titleEt.getText().toString().length() > 20) {
			Toast.makeText(this, "亲，标题不能超过20个字", 0).show();
			return false;
		}
		return true;
	}

	public ImageView getMoodView(Context context, final String picName) {
		ImageView moodView = new ImageView(context);
		moodView.setImageBitmap(ImageUtil.getAssetImg(this, picName));
		moodView.setPadding(10, 10, 10, 10);
		moodView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				moodPicName = picName;
				moodIv.setImageBitmap(ImageUtil.getAssetImg(WriteActivity.this,
						picName));
				moodLayout.setVisibility(View.GONE);
			}
		});
		return moodView;
	}

	private void back() {
		if (!"".equals(titleEt.getText().toString())
				|| !"".equals(contentEt.getText().toString())) {
			new AlertDialog.Builder(this)
					.setTitle("退出提示")
					.setMessage("放弃这篇日记？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									WriteActivity.this.finish();
									overridePendingTransition(R.anim.left_in, R.anim.right_out);
								}
							}).setNegativeButton("取消", null).show();
		} else {
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}
}
