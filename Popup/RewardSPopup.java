package Popup;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Datarelated.Skill;
import Datarelated.SkillDataSet;
import MainScreen.WaitForm;


//스킬 보상 팝업 , 홀수 스테이지 클리어시 스킬 추가 획득
public class RewardSPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private SkillDataSet skilldata = new SkillDataSet();
	private Skill[] Skill;
	private JButton[] btnSkill = new JButton[3];
	private JButton[] btnSkillex = new JButton[3];
	//private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
	private WaitForm owner;
	public RewardSPopup(WaitForm owner, String class_no) {
		super(owner, "텍스트 기반 로그라이크", true);
	    icon = new ImageIcon(RewardSPopup.class.getResource("/image/popupbg/rewardbg.png"));
		Skill = skilldata.rewardSkill(class_no);
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

		for(int i=0;i<3;i++) {
			bg.add(btnSkill[i]);
			bg.add(btnSkillex[i]);
		}
		
	    scrollPane = new JScrollPane(bg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		ImageIcon exicon = new ImageIcon(RewardSPopup.class.getResource("/image/ui/ex.png"));
		
		for(int i=0;i<3;i++) {		
			ImageIcon skillicon = new ImageIcon(RewardSPopup.class.getResource("/image/skill/"+Skill[i].getId()+".png"));
			btnSkill[i] = new JButton(skillicon);
			btnSkill[i].setBorderPainted(false);
			btnSkill[i].setContentAreaFilled(false);
			btnSkill[i].setBounds(78 +(i*182), 112, 80, 120);
			btnSkillex[i] = new JButton(exicon);
			btnSkillex[i].setBorderPainted(false);
			btnSkillex[i].setContentAreaFilled(false);
			btnSkillex[i].setBounds(102 +(i*182), 250, 30, 30);
		}
	}
	
    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(603,346);//프레임의 크기
        setUndecorated(true);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
	
    private void addListeners() {
    	for(int btnsk=0;btnsk<3;btnsk++) {
        	int btn = btnsk;
        	btnSkill[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	int choice = JOptionPane.showConfirmDialog(
                    		RewardSPopup.this,
                            "이 스킬을 선택하시겠습니까?",
                            "선택",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (choice == JOptionPane.OK_OPTION) {
                        skilldata.addUskill(Skill[btn].getId());
                        dispose();
                    }
                }
                	
    	});
        	btnSkillex[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(
                            RewardSPopup.this,
                            " "+ Skill[btn].getName() + "\n" +
                            "마나소모 :" + Skill[btn].getUmana()+ "\n" +
                            "설명 :" + Skill[btn].getExplan() + "\n",
                            "스킬 정보",JOptionPane.PLAIN_MESSAGE
                            );             
                }
        	});
        }
    }
}
