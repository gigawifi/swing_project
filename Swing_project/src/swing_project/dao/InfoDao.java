package swing_project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

import swing_project.vo.InfoVo;

public class InfoDao {
	private static InfoDao instance;
	private InfoDao() {}
	public static InfoDao getInstance() {
		if(instance == null) {
			instance = new InfoDao();
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
	
	//모두 닫기
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
	
	//강좌 데이터 얻기
	public Vector<InfoVo> getInfoVector() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Vector<InfoVo> vec = new Vector<>();
		try {
			conn = getConnection();
			String sql = "select * from infodata"
					+ "	  order by info_no";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				String info_name = rs.getString("info_name");
				String info_location = rs.getString("info_location");
				int info_var = rs.getInt("info_var");
				int info_no = rs.getInt("info_no");
				int info_cap = rs.getInt("info_cap");
				Date info_date = rs.getDate("info_date");
				vec.add(new InfoVo(info_name, info_location, info_cap, info_no, info_var, info_date, null));
			}
			return vec;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstm, rs);
		}
		return null;
	}
	
	//강좌번호로 강좌 삭제
	public boolean deleteByInfo_no(int info_no) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "delete from infodata"
					+ "   where info_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, info_no);
			int count = pstm.executeUpdate();
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
	public boolean addInfo(InfoVo vo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "insert into infodata(info_no, info_name,"
					+ "                        info_location, info_cap, info_var)"
					+ "   values(?,?,?,?,?)";
			System.out.println(vo);
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, vo.getInfo_no());
			pstm.setString(2, vo.getInfo_name());
			pstm.setString(3, vo.getInfo_location());
			pstm.setInt(4, vo.getInfo_cap());
			pstm.setInt(5, vo.getInfo_var());
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
	public boolean changeInfo(InfoVo infoVo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getConnection();
			String sql = "update infodata"
					+ "   set info_name = ?,"
					+ "		  info_location = ?,"
					+ "       info_var = ?,"
					+ "       info_cap = ?"
					+ "   where info_no = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, infoVo.getInfo_name());
			pstm.setString(2, infoVo.getInfo_location());
			pstm.setInt(3, infoVo.getInfo_var());
			pstm.setInt(4, infoVo.getInfo_cap());
			pstm.setInt(5, infoVo.getInfo_no());
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
	
	//시퀀스에서 다음 info_no 찾기
	public int getNextInfo_no() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select seq_info.nextval"
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
	
}
