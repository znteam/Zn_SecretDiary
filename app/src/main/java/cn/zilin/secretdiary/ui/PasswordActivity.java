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
import cn.zilin.secretdiary.util.AdUtil;
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

		// ����Ϊ�ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����Ϊȫ��ģʽ
		/*
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.password);

		backLayout = (LinearLayout) this.findViewById(R.id.password_layout_back);
		okIv = (ImageView) this.findViewById(R.id.password_iv_ok);
		oldPwEt = (EditText) this.findViewById(R.id.password_et_old_pw);
		newPw1Et = (EditText) this.findViewById(R.id.password_et_new_pw1);
		newPw2Et = (EditText) this.findViewById(R.id.password_et_new_pw2);

		backLayout.setOnClickListener(this);
		okIv.setOnClickListener(this);

		AdUtil.initAd(this);
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
				Toast.makeText(this, "ԭ���벻��Ϊ��", 0).show();
			}else if("".equals(newPw1.trim())){
				Toast.makeText(this, "�����벻��Ϊ��", 0).show();
			}else if("".equals(newPw2.trim())){
				Toast.makeText(this, "ȷ�����벻��Ϊ��", 0).show();
			} else if (newPw1.length() < 6) {
				Toast.makeText(v.getContext(), "�����벻��С��6λ��", 0).show();
				newPw1Et.setText("");
				newPw2Et.setText("");
			} else if(!newPw1.equals(newPw2)){
				Toast.makeText(this, "�������������벻һ��", 0).show();
				newPw1Et.setText("");
				newPw2Et.setText("");
			}else if(!PreferencesUtil.checkPassword(this, oldPw)){
				Toast.makeText(this, "ԭ���벻��ȷ", 0).show();
				oldPwEt.setText("");
				newPw1Et.setText("");
				newPw2Et.setText("");
			}else if(!PreferencesUtil.savePassword(this, newPw1)){
				Toast.makeText(this, "ϵͳ��������������ʧ�ܣ�������", 0).show();
			}else{
				Toast.makeText(this, "����������ɹ�", 0).show();
				finish();
			}
			
			break;
		}
	}
	
}