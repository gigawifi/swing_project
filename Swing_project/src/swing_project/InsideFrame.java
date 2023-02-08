package swing_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import swing_project.dao.UserDao;
import swing_project.pnl.InfoListPnl;
import swing_project.pnl.ManageInfoPnl;
import swing_project.pnl.ManageUserPnl;
import swing_project.pnl.MyInfoListPnl;
import swing_project.pnl.UserDataPnl;
import swing_project.vo.UserDataVo;

@SuppressWarnings("serial")
public class InsideFrame extends JFrame{
	Container con = getContentPane();
	UserDao dao = UserDao.getInstance();
	
	JLabel lblLogout = new JLabel("로그아웃");
	JLabel lblInfo = new JLabel("information");
	JPanel pnlMain = new JPanel();
	JPanel pnlMyLecInfo = new JPanel();
	JTabbedPane tabPane = new JTabbedPane();
	int idType = 0;
	String id;
	UserDataVo myInfoVo;
	
	public InsideFrame(String id, int idType) {
		this.id = id;
		this.idType = idType;
		myInfoVo = dao.searchMyInfo(id);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(MainPage.FRAME_WIDTH, MainPage.FRAME_HEIGHT);
		setTitle("main");
		setVisible(true);
		setResizable(false);
		setUi();
		setObjectLocate();
		setTabPane();
		setListener();
	}

	//UI 설정
	private void setUi() {
		con.setLayout(null);
		pnlMain.setLayout(null);
		con.add(pnlMain);
		con.setBackground(MainPage.FRAME_COLOR);
		pnlMain.setBackground(MainPage.BOARD_COLOR);
		con.add(lblInfo);
		con.add(lblLogout);
		lblLogout.setForeground(Color.DARK_GRAY);
		pnlMain.add(tabPane);
		String name = myInfoVo.getUserName();
		lblInfo.setText(name + "님 환영합니다.");
	}

	//위치 설정
	private void setObjectLocate() {
		int mainWidth = 800,
			mainHeight = 500;
		pnlMain.setBounds((con.getWidth() - mainWidth) / 2,
				(con.getHeight() - mainHeight) / 2, mainWidth, mainHeight);
		lblLogout.setBounds(con.getWidth() - 80, 10, 50, 20);
		lblInfo.setBounds(lblLogout.getX() - 160, 10, 160, 20);
		tabPane.setBounds(0, 0, pnlMain.getWidth(), pnlMain.getHeight());
	}
	
	//탭 설정
	private void setTabPane() {
		tabPane.setFont(new Font("맑은고딕", Font.BOLD, 15));
		if(idType == 0) { // 개인용	
			tabPane.addTab("내 정보", new UserDataPnl(InsideFrame.this, id));
			tabPane.addTab("나의 강좌", new MyInfoListPnl(InsideFrame.this, id));
			tabPane.addTab("모든 강좌 정보", new InfoListPnl(InsideFrame.this, id));
		}
		if(idType == 1) { // 관리자용
			tabPane.addTab("강좌 관리", new ManageInfoPnl(InsideFrame.this));			
			tabPane.addTab("회원 관리", new ManageUserPnl(InsideFrame.this));			
		}
	}
	
	//탭 새로고침
	public void setTabRefresh() {
		int index = tabPane.getSelectedIndex();
		tabPane.removeAll();
		setTabPane();
		tabPane.setSelectedIndex(index);
	}
	
	//리스너 설정
	private void setListener() {
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InsideFrame.this.dispose();
				new Login();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblLogout.setForeground(Color.GRAY);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblLogout.setForeground(Color.DARK_GRAY);
			}
		});
	}
}
