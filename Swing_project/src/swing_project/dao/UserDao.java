package swing_project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import swing_project.vo.UserDataVo;

public class UserDao {
	private static UserDao instance;
	private UserDao() {}
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String ID = "swingtest";
	private final String PW = "1234";
	
	private Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn =  DriverManager.getConnection(URL, ID, PW);
			System.out.println("conn:" + conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private void closeAll(Connection conn,
			PreparedStatement pstm, ResultSet rs) {
		try {
			if(conn != null) conn.close();
			if(pstm != null) pstm.close();
			if(rs != null) rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//아이디로 개인정보 받기
	public UserDataVo searchMyInfo(String id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from userdata"
					+ "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			if(rs.next()) {
				String password = rs.getString("password");
				String userName = rs.getString("username");
				String gender = rs.getString("gender");
				String email = rs.getString("useremail");
				String phoneNumber = rs.getString("phoneNum");
				return new UserDataVo(id, password, userName, gender, email, phoneNumber, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return null;
	}
	
	//유저 현재 비밀번호 확인
	public boolean checkPassword(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) cnt "
					+ "   from userdata "
					+ "   where password = ?"
					+ "   and userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, pw);
			pstm.setString(2, id);
			rs = pstm.executeQuery();
			if(rs.next()) {
				int count = rs.getInt("cnt");
				if(count > 0) {
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return false;
	}
	
	//유저 비밀번호 변경
	public boolean changePassword(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "update userdata"
					+ "   set password = ?"
					+ "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, pw);
			pstm.setString(2, id);
			int count = pstm.executeUpdate();
			if(count > 0) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		return false;
	}
	
	//유저정보 변경
	public boolean changeUser(UserDataVo vo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "update userdata"
					+ "   set username = ?,"
					+ "		  gender = ?,"
					+ "       useremail = ?,"
					+ "       phonenum = ?";
			if(vo.getPassword() != null) {
				sql +=",      password = ?";				
			}
			sql +=    "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, vo.getUserName());
			pstm.setString(2, vo.getGender());
			pstm.setString(3, vo.getEmail());
			pstm.setString(4, vo.getPhoneNumber());
			if(vo.getPassword() != null) {
				pstm.setString(5, vo.getPassword());
				pstm.setString(6, vo.getId());				
			} else {				
				pstm.setString(5, vo.getId());
			}
			int count = pstm.executeUpdate();
			if(count > 0) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		return false;
	}
	
	public boolean applyLec() {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "";
			pstm = conn.prepareStatement(sql);
			
			int count = pstm.executeUpdate();
			if(count > 0) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		
		return false;
	}
	
	//전체 유저 정보 얻기
	public Vector<UserDataVo> searchUserAll(){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from userdata"
					+ "   order by userno";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			Vector<UserDataVo> vec = new Vector<>();
			while(rs.next()) {
				int userNo = rs.getInt("userno");
				String userId = rs.getString("userid");
				String password = rs.getString("password");
				String userName = rs.getString("username");
				String gender = rs.getString("gender");
				String email = rs.getString("useremail");
				String phoneNumber = rs.getString("phoneNum");
				vec.add(new UserDataVo(
						userId, password, userName, gender, email, phoneNumber, userNo));
			}
			return vec;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		return null;
	}
	
	//시퀀스에서 userno 얻기
	public int getNextUserNo() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select seq_userno.nextval"
					+ "   from dual";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(rs.next()) {				
				rs.getInt(1);
			}
			return rs.getInt(1);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return 0;
	}
	
	//유저 삭제
	public boolean deleteById(String deleteId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "delete from userData "
					+ "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, deleteId);
			int count  = pstm.executeUpdate();
			if(count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		return false;
	}
	
	//로그인
	public boolean login(String[] data) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) cnt from userdata "
					+ "   where userid = ? "
					+ "   and password = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, data[0]);
			pstm.setString(2, data[1]);
			rs = pstm.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) {
					return true;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return false;
	}
	
	//회원가입
	public boolean addUser(UserDataVo vo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "insert into userdata(userid,password,"
					+ "      useremail,phonenum,userno,username,gender)"
					+ "   values(?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, vo.getId());
			pstm.setString(2, vo.getPassword());
			pstm.setString(3, vo.getEmail());
			pstm.setString(4, vo.getPhoneNumber());
			pstm.setInt(5, vo.getUserNo());
			pstm.setString(6, vo.getUserName());
			pstm.setString(7, vo.getGender());
			int count = pstm.executeUpdate();
			if(count > 0) {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, null);
		}
		return false;
	}
	
	//ID중복확인
	public boolean isIdExist(String id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) "
					+ "   from userdata "
					+ "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) {
					return true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return false;
	}

}
