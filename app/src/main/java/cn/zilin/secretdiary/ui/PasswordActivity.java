package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.zilin.secretdiary.util.PreferencesUtil;

public class PasswordActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ImageView okIv;
	private EditText oldPwEt;
	private EditText newPw1Et;
	private EditText newPw2Et;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		backLayout = (LinearLayout) this.findViewById(R.id.password_layout_back);
		okIv = (ImageView) this.findViewById(R.id.password_iv_ok);
		oldPwEt = (EditText) this.findViewById(R.id.password_et_old_pw);
		newPw1Et = (EditText) this.findViewById(R.id.password_et_new_pw1);
		newPw2Et = (EditText) this.findViewById(R.id.password_et_new_pw2);

		backLayout.setOnClickListener(this);
		okIv.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.password_layout_back:
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
				break;
			case R.id.password_iv_ok:
				String oldPw = oldPwEt.getText().toString();
				String newPw1 = newPw1Et.getText().toString();
				String newPw2 = newPw2Et.getText().toString();

				if("".equals(oldPw.trim())){
					Toast.makeText(this, "原密码不能为空", 0).show();
				}else if("".equals(newPw1.trim())){
					Toast.makeText(this, "新密码不能为空", 0).show();
				}else if("".equals(newPw2.trim())){
					Toast.makeText(this, "确认密码不能为空", 0).show();
				} else if (newPw1.length() < 6) {
					Toast.makeText(v.getContext(), "新密码不能小于6位数", 0).show();
					newPw1Et.setText("");
					newPw2Et.setText("");
				} else if(!newPw1.equals(newPw2)){
					Toast.makeText(this, "两次输入新密码不一致", 0).show();
					newPw1Et.setText("");
					newPw2Et.setText("");
				}else if(!PreferencesUtil.checkPassword(this, oldPw)){
					Toast.makeText(this, "原密码不正确", 0).show();
					oldPwEt.setText("");
					newPw1Et.setText("");
					newPw2Et.setText("");
				}else if(!PreferencesUtil.savePassword(this, newPw1)){
					Toast.makeText(this, "系统出错：设置新密码失败！请重试", 0).show();
				}else{
					Toast.makeText(this, "设置新密码成功", 0).show();
					finish();
				}

				break;
		}
	}

}