package swing_project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import swing_project.vo.InfoVo;
import swing_project.vo.RegistVo;

public class RegistDao {
	private static RegistDao instance;
	private RegistDao() {}
	public static RegistDao getInstance() {
		if(instance == null) {
			instance = new RegistDao();
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
	
	//강좌 현재 인원 구하기
	public int getCapCount(int info_no) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) from registdata "
					+ "   where info_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, info_no);
			rs = pstm.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return 0;
	}
	
	//등록한 강좌인지 체크
	public boolean isMyInfoExist(String userid, int info_no) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) from registdata"
					+ "   where userid = ? and info_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userid);
			pstm.setInt(2, info_no);
			rs = pstm.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) > 0) {
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
	
	//강좌 등록
	public boolean registMyInfo(RegistVo vo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "insert into registdata(regist_no, info_no, userid)"
					+ "   values(seq_regist.nextval,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, vo.getInfo_no());
			pstm.setString(2, vo.getUserid());
			System.out.println(vo);
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
	
	//강좌 내 정보 구하기
	public Vector<InfoVo> getMyInfo(String userid) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select rd.regdate, id.info_date, id.info_name,"
					+ "     id.info_location, id.info_var, id.info_no"
					+ "   from registdata rd, infodata id"
					+ "   where rd.info_no = id.info_no"
					+ "     and rd.userid = ?"
					+ "   order by rd.regdate";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userid);
			rs = pstm.executeQuery();
			Vector<InfoVo> vec = new Vector<>();
			while(rs.next()) {
				InfoVo infoVo = new InfoVo();
				infoVo.setMy_regdate(rs.getDate("regdate"));
				infoVo.setInfo_date(rs.getDate("info_date"));
				infoVo.setInfo_name(rs.getString("info_name"));
				infoVo.setInfo_location(rs.getString("info_location"));
				infoVo.setInfo_var(rs.getInt("info_var"));
				infoVo.setInfo_no(rs.getInt("info_no"));
				vec.add(infoVo);
			}
			System.out.println(vec);
			return vec;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return null;
	}
	
	//내 강좌 삭제
	public boolean deleteMyInfo(Map<String, Object> map) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "delete from registdata"
					+ "   where userid = ? and info_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, (String)map.get("userid"));
			pstm.setInt(2, (Integer)map.get("info_no"));
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
	
	//유저 하위 데이터 있는지 조사
	public boolean isChildExist(String deleteId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select count(*) from registdata "
					+ "   where userid = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, deleteId);
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
