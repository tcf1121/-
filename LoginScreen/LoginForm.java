package LoginScreen;

import javax.swing.*;

import Datarelated.UserDataSet;
import GameScreen.GameForm;
import MainScreen.WaitForm;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
public class LoginForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDataSet users;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    
    public LoginForm() {
    	users = new UserDataSet();
        icon = new ImageIcon(LoginForm.class.getResource("/image/background/loginbg.png"));
       
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
        bg.add(tfId);
        bg.add(tfPw);
        bg.add(btnLogin);
        bg.add(btnJoin);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
    }
    public void init() {
    	ImageIcon loginicon = new ImageIcon(GameForm.class.getResource("/image/ui/login.png"));
    	ImageIcon joinicon = new ImageIcon(GameForm.class.getResource("/image/ui/join.png"));
        Color YColor = new Color(255, 217, 102);
        Color BColor = new Color(28, 69, 135);
        
        
        tfId = new JTextField();
        tfId.setBackground(YColor);
        tfId.setForeground(BColor);
        tfPw = new JPasswordField();
        tfPw.setBackground(YColor);
        tfId.setForeground(BColor);
        
        btnLogin = new JButton(loginicon);
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
        btnJoin = new JButton(joinicon);
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        
        tfId.setBounds(410, 380, 100, 30);
        tfPw.setBounds(410, 415, 100, 30);
        btnLogin.setBounds(290, 460, 100, 50);
        btnJoin.setBounds(410, 460, 100, 50);
        
        
    }
    public void showFrame() {
        setTitle("로그인창");
        setSize(800,600);
        setLocationRelativeTo(null);//창이 가운데 나오게
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디칸이 비었을 경우
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "아이디를 입력하세요.",
                            "오류",
                            JOptionPane.WARNING_MESSAGE);

                    // 존재하는 아이디일 경우
                } else if (users.isIdOverlap(tfId.getText())) {

                    // 비밀번호칸이 비었을 경우
                    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(
                        		LoginForm.this,
                                "비밀번호를 입력하세요.",
                                "오류",
                                JOptionPane.WARNING_MESSAGE);

                        // 비밀번호가 일치하지 않을 경우
                    } else if (!users.getUser(tfId.getText()).getPw().equals(String.valueOf(tfPw.getPassword()))) {
                        JOptionPane.showMessageDialog(
                        		LoginForm.this,
                                "비밀번호가 일치하지 않습니다.");

                        // 다 완료될 경우
                    } else {
                    	setVisible(false);
                        new WaitForm(LoginForm.this);
                        tfId.setText("");
                        tfPw.setText("");
                    }
                    // 존재하지 않는 Id일 경우
                } else {
                    JOptionPane.showMessageDialog(
                    		LoginForm.this,
                            "존재하지 않는 Id입니다."
                         
                    );
                
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		LoginForm.this,
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
   
    public UserDataSet getUsers() {
    	return users;
    }
    
    public String getTfId() {
		return tfId.getText();
	}
    
    public static void main(String[] args) {
    	LoginForm frame = new LoginForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
