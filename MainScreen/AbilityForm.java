package MainScreen;

import javax.swing.*;

import Datarelated.Ability;
import Datarelated.CharDataSet;
import Datarelated.UserDataSet;
import GameScreen.GameForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AbilityForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDataSet users;
	private CharDataSet CharD;
    private WaitForm owner;
    JScrollPane scrollPane;
    ImageIcon icon;

    private JButton btnStrP;
    private JButton btnStrM;
    private JButton btnDefP;
    private JButton btnDefM;
    private JButton btnHpP;
    private JButton btnHpM;
    private JButton btnMpP;
    private JButton btnMpM;
 /*   private JButton btnCriP;
    private JButton btnCriM;
    private JButton btnMissP;
    private JButton btnMissM;*/
    private JButton btnApply;
    private JButton btnCancel;
    private JLabel lblStr;
    private JLabel lblDef;
    private JLabel lblHp;
    private JLabel lblMp;
    private JLabel lblAP;
    private Ability sAP;
    int Strcount = 0;
    int Defcount = 0;
    int Hpcount = 0;
    int Mpcount = 0;
    int Cricount = 0;
    int Misscount = 0;
    int APcount;
    
    public AbilityForm(WaitForm owner, int Lv) {
        //super(owner, "텍스트 기반 로그라이크", true);
        this.owner = owner;
        CharD = new CharDataSet();
        APcount = Lv;
    	if(CharD.isSaveAP(owner.getId())) {
    		sAP = CharD.LoadAP(owner.getId());
    		Strcount = sAP.getStr();
    		Defcount = sAP.getDef();
    		Hpcount = sAP.getHp();
    		Mpcount = sAP.getMp();
    		/*Cricount = sAP.getCri();
    		Misscount = sAP.getMiss();*/
    		APcount = (Lv - ((Strcount+Defcount)+
    				(Hpcount+Mpcount+Cricount+Misscount)/2));
    	}
    	users = new UserDataSet();
        icon = new ImageIcon(AbilityForm.class.getResource("/image/background/abbg.png"));
       
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
        bg.add(btnStrP); bg.add(btnStrM); bg.add(btnDefP);
        bg.add(btnDefM); bg.add(btnHpP); bg.add(btnHpM);
        bg.add(btnMpP); bg.add(btnMpM); //bg.add(btnCriP);
        //bg.add(btnCriM); bg.add(btnMissP); bg.add(btnMissM);
        bg.add(btnApply); bg.add(btnCancel);
        bg.add(lblStr); bg.add(lblDef); bg.add(lblHp);
        bg.add(lblMp); //bg.add(lblCri); bg.add(lblMiss);
        bg.add(lblAP);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        addListeners();
        showFrame();
    }
    private void init() {
        // 사이즈 통합
		ImageIcon applyicon = new ImageIcon(GameForm.class.getResource("/image/ui/apply.png"));
		ImageIcon cancleicon = new ImageIcon(GameForm.class.getResource("/image/ui/cancle.png"));
		ImageIcon plusicon = new ImageIcon(GameForm.class.getResource("/image/ui/plus.png"));
		ImageIcon minusicon = new ImageIcon(GameForm.class.getResource("/image/ui/minus.png"));
        Color WColor = new Color(255, 255, 255);
        int ws = 42;
        
        btnStrP = new JButton(plusicon);
        btnStrP.setBorderPainted(false);
        btnStrP.setContentAreaFilled(false);
        btnStrP.setBounds(250, 150, ws, ws);
        btnStrM = new JButton(minusicon);
        btnStrM.setBorderPainted(false);
        btnStrM.setContentAreaFilled(false);
        btnStrM.setBounds(310, 150, ws, ws);
        btnDefP = new JButton(plusicon);
        btnDefP.setBorderPainted(false);
        btnDefP.setContentAreaFilled(false);
        btnDefP.setBounds(570, 150, ws, ws);
        btnDefM = new JButton(minusicon);
        btnDefM.setBorderPainted(false);
        btnDefM.setContentAreaFilled(false);
        btnDefM.setBounds(630, 150, ws, ws);
        btnHpP = new JButton(plusicon);
        btnHpP.setBorderPainted(false);
        btnHpP.setContentAreaFilled(false);
        btnHpP.setBounds(250, 250, ws, ws);
        btnHpM = new JButton(minusicon);
        btnHpM.setBorderPainted(false);
        btnHpM.setContentAreaFilled(false);
        btnHpM.setBounds(310, 250, ws, ws);
        btnMpP = new JButton(plusicon);
        btnMpP.setBorderPainted(false);
        btnMpP.setContentAreaFilled(false);
        btnMpP.setBounds(570, 250, ws, ws);
        btnMpM = new JButton(minusicon);
        btnMpM.setBorderPainted(false);
        btnMpM.setContentAreaFilled(false);
        btnMpM.setBounds(630, 250, ws, ws);
        /*btnCriP = new JButton("+");
        btnCriP.setBackground(YColor);
        btnCriP.setForeground(BColor);
        btnCriP.setBounds(250, 350, ws, ws);
        btnCriM = new JButton("-");
        btnCriM.setBackground(YColor);
        btnCriM.setForeground(BColor);
        btnCriM.setBounds(310, 350, ws, ws);
        btnMissP = new JButton("+");
        btnMissP.setBackground(YColor);
        btnMissP.setForeground(BColor);
        btnMissP.setBounds(570, 350, ws, ws);
        btnMissM = new JButton("-");
        btnMissM.setBackground(YColor);
        btnMissM.setForeground(BColor);
        btnMissM.setBounds(630, 350, ws, ws);*/
        btnApply = new JButton(applyicon);
        btnApply.setBorderPainted(false);
        btnApply.setContentAreaFilled(false);
        btnCancel = new JButton(cancleicon);
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        btnApply.setBounds(290, 340, 100, 50);
        btnCancel.setBounds(410, 340, 100, 50);
        
        lblStr = new JLabel(""+Strcount);
        lblStr.setBounds(195, 150, ws, ws);
        lblStr.setFont(new Font("돋움", Font.BOLD, 35));
        lblStr.setForeground(WColor);
        lblDef = new JLabel(""+Defcount);
        lblDef.setBounds(515, 150, ws, ws);
        lblDef.setFont(new Font("돋움", Font.BOLD, 35));
        lblDef.setForeground(WColor);
        lblHp = new JLabel(""+Hpcount);
        lblHp.setBounds(195, 250, ws, ws);
        lblHp.setFont(new Font("돋움", Font.BOLD, 35));
        lblHp.setForeground(WColor);
        lblMp = new JLabel(""+Mpcount);
        lblMp.setBounds(515, 250, ws, ws);
        lblMp.setFont(new Font("돋움", Font.BOLD, 35));
        lblMp.setForeground(WColor);
        /*lblCri = new JLabel(""+Cricount);
        lblCri.setBounds(195, 350, ws, ws);
        lblCri.setFont(new Font("돋움", Font.BOLD, 35));
        lblCri.setForeground(WColor);
        lblMiss = new JLabel(""+Misscount);
        lblMiss.setBounds(515, 350, ws, ws);
        lblMiss.setFont(new Font("돋움", Font.BOLD, 35));
        lblMiss.setForeground(WColor);*/
        lblAP = new JLabel(""+APcount);
        lblAP.setBounds(368, 50, ws, ws);
        lblAP.setFont(new Font("돋움", Font.BOLD, 35));
        lblAP.setForeground(WColor);
    }
    
	private void addListeners() {
    	
        btnStrP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Strcount==10 || APcount == 0) {}
            	else{
            		Strcount++;
            		lblStr.setText(""+Strcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnStrM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Strcount==0) {}
            	else{
            		Strcount--;
            		lblStr.setText(""+Strcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnDefP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Defcount==10|| APcount == 0) {}
            	else{
            		Defcount++;
            		lblDef.setText(""+Defcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnDefM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Defcount==0) {}
            	else{
            		Defcount--;
            		lblDef.setText(""+Defcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnHpP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Hpcount==20|| APcount == 0) {}
            	else{
            		Hpcount = Hpcount +2;
            		lblHp.setText(""+Hpcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnHpM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Hpcount==0) {}
            	else{
            		Hpcount = Hpcount -2;
            		lblHp.setText(""+Hpcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMpP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Mpcount==20|| APcount == 0) {}
            	else{
            		Mpcount = Mpcount +2;
            		lblMp.setText(""+Mpcount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMpM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Mpcount==0) {}
            	else{
            		Mpcount = Mpcount -2;
            		lblMp.setText(""+Mpcount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        /*btnCriP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Cricount==10|| APcount == 0) {}
            	else{
            		Cricount = Cricount +2;
            		lblCri.setText(""+Cricount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnCriM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Cricount==0) {}
            	else{
            		Cricount = Cricount -2;
            		lblCri.setText(""+Cricount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMissP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Misscount==10|| APcount == 0) {}
            	else{
            		Misscount = Misscount +2;
            		lblMiss.setText(""+Misscount);
            		APcount--;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });
        btnMissM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(Misscount==0) {}
            	else{
            		Misscount = Misscount -2;
            		lblMiss.setText(""+Misscount);
            		APcount++;
            		lblAP.setText(""+APcount);
            	}       		      
            }
        });*/
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	sAP = new Ability(getName(), Strcount,
            			Defcount, Hpcount, Mpcount);
            	CharD.SaveAP(owner.getId(), sAP);
                dispose();
                owner.setVisible(true);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                owner.setVisible(true);
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

   
    public UserDataSet getUsers() {
    	return users;
    }
    
}