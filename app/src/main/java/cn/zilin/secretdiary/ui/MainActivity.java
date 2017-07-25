package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;

import cn.zilin.secretdiary.adapter.DiaryAdapter;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.BackgroundThread;
import cn.zilin.secretdiary.common.DataCommon;
import cn.zilin.secretdiary.task.DiaryTask;
import cn.zilin.secretdiary.ui.MyListView.onFristChangeListener;
import cn.zilin.secretdiary.util.PreferencesUtil;

import static cn.zilin.secretdiary.ui.R.layout.diary;

public class MainActivity extends Activity implements OnClickListener {

	private RecyclerView contentRv;
	private FloatingActionButton writeFab;
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

		writeFab.setOnClickListener(this);

		adapter = new DiaryAdapter(new DiaryAdapter.IDiaryListener() {
			@Override
			public void onClick(DiaryBean bean, int pos) {
				Intent intent = new Intent(MainActivity.this,
						DiaryDisplayActivity.class);
				intent.putExtra("index", pos);
				intent.putExtra("diary", diary);
				startActivity(intent);
			}
		});
		contentRv.setAdapter(adapter);
		contentRv.setLayoutManager(new LinearLayoutManager(this));
		initData();

		/*contentLv.setOnItemLongClickListener(new OnItemLongClickListener() {

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
		});*/

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
							DataCommon.diaryList.remove(diary);
							//adapter.remove(diary);
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
				startActivity(new Intent(this, WriteActivity.class));
				break;
		}
	}

	private void updateData() {
		//new DiaryTask(MainActivity.this, adapter).execute("");
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
		}
		return super.onKeyDown(keyCode, event);
	}


}