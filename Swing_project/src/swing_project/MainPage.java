package swing_project;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainPage extends JFrame {
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 600;

	public static final int VAR_IT = 0;
	public static final int VAR_FOREIGN_LANGUAGE = 1;
	public static final int VAR_HUMANITIES = 2;
	public static final int VAR_CERTIFICATE = 3;
	public static final String[] VAR_CATEGORYS = { "IT", "외국어", "인문학", "자격증" };
	public static final String[] VAR_IMAGES = {"src/image/itImg.png", "src/image/foreignImg.jpg",
											   "src/image/humanityImg.jpg", "src/image/certificateImg.png"};

	public static final Color FRAME_COLOR = new Color(153, 255, 255);
	public static final Color BOARD_COLOR = new Color(235, 235, 235);
	public static final String[] DOMAINS = { "--직접 입력--", "naver.com", "gmail.com", "nate.com", "hanmail.net" };

	// 번호로 카테고리 정보 얻기
	public static String getVarStrByNo(int info_var) {
		String strInfo_var = null;
		switch (info_var) {
		case MainPage.VAR_IT:
			strInfo_var = MainPage.VAR_CATEGORYS[MainPage.VAR_IT];
			break;
		case MainPage.VAR_FOREIGN_LANGUAGE:
			strInfo_var = MainPage.VAR_CATEGORYS[MainPage.VAR_FOREIGN_LANGUAGE];
			break;
		case MainPage.VAR_HUMANITIES:
			strInfo_var = MainPage.VAR_CATEGORYS[MainPage.VAR_HUMANITIES];
			break;
		case MainPage.VAR_CERTIFICATE:
			strInfo_var = MainPage.VAR_CATEGORYS[MainPage.VAR_CERTIFICATE];
			break;
		}
		return strInfo_var;
	}

	// 번호로 카테고리 정보 얻기
	public static int getNoByStrVar(String selectedItem) {
		int info_var = -1;
		if (MainPage.VAR_CATEGORYS[MainPage.VAR_IT].equals(selectedItem)) {
			info_var = MainPage.VAR_IT;
		} else if (MainPage.VAR_CATEGORYS[MainPage.VAR_FOREIGN_LANGUAGE].equals(selectedItem)) {
			info_var = MainPage.VAR_FOREIGN_LANGUAGE;
		} else if (MainPage.VAR_CATEGORYS[MainPage.VAR_HUMANITIES].equals(selectedItem)) {
			info_var = MainPage.VAR_HUMANITIES;
		} else if (MainPage.VAR_CATEGORYS[MainPage.VAR_CERTIFICATE].equals(selectedItem)) {
			info_var = MainPage.VAR_CERTIFICATE;
		}
		return info_var;
	}
	
	// 이미지 띄우기
	public static JLabel getImage(int info_var, int width, int height) {
		JLabel imgLabel = new JLabel();
		ImageIcon icon = new ImageIcon(MainPage.VAR_IMAGES[info_var]);
		Image image = icon.getImage();
		Image updateImg = image.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		imgLabel.setIcon(new ImageIcon(updateImg));
		return imgLabel;
	}
	
	public static void main(String[] args) {
		new Login();
	}

}
