package cn.zilin.secretdiary.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.ui.R;
import cn.zilin.secretdiary.ui.ZnApp;
import cn.zilin.secretdiary.util.ImageUtil;
import cn.zilin.secretdiary.util.MyUtil;

public class DiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DiaryBean> dataList;
    private LayoutInflater inflater;

    public void setData(List<DiaryBean> addList) {
        if (addList != null) {
            this.dataList = addList;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.diary_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderbase, final int position) {
        Log.i("zning", ">>>" + position);
        final DiaryAdapter.MyViewHolder holder = (DiaryAdapter.MyViewHolder) holderbase;
        DiaryBean diary = getBeanByPosition(position);
        Log.i("zning", ">>>" + diary.getId());

        if (diary == null) {
            return;
        }

        if (TextUtils.equals(diary.getSign(), "1")) {
            holder.signIv.setImageResource(R.drawable.diary_important);
        } else {
            holder.signIv.setImageResource(R.drawable.diary_normal);
        }
        holder.moodIv.setImageBitmap(ImageUtil.getAssetImg(ZnApp.getAppContext(), diary.getMood()));
        holder.titleTv.setText(diary.getTitle());
        holder.contentTv.setText("· " + diary.getContent());
        holder.timeTv.setText(MyUtil.convertTime(diary.getTime()));
    }

    private DiaryBean getBeanByPosition(int position) {
        try {
            return dataList.get(position);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView signIv, moodIv;
        private TextView titleTv, contentTv, timeTv;

        public MyViewHolder(View view) {
            super(view);
            signIv = (ImageView) view
                    .findViewById(R.id.diary_item_iv_sign);
            moodIv = (ImageView) view
                    .findViewById(R.id.diary_item_iv_mood);
            titleTv = (TextView) view
                    .findViewById(R.id.diary_item_tv_title);
            contentTv = (TextView) view
                    .findViewById(R.id.diary_item_tv_content);
            timeTv = (TextView) view
                    .findViewById(R.id.diary_item_tv_time);
        }
    }
}
