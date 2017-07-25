package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.zilin.secretdiary.common.WelBgInstance;
import cn.zilin.secretdiary.util.ImageUtil;
import cn.zilin.secretdiary.util.PreferencesUtil;

public class WelActivity extends Activity implements OnClickListener {

	private ImageView bgIv;
	private ImageView toMainIv;
	private EditText passwordEt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置为无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置为全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.wel);

		initLayout();
		initBg();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (PreferencesUtil.checkFirstSign(WelActivity.this)) {
					new FirstSignDialog(WelActivity.this).showDialog();
				}
			}
		}, 500);
	}

	private void initLayout() {
		bgIv = (ImageView) this.findViewById(R.id.wel_iv_bg);
		toMainIv = (ImageView) this.findViewById(R.id.wel_iv_tomain);
		passwordEt = (EditText) this.findViewById(R.id.wel_et_password);

		bgIv.setImageBitmap(ImageUtil.getAssetImg(this, WelBgInstance
				.getInstance().getRandomBg()));
		toMainIv.setOnClickListener(this);
		passwordEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (PreferencesUtil.checkPassword(ZnApp.getAppContext(), passwordEt.getText().toString())) {
					startActivity(new Intent(ZnApp.getAppContext(), MainActivity.class));
					overridePendingTransition(R.anim.down_in, R.anim.up_out);
					finish();
				}
			}
		});
	}

	private void initBg() {
		final TranslateAnimation right = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				-1f, Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0f);
		final TranslateAnimation left = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				bgIv.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				bgIv.startAnimation(right);
			}
		});
		bgIv.startAnimation(right);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.wel_iv_tomain:
				goMain();
				break;
		}
	}

	private void goMain() {
		if (passwordEt.getText() == null
				|| "".equals(passwordEt.getText().toString().trim())) {
			ToastUtils.toToast("请输入密码");
		} else if (!PreferencesUtil.checkPassword(this, passwordEt.getText()
				.toString())) {
			ToastUtils.toToast("密码不正确");
		} else {
			startActivity(new Intent(this, MainActivity.class));
			overridePendingTransition(R.anim.down_in, R.anim.up_out);
			finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP)
			goMain();
		return super.onTouchEvent(event);
	}

}