package cn.zilin.secretdiary.bean;

public class AppBean {

	private String icon;
	private String name;
	private String link;
	
	public AppBean(String icon, String name, String link) {
		super();
		this.icon = icon;
		this.name = name;
		this.link = link;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
