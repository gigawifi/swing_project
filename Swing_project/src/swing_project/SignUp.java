package swing_project;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import swing_project.dao.UserDao;
import swing_project.vo.UserDataVo;

@SuppressWarnings("serial")
public class SignUp extends JFrame implements ActionListener{
	Container con = getContentPane();
	JPanel mainPanel = new JPanel();
	JTextField tfId = new JTextField();
	JPasswordField pwf = new JPasswordField();
	JPasswordField pwfConfirm = new JPasswordField();
	JTextField tfName = new JTextField();
	JTextField tfMail = new JTextField();
	JTextField tfPhone = new JTextField();
	JTextField tfMailDomain = new JTextField();
	ButtonGroup rdoGroup = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남자");
	JRadioButton rdoFemle = new JRadioButton("여자");
	JLabel lblPwConfirm = new JLabel("비밀번호를 입력해 주세요");
	JLabel lblAT = new JLabel("@");
	JButton btnIdConfirm = new JButton("중복 확인");
	JButton btnSubmit = new JButton("제출하기");
	JButton btnRollBack = new JButton("다시하기");
	JButton btnBack = new JButton("돌아가기");
	JButton[] btnBottoms = {
		btnBack, btnRollBack, btnSubmit	
	};
	String[] lblTexts= {
		"아이디", "비밀번호" ,"비밀번호 확인", 
		"이름", "성별", "이메일", "전화번호"	
	};
	JLabel[] lbls = new JLabel[lblTexts.length];
	JTextField[] tfs = { tfId, pwf,pwfConfirm,
			tfName,tfMail, tfPhone};
	boolean[] checks = new boolean[6];
	JComboBox<String> comboDomain = new JComboBox<>(MainPage.DOMAINS);
	UserDao dao = UserDao.getInstance();
	boolean isIdCheck,
		    isPwConfirmedCheck;
	int mainWidth = 800,
		mainHeight = 500;

	public SignUp() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(MainPage.FRAME_WIDTH, MainPage.FRAME_HEIGHT);
		setTitle("회원가입");
		setResizable(false);
		setVisible(true);
		setUi();
	}

	private void setUi() {
		con.setLayout(null);
		con.add(mainPanel);
		con.setBackground(MainPage.FRAME_COLOR);
		setMainPanel();
		setObjectLocate();
		SetListener();
	}

	private void setMainPanel() {
		mainPanel.setLayout(null);
		mainPanel.setBackground(MainPage.BOARD_COLOR);
		mainPanel.setSize(mainWidth, mainHeight);
		mainPanel.setLocation((con.getWidth() - mainWidth) / 2, (con.getHeight() - mainHeight) / 2);
		mainPanel.add(btnIdConfirm);
		mainPanel.add(lblPwConfirm);
		mainPanel.add(lblAT);
		mainPanel.add(tfMailDomain);
		mainPanel.add(comboDomain);
		mainPanel.add(rdoMale);
		mainPanel.add(rdoFemle);
		JLabel label = null;
		for (int i = 0; i < lblTexts.length; i++) {
			label = new JLabel("- " + lblTexts[i] + ": ");
			label.setFont(new Font("고딕", Font.PLAIN, 16));
			lbls[i] = label;
			mainPanel.add(label);
		}
		for (JTextField jtf : tfs) {
			mainPanel.add(jtf);
		}
		for(JButton btn : btnBottoms) {
			mainPanel.add(btn);
		}
		rdoMale.setSelected(true);
		rdoGroup.add(rdoMale);
		rdoGroup.add(rdoFemle);
	}

	private void setObjectLocate() {
		int startX = 20,
			startY = 30,
			marginY = 50,
			lblWidth = 150,
			secondX = startX + lblWidth,
			thirdX = 300,
			thirdWidth = 100,
			defaultHeight = 30,
			pwdWidth = 300,
			bottomX = mainWidth / 6 - 60,
			bottomY = 450,
			bottomMargin = mainWidth / 3,
			rdoWidth = 50,
			tfIndex = 0;

		for (int i = 0; i < lbls.length; i++) {
			lbls[i].setBounds(startX, startY + marginY * i,
					lblWidth, defaultHeight);
		}
		for (JTextField tf : tfs) {
			if(tfIndex == 4) {				
				tfIndex++;
			}
			tf.setBounds(secondX, startY + marginY * tfIndex, 120, defaultHeight);
			tfIndex++;
		}
		for (int i = 0; i < btnBottoms.length; i++) {
			btnBottoms[i].setBounds(bottomX + bottomMargin * i,
					bottomY, 120, defaultHeight);
		}
		btnIdConfirm.setBounds(thirdX, tfs[0].getY(),
				thirdWidth, defaultHeight);
		lblPwConfirm.setBounds(thirdX, tfs[2].getY(), pwdWidth, defaultHeight);
		lblAT.setBounds(thirdX, tfs[4].getY(), 20, defaultHeight);
		tfMailDomain.setBounds(thirdX + lblAT.getWidth(),
				lblAT.getY(), thirdWidth, defaultHeight);
		comboDomain.setBounds(tfMailDomain.getX() + tfMailDomain.getWidth(),
				lblAT.getY(), thirdWidth, defaultHeight);
		rdoMale.setBounds(secondX, lbls[4].getY(), rdoWidth, defaultHeight);
		rdoFemle.setBounds(rdoMale.getX() + rdoMale.getWidth() +10,
				lbls[4].getY(), rdoWidth, defaultHeight);
	}

	private void SetListener() {
		btnIdConfirm.addActionListener(this);
		btnBack.addActionListener(this);
		btnRollBack.addActionListener(this);
		pwfConfirm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				isPwConfirmedCheck = false;
				String pw = String.valueOf(pwf.getPassword());
				String pw2 = String.valueOf(pwfConfirm.getPassword());
				if(pw.equals(pw2)) {					
					lblPwConfirm.setText("비밀번호 확인이 완료되었습니다.");
					isPwConfirmedCheck = true;
				} else {					
					lblPwConfirm.setText("같은 비밀번호를 입력해주세요.");
				}
			}
		});
		comboDomain.addActionListener(this);
		btnSubmit.addActionListener(new ActionListener() {
			String email = "";
			String gender = "";
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailFirst = tfMail.getText().replace(" ", ""); 
				String emailLast = tfMailDomain.getText().replace(" ", "");
				if(emailLast.equals("") || emailLast == null) {					
					JOptionPane.showMessageDialog(SignUp.this, 
							"이메일 뒷부분을 입력해주세요", "도메인 확인",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				email = emailFirst + "@" + emailLast;
				int index = 0;
				for(int i = 0; i < tfs.length; i++) {
					if(i == 4) index++;
					System.out.println(lblTexts[index]);
					String text = tfs[i].getText();
					if(text.equals("") || text== null) {
						JOptionPane.showMessageDialog(SignUp.this,
								lblTexts[index] + "을 입력해주세요", "입력 확인",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					index++;
				}
				if(!isIdCheck) {					
					JOptionPane.showMessageDialog(SignUp.this, 
							"아이디 중복확인을 해주세요", "아이디 중복 확인",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!isPwConfirmedCheck) {
					JOptionPane.showMessageDialog(SignUp.this, 
							"비밀번호와 비밀번호 확인을 같게 해주세요", "비밀번호 확인",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(rdoMale.isSelected()) {
					gender = "남";
				} else if (rdoFemle.isSelected()) {
					gender = "여";					
				}
				UserDataVo vo = new UserDataVo(tfId.getText(),
						String.valueOf(pwf.getPassword()),
						tfName.getText(), gender, email, tfPhone.getText(), dao.getNextUserNo());
				Boolean isDataSet =  dao.addUser(vo);
				if(isDataSet) {
					JOptionPane.showMessageDialog(SignUp.this, 
							"회원가입에 성공했습니다", "회원가입 성공",
							JOptionPane.DEFAULT_OPTION);
					new Login();
					SignUp.this.dispose();
				} else {
					JOptionPane.showMessageDialog(SignUp.this, 
							"회원가입에 실패했습니다", "회원가입 오류",
							JOptionPane.ERROR_MESSAGE);						
				}
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnIdConfirm) {
			String id = tfId.getText().trim();
			System.out.println(id);
			boolean isIdChecked = dao.isIdExist(id);
			System.out.println(isIdChecked);
			if (!isIdChecked){
				JOptionPane.showMessageDialog(SignUp.this, 
						"사용 가능한 아이디입니다", "아이디 사용 가능",
						JOptionPane.INFORMATION_MESSAGE);		
			} else {
				JOptionPane.showMessageDialog(SignUp.this, 
						"중복된 아이디입니다", "중복된 아이디",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			isIdCheck = true;
			tfId.setEditable(false);
		} else if (obj == btnBack) {
			new Login();
			SignUp.this.dispose();
		} else if (obj == btnRollBack) {
			for(JTextField tf : tfs) {
				tf.setText("");
			}
			tfId.setEditable(true);
			tfMailDomain.setText("");
			comboDomain.setSelectedIndex(0);
		} else if(obj == comboDomain) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) obj;
			if (cb.getSelectedIndex() != 0) {
				String domain = (String)cb.getSelectedItem();
				tfMailDomain.setText(domain);
				System.out.println(checks[1]);
			} else if(cb.getSelectedIndex() == 0) {
				tfMailDomain.setText("");
			}
		}
		
	}
}
