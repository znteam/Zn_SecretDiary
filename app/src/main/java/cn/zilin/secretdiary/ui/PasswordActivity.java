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
import android.widget.TextView;
import android.widget.Toast;

import cn.zilin.secretdiary.util.PreferencesUtil;

public class PasswordActivity extends Activity implements OnClickListener {

	private TextView backTv;
	private ImageView okIv;
	private EditText oldPwEt;
	private EditText newPw1Et;
	private EditText newPw2Et;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		backTv = (TextView) this.findViewById(R.id.password_tv_back);
		okIv = (ImageView) this.findViewById(R.id.password_iv_ok);
		oldPwEt = (EditText) this.findViewById(R.id.password_et_old_pw);
		newPw1Et = (EditText) this.findViewById(R.id.password_et_new_pw1);
		newPw2Et = (EditText) this.findViewById(R.id.password_et_new_pw2);

		backTv.setOnClickListener(this);
		okIv.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.password_tv_back:
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
				break;
			case R.id.password_iv_ok:
				String oldPw = oldPwEt.getText().toString();
				String newPw1 = newPw1Et.getText().toString();
				String newPw2 = newPw2Et.getText().toString();

				if("".equals(oldPw.trim())){
					ToastUtils.toToast("原密码不能为空");
				}else if("".equals(newPw1.trim())){
					ToastUtils.toToast("新密码不能为空");
				}else if("".equals(newPw2.trim())){
					ToastUtils.toToast("确认密码不能为空");
				} else if (newPw1.length() < 6) {
					ToastUtils.toToast("新密码不能小于6位数");
					newPw1Et.setText("");
					newPw2Et.setText("");
				} else if(!newPw1.equals(newPw2)){
					ToastUtils.toToast("两次输入新密码不一致");
					newPw1Et.setText("");
					newPw2Et.setText("");
				}else if(!PreferencesUtil.checkPassword(this, oldPw)){
					ToastUtils.toToast("原密码不正确");
					oldPwEt.setText("");
					newPw1Et.setText("");
					newPw2Et.setText("");
				}else if(!PreferencesUtil.savePassword(this, newPw1)){
					ToastUtils.toToast("系统出错：设置新密码失败！请重试");
				}else{
					ToastUtils.toToast("设置新密码成功");
					finish();
				}

				break;
		}
	}

}