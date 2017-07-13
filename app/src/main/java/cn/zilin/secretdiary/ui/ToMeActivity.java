package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.zilin.secretdiary.util.AdUtil;

public class ToMeActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ImageView okIv;
	private EditText contentEt;

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
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.tome);

		backLayout = (LinearLayout) this.findViewById(R.id.tome_layout_back);
		okIv = (ImageView) this.findViewById(R.id.tome_iv_ok);
		contentEt = (EditText) this.findViewById(R.id.tome_et_content);

		backLayout.setOnClickListener(this);
		okIv.setOnClickListener(this);

		AdUtil.initAd(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tome_layout_back:
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		case R.id.tome_iv_ok:

			if (contentEt.getText().toString() != null
					&& !"".equals(contentEt.getText().toString().trim())) {

				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("plain/text");
				intent.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "ht521880601@qq.com" });
				intent.putExtra(Intent.EXTRA_SUBJECT, "�����ռ�->�������");
				intent.putExtra(android.content.Intent.EXTRA_TEXT, contentEt
						.getText().toString().trim());
				startActivity(Intent.createChooser(intent, "tome..."));
			}
			break;
		}
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}