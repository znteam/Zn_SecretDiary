package cn.zilin.secretdiary.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.zilin.secretdiary.adapter.AppAdapter;
import cn.zilin.secretdiary.bean.AppBean;
import cn.zilin.secretdiary.common.AppInstance;

public class AboutActivity extends Activity implements OnClickListener {

	private LinearLayout backLayout;
	private ListView appLv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		backLayout = (LinearLayout) this.findViewById(R.id.about_layout_back);
		appLv = (ListView) this.findViewById(R.id.about_lv_app);

		backLayout.setOnClickListener(this);
		appLv.setAdapter(new AppAdapter(this, R.layout.app_item, AppInstance
				.getInstance().getAppList()));
		appLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				AppBean app = (AppBean) parent.getItemAtPosition(position);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.getLink()));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.about_layout_back:
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
				break;
		}
	}

}