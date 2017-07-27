package cn.zilin.secretdiary.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.util.PreferencesUtil;

public class FirstSignDialog {

	private Dialog dialog;
	private Context mContext;

	public FirstSignDialog(Context context) {
		this.mContext = context;
		dialog = new Dialog(context, R.style.my_dialog);
		dialog.setContentView(getFsView(context, dialog));
		dialog.getWindow().setWindowAnimations(R.style.dialog_anim);
		dialog.setCancelable(false);
	}

	private View getFsView(Context context, final Dialog dialog) {
		View firstSignView = LayoutInflater.from(context).inflate(
				R.layout.firstsign, null);
		final EditText pw1Et = (EditText) firstSignView
				.findViewById(R.id.firstsign_et_pw1);
		final EditText pw2Et = (EditText) firstSignView
				.findViewById(R.id.firstsign_et_pw2);
		Button yesBtn = (Button) firstSignView
				.findViewById(R.id.firstsign_btn_yes);
		Button noBtn = (Button) firstSignView
				.findViewById(R.id.firstsign_btn_no);
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (pw1Et.getText().toString() == null
						|| "".equals(pw1Et.getText().toString().trim())) {
					Toast.makeText(v.getContext(), "密码不能为空", 0).show();
				} else if (pw1Et.getText().toString().length() < 6) {
					Toast.makeText(v.getContext(), "密码不能小于6位数", 0).show();
				} else if (!pw1Et.getText().toString()
						.equals(pw2Et.getText().toString())) {
					Toast.makeText(v.getContext(), "两次密码不一致", 0).show();
				} else if (!PreferencesUtil.savePassword(v.getContext(), pw1Et
						.getText().toString())) {
					Toast.makeText(v.getContext(), "系统出错：设置密码失败！请重试", 0).show();
				} else {
					initData();
					Toast.makeText(v.getContext(), "设置成功", 0).show();
					dialog.cancel();
				}

			}
		});

		noBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		return firstSignView;

	}

	public void showDialog() {
		if (dialog != null)
			dialog.show();
	}

	private void initData() {
		DiaryBean diary = new DiaryBean();
		diary.setTitle("点击列表可浏览日记");
		diary.setContent("浏览日记功能：\n1.左右滑屏，可浏览上一篇/下一篇日记\n2.长按屏幕，可编辑日记（删除、修改、标记）\n3.双指缩小，可退出浏览日记到主页\n");
		diary.setSign("1");
		diary.setMood("mood_bg_04.png");
		diary.setTime(System.currentTimeMillis() + "");
		new DiaryManage(mContext).insertDiary(diary);

		diary = new DiaryBean();
		diary.setTitle("点击右下角处添加日记");
		diary.setContent("秘密日记主页功能：\n1.点击列表，可打开浏览日记\n2.长按屏幕，可编辑日记（删除、修改）\n3.拖下列表，可显示/隐藏搜索日记（列表处于顶部状态时）\n4.根据标题关键字搜索相关标题的日记，输入“*”可搜索所有标志重要的日记\n5.菜单或搜索按键可显示/隐藏搜索日记功能\n");
		diary.setSign("1");
		diary.setMood("mood_bg_01.png");
		diary.setTime(System.currentTimeMillis() + "");
		new DiaryManage(mContext).insertDiary(diary);
	}

}
