package swing_project.pnl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import swing_project.InsideFrame;
import swing_project.MainPage;
import swing_project.dao.InfoDao;
import swing_project.dao.RegistDao;
import swing_project.dao.UserDao;
import swing_project.vo.InfoVo;

@SuppressWarnings("serial")
public class ManageInfoPnl extends JPanel implements ActionListener{
	InsideFrame insideFrame;
	JPanel pnlNorth = new JPanel();
	JPanel pnlCenter = new JPanel();
	JButton btnInsert = new JButton("새 강좌 추가");
	JButton btndelete = new JButton("삭제");
	String[] headers = {
		"강좌 번호", "강좌 이름", "등록일", "강좌 장소", "신청 가능 인원", "현재 인원", "카테고리"	
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
	InfoDao infoDao = InfoDao.getInstance();
	RegistDao registDao = RegistDao.getInstance();
	int info_no;
	
	public ManageInfoPnl(InsideFrame insideFrame) {
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
		Vector<InfoVo> vec = infoDao.getInfoVector();
		for(InfoVo vo : vec) {
			String strInfo_var = MainPage.getVarStrByNo(vo.getInfo_var());
			System.out.println(vo.getInfo_var());
			System.out.println(strInfo_var);
			Object[] objs = {
				vo.getInfo_no(), vo.getInfo_name(), vo.getInfo_date(), vo.getInfo_location(),
				vo.getInfo_cap(), registDao.getCapCount(vo.getInfo_no()), strInfo_var
			};
			dtm.addRow(objs);
		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = table.getSelectedRow();
				int choose = JOptionPane.showConfirmDialog(insideFrame,
						"[" + table.getValueAt(selectRow, 1) + "]"
						+ "의 강좌 정보를 수정하시겠습니까?","강좌 정보 수정",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(choose == 0) {
					Object[] objs = new Object[table.getColumnCount() - 2];
					int index = 0;
					for (int i = 0; i < objs.length; i++) {
						if(index == 2 || index == 5) index++;
						objs[i] = table.getValueAt(selectRow, index++);
					}
					InfoManageDialog dialog = 
							new InfoManageDialog(insideFrame, "강좌 정보 수정", objs, selectRow);
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
			String strInfo_no = JOptionPane.showInputDialog(insideFrame, "삭제할 강좌 번호를 입력하세요",
					"삭제 번호 입력", JOptionPane.INFORMATION_MESSAGE);
			//취소시 종료
			if(strInfo_no == null) {
				return;
			}
			int info_no = 0;
			try {				
				info_no = Integer.parseInt(strInfo_no);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(insideFrame, "숫자를 입력해 주세요", "번호 재입력",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int count = registDao.getCapCount(info_no);
			if(count > 0) {
				int choose = JOptionPane.showConfirmDialog(insideFrame,
						"이 강좌를 등록한 사용자가 존재합니다. 정말로 삭제하겠습니까?"," 등록된 사용자 존재",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(choose == 1) {
					return;
				}
			}
			boolean result = infoDao.deleteByInfo_no(info_no);
			if(result) {
				JOptionPane.showMessageDialog(insideFrame, "삭제가 완료되었습니다", "삭제 성공",
					JOptionPane.PLAIN_MESSAGE);
				insideFrame.setTabRefresh();
			} else {
				JOptionPane.showMessageDialog(insideFrame, "삭제할 강좌 번호를 다시 입력해주세요", "번호 재입력",
					JOptionPane.ERROR_MESSAGE);
			}
		} else if(obj == btnInsert) {
			InfoManageDialog dialog = 
					new InfoManageDialog(insideFrame, "강좌 정보 입력", null, 0);
			dialog.setLocationRelativeTo(insideFrame);
			dialog.setVisible(true);
		}
	}
	public class InfoManageDialog extends JDialog implements ActionListener {
		Container con = getContentPane();
		JButton btnAction = new JButton("수정");
		JButton btnCancel = new JButton("취소");
		JTextField tfNo = new JTextField();
		JTextField tfName = new JTextField();
		JTextField tfLocation = new JTextField();
		JTextField tfCap = new JTextField();
		JTextField[] jtfs = {
				tfNo, tfName, tfLocation, tfCap
		};
		JComboBox<String> comboVars = new JComboBox<>(MainPage.VAR_CATEGORYS);
		Object[] objs;
		int selectedRow;
		boolean isChangeInfo = true;
		public InfoManageDialog (JFrame frame, String title,
				Object[] objs, int seletedRow) {
			super(frame, title, true);
			this.objs = objs;
			this.selectedRow = seletedRow;
			setLayout(null);
			setSize(300, 360);
			setDialog();
			btnAction.addActionListener(this);
			btnCancel.addActionListener(this);
		}
		
		private void setDialog() {
			final int DIALOG_MARGIN = 15,
					  HALF_WIDTH = 150,
					  OBJECT_HEIGHT = 30,
					  OBJECT_WIDTH = HALF_WIDTH - DIALOG_MARGIN * 2,
					  BUTTON_MOTION_HEIGHT = 280;
			
			if(objs == null) {
				isChangeInfo = false;
			}
			con.add(btnCancel);
			con.add(btnAction);
			int index = 0;
			for(int i = 0; i < headers.length - 2; i++) {
				JLabel label = null;
				if(i == 2 || i == 4) {
					index++;
				}
				System.out.println(headers[index]);
				label = new JLabel(headers[index++]);					
				label.setBounds(DIALOG_MARGIN,
						DIALOG_MARGIN + i * 50, OBJECT_WIDTH, OBJECT_HEIGHT);
				con.add(label);
			}
			for (int i = 0; i < jtfs.length; i++) {
				jtfs[i].setBounds(HALF_WIDTH + DIALOG_MARGIN,
						DIALOG_MARGIN + i * 50, OBJECT_WIDTH - DIALOG_MARGIN,
						OBJECT_HEIGHT);
				con.add(jtfs[i]);
				if (isChangeInfo) {
					jtfs[i].setText(String.valueOf(objs[i]));
				} else if(i == 0){
					if(info_no == 0) {						
						info_no = infoDao.getNextInfo_no();
					}
					tfNo.setText(String.valueOf(info_no));
				}
				comboVars.setBounds(HALF_WIDTH + DIALOG_MARGIN, DIALOG_MARGIN + jtfs.length * 50,
						OBJECT_WIDTH - DIALOG_MARGIN, OBJECT_HEIGHT);
				con.add(comboVars);
				if (isChangeInfo) {
					int selectedIndex = 
							MainPage.getNoByStrVar((String)objs[objs.length - 1]);
					comboVars.setSelectedIndex(selectedIndex);
				} else {
					btnAction.setText("입력");
				}
			}
			btnCancel.setBounds(DIALOG_MARGIN, BUTTON_MOTION_HEIGHT,
					OBJECT_WIDTH, OBJECT_HEIGHT);
			btnAction.setBounds(HALF_WIDTH + DIALOG_MARGIN,
					BUTTON_MOTION_HEIGHT , OBJECT_WIDTH - DIALOG_MARGIN, OBJECT_HEIGHT);
			
			tfNo.setEditable(false);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnAction) {
				int tfInfo_no = Integer.parseInt(tfNo.getText());
				String info_name = tfName.getText();
				String info_location = tfLocation.getText();
				String selectedItem = (String)comboVars.getSelectedItem();
				int info_var = MainPage.getNoByStrVar(selectedItem);
				int info_cap = 0;
				if(info_name.isEmpty() || info_location.isEmpty()) {
					JOptionPane.showMessageDialog(InfoManageDialog.this,
							"모든 정보를 입력해 주세요.", "모든 정보 입력",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					 info_cap = Integer.parseInt(tfCap.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(InfoManageDialog.this,
							"신청 가능 인원에는 숫자만 입력해 주세요.", "숫자 입력",
							JOptionPane.ERROR_MESSAGE);
					tfCap.setText("");
					return;
				}
				InfoVo infoVo = new InfoVo(info_name, info_location, info_cap, tfInfo_no, info_var, null, null);
				if(isChangeInfo) {
					boolean result = infoDao.changeInfo(infoVo);
					if(result) {
						JOptionPane.showMessageDialog(InfoManageDialog.this,
								"강좌 정보변경이 완료되었습니다.", "정보 변경 완료",
								JOptionPane.PLAIN_MESSAGE);
						insideFrame.setTabRefresh();
					}					
				} else {
					boolean result = infoDao.addInfo(infoVo);
					if(result) {
						JOptionPane.showMessageDialog(InfoManageDialog.this,
								"강좌 생성이 완료되었습니다.", "강좌 생성 완료",
								JOptionPane.PLAIN_MESSAGE);
						info_no = 0;
						insideFrame.setTabRefresh();
					}
				}
			}
			this.dispose();
		}
	}
}
