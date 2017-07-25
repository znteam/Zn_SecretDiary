package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private TextView passwordTv;
	private TextView toMeTv;
	private TextView toSdTv;
	private TextView aboutTv;
	private TextView helpTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);

		backLayout = (LinearLayout) this.findViewById(R.id.user_layout_back);
		passwordTv = (TextView) this.findViewById(R.id.user_tv_change_password);
		toMeTv = (TextView) this.findViewById(R.id.user_tv_tome);
		toSdTv = (TextView) this.findViewById(R.id.user_tv_tosd);
		aboutTv = (TextView) this.findViewById(R.id.user_tv_about);
		helpTv = (TextView) this.findViewById(R.id.user_tv_help);

		backLayout.setOnClickListener(this);
		passwordTv.setOnClickListener(this);
		toMeTv.setOnClickListener(this);
		toSdTv.setOnClickListener(this);
		aboutTv.setOnClickListener(this);
		helpTv.setOnClickListener(this);
		findViewById(R.id.user_tv_move).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.user_layout_back:
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
				break;
			case R.id.user_tv_change_password:
				startActivity(PasswordActivity.class);
				break;
			case R.id.user_tv_tome:
				startActivity(ToMeActivity.class);
				break;
			case R.id.user_tv_tosd:
				startActivity(ToSdActivity.class);
				break;
			case R.id.user_tv_about:
				startActivity(AboutActivity.class);
				break;
			case R.id.user_tv_help:
				startActivity(HelpActivity.class);
				break;
			case R.id.user_tv_move:
				startActivity(MoveDataActivity.class);
				break;
		}
	}

	private void startActivity(Class<?> clazz){
		startActivity(new Intent(this, clazz));
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}


}
