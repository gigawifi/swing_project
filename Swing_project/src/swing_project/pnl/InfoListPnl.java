package swing_project.pnl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
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
import swing_project.vo.RegistVo;

@SuppressWarnings("serial")
public class InfoListPnl extends JPanel{
	InsideFrame insideFrame;
	JTextArea area = new JTextArea();
	JPanel pnlNorth = new JPanel();
	JButton btnSelect = new JButton("select");
	JScrollPane pane = new JScrollPane(area);
	InfoDao infoDao = InfoDao.getInstance();
	RegistDao registDao = RegistDao.getInstance();
	Vector<InfoVo> vec = new Vector<>();
	String id;
	 
	public InfoListPnl(InsideFrame insideFrame, String id) {
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
		setTableTitle();
		vec = infoDao.getInfoVector();
		setPane(vec.size());
	}


	private void setPane(int index) {
		int margin = 3;
		int pnlWidth = 100 - margin;
		int pnlHeight = 100 - margin;
		int lblWidth = 100;
		int lblHeight = 50;
		int lblIndex = 6;
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
			labels[2].setText(vo.getInfo_location());
			labels[3].setText("총원: " + String.valueOf(vo.getInfo_cap()));
			labels[4].setText("현재 인원: "
						+ String.valueOf(registDao.getCapCount(vo.getInfo_no())));
			labels[5].setText(MainPage.getVarStrByNo(vo.getInfo_var()));
			pnlPic.setLayout(new BorderLayout());
			pnlPic.add(MainPage.getImage(vo.getInfo_var(), pnlPic.getWidth(), pnlPic.getHeight()));
			
			JLabel jl = new JLabel();
			jl.setText(String.valueOf(vo.getInfo_no()));
			pnlInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(vo.getInfo_no());
					if(registDao.isMyInfoExist(id, vo.getInfo_no())) {
						JOptionPane.showMessageDialog(insideFrame,
								"이미 신청한 강좌입니다.", "이미 신청한 강좌",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					String strInfo_cap = labels[3].getText(); 
					String strNow_cap = labels[4].getText(); 
					int info_cap = Integer.parseInt(
							strInfo_cap.substring(strInfo_cap.indexOf(": ") + 2));
					int now_cap = Integer.parseInt(
							strNow_cap.substring(strNow_cap.indexOf(": ") + 2));
					if(info_cap <= now_cap) {
						JOptionPane.showMessageDialog(insideFrame,
								"인원이 초과했습니다.", "인원 초과",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					int choice = JOptionPane.showConfirmDialog(insideFrame,
							"[" + labels[0].getText() + "]"
							+ "강좌를 신청하시겠습니까?","강좌 신청",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(choice == 0) {
						RegistVo registVo = new RegistVo(0, Integer.parseInt(jl.getText()),
								id, null);
						boolean result = registDao.registMyInfo(registVo);
						if(result) {
							JOptionPane.showMessageDialog(insideFrame,
									"강좌가 등록되었습니다.", "강좌 등록",
									JOptionPane.PLAIN_MESSAGE);
							insideFrame.setTabRefresh();
						} else {
							JOptionPane.showMessageDialog(insideFrame,
									"강좌 등록이 실패했습니다.", "등록 실패",
									JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			});
			pnlInfo.setBackground(Color.white);
			area.add(pnlPic);
			area.add(pnlInfo);
			if(i  == vec.size() - 1)break;
			area.append("\n");
			
			area.setCaretPosition(0);
			area.requestFocus();
		}
	}
	
	//표 제목
	private void setTableTitle() {
		JLabel lblInfoName = new JLabel(
				"                                 강좌 이름                ");
		JLabel lblInfoDate = new JLabel("등록일               ");
		JLabel lblInfoLocation = new JLabel("강좌 장소               ");
		JLabel lblInfoCap = new JLabel("신청 가능 인원            ");
		JLabel lblInfoNowCap = new JLabel("현재 인원                ");
		JLabel lblInfoVal = new JLabel("카테고리            ");
		pnlNorth.setLayout(new FlowLayout());
		pnlNorth.add(lblInfoName);
		pnlNorth.add(lblInfoDate);
		pnlNorth.add(lblInfoLocation);
		pnlNorth.add(lblInfoCap);
		pnlNorth.add(lblInfoNowCap);
		pnlNorth.add(lblInfoVal);
	}
}
