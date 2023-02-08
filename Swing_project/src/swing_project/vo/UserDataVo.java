package swing_project.vo;

public class UserDataVo {
	private String id, password, userName, gender, email, phoneNumber;
	private int userNo;
	public UserDataVo() {
		super();
	}
	public UserDataVo(String id, String password, String userName, String gender, String email, String phoneNumber,
			int userNo) {
		super();
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userNo = userNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "UserDataVo [id=" + id + ", password=" + password + ", userName=" + userName + ", gender=" + gender
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", userNo=" + userNo + "]";
	}
	
}
