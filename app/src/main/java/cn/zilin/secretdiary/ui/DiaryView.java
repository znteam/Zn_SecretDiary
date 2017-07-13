package cn.zilin.secretdiary.ui;

import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.ui.R;
import cn.zilin.secretdiary.util.ImageUtil;
import cn.zilin.secretdiary.util.MyUtil;

public class DiaryView {

	private View parentView;
	//private View topView;
	private ImageView moodIv;
	private ImageView signIv;
	private TextView titleTv;
	private TextView contentTv;
	private TextView timeTv;
	private Context mContext;
	private int[] styles = { 0xFF9370db, 0xFFff4500, 0xFF32cd32, 0xFF00ff00, 0xFF800000, 0xFFEE55AA, 0xFF6495ED, 0xFF28b3f4, 0xFFf73c29};
	
	public DiaryView(Context context){
		this.mContext = context;
		parentView = LayoutInflater.from(context).inflate(R.layout.diary_view, null);
		//topView = parentView.findViewById(R.id.diary_view_view_top);
		moodIv = (ImageView) parentView.findViewById(R.id.diary_view_iv_mood);
		signIv = (ImageView) parentView.findViewById(R.id.diary_view_iv_sign);
		titleTv = (TextView) parentView.findViewById(R.id.diary_view_tv_title);
		contentTv = (TextView) parentView.findViewById(R.id.diary_view_tv_content);
		timeTv = (TextView) parentView.findViewById(R.id.diary_view_tv_time);
	}
	
	public View getDiaryView(DiaryBean diary){
		if(diary.getSign() != null && "1".equals(diary.getSign())){
			signIv.setImageResource(R.drawable.diary_important);
		}else{
			signIv.setImageResource(R.drawable.diary_normal);
		}
		
		moodIv.setImageBitmap(ImageUtil.getAssetImg(mContext, diary.getMood()));
		titleTv.setText(diary.getTitle());
		contentTv.setText("\t\t"+MyUtil.toDBC(diary.getContent()));
		timeTv.setText(MyUtil.convertTime(diary.getTime()));
		setBgStyle();
		
		return parentView;
		
	}
	
	public View getSignIv(){
		return signIv;
	}
	
	public void setBgStyle(){
		int index = new Random().nextInt(styles.length);
		//topView.setBackgroundColor(styles[index]);
		timeTv.setBackgroundColor(styles[index]);
	}
	
}
