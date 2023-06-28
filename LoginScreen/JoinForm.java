package LoginScreen;

import javax.swing.*;

import Datarelated.User;
import Datarelated.UserDataSet;
import GameScreen.GameForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;


public class JoinForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private LoginForm owner;
    private UserDataSet users;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JPasswordField tfRe;
    private JTextField tfNickName;
    private JButton btnJoin;
    private JButton btnCancel;

    public JoinForm(LoginForm owner) {
        //super(owner, "회원가입", true);
        this.owner = owner;
        users = owner.getUsers();
        icon = new ImageIcon(JoinForm.class.getResource("/image/background/joinbg.png"));
        
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
            DriverManager.getConnection(connectionUrl);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        bg.setLayout(null);
        init();
        bg.add(tfId);
        bg.add(tfPw);
        bg.add(tfRe);
        bg.add(tfNickName);
        bg.add(btnJoin);
        bg.add(btnCancel);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
    }
    private void init() {
        // 크기 고정
    	ImageIcon joinicon = new ImageIcon(GameForm.class.getResource("/image/ui/join.png"));
    	ImageIcon cancleicon = new ImageIcon(GameForm.class.getResource("/image/ui/cancle.png"));

        Color YColor = new Color(255, 217, 102);
        Color BColor = new Color(28, 69, 135);

        tfId = new JTextField();
        tfId.setBackground(YColor);
        tfId.setForeground(BColor);
        tfPw = new JPasswordField();
        tfPw.setBackground(YColor);
        tfId.setForeground(BColor);
        tfRe = new JPasswordField();
        tfRe.setBackground(YColor);
        tfId.setForeground(BColor);
        tfNickName = new JTextField();
        tfNickName.setBackground(YColor);
        tfId.setForeground(BColor);
        
        btnJoin = new JButton(joinicon);
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        btnCancel = new JButton(cancleicon);
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        
        tfId.setBounds(410, 305, 100, 30);
        tfPw.setBounds(410, 340, 100, 30);
        tfRe.setBounds(410, 375, 100, 30);
        tfNickName.setBounds(410, 410, 100, 30);
        btnJoin.setBounds(290, 460, 100, 50);
        btnCancel.setBounds(410, 460, 100, 50);

    }
    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
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
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // 정보 하나라도 비어있으면
                if(isBlank()) {
                    JOptionPane.showMessageDialog(
                            JoinForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    if(users.isIdOverlap(tfId.getText())) {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "이미 존재하는 Id입니다."
                        );
                        tfId.requestFocus();

                        // Pw와 Re가 일치하지 않았을 때
                    } else if(!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "Password와 Retry가 일치하지 않습니다."
                        );
                        tfPw.requestFocus();
                    } else {
                    	users.addUsers(new User(
                    			tfId.getText(),
                                String.valueOf(tfPw.getPassword()),
                                tfNickName.getText())
                    	);
                        JOptionPane.showMessageDialog(
                                JoinForm.this,
                                "회원가입을 완료했습니다!"
                        );
                        dispose();
                        owner.setVisible(true);
                    }
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                		JoinForm.this,
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
    }
    public boolean isBlank() {
        boolean result = false;
        if(tfId.getText().isEmpty()) {
            tfId.requestFocus();
            return true;
        }
        if(String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if(String.valueOf(tfRe.getPassword()).isEmpty()) {
            tfRe.requestFocus();
            return true;
        }
        if(tfNickName.getText().isEmpty()) {
            tfNickName.requestFocus();
            return true;
        }
        return result;
    }
    

    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(800,600);//프레임의 크기
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
}