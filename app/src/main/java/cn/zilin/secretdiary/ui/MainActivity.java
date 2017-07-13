package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.zilin.secretdiary.adapter.DiaryAdapter;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.DataCommon;
import cn.zilin.secretdiary.task.DiaryTask;
import cn.zilin.secretdiary.ui.MyListView.onFristChangeListener;
import cn.zilin.secretdiary.util.AdUtil;
import cn.zilin.secretdiary.util.PreferencesUtil;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView userIv;
	private ImageView writeIv;
	private MyListView contentLv;
	private RelativeLayout searchLayout;
	private EditText searchEt;
	private TextView hiddenTv;

	private DiaryAdapter adapter;

	private DBBroadCastReceiver dbReceiver;

	private long exitTime = 0;

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
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if(PreferencesUtil.isHelpStatus(this, PreferencesUtil.MAINHELPSTATUS)){
			final ImageView helpIv = new ImageView(MainActivity.this);
			helpIv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			helpIv.setImageResource(R.drawable.help_item1);
			helpIv.setScaleType(ScaleType.FIT_XY);
			helpIv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					helpIv.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.help_anim));
					initMainView();
					PreferencesUtil.saveHelpStatus(MainActivity.this, PreferencesUtil.MAINHELPSTATUS, "ok");
				}
			});
			setContentView(helpIv);
		}else{
			initMainView();
		}
	}

	private void initMainView() {
		setContentView(R.layout.main);
		initLayout();
		registerReceiver();
		AdUtil.initAd(this);
	}

	private void initLayout() {

		userIv = (ImageView) this.findViewById(R.id.main_iv_user);
		writeIv = (ImageView) this.findViewById(R.id.main_iv_write);
		contentLv = (MyListView) this.findViewById(R.id.main_lv_content);
		searchLayout = (RelativeLayout) this
				.findViewById(R.id.main_layout_search);
		searchEt = (EditText) this.findViewById(R.id.main_et_search);
		hiddenTv = (TextView) this.findViewById(R.id.main_tv_hidden);

		userIv.setOnClickListener(this);
		writeIv.setOnClickListener(this);
		hiddenTv.setOnClickListener(this);

		adapter = new DiaryAdapter(this, R.layout.diary_item);
		contentLv.setAdapter(adapter);

		contentLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				DiaryBean diary = (DiaryBean) parent
						.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this,
						DiaryDisplayActivity.class);
				intent.putExtra("index", position);
				intent.putExtra("diary", diary);
				view.startAnimation(AnimationUtils.loadAnimation(
						MainActivity.this, R.anim.scale_big));
				startActivity(intent);
			}
		});

		contentLv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {

				final DiaryBean diary = (DiaryBean) parent
						.getItemAtPosition(position);

				final String title = diary.getTitle().length() > 5 ? diary.getTitle()
						.substring(0, 5) + "..." : diary.getTitle();
				new AlertDialog.Builder(MainActivity.this)
						.setTitle(title)
						.setItems(new String[] { "删除", "修改" },
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										switch (which) {
											case 0:
												showDeleteDialog(title, diary);
												break;
											case 1:
												Intent intent = new Intent(
														MainActivity.this,
														UpdateActivity.class);
												intent.putExtra("diary", diary);
												MainActivity.this
														.startActivity(intent);
												break;
										}
									}
								}).show();
				return true;
			}
		});

		new DiaryTask(this, adapter).execute("");

		contentLv.setOnFristChangeListener(new onFristChangeListener() {
			@Override
			public void update() {
				showSearchView();
			}
		});

		searchEt.addTextChangedListener(new TextWatcher() {

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
				new DiaryTask(MainActivity.this, adapter).execute(searchEt
						.getText().toString().trim());
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
							DataCommon.diaryList.remove(diary);
							adapter.remove(diary);
							Toast.makeText(MainActivity.this, "删除成功", 0).show();

						} else {
							Toast.makeText(
									MainActivity.this,
									"删除失败", 0).show();
						}
					}
				}).show();
	}

	private void showSearchView() {
		if (searchLayout.getVisibility() == View.VISIBLE) {
			showSearchView(false);
		} else {
			showSearchView(true);
		}
	}

	private void showSearchView(boolean flag) {
		if (flag) {
			searchLayout.setVisibility(View.VISIBLE);
		} else {
			searchLayout.setVisibility(View.GONE);
			if (!"".equals(searchEt.getText().toString().trim()))
				searchEt.setText("");
		}
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
			case R.id.main_iv_user:
				startActivity(new Intent(this, UserActivity.class));
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;
			case R.id.main_iv_write:
				startActivity(new Intent(this, WriteActivity.class));
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;
			case R.id.main_tv_hidden:
				showSearchView(false);
				break;
		}
	}

	private void updateData() {
		new DiaryTask(MainActivity.this, adapter).execute("");
	}

	private class DBBroadCastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateData();
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

		} else if (keyCode == KeyEvent.KEYCODE_MENU
				|| keyCode == KeyEvent.KEYCODE_SEARCH) {
			showSearchView();
		}
		return super.onKeyDown(keyCode, event);
	}


}