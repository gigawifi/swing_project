package swing_project;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import swing_project.dao.UserDao;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{
	Container con = getContentPane();
	JPanel pnlMain = new JPanel();
	JPanel pnlTitle = new JPanel();
	JPanel pnlForm = new JPanel();
	JLabel imgLabel = new JLabel();
	JLabel lblId = new JLabel("아이디:");
	JLabel lblPassword = new JLabel("비밀번호:");
	JTextField tfId = new JTextField();
	JPasswordField pfPw = new JPasswordField();
	JButton btnLogin = new JButton("로그인");
	JButton btnSingUp = new JButton("회원가입");
	UserDao dao = UserDao.getInstance();
	
	public Login() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(MainPage.FRAME_WIDTH, MainPage.FRAME_HEIGHT);
		setTitle("로그인");
		setVisible(true);
		setResizable(false);
		setUi();
		setPnlMain();
		setObjectLocation();
		setListener();
	}
	private void setUi() {
		con.setBackground(MainPage.FRAME_COLOR);
		con.setLayout(null);
		con.add(pnlMain);
		
	}
	private void setPnlMain() {
		int pnlMainWidth = 600,
			pnlMainHeight = 400;
		pnlMain.setLayout(null);
		pnlForm.setLayout(null);
		pnlMain.setBounds((MainPage.FRAME_WIDTH - pnlMainWidth) / 2,
				(con.getHeight() - pnlMainHeight) / 2,
				pnlMainWidth, pnlMainHeight);
		pnlMain.add(btnSingUp);
		pnlMain.add(btnLogin);
		pnlMain.add(pnlTitle);
		pnlMain.add(pnlForm);
		pnlMain.setBackground(MainPage.BOARD_COLOR);
		pnlForm.setBackground(MainPage.BOARD_COLOR);
		pnlTitle.setBackground(MainPage.BOARD_COLOR);
		
		ImageIcon icon = new ImageIcon("src/image/title.png");
		Image image = icon.getImage();
		Image updateImg = image.getScaledInstance(350, 125,
				Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(updateImg));
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		pnlTitle.add(imgLabel);
		pnlTitle.revalidate();
		pnlForm.add(lblId);
		pnlForm.add(tfId);		
		pnlForm.add(lblPassword);
		pnlForm.add(pfPw);
	}
	
	private void setObjectLocation() {
		int btnY = 300,
			formX = 10,
			formY = 30,
			formWidth = 100,
			formHeight = 25,
			objWidth = 60,
			objHeight = 30,
			margin = 50,
			btnWidth = 150,
			centerX = pnlMain.getWidth() / 2;
		pnlTitle.setBounds(centerX - 350/2, 20, 350, 125);
		pnlForm.setBounds(centerX - 200/2, 140, 200, 130);
		lblId.setBounds(formX, formY, objWidth, formHeight);
		tfId.setBounds(formX + objWidth, formY, formWidth, formHeight);
		lblPassword.setBounds(formX, formY + margin,
				objWidth, formHeight);
		pfPw.setBounds(formX + objWidth, formY + margin,
				formWidth, formHeight);
		btnSingUp.setBounds(centerX + 10,
				btnY, btnWidth, objHeight);
		btnLogin.setBounds(centerX - (btnWidth + 10),
				btnY, btnWidth, objHeight);
	}
	private void setListener() {
		pfPw.addActionListener(this);
		btnLogin.addActionListener(this);
		btnSingUp.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnLogin || obj == pfPw) {
			String[] data = {
				tfId.getText(), String.valueOf(pfPw.getPassword())
			};
			boolean result = dao.login(data);
			if(result) {
				Login.this.dispose();
				if(data[0].equals("admin1234")) {
					new InsideFrame(tfId.getText(), 1);
					return;
				}
				new InsideFrame(tfId.getText(), 0);
			} else {
				JOptionPane.showMessageDialog(con, "로그인에 실패했습니다. "
						+ "아이디와 비밀번호를 다시 입력해주세요.", "로그인 실패",
						JOptionPane.ERROR_MESSAGE);
				tfId.setText("");
				pfPw.setText("");
			}
		} else if (obj == btnSingUp) {
			new SignUp();
			Login.this.dispose();
		}
	}
}
