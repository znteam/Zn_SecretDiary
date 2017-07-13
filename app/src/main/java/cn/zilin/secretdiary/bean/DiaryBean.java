package cn.zilin.secretdiary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DiaryBean implements Parcelable {
	private Integer id;
	private String title;
	private String content;
	private String mood;
	private String sign;
	private String tape;
	private String time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTape() {
		return tape;
	}

	public void setTape(String tape) {
		this.tape = tape;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static final Parcelable.Creator<DiaryBean> CREATOR = new Creator<DiaryBean>() {

		@Override
		public DiaryBean createFromParcel(Parcel source) {
			DiaryBean diary = new DiaryBean();
			diary.id = source.readInt();
			diary.title = source.readString();
			diary.content = source.readString();
			diary.mood = source.readString();
			diary.sign = source.readString();
			diary.tape = source.readString();
			diary.time = source.readString();
			return diary;
		}

		public DiaryBean[] newArray(int size) {
			return new DiaryBean[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(title);
		parcel.writeString(content);
		parcel.writeString(mood);
		parcel.writeString(sign);
		parcel.writeString(tape);
		parcel.writeString(time);
	}
}
