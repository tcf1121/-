package Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import MainScreen.WaitForm;
import Datarelated.Ability;


public class AbilityPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btnX;
	private JLabel lblClass;
	private JLabel lblStr;
	private JLabel lblDef;
	private JLabel lblMaxhp;
	private JLabel lblMaxmp;
	private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
	private WaitForm owner;
	
	public AbilityPopup(WaitForm owner, Ability nAbility) {
		/*super(owner, "텍스트 기반 로그라이크", true);*/
		Now_Ability = nAbility;
		this.owner = owner;
	    icon = new ImageIcon(AbilityPopup.class.getResource("/image/popupbg/abilitybg.png"));
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
	    /*
        "직업 :" + CAbility.getcl() + "\n" +
        "공격력 :" + CAbility.getStr()+ "\n" +
        "방어력 :" + CAbility.getDef()+ "\n" +
        "최대 체력 :" + CAbility.getHp()+ "\n" +
        "최대 마나 :" + CAbility.getMp()+ "\n" */
	    bg.setLayout(null);
	    init();

		bg.add(lblClass);
		bg.add(lblStr);
		bg.add(lblDef);
		bg.add(lblMaxhp);
		bg.add(lblMaxmp);
		bg.add(btnX);
		
	    scrollPane = new JScrollPane(bg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		ImageIcon xicon = new ImageIcon(AbilityPopup.class.getResource("/image/ui/x.png"));
		Font nowf = new Font("돋움", Font.BOLD, 20);
        lblClass = new JLabel(Now_Ability.getcl());
        lblClass.setForeground(Color.white);
        lblClass.setBounds(130, 54, 50, 25);
        lblClass.setFont(nowf);
        
        lblStr = new JLabel(String.valueOf(Now_Ability.getStr()));
        lblStr.setForeground(Color.white);
        lblStr.setBounds(130, 83, 50, 25);
        lblStr.setFont(nowf);
        
        lblDef = new JLabel(String.valueOf(Now_Ability.getDef()));
        lblDef.setForeground(Color.white);
        lblDef.setBounds(130, 112, 50, 25);
        lblDef.setFont(nowf);
        
        lblMaxhp = new JLabel(String.valueOf(Now_Ability.getHp()));
        lblMaxhp.setForeground(Color.white);
        lblMaxhp.setBounds(130, 141, 50, 25);
        lblMaxhp.setFont(nowf);
        
        lblMaxmp = new JLabel(String.valueOf(Now_Ability.getMp()));
        lblMaxmp.setForeground(Color.white);
        lblMaxmp.setBounds(130, 170, 50, 25);
        lblMaxmp.setFont(nowf);
        
        btnX = new JButton(xicon);
		btnX.setBorderPainted(false);
		btnX.setContentAreaFilled(false);
		btnX.setBounds(204, 5, 18, 19);

	}
	
    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(230,205);//프레임의 크기
        setUndecorated(true);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() { 
    	
        btnX.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            dispose();
        	}
    	});
    }
    

}
