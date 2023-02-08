package swing_project.vo;

import java.util.Date;

public class InfoVo {
	private String info_name,
				   info_location;
	private int info_cap,
				info_no,
				info_var;
	private Date info_date,
	             my_regdate;
	public InfoVo() {
		super();
	}
	
	public InfoVo(String info_name, String info_location, int info_cap, int info_no, int info_var, Date info_date,
			Date my_regdate) {
		super();
		this.info_name = info_name;
		this.info_location = info_location;
		this.info_cap = info_cap;
		this.info_no = info_no;
		this.info_var = info_var;
		this.info_date = info_date;
		this.my_regdate = my_regdate;
	}

	public String getInfo_name() {
		return info_name;
	}
	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}
	public String getInfo_location() {
		return info_location;
	}
	public void setInfo_location(String info_location) {
		this.info_location = info_location;
	}
	public int getInfo_cap() {
		return info_cap;
	}
	public void setInfo_cap(int info_cap) {
		this.info_cap = info_cap;
	}
	public int getInfo_no() {
		return info_no;
	}
	public void setInfo_no(int info_no) {
		this.info_no = info_no;
	}
	public int getInfo_var() {
		return info_var;
	}
	public void setInfo_var(int info_var) {
		this.info_var = info_var;
	}
	public Date getInfo_date() {
		return info_date;
	}
	public void setInfo_date(Date info_date) {
		this.info_date = info_date;
	}
	public Date getMy_regdate() {
		return my_regdate;
	}
	public void setMy_regdate(Date my_regdate) {
		this.my_regdate = my_regdate;
	}
	@Override
	public String toString() {
		return "InfoVo [info_name=" + info_name + ", info_location=" + info_location + ", info_cap=" + info_cap
				+ ", info_no=" + info_no + ", info_var=" + info_var + ", info_date=" + info_date + ", my_regdate="
				+ my_regdate + "]";
	}
	
}
