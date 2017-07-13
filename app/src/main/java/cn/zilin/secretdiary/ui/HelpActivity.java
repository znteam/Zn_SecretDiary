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

		// 设置为无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置为全屏模式
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

		tv1.setText("秘密日记主页功能：\n\n1.点击列表，可打开浏览日记\n2.长按屏幕，可编辑日记（删除、修改）\n3.拖下列表，可显示/隐藏搜索日记（列表处于顶部状态时）\n4.根据标题关键字搜索相关标题的日记，输入“*”可搜索所有标志重要的日记\n5.菜单或搜索按键可显示/隐藏搜索日记功能");
		iv1.setImageResource(R.drawable.help_item1);
		tv2.setText("\n\n浏览日记功能：\n\n1.左右滑屏，可浏览上一篇/下一篇日记\n2.长按屏幕，可编辑日记（删除、修改、标记）\n3.双指缩小，可退出浏览日记到主页");
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