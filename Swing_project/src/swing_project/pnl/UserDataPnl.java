package swing_project.pnl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import swing_project.InsideFrame;
import swing_project.dao.UserDao;
import swing_project.vo.UserDataVo;


@SuppressWarnings("serial")
public class UserDataPnl extends JPanel implements ActionListener{
	JPanel pnlNorth = new JPanel();
	JPanel pnlCenter = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlLeft = new JPanel();
	
	JButton btnChangePwd = new JButton("비밀번호 변경");
	JButton btnChangeMyInfo = new JButton("내 정보 변경");
	UserDao userDao = UserDao.getInstance();
	InsideFrame insideFrame;
	PwCheckDialog checkDialog;
	ChangeUserDialog changeDialog;
	String[] myInfoStr = {
			"- 나의 이름: ", "- 나의 아이디: ","- 성별: ",
			"- 나의 이메일: ", "- 나의 전화번호: "
	};
	JLabel[] lblMyInfos = new JLabel[myInfoStr.length];
	private UserDataVo myInfoVo;
	private boolean isPwMode = false;
	private String id;

	
	public UserDataPnl(InsideFrame insideFrame, String id) {
		this.insideFrame = insideFrame;
		myInfoVo = userDao.searchMyInfo(id);
		System.out.println(myInfoVo);
		this.id = id;
		setUi();
	}

	private void setUi() {
		this.setLayout(new BorderLayout());
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new GridLayout(1,2));
		pnlLeft.setLayout(new GridLayout(5,1));
		pnlCenter.add(pnlLeft);
		pnlCenter.add(pnlRight);
		
		JLabel imgLabel = new JLabel();
		pnlRight.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("src/image/myPage.png");
		Image image = icon.getImage();
		Image updateImg = image.getScaledInstance(375, 180,
				Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(updateImg));
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		pnlRight.add(imgLabel);
		
		int btnHeight = 30;
		String[] myInfo = {
				myInfoVo.getUserName(),
				myInfoVo.getId(),
				myInfoVo.getGender() + "성",
				myInfoVo.getEmail(),
				myInfoVo.getPhoneNumber()
		};
		for(int i = 0; i < lblMyInfos.length; i++) {
			lblMyInfos[i] = new JLabel(
					myInfoStr[i] + myInfo[i]);
			lblMyInfos[i].setOpaque(true);
			lblMyInfos[i].setFont(new Font("맑은고딕", Font.BOLD, 17));
			lblMyInfos[i].setBackground(Color.gray);
			pnlLeft.add(lblMyInfos[i]);
		}
		pnlRight.setBackground(Color.WHITE);
		btnChangePwd.setBounds(0, 0, 150, btnHeight);
		btnChangeMyInfo.setBounds(170, 0, 150, btnHeight);
		btnChangeMyInfo.addActionListener(this);
		btnChangePwd.addActionListener(this);
		pnlNorth.add(btnChangePwd);
		pnlNorth.add(btnChangeMyInfo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnChangeMyInfo) {
			changeDialog = new ChangeUserDialog(insideFrame, "내 정보 변경");
			changeDialog.setLocationRelativeTo(insideFrame);
			changeDialog.setVisible(true);			
		} else if(obj == btnChangePwd) {
			 checkDialog = new PwCheckDialog(insideFrame, "비밀번호 확인");
			 checkDialog.setLocationRelativeTo(insideFrame);
			 checkDialog.setVisible(true);
		}
	}

	public class PwCheckDialog extends JDialog implements ActionListener{
		Container con = getContentPane();
		JLabel label = new JLabel("비밀번호 입력:");
		JPasswordField tfPwdCheck = new JPasswordField(10);
		JButton btnPwd = new JButton("입력");
		public PwCheckDialog(JFrame frame, String title) {
			super(frame, title, true);
			setLayout(new FlowLayout());
			setSize(300, 100);
			con.add(label);
			con.add(tfPwdCheck);
			con.add(btnPwd);
			tfPwdCheck.addActionListener(this);
			btnPwd.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String password = String.valueOf(tfPwdCheck.getPassword());
			boolean isPwCheck = userDao.checkPassword(id, password);
			if(isPwMode) {
				boolean result = 
						userDao.changePassword(id, password);
				if(result) {
					JOptionPane.showMessageDialog(PwCheckDialog.this,
							"비밀번호 변경이 완료되었습니다.", "비밀번호 변경 완료",
							JOptionPane.PLAIN_MESSAGE);
					isPwMode = false;
					dispose();
					insideFrame.setTabRefresh();
					return;
				}
			}
			if(isPwCheck) {
				JOptionPane.showMessageDialog(PwCheckDialog.this,
						"비밀번호 확인이 완료되었습니다.", "비밀번호 확인",
						JOptionPane.PLAIN_MESSAGE);
				label.setText("변경할 비밀번호:");
				tfPwdCheck.setText("");
				btnPwd.setText("변경");
				isPwMode = true;
				insideFrame.setTabRefresh();
			} else {
				JOptionPane.showMessageDialog(PwCheckDialog.this,
						"비밀번호를 다시 확인해주세요", "비멀번호 확인",
						JOptionPane.WARNING_MESSAGE);					
				tfPwdCheck.setText("");
			}
			
		}
	}
	
	//다이얼로그 클래스
	private class ChangeUserDialog extends JDialog implements ActionListener{
		Container con = getContentPane();
		String[] strInfos = {
				"- 이름: ", "- 성별: ",
				"- 이메일: ", "- 전화번호: "
		};
		JTextField tfName = new JTextField();
		JTextField tfEmail = new JTextField();
		JTextField tfPhone = new JTextField();
		JTextField[] tfInfos = {
				tfName, tfEmail, tfPhone
		};
		JLabel lblAt = new JLabel("@");
		JTextField tfDomain = new JTextField();		
		ButtonGroup rdoGroup = new ButtonGroup();
		JButton btnClose = new JButton("닫기");
		JButton btnInfoSubmit = new JButton("수정");
		JRadioButton rdoMale = new JRadioButton("남자");
		JRadioButton rdoFemale = new JRadioButton("여자");
		private int dialogWidth = 350,
					dialogHeight = 400,
					startX = 10,
					startY = 20;
		
		
		public ChangeUserDialog(JFrame frame, String title) {
			super(frame, title, true);
			setLayout(null);
			setSize(dialogWidth, dialogHeight);
			setUi();
			setInfo();
		}
		
		//UI 배치
		private void setUi() {
			int count;
			int lblHeight = dialogHeight / (strInfos.length + 1);
			int btnWidth = 120;
			int objHeight = 30;
			int btnY = dialogHeight - 80;
			for(int i = 0; i < strInfos.length; i++) {
				JLabel lbl = new JLabel(strInfos[i]);
				lbl.setBounds(startX, startY + lblHeight * i, dialogWidth / 5,
						30);
				con.add(lbl);
			}
			for(int i = 0; i < tfInfos.length; i++) {
				if(i == 0) {
					count = startY;
				} else {
					count = startY + lblHeight * (i + 1);
				}
				tfInfos[i].setBounds(startX + dialogWidth / 4, count,
						dialogWidth / 3, objHeight);
				con.add(tfInfos[i]);
			}
			lblAt.setBounds(tfEmail.getX() + tfEmail.getWidth() + 5,
					tfEmail.getY(), 20, objHeight);
			tfDomain.setBounds(lblAt.getX() + lblAt.getWidth(),
					tfEmail.getY(), dialogWidth / 4, objHeight);
			rdoMale.setBounds(startX + dialogWidth / 4, startY + lblHeight,
					50, 30);
			rdoFemale.setBounds(startX + dialogWidth / 4 + rdoMale.getWidth(),
					startY + lblHeight,	50, 30);
			btnClose.setBounds(dialogWidth / 2 - btnWidth - 10, btnY, btnWidth, objHeight);
			btnInfoSubmit.setBounds(dialogWidth / 2 + 10, btnY, btnWidth, objHeight);
			rdoGroup.add(rdoMale);
			rdoGroup.add(rdoFemale);
			btnClose.addActionListener(this);
			btnInfoSubmit.addActionListener(this);
			con.add(btnClose);
			con.add(btnInfoSubmit);
			con.add(rdoMale);
			con.add(rdoFemale);
			con.add(lblAt);
			con.add(tfDomain);
		}
		
		private void setInfo() {
			UserDataVo userDataVo = userDao.searchMyInfo(id);
			tfName.setText(userDataVo.getUserName());
			String email = userDataVo.getEmail();
			String strArr[] = email.split("@");
			tfEmail.setText(strArr[0]);
			tfDomain.setText(strArr[1]);
			tfPhone.setText(userDataVo.getPhoneNumber());
			if(userDataVo.getGender().equals("남")) {
				rdoMale.setSelected(true);
			} else if (userDataVo.getGender().equals("여")) {
				rdoFemale.setSelected(true);				
			}
		}
		
		//액션리스너 설정
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnClose) {
				dispose();
			} else if (obj == btnInfoSubmit) {
				String userName = tfName.getText();
				String gender = "";
				if(rdoMale.isSelected()) {
					gender = "남";
				} else if(rdoFemale.isSelected()) {
					gender = "여";
				}
				for(JTextField jtf : tfInfos) {
					if(jtf.getText().equals("") || jtf.getText() == null) {
						JOptionPane.showMessageDialog(ChangeUserDialog.this,
								"모든 정보를 입력해주세요.", "모든 정보 입력",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				String email = tfEmail.getText() + "@" + tfDomain.getText();
				String phoneNumber = tfPhone.getText();
				UserDataVo userDataVo = new UserDataVo(id, null,
						userName, gender, email, phoneNumber, 0);
				boolean result = userDao.changeUser(userDataVo);
				if(result) {
					JOptionPane.showMessageDialog(ChangeUserDialog.this,
							"정보변경이 완료되었습니다.", "정보 변경 완료",
							JOptionPane.PLAIN_MESSAGE);
					insideFrame.setTabRefresh();
					dispose();
				}
			}
		}
	}
}	