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

public class UpdateActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ImageView okIv;
	private ImageView moodIv;
	private ImageView signIv;
	private EditText titleEt;
	private EditText contentEt;

	private LinearLayout moodLayout;

	private int diaryId;
	private String moodPicName;
	private String signStatus = "0";
	private String oldTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����Ϊ�ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����Ϊȫ��ģʽ
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.update);

		init();

		DiaryBean diary = getIntent().getParcelableExtra("diary");
		if (diary != null){
			initData(diary);
		}else{
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
		}

	}

	private void init() {
		backLayout = (LinearLayout) this.findViewById(R.id.update_layout_back);
		okIv = (ImageView) this.findViewById(R.id.update_iv_ok);
		signIv = (ImageView) this.findViewById(R.id.update_iv_sign);
		moodIv = (ImageView) this.findViewById(R.id.update_iv_mood);
		titleEt = (EditText) this.findViewById(R.id.update_et_title);
		contentEt = (EditText) this.findViewById(R.id.update_et_content);
		moodLayout = (LinearLayout) this.findViewById(R.id.update_layout_mood);

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

	private void initData(DiaryBean diary) {
		if ("1".equals(diary.getSign())) {
			signIv.setSelected(true);
		} else {
			signIv.setSelected(false);
		}

		this.diaryId = diary.getId();
		this.oldTime = diary.getTime();
		this.moodPicName = diary.getMood();
		this.signStatus = diary.getSign();
		moodIv.setImageBitmap(ImageUtil.getAssetImg(this, moodPicName));
		titleEt.setText(diary.getTitle());
		contentEt.setText(diary.getContent());
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
		case R.id.update_layout_back:
			back();
			break;
		case R.id.update_iv_sign:

			if (signIv.isSelected()) {
				signIv.setSelected(false);
				Toast.makeText(this, "~O ƽ����һ�� O~", 0).show();
				signStatus = "0";
			} else {
				signIv.setSelected(true);
				Toast.makeText(this, "~O ��Ҫ��һ�� O~", 0).show();
				signStatus = "1";
			}

			break;
		case R.id.update_iv_ok:

			if (checkDiary()) {
				DiaryBean diary = new DiaryBean();
				diary.setId(diaryId);
				diary.setMood(moodPicName);
				diary.setTitle(titleEt.getText().toString());
				diary.setContent(contentEt.getText().toString());
				diary.setTape(null);
				diary.setSign(signStatus);
				diary.setTime(oldTime);

				if (new DiaryManage(this).updateDiary(diary)) {
					Toast.makeText(this, "�޸��ռǳɹ�", 0).show();
					finish();
				} else {
					Toast.makeText(this, "�޸�ʧ�ܣ������±���", 0).show();
				}
			}

			break;
		case R.id.update_iv_mood:
			if (moodLayout.getVisibility() == View.VISIBLE) {
				moodLayout.setVisibility(View.GONE);
			} else {
				moodLayout.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	private boolean checkDiary() {
		if (moodPicName == null || "".equals(moodPicName.trim())) {
			Toast.makeText(this, "�ף��������->ѡ������", 0).show();
			return false;
		} else if ("".equals(titleEt.getText().toString().trim())) {
			Toast.makeText(this, "�ף���ûд����", 0).show();
			return false;
		} else if ("".equals(contentEt.getText().toString().trim())) {
			Toast.makeText(this, "�ף��ǵ�д����", 0).show();
			return false;
		} else if (titleEt.getText().toString().length() > 20) {
			Toast.makeText(this, "�ף����ⲻ�ܳ���20����", 0).show();
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
				moodIv.setImageBitmap(ImageUtil.getAssetImg(UpdateActivity.this,
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
					.setTitle("�˳���ʾ")
					.setMessage("�����޸��ռǣ�")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									UpdateActivity.this.finish();
									overridePendingTransition(R.anim.left_in, R.anim.right_out);
								}
							}).setNegativeButton("ȡ��", null).show();
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
