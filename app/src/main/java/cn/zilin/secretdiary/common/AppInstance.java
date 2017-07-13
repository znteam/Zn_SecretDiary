package cn.zilin.secretdiary.common;

import java.util.ArrayList;

import cn.zilin.secretdiary.bean.AppBean;

public class AppInstance {

	private static AppInstance appInstance;
	private ArrayList<AppBean> appList = new ArrayList<AppBean>();
	
	private AppInstance(){
		appList.add(new AppBean("bizhisuixinbian.png", "壁纸随心变", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=160734"));
		appList.add(new AppBean("bencaogangmu.png", "本草纲目", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=163065"));
		appList.add(new AppBean("shiwuxiangke.png", "食物相克", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=163770"));
		appList.add(new AppBean("huayushijie.png", "花语世界", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=169857"));
		appList.add(new AppBean("pianshudaquan.png", "骗术大全", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=176519"));
		appList.add(new AppBean("jiemengdaquan.png", "解梦大全", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=166964"));
		appList.add(new AppBean("duanxindaquan.png", "短信大全", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=170316"));
		appList.add(new AppBean("fengxiongjihua.png", "丰胸计划", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=173247"));
		appList.add(new AppBean("yingyongxiezaiqi.png", "应用卸载器", "http://m.appchina.com/market-web/lemon/soft_detail.action?id=157166"));
		appList.add(new AppBean("guangzhouditiezhushou.png", "广州地铁助手", "http://apk.gfan.com/Product/App192842.html####"));
	}
	
	public static AppInstance getInstance(){
		if(appInstance == null){
			appInstance = new AppInstance();
		}
		return appInstance;
	}
	
	public ArrayList<AppBean> getAppList(){
		return appList;
	}
	
}
