package cn.zilin.secretdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.ui.R;
import cn.zilin.secretdiary.util.ImageUtil;
import cn.zilin.secretdiary.util.MyUtil;

public class DiaryAdapter extends ArrayAdapter<DiaryBean> {

	private LayoutInflater inflater;
	private int itemId;

	public DiaryAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inflater = LayoutInflater.from(context);
		this.itemId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(itemId, null);

			holder = new ViewHolder();
			holder.signIv = (ImageView) convertView
					.findViewById(R.id.diary_item_iv_sign);
			holder.moodIv = (ImageView) convertView
					.findViewById(R.id.diary_item_iv_mood);
			holder.titleTv = (TextView) convertView
					.findViewById(R.id.diary_item_tv_title);
			holder.contentTv = (TextView) convertView
					.findViewById(R.id.diary_item_tv_content);
			holder.timeTv = (TextView) convertView
					.findViewById(R.id.diary_item_tv_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final DiaryBean diary = getItem(position);

		if(diary.getSign() != null && "1".equals(diary.getSign())){
			holder.signIv.setImageResource(R.drawable.diary_important);
		}else{
			holder.signIv.setImageResource(R.drawable.diary_normal);
		}
		holder.moodIv.setImageBitmap(ImageUtil.getAssetImg(getContext(),diary.getMood()));
		holder.titleTv.setText(diary.getTitle());
		holder.contentTv.setText("· " + diary.getContent());
		holder.timeTv.setText(MyUtil.convertTime(diary.getTime()));

		return convertView;
	}

	class ViewHolder {
		ImageView moodIv;
		TextView titleTv;
		TextView contentTv;
		ImageView signIv;
		ImageView tapeIv;
		TextView timeTv;
	}

}
