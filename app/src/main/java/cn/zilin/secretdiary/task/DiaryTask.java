package cn.zilin.secretdiary.task;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.business.DiaryManage;
import cn.zilin.secretdiary.common.DataCommon;

public class DiaryTask extends AsyncTask<String, DiaryBean, Void> {

	private ArrayAdapter<DiaryBean> adapter;
	private DiaryManage dm;
	private ArrayList<DiaryBean> diaryList;

	public DiaryTask(Context context, ArrayAdapter<DiaryBean> adapter) {
		this.adapter = adapter;
		dm = new DiaryManage(context);
	}

	@Override
	protected Void doInBackground(String... params) {
		if (!"".equals(params[0].trim())) {
			diaryList = dm.selectDiary(params[0]);
		} else {
			diaryList = dm.selectAllDiary();
			DataCommon.diaryList = diaryList;
		}

		for (DiaryBean diary : diaryList) {
			if (isCancelled())
				break;
			publishProgress(diary);
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (adapter != null)
			adapter.clear();
	}

	@Override
	protected void onProgressUpdate(DiaryBean... values) {
		super.onProgressUpdate(values);
		adapter.add(values[0]);
		adapter.notifyDataSetChanged();
	}

}
