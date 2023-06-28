package MainScreen;

import javax.swing.*;


import Datarelated.CharDataSet;
import Datarelated.UserDataSet;
import GameScreen.GameForm;
import LoginScreen.LoginForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaitForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private LoginForm owner;
    private Connection conn;
    private CharDataSet LookAb = new CharDataSet();
    private String Class_Id = "01";
    private JLabel lblLv;
    private JLabel lblNickName;
    private JButton btnDungeon;
    private JButton btnAbility;
    private JButton btnScharacter;
    private JButton btnSaveEnd;
    private String NickName = null;
    private UserDataSet users;
    private String id;
    private int Lv;
    public WaitForm(LoginForm owner) {
        //super(owner, "텍스트 기반 로그라이크", true);
        this.owner = owner;
        id = owner.getTfId();
        LookAb.deleteNAbility();
        LookAb.setNAbility(Class_Id, owner.getTfId());
        users = owner.getUsers();
        System.out.println();
        icon = new ImageIcon(WaitForm.class.getResource("/image/background/waitbg.png"));
        
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
        try {
        	//JDBC Driver 등록
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //연결하기
            String connectionUrl = 
            		"jdbc:sqlserver://localhost:1433;"
            				+"database=rogue;"
            				+"encrypt=false;"
            				+"user=tcf12;"
            				+"password=wlslaks!12;";
            conn = DriverManager.getConnection(connectionUrl);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        bg.setLayout(null);
        init();
        

        bg.add(lblLv);
        bg.add(lblNickName);
        bg.add(btnDungeon);
        bg.add(btnAbility);
        bg.add(btnScharacter);
        bg.add(btnSaveEnd);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        addListeners();
        showFrame();

    }
    private void init() {
        Color YColor = new Color(255, 217, 102);
		ImageIcon dungenicon = new ImageIcon(GameForm.class.getResource("/image/ui/dungeon.png"));
		ImageIcon abilityicon = new ImageIcon(GameForm.class.getResource("/image/ui/ability.png"));
		ImageIcon selectCharicon = new ImageIcon(GameForm.class.getResource("/image/ui/selectChar.png"));
		ImageIcon endgameicon = new ImageIcon(GameForm.class.getResource("/image/ui/endgame.png"));

        try {
        	String sql = "select user_lv, nickname from userinfo where id=?";
     	   	PreparedStatement pstmt = conn.prepareStatement(sql);
     	   	pstmt.setString(1, owner.getTfId());
     	   	ResultSet rs = pstmt.executeQuery();
     	   	while(rs.next()) {
     	   		Lv = rs.getInt("user_lv");
     	   		NickName = rs.getString("nickname");
     	   	}
     	   	rs.close();
     	   	pstmt.close();	   
        }catch (SQLException e) {
     	   e.printStackTrace();
        }
        
        lblLv = new JLabel(String.valueOf(Lv));
        lblLv.setForeground(YColor);
        lblLv.setFont(new Font("돋움", Font.BOLD, 40));
        lblLv.setBounds(405, 42, 200, 50);
        lblNickName = new JLabel(NickName);
        lblNickName.setForeground(YColor);
        lblNickName.setBounds(390 - (15*NickName.length()), 80, 200, 50);
        lblNickName.setFont(new Font("돋움", Font.BOLD, 50));
        
        btnDungeon = new JButton(dungenicon);
        btnDungeon.setBounds(265, 150, 250, 50);
        btnDungeon.setBorderPainted(false);
        btnDungeon.setContentAreaFilled(false);
        btnAbility = new JButton(abilityicon);
        btnAbility.setBounds(265, 220, 250, 50);
        btnAbility.setBorderPainted(false);
        btnAbility.setContentAreaFilled(false);
        btnScharacter = new JButton(selectCharicon);
        btnScharacter.setBounds(265, 290, 250, 50);
        btnScharacter.setBorderPainted(false);
        btnScharacter.setContentAreaFilled(false);
        btnSaveEnd = new JButton(endgameicon);
        btnSaveEnd.setBounds(265, 360, 250, 50);
        btnSaveEnd.setBorderPainted(false);
        btnSaveEnd.setContentAreaFilled(false);
        
        
    }
    
	//버튼 이벤트 항수
    private void addListeners() {
        btnDungeon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	new GameForm(WaitForm.this, 1);
            }
        });
        
        btnScharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	new SelectForm(WaitForm.this);
            	
            }
        });
        btnAbility.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	new AbilityForm(WaitForm.this, Lv);
            }
        });
        btnSaveEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int choice = JOptionPane.showConfirmDialog(
                		WaitForm.this,
                        "게임을 종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                	System.exit(0);
                }
                
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
            	int choice = JOptionPane.showConfirmDialog(
                		WaitForm.this,
                        "로그아웃하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
            	if (choice == JOptionPane.YES_OPTION) {
            		dispose();
                    owner.setVisible(true);
                }
                
            }
        });
    }
    public String getId() {
		return id;
	}
    public String getClass_Id() {
    	return Class_Id;
    }
    public void setClass_Id(String Class_Id) {
    	this.Class_Id = Class_Id;
    }
    public String getNickname() {
    	return NickName;
    }
    public UserDataSet getUsers() {
    	return users;
    }
    private void showFrame() {
    	setTitle("텍스트 기반 로그라이크");
        setSize(800,600);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    
    

}