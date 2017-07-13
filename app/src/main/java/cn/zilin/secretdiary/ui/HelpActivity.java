package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.zilin.secretdiary.util.AdUtil;

public class HelpActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private TextView tv1;
	private TextView tv2;
	private ImageView iv1;
	private ImageView iv2;

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
		setContentView(R.layout.help);

		backLayout = (LinearLayout) this.findViewById(R.id.help_layout_back);

		backLayout.setOnClickListener(this);
		
		tv1 = (TextView) this.findViewById(R.id.help_tv_tv1);
		tv2 = (TextView) this.findViewById(R.id.help_tv_tv2);
		iv1 = (ImageView) this.findViewById(R.id.help_iv_iv1);
		iv2 = (ImageView) this.findViewById(R.id.help_iv_iv2);
		
		tv1.setText("�����ռ���ҳ���ܣ�\n\n1.����б��ɴ�����ռ�\n2.������Ļ���ɱ༭�ռǣ�ɾ�����޸ģ�\n3.�����б�����ʾ/���������ռǣ��б��ڶ���״̬ʱ��\n4.���ݱ���ؼ���������ر�����ռǣ����롰*�����������б�־��Ҫ���ռ�\n5.�˵���������������ʾ/���������ռǹ���");
		iv1.setImageResource(R.drawable.help_item1);
		tv2.setText("\n\n����ռǹ��ܣ�\n\n1.���һ������������һƪ/��һƪ�ռ�\n2.������Ļ���ɱ༭�ռǣ�ɾ�����޸ġ���ǣ�\n3.˫ָ��С�����˳�����ռǵ���ҳ");
		iv2.setImageResource(R.drawable.help_item2);
		
		AdUtil.initAd(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.help_layout_back:
			finish();
			overridePendingTransition(R.anim.left_in, R.anim.right_out);
			break;
		}
	}

}