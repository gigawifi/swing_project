package swing_project.pnl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import swing_project.InsideFrame;
import swing_project.dao.RegistDao;
import swing_project.dao.UserDao;
import swing_project.vo.UserDataVo;

@SuppressWarnings("serial")
public class ManageUserPnl extends JPanel implements ActionListener{
	InsideFrame insideFrame;
	JPanel pnlNorth = new JPanel();
	JPanel pnlCenter = new JPanel();
	JButton btnInsert = new JButton("새 회원 추가");
	JButton btndelete = new JButton("삭제");
	String[] headers = {
		"유저 번호", "아이디", "비밀번호", "이름", "이메일", "전화번호", "성별"	
	};
	String[][] bodys = {};
	DefaultTableModel dtm = new DefaultTableModel(bodys, headers);
	JTable table = new JTable(dtm) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JScrollPane pane = new JScrollPane(table);
	UserDao userDao = UserDao.getInstance();
	RegistDao registDao = RegistDao.getInstance();
	int userNo;
	
	public ManageUserPnl(InsideFrame insideFrame) {
		this.insideFrame = insideFrame;
		setUi();
		setVisible(true);
	}

	private void setUi() {
		pnlNorth.setLayout(new FlowLayout());
		pnlNorth.add(btnInsert);
		pnlNorth.add(btndelete);
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		btnInsert.addActionListener(this);
		btndelete.addActionListener(this);
		table.setAutoCreateRowSorter(true);
		Vector<UserDataVo> vec = userDao.searchUserAll();
		for(UserDataVo vo : vec) {
			Object[] objs = {
				vo.getUserNo(), vo.getId(), vo.getPassword(),
				vo.getUserName(), vo.getEmail(), vo.getPhoneNumber(),
				vo.getGender()
			};
			dtm.addRow(objs);
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = table.getSelectedRow();
				int choose = JOptionPane.showConfirmDialog(insideFrame,
						"[" + table.getValueAt(selectRow, 1) + "]"
						+ "의 회원 정보를 수정하시겠습니까?","정보 수정",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(choose == 0) {
					Object[] objs = new Object[table.getColumnCount()];
					for (int i = 0; i < table.getColumnCount(); i++) {
						objs[i] = table.getValueAt(selectRow, i);
					}
					UserManageDialog dialog = 
							new UserManageDialog(insideFrame, "정보 수정", objs, selectRow);
					dialog.setLocationRelativeTo(insideFrame);
					dialog.setVisible(true);
				}
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btndelete) {
			String deleteId = JOptionPane.showInputDialog(insideFrame, "삭제할 회원의 아이디를 입력하세요",
					"삭제값 입력", JOptionPane.INFORMATION_MESSAGE);
			if(deleteId == null || deleteId.isEmpty()) {
				return;
			}
			boolean isChildExist = registDao.isChildExist(deleteId);
			if(isChildExist) {
				int choose = JOptionPane.showConfirmDialog(insideFrame,
						"이 회원의 등록된 강좌가 존재합니다. 정말로 삭제하겠습니까?"," 등록된 강좌 존재",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(choose == 1) {
					return;
				}
			}
			boolean result = userDao.deleteById(deleteId);
			if(result) {
				JOptionPane.showMessageDialog(insideFrame, "삭제가 완료되었습니다", "삭제 성공",
					JOptionPane.PLAIN_MESSAGE);
				insideFrame.setTabRefresh();
			} else {
				JOptionPane.showMessageDialog(insideFrame, "삭제할 아이디를 다시 입력해주세요", "아이디 입력",
					JOptionPane.ERROR_MESSAGE);				
			}
		} else if(obj == btnInsert) {
			UserManageDialog dialog = 
					new UserManageDialog(insideFrame, "정보 입력", null, 0);
			dialog.setLocationRelativeTo(insideFrame);
			dialog.setVisible(true);
		}
	}
	public class UserManageDialog extends JDialog implements ActionListener {
		Container con = getContentPane();
		JButton btnAction = new JButton("수정");
		JButton btnCancel = new JButton("취소");
		JTextField tfNo = new JTextField();
		JTextField tfId = new JTextField();
		JPasswordField tfPwd = new JPasswordField();
		JTextField tfName = new JTextField();
		JTextField tfEmail = new JTextField();
		JTextField tfPhone = new JTextField();
		JTextField[] jtfs = {
				tfNo, tfId, tfPwd, tfName, tfEmail, tfPhone
		};
		JRadioButton rdoMale = new JRadioButton("남");
		JRadioButton rdoFemale = new JRadioButton("여");
		ButtonGroup rdoGroup = new ButtonGroup();
		Object[] objs;
		int selectedRow;
		boolean isChangeUser = true;
		public UserManageDialog (JFrame frame, String title,
				Object[] objs, int seletedRow) {
			super(frame, title, true);
			this.objs = objs;
			this.selectedRow = seletedRow;
			setLayout(null);
			setSize(300, 450);
			setDialog();
			btnAction.addActionListener(this);
			btnCancel.addActionListener(this);
		}
		private void setDialog() {
			final int DIALOG_MARGIN = 15,
					  HALF_WIDTH = 150,
					  OBJECT_HEIGHT = 30,
					  OBJECT_WIDTH = HALF_WIDTH - DIALOG_MARGIN * 2,
					  BUTTON_MOTION_HEIGHT = 370,
					  RADIO_WIDTH = 50;
			
			if(objs == null) {
				isChangeUser = false;
			}
			con.add(btnCancel);
			con.add(btnAction);
			con.add(rdoMale);
			con.add(rdoFemale);
			for(int i = 0; i < headers.length; i++) {
				JLabel label = new JLabel(headers[i]);
				label.setBounds(DIALOG_MARGIN,
						DIALOG_MARGIN + i * 50, OBJECT_WIDTH, OBJECT_HEIGHT);
				con.add(label);
			}
			for (int i = 0; i < jtfs.length; i++) {
				jtfs[i].setBounds(HALF_WIDTH + DIALOG_MARGIN,
						DIALOG_MARGIN + i * 50, OBJECT_WIDTH - DIALOG_MARGIN,
						OBJECT_HEIGHT);
				con.add(jtfs[i]);
				if (isChangeUser) {
					jtfs[i].setText(String.valueOf(objs[i]));
				} else if(i == 0){	
					if(userNo == 0) {						
						userNo = userDao.getNextUserNo();
					}
					tfNo.setText(String.valueOf(userNo));
				}
			}
			rdoMale.setBounds(HALF_WIDTH + DIALOG_MARGIN,
					DIALOG_MARGIN + (headers.length - 1) * 50, RADIO_WIDTH, OBJECT_HEIGHT);
			rdoFemale.setBounds(rdoMale.getX() + RADIO_WIDTH + 10,
					rdoMale.getY(), RADIO_WIDTH, OBJECT_HEIGHT);
			rdoGroup.add(rdoMale);
			rdoGroup.add(rdoFemale);
			btnCancel.setBounds(DIALOG_MARGIN, BUTTON_MOTION_HEIGHT,
					OBJECT_WIDTH, OBJECT_HEIGHT);
			btnAction.setBounds(HALF_WIDTH + DIALOG_MARGIN,
					BUTTON_MOTION_HEIGHT , OBJECT_WIDTH - DIALOG_MARGIN, OBJECT_HEIGHT);
			
			tfNo.setEditable(false);
			if(isChangeUser) {
				if(objs[objs.length - 1].toString().equals("남")) {
					rdoMale.setSelected(true);
				} else {
					rdoFemale.setSelected(true);
				}				
				tfId.setEditable(false);
			} else {
				rdoMale.setSelected(true);
				btnAction.setText("입력");
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnAction) {
				int no = Integer.parseInt(tfNo.getText());
				String id = tfId.getText();
				String pw = String.valueOf(tfPwd.getPassword());
				String name = tfName.getText();
				String email = tfEmail.getText();
				String phoneNum = tfPhone.getText();
				String gender = "";
				if(rdoMale.isSelected()) {
					gender = "남";
				} else {
					gender = "여";
				}
				if(id.isEmpty() || pw.isEmpty() || name.isEmpty()
						|| email.isEmpty() || phoneNum.isEmpty()
						|| gender.isEmpty()) {
					JOptionPane.showMessageDialog(UserManageDialog.this,
							"모든 정보를 입력해 주세요.", "모든 정보 입력",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				UserDataVo vo = new UserDataVo(id, pw, name, gender, email, phoneNum, no);
				if(isChangeUser) {
					boolean result = userDao.changeUser(vo);
					if(result) {
						JOptionPane.showMessageDialog(UserManageDialog.this,
								"정보변경이 완료되었습니다.", "정보 변경 완료",
								JOptionPane.PLAIN_MESSAGE);
						insideFrame.setTabRefresh();
					}					
				} else {
					boolean isIdExist = userDao.isIdExist(id);
					if(isIdExist) {
						JOptionPane.showMessageDialog(UserManageDialog.this,
								"중복된 아이디입니다.", "아이디 중복",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					boolean result = userDao.addUser(vo);
					if(result) {
						JOptionPane.showMessageDialog(UserManageDialog.this,
								"회원 입력이 완료되었습니다.", "회원 입력 완료",
								JOptionPane.PLAIN_MESSAGE);
						insideFrame.setTabRefresh();
						userNo = 0;
					}
				}
			}
			this.dispose();
		}
	}
}
