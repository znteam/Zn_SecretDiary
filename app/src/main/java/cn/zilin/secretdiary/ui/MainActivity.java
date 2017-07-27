package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

import cn.zilin.secretdiary.adapter.DiaryAdapter;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.BackgroundThread;
import cn.zilin.secretdiary.common.DataCommon;

import static cn.zilin.secretdiary.ui.R.layout.diary;

public class MainActivity extends Activity implements OnClickListener {

	private TextView titleTv;
	private RecyclerView contentRv;
	private FloatingActionButton writeFab;
	private TextView pwdMenuTv, toMeMenuTv, moveDataMenuTv;
	private DrawerLayout rootDrawerLayout;
	private final int DRAWER_GRAVITY_COMPAT = GravityCompat.START;

	private DiaryAdapter adapter;
	private DBBroadCastReceiver dbReceiver;
	private long exitTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initLayout();
		registerReceiver();
	}

	private void initLayout() {
		writeFab = (FloatingActionButton) this.findViewById(R.id.main_fab_write);
		contentRv = (RecyclerView) this.findViewById(R.id.main_rv_content);
		pwdMenuTv = (TextView) this.findViewById(R.id.menu_tv_pwd);
		toMeMenuTv = (TextView) this.findViewById(R.id.menu_tv_tome);
		moveDataMenuTv = (TextView) this.findViewById(R.id.menu_tv_move);
		rootDrawerLayout = (DrawerLayout) findViewById(R.id.main_dl_root);
		titleTv = (TextView) findViewById(R.id.main_tv_title);

		adapter = new DiaryAdapter(new DiaryAdapter.IDiaryListener() {
			@Override
			public void onClick(DiaryBean bean, int pos) {
				Intent intent = new Intent(MainActivity.this,
						DiaryDisplayActivity.class);
				intent.putExtra("index", pos);
				intent.putExtra("diary", bean);
				intent.putParcelableArrayListExtra("diaryList", adapter.getDataList());
				startActivity(intent);
			}

			@Override
			public void onLongClick(final DiaryBean bean, int pos) {
				if (bean == null) {
					return;
				}
				final String title = bean.getTitle().length() > 5 ? bean.getTitle()
						.substring(0, 5) + "..." : bean.getTitle();
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(title)
						.setItems(new String[] { "删除", "修改" },
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										switch (which) {
											case 0:
												showDeleteDialog(title, bean);
												break;
											case 1:
												Intent intent = new Intent(
														MainActivity.this,
														UpdateActivity.class);
												intent.putExtra("diary", bean);
												MainActivity.this
														.startActivity(intent);
												break;
										}
									}
								}).show();
			}
		});

		contentRv.setAdapter(adapter);
		contentRv.setLayoutManager(new LinearLayoutManager(this));

		initListener(writeFab, pwdMenuTv, toMeMenuTv, moveDataMenuTv, titleTv);
		initData();
	}

	private void initListener(View... views) {
		for (View view : views) {
			view.setOnClickListener(this);
		}
	}

	private void initData() {
		BackgroundThread.post(new Runnable() {
			@Override
			public void run() {
				DiaryManage diaryManage = new DiaryManage(ZnApp.getAppContext());
				final ArrayList<DiaryBean> diaryList = diaryManage.selectAllDiary();
				ZnApp.getMainThreadHandler().post(new Runnable() {
					@Override
					public void run() {
						adapter.setData(diaryList);
					}
				});
			}
		});
	}

	private void showDeleteDialog(String title, final DiaryBean diary) {
		new AlertDialog.Builder(this).setTitle(title).setMessage("确定删除该信息吗？")
				.setNegativeButton("取消", null)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (new DiaryManage(
								MainActivity.this)
								.deleteDiary(diary)) {
							adapter.removeData(diary);
							Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

						} else {
							Toast.makeText(
									MainActivity.this,
									"删除失败", Toast.LENGTH_SHORT).show();
						}
					}
				}).show();
	}


	private void registerReceiver() {
		dbReceiver = new DBBroadCastReceiver();
		IntentFilter InsertFilter = new IntentFilter(
				DataCommon.INSERT_DIARY_RECEIVED);
		IntentFilter updateFilter = new IntentFilter(
				DataCommon.UPDATE_DIARY_RECEIVED);
		IntentFilter deleteFilter = new IntentFilter(
				DataCommon.DELETE_DIARY_RECEIVED);
		registerReceiver(dbReceiver, InsertFilter);
		registerReceiver(dbReceiver, updateFilter);
		registerReceiver(dbReceiver, deleteFilter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_fab_write:
				startActivity(WriteActivity.class);
				break;
			case R.id.menu_tv_pwd:
				switchMenu();
				startActivity(PasswordActivity.class);
				break;
			case R.id.menu_tv_tome:
				startActivity(ToMeActivity.class);
				switchMenu();
				break;
			case R.id.menu_tv_move:
				startActivity(MoveDataActivity.class);
				switchMenu();
				break;
			case R.id.main_tv_title:
				switchMenu();
				break;
		}
	}

	private void startActivity(Class<?> clazz){
		startActivity(new Intent(this, clazz));
	}

	private class DBBroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			initData();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				unregisterReceiver(dbReceiver);
				finish();
			}
			return true;
		}


		return super.onKeyDown(keyCode, event);
	}


	private void switchMenu() {
		if (rootDrawerLayout.isDrawerOpen(DRAWER_GRAVITY_COMPAT)) {
			ZnApp.getMainThreadHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					rootDrawerLayout.closeDrawer(DRAWER_GRAVITY_COMPAT);
				}
			}, 500);
		} else {
			rootDrawerLayout.openDrawer(DRAWER_GRAVITY_COMPAT);
		}
	}
}