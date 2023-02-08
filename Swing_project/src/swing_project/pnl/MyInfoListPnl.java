package swing_project.pnl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import swing_project.InsideFrame;
import swing_project.MainPage;
import swing_project.dao.InfoDao;
import swing_project.dao.RegistDao;
import swing_project.vo.InfoVo;

@SuppressWarnings("serial")
public class MyInfoListPnl extends JPanel{
	InsideFrame insideFrame;
	JTextArea area = new JTextArea();
	JPanel pnlNorth = new JPanel();
	JScrollPane pane = new JScrollPane(area);
	InfoDao infoDao = InfoDao.getInstance();
	RegistDao registDao = RegistDao.getInstance();
	Vector<InfoVo> vec = new Vector<>();
	String id;
	 
	public MyInfoListPnl(InsideFrame insideFrame, String id) {
		this.insideFrame = insideFrame;
		this.id = id;
		setUi();
		setVisible(true);
	}
	
	
	private void setUi() {
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		area.setBackground(Color.black);
		area.setEditable(false);
		vec = registDao.getMyInfo(id);
		setPane(vec.size());
		setTableTitle();
	}


	private void setPane(int index) {
		int margin = 3;
		int pnlWidth = 100 - margin;
		int pnlHeight = 100 - margin;
		int lblWidth = 100;
		int lblHeight = 50;
		int lblIndex = 5;
		area.setFont(new Font("consolas",  Font.PLAIN, pnlHeight - 12));
		area.setText("");
		for(int i = 0; i < index; i++) {
			JLabel[] labels = new JLabel[lblIndex];
			JPanel pnlPic = new JPanel();
			JPanel pnlInfo = new JPanel();
			
			pnlPic.setBounds(margin, margin + (pnlHeight + margin) * i,
					pnlWidth, pnlHeight);
			pnlPic.setBackground(Color.white);
			pnlInfo.setLayout(null);
			pnlInfo.setBounds(pnlPic.getWidth() + margin * 2,
					margin + (pnlHeight + margin) * i,
					795 - pnlPic.getWidth() - (margin * 4), pnlHeight);
			for(int j = 0; j < lblIndex; j++) {
				JLabel label = new JLabel(String.valueOf(i + 1));
				label.setFont(new Font("consloas", Font.PLAIN, 15));
				label.setBounds(pnlInfo.getWidth() / lblIndex * j +
						(pnlInfo.getWidth() -(pnlInfo.getWidth() / lblIndex * (lblIndex - 1) + lblWidth)) / 2,
						(pnlWidth - lblHeight) / 2, lblWidth, lblHeight);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				labels[j] = label;
				pnlInfo.add(label);
			}
			InfoVo vo = vec.get(i);
			labels[0].setText(vo.getInfo_name());
			labels[1].setText(String.valueOf(vo.getInfo_date()));
			labels[2].setText(MainPage.getVarStrByNo(vo.getInfo_var()));
			labels[3].setText(vo.getInfo_location());
			labels[4].setText(String.valueOf(vo.getMy_regdate()));
			pnlInfo.setBackground(Color.white);
			pnlPic.setLayout(new BorderLayout());
			pnlPic.add(MainPage.getImage(vo.getInfo_var(), pnlPic.getWidth(), pnlPic.getHeight()));
			
			
			pnlInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int choice = JOptionPane.showConfirmDialog(insideFrame,
							"[" + labels[0].getText() + "]"
							+ "강좌 신청을 취소하시습니까?","강좌 신청 취소",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(choice == 0) {
						Map<String, Object> map = new HashMap<>();
						map.put("userid", id);
						map.put("info_no", vo.getInfo_no());
						boolean result = registDao.deleteMyInfo(map);
						if(result) {
							JOptionPane.showMessageDialog(insideFrame,
									"취소에 성공했습니다.", "취소 성공",
									JOptionPane.PLAIN_MESSAGE);
							insideFrame.setTabRefresh();
						} else {
							JOptionPane.showMessageDialog(insideFrame,
									"취소가 실패했습니다.", "취소 실패",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});
			
			area.add(pnlPic);
			area.add(pnlInfo);
			if(i  == vec.size() - 1) {
				break;
			}
			area.append("\n");
			
			area.setCaretPosition(0);
			area.requestFocus();
		}
	}
	
	//표 제목
	private void setTableTitle() {
		JLabel lblInfoName = new JLabel(
				"                                 강좌 이름                    ");
		JLabel lblInfoDate = new JLabel("    강좌 등록일                     ");
		JLabel lblInfoVal = new JLabel("카테고리                          ");
		JLabel lblInfoLocation = new JLabel("   강좌 장소                       ");
		JLabel lblInfo = new JLabel("   나의 등록일          ");
		pnlNorth.setLayout(new FlowLayout());
		pnlNorth.add(lblInfoName);
		pnlNorth.add(lblInfoDate);
		pnlNorth.add(lblInfoVal);
		pnlNorth.add(lblInfoLocation);
		pnlNorth.add(lblInfo);
	}
}
