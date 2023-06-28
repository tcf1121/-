package MainScreen;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import Datarelated.CharDataSet;
import GameScreen.GameForm;

public class SelectForm extends JFrame implements ItemListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WaitForm owner;
    JScrollPane scrollPane;
    ImageIcon icon;
	ImageIcon iiClass = new ImageIcon(SelectForm.class.getResource("/image/ui/selectCK.png"));
	ImageIcon iiClassO = new ImageIcon(SelectForm.class.getResource("/image/ui/selectCKO.png"));
	private JRadioButton rbRouge;
	private JRadioButton rbWarrior;
	private JRadioButton rbwizard;
	private JButton btnSelect;
	private JButton btnCancle;
	private ButtonGroup bgClass;
    

	public SelectForm(WaitForm owner) {
		//super(owner, "캐릭터 선택창", true);
		this.owner = owner;
		new CharDataSet();
        icon = new ImageIcon(SelectForm.class.getResource("/image/background/SCbg.png"));
        
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
        bg.add(rbRouge);
        bg.add(rbWarrior);
        bg.add(rbwizard);
        bg.add(btnSelect);
        bg.add(btnCancle);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
	}

	private void init() {
		// 크기 고정 및 위치 조절
		ImageIcon selecticon = new ImageIcon(GameForm.class.getResource("/image/ui/select.png"));
		ImageIcon cancleicon = new ImageIcon(GameForm.class.getResource("/image/ui/cancle.png"));
        
		rbRouge = new JRadioButton(iiClass);
		rbRouge.setBounds(115, 360, 100, 100);
		rbRouge.setBorderPainted(false);
		rbRouge.setContentAreaFilled(false);
		rbRouge.addItemListener(this);
		
		rbWarrior = new JRadioButton(iiClass);
		rbWarrior.setBounds(350, 360, 100, 100);
		rbWarrior.setBorderPainted(false);
		rbWarrior.setContentAreaFilled(false);
		rbWarrior.addItemListener(this);
		
		rbwizard = new JRadioButton(iiClass);
		rbwizard.setBounds(580, 360, 100, 100);
		rbwizard.setBorderPainted(false);
		rbwizard.setContentAreaFilled(false);
		rbwizard.addItemListener(this);
		
		bgClass = new ButtonGroup();
		bgClass.add(rbRouge);
		bgClass.add(rbWarrior);
		bgClass.add(rbwizard);
		
		btnSelect = new JButton("선택");


        btnSelect = new JButton(selecticon);
        btnSelect.setBorderPainted(false);
        btnSelect.setContentAreaFilled(false);
        btnCancle = new JButton(cancleicon);
        btnCancle.setBorderPainted(false);
        btnCancle.setContentAreaFilled(false);
        btnSelect.setBounds(290, 460, 100, 50);
        btnCancle.setBounds(410, 460, 100, 50);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(rbRouge.isSelected()) {
				rbRouge.setIcon(iiClassO);
				rbWarrior.setIcon(iiClass);
				rbwizard.setIcon(iiClass);
			}
			else if(rbWarrior.isSelected()) {			
				rbRouge.setIcon(iiClass);
				rbWarrior.setIcon(iiClassO);
				rbwizard.setIcon(iiClass);
			}
			else if(rbwizard.isSelected()) {
				rbRouge.setIcon(iiClass);
				rbWarrior.setIcon(iiClass);
				rbwizard.setIcon(iiClassO);
			}
		}
	}
	
	private void addListeners() {

        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(rbRouge.isSelected()) {
            		owner.setClass_Id("03");
                    JOptionPane.showMessageDialog(
                    		SelectForm.this,
                            "도적을 선택했습니다."
                    );
            	}
            	else if(rbWarrior.isSelected()) {
            		owner.setClass_Id("01");
                    JOptionPane.showMessageDialog(
                    		SelectForm.this,
                            "전사를 선택했습니다."
                    );
            	}
            	else if(rbwizard.isSelected()) {
            		owner.setClass_Id("02");
                    JOptionPane.showMessageDialog(
                    		SelectForm.this,
                            "마법사를 선택했습니다."
                    );
            	}

            	dispose();
                owner.setVisible(true);
            }
        });
        btnCancle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                owner.setVisible(true);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                		SelectForm.this,
                        "게임을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(800,600);//프레임의 크기
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    /*메인함수*/
	/*public static void main(String[] args){
    	select frame = new select(owner);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}*/
}
