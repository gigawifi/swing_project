package swing_project.vo;

import java.util.Date;

public class RegistVo {
	private int regist_no,
				info_no;
	private String userid;
	private Date regdate;
	
	public RegistVo() {
		super();
	}

	public RegistVo(int regist_no, int info_no, String userid, Date regdate) {
		super();
		this.regist_no = regist_no;
		this.info_no = info_no;
		this.userid = userid;
		this.regdate = regdate;
	}

	public int getRegist_no() {
		return regist_no;
	}

	public void setRegist_no(int regist_no) {
		this.regist_no = regist_no;
	}

	public int getInfo_no() {
		return info_no;
	}

	public void setInfo_no(int info_no) {
		this.info_no = info_no;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "RegistVo [regist_no=" + regist_no + ", info_no=" + info_no + ", userid=" + userid + ", regdate="
				+ regdate + "]";
	}
}
