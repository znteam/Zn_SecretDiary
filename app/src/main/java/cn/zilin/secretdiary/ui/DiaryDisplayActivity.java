package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.DataCommon;

public class DiaryDisplayActivity extends Activity {

	private GestureDetector gesture;

	private LinearLayout parentLayout;
	private View diaryView;
	private ImageView signIv;
	private TextView pageTv;

	private int index;
	private DiaryBean diary;
	private ArrayList<DiaryBean> diaryList;

	private Animation finishAnim;

	private float downSpace;
	private float upSpace;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary);

		index = getIntent().getIntExtra("index", -1);
		diary = getIntent().getParcelableExtra("diary");
		diaryList = getIntent().getParcelableArrayListExtra("diaryList");

		if (diaryList != null && index != -1 && diary != null) {

			parentLayout = (LinearLayout) findViewById(R.id.diary_layout);
			diaryView = getDiaryView(diary);
			parentLayout.addView(diaryView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

			gesture = new GestureDetector(this, new MyGestureListener());

			finishAnim = AnimationUtils.loadAnimation(this, R.anim.scale_small);
			finishAnim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					finish();
				}
			});
		} else {
			finish();
		}
	}

	private View getDiaryView(DiaryBean diary) {
		diaryView = new DiaryView(DiaryDisplayActivity.this)
				.getDiaryView(diary);
		signIv = (ImageView) diaryView
				.findViewById(R.id.diary_view_iv_sign);
		pageTv = (TextView) diaryView.findViewById(R.id.diary_view_tv_page);
		pageTv.setText(index + 1 + "/" + diaryList.size());
		return diaryView;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_POINTER_DOWN:
				downSpace = spacing(ev);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				upSpace = spacing(ev);
				if (upSpace < downSpace) {
					if (parentLayout != null)
						parentLayout.startAnimation(finishAnim);
				}
				break;
		}

		if (gesture != null)
			gesture.onTouchEvent(ev);

		return super.dispatchTouchEvent(ev);
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
							   float velocityY) {
			lable:
			if (Math.abs(e1.getY() - e2.getY()) > 150) {
				break lable;
			} else if (velocityX > 500 && e1.getX() - e2.getX() < 80
					&& diaryView.getAnimation() == null) {
				index--;
				if (index < 0) {
					Toast.makeText(DiaryDisplayActivity.this, "这是第一页", Toast.LENGTH_SHORT)
							.show();
					index = 0;
					return true;
				}
				diaryView.startAnimation(AnimationUtils.loadAnimation(
						DiaryDisplayActivity.this, R.anim.right_out));
				diary = diaryList.get(index);
				if (parentLayout != null) {
					parentLayout.removeAllViews();
					diaryView = getDiaryView(diary);
					parentLayout.addView(diaryView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				}
				diaryView.startAnimation(AnimationUtils.loadAnimation(
						DiaryDisplayActivity.this, R.anim.left_in));
				System.gc();
				return true;
			} else if (velocityX < -500 && e1.getX() - e2.getX() > 80
					&& diaryView.getAnimation() == null) {
				index++;
				if (index > diaryList.size() - 1) {
					ToastUtils.toToast("这是最后一页");
					index = diaryList.size() - 1;
					return true;
				}
				diaryView.startAnimation(AnimationUtils.loadAnimation(
						DiaryDisplayActivity.this, R.anim.left_out));
				diary = diaryList.get(index);
				if (parentLayout != null) {
					parentLayout.removeAllViews();
					diaryView = getDiaryView(diary);
					parentLayout.addView(diaryView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				}
				diaryView.startAnimation(AnimationUtils.loadAnimation(
						DiaryDisplayActivity.this, R.anim.right_in));
				System.gc();
				return true;
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public void onLongPress(MotionEvent e) {

			new AlertDialog.Builder(DiaryDisplayActivity.this)
					.setTitle("日记功能")
					.setItems(new String[]{"删除", "修改", "标记"},
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									switch (which) {
										case 0:
											showDeleteDialog(diary);
											break;
										case 1:
											Intent intent = new Intent(
													DiaryDisplayActivity.this,
													UpdateActivity.class);
											intent.putExtra("diary", diary);
											DiaryDisplayActivity.this
													.startActivity(intent);
											finish();
											break;
										case 2:

											if ("1".equals(diary.getSign())) {
												diary.setSign("0");
												boolean flag = new DiaryManage(
														DiaryDisplayActivity.this)
														.updateSign(diary);
												if (flag)
													signIv.setImageResource(R.drawable.diary_normal);
											} else {
												diary.setSign("1");
												boolean flag = new DiaryManage(
														DiaryDisplayActivity.this)
														.updateSign(diary);
												if (flag)
													signIv.setImageResource(R.drawable.diary_important);
											}

											break;
									}
								}
							}).show();

			super.onLongPress(e);
		}
	}

	private void showDeleteDialog(final DiaryBean diary) {
		new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("确定删除该信息吗？")
				.setNegativeButton("取消", null)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						if (new DiaryManage(DiaryDisplayActivity.this)
								.deleteDiary(diary)) {
							sendBroadcast(new Intent(
									DataCommon.DELETE_DIARY_RECEIVED));
							finish();
							ToastUtils.toToast("删除成功");
						} else {
							ToastUtils.toToast("删除失败");
						}
					}
				}).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		diaryView = null;
		parentLayout = null;
	}

}
