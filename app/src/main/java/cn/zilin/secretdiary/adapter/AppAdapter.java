package cn.zilin.secretdiary.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.zilin.secretdiary.bean.AppBean;
import cn.zilin.secretdiary.ui.R;
import cn.zilin.secretdiary.util.ImageUtil;

public class AppAdapter extends ArrayAdapter<AppBean> {

	private LayoutInflater inflater;
	private int itemId;
	private ArrayList<AppBean> appList;
	
	public AppAdapter(Context context, int textViewResourceId,
			ArrayList<AppBean> appList) {
		super(context, textViewResourceId);
		this.inflater = LayoutInflater.from(context);
		this.itemId = textViewResourceId;
		this.appList = appList;
	}
	
	@Override
	public int getCount() {
		return appList.size();
	}

	@Override
	public AppBean getItem(int position) {
		return appList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(itemId, null);

			holder = new ViewHolder();
			holder.iconIv = (ImageView) convertView
					.findViewById(R.id.app_item_iv_icon);
			holder.titleTv = (TextView) convertView
					.findViewById(R.id.app_item_iv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final AppBean app = getItem(position);

		holder.iconIv.setImageBitmap(ImageUtil.getAssetImg(getContext(), app.getIcon()));
		holder.titleTv.setText(app.getName());

		return convertView;
	}

	class ViewHolder {
		ImageView iconIv;
		TextView titleTv;
	}

}
