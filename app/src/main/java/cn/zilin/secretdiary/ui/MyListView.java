package cn.zilin.secretdiary.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView{

	private boolean flag = false;

	private onFristChangeListener fcListener;
	private GestureDetector gesture;

	public MyListView(Context context) {
		super(context);
		init(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		gesture = new GestureDetector(context, new MyGestureListener());
		/*
		 * searchView =
		 * LayoutInflater.from(context).inflate(R.layout.list_search, null);
		 * addHeaderView(searchView);
		 */
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
			gesture.onTouchEvent(ev);
		
		return super.dispatchTouchEvent(ev);
	}
	
	public void setOnFristChangeListener(onFristChangeListener fcListener) {
		this.fcListener = fcListener;
	}

	interface onFristChangeListener {
		void update();
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() > -1) {
				flag = true;
			}
			
			if(velocityY > 100 && e2.getY() - e1.getY() > 50 && flag){
				fcListener.update();
			}
			
			flag = false;
			
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
	}
}
