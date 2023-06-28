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

import Datarelated.Equip;
import Datarelated.EquipDataSet;
import MainScreen.WaitForm;


//장비 보상 팝업 , 짝수 스테이지 클리어시 장비 추가 획득 2 신발 4 갑옷 6 방패 8 검
public class RewardEPopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private EquipDataSet equipdata = new EquipDataSet();
	private Equip[] Equip;
	private JButton[] btnEquip = new JButton[3];
	private JButton[] btnEquipex = new JButton[3];
	private WaitForm owner;
	private int s_num;
	
	public RewardEPopup(WaitForm owner, int stage_num) {
		super(owner, "텍스트 기반 로그라이크", true);
	    icon = new ImageIcon(RewardEPopup.class.getResource("/image/popupbg/rewardbg.png"));
	    Equip = equipdata.rewardEquip(stage_num);
		this.owner = owner;
		s_num = stage_num;
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
			bg.add(btnEquip[i]);
			bg.add(btnEquipex[i]);
		}
		
	    scrollPane = new JScrollPane(bg);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		ImageIcon exicon = new ImageIcon(RewardEPopup.class.getResource("/image/ui/ex.png"));
		
		for(int i=0;i<3;i++) {		
			ImageIcon equipicon = new ImageIcon(RewardEPopup.class.getResource("/image/equipment/"+Equip[i].getId()+".png"));
			btnEquip[i] = new JButton(equipicon);
			btnEquip[i].setBorderPainted(false);
			btnEquip[i].setContentAreaFilled(false);
			btnEquip[i].setBounds(78 +(i*182), 112, 80, 120);
			btnEquipex[i] = new JButton(exicon);
			btnEquipex[i].setBorderPainted(false);
			btnEquipex[i].setContentAreaFilled(false);
			btnEquipex[i].setBounds(102 +(i*182), 250, 30, 30);
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
        	btnEquip[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	int choice = JOptionPane.showConfirmDialog(
                    		RewardEPopup.this,
                            "이 장비를 선택하시겠습니까?",
                            "선택",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (choice == JOptionPane.OK_OPTION) {
                    	equipdata.Equipped(Equip[btn].getId());
                    	equipdata.Apply_Stats(s_num, Equip[btn].getEffect());
                        dispose();
                    }
                }
                	
    	});
        	btnEquipex[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(
                            RewardEPopup.this,
                            " "+ Equip[btn].getName() + "\n" +
                            "등급 :" + Equip[btn].getRarity()+ "\n" +
                            "설명 :" + Equip[btn].getExplan()+ "\n" +
                            "효과 :" + Equip[btn].getEf_ex() + "\n",
                            "장비 정보",JOptionPane.PLAIN_MESSAGE
                            );             
                }
        	});
        }
    }
}
