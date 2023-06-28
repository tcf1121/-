package Popup;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import MainScreen.WaitForm;



public class OverPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btnok;
	private WaitForm owner;
	public OverPopup(WaitForm owner) {
		super(owner, "텍스트 기반 로그라이크", true);
	    icon = new ImageIcon(OverPopup.class.getResource("/image/popupbg/gameoverpu.png"));
		this.owner = owner;
	    //배경 Panel 생성후 컨텐츠페인으로 지정  
	    JPanel bg = new JPanel() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
	
			public void paintComponent(Graphics g) {
	            g.drawImage(icon.getImage(), 0, 0, null);
	            setOpaque(false); //그림을 표시하게 설정,투명하게 조절
	            super.paintComponent(g);
	        }
	    };
	    
	    bg.setLayout(null);
	    init();
	    
	    bg.add(btnok);
		
	    scrollPane = new JScrollPane(bg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		ImageIcon okicon = new ImageIcon(OverPopup.class.getResource("/image/ui/ok.png"));
		
		btnok = new JButton(okicon);
		btnok.setBorderPainted(false);
		btnok.setContentAreaFilled(false);
		btnok.setBounds(100, 60, 100, 50);
		
	}
	
    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(298, 130);//프레임의 크기
        setUndecorated(true);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {
        btnok.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            dispose();
        	}
    	});
    }
}
