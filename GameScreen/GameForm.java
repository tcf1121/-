package GameScreen;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import Datarelated.Ability;
import Datarelated.CharDataSet;
import Datarelated.EquipDataSet;
import MainScreen.WaitForm;
import Popup.AbilityPopup;
import Popup.ClearPopup;
import Popup.OverPopup;
import Datarelated.Monster;
import Datarelated.MonsterDataSet;
import Datarelated.Skill;
import Datarelated.SkillDataSet;
import Datarelated.UserDataSet;

public class GameForm extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	public int skillnum = 3;
	private SkillDataSet skilldata = new SkillDataSet();
	private EquipDataSet equipdata = new EquipDataSet();
	private Skill[] Skill;
	private JButton[] btnSkill;
	private JButton[] btnSkillex;
	private JButton btnTurnend;
	private JButton btnLookAb;
	private JLabel lblCharater;
	private JLabel lblOnedef;
	private Random random = new Random();;
	private int mon_num = random.nextInt(3)+1;
	private JLabel[] lblMonster;
	private JLabel[] lblattack;
	private WaitForm owner;
    private Ability Now_Ability = new Ability("" , 0, 0, 0, 0);
    private ImageIcon Charicon;
    private JProgressBar Hpbar;
    private JProgressBar Mpbar;
    private int max_hp, now_hp;
    private int max_mp, now_mp;
    private int monsize;
    private int s_num;
    private UserDataSet users;
    private CharDataSet lookab = new CharDataSet();
    private MonsterDataSet Monsterdata;
    private Monster[] Monster;
    private String[] s_mon;
    private String[] w_mon = {"전체공격"};
    private JTextArea BattleLog;
    private JScrollPane scrollLog;
    private int magic_str=0;
    private int magic_def=0;
    private int onetime_def = 0;
    private int bf_exp = 0;

	public GameForm(WaitForm owner, int Stage_num) {
		//super(owner, "텍스트 기반 로그라이크", true);
        this.owner = owner;
        users = owner.getUsers();
		if(Stage_num >3) {
			skillnum = 4;
		}
		else if(Stage_num >6) {
			skillnum = 5;
		}
		monsize = 100;
		if(Stage_num >5 && Stage_num <10) {
			icon = new ImageIcon(GameForm.class.getResource("/image/background/gamehbg.png"));
			mon_num = random.nextInt(2)+1;
			monsize = 150;
		}else if(Stage_num == 10) {
			icon = new ImageIcon(GameForm.class.getResource("/image/background/gamehbg.png"));
			mon_num = 1;
			monsize = 400;}
        Now_Ability = lookab.getNAbility();
        max_hp = Now_Ability.getHp();
        now_hp = max_hp;
        max_mp = Now_Ability.getMp();
        now_mp = max_mp;
		s_num = Stage_num; 
		bf_exp = users.get_exp(owner.getId());
		btnSkill = new JButton[skillnum];
		btnSkillex = new JButton[skillnum];
		lblMonster = new JLabel[mon_num];
		lblattack = new JLabel[mon_num];
		Monster = new Monster[mon_num];
		s_mon = new String[mon_num];
		icon = new ImageIcon(GameForm.class.getResource("/image/background/gamebg.png"));
		if(Stage_num == 1) {
			skilldata.deleteUskill();
			equipdata.deleteEquipped();
			skilldata.SGskill(owner.getClass_Id());
		}


        img_class(Now_Ability.getcl());
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
		for(int i=0;i<skillnum;i++) {
			bg.add(btnSkill[i]);
			bg.add(btnSkillex[i]);
		}
		bg.add(lblCharater);
		bg.add(lblOnedef);
		for(int i=0;i<mon_num;i++) {
			bg.add(lblMonster[i]);
			bg.add(lblattack[i]);
		}
		
		bg.add(btnTurnend);
		bg.add(btnLookAb);
		bg.add(Hpbar);
		bg.add(Mpbar);
		bg.add(scrollLog);
		
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        addListeners();
		showFrame();
	}
	//위치 정의
	private void init() {
        Color YColor = new Color(255, 217, 102);
        Color BColor = new Color(28, 69, 135);
		ImageIcon turnicon = new ImageIcon(GameForm.class.getResource("/image/ui/turnend.png"));
		ImageIcon exicon = new ImageIcon(GameForm.class.getResource("/image/ui/ex.png"));
		ImageIcon shieldicon = new ImageIcon(GameForm.class.getResource("/image/ui/shield.png"));
		ImageIcon attackicon = new ImageIcon(GameForm.class.getResource("/image/ui/attack.png"));
		Skill = skilldata.randUskill(skillnum);
		for(int i=0;i<skillnum;i++) {		
			ImageIcon skillicon = new ImageIcon(GameForm.class.getResource("/image/skill/"+Skill[i].getId()+".png"));
			btnSkill[i] = new JButton(skillicon);
			btnSkill[i].setBorderPainted(false);
			btnSkill[i].setContentAreaFilled(false);
			btnSkill[i].setBounds(258 +(i*100), 400, 80, 120);
			btnSkillex[i] = new JButton(exicon);
			btnSkillex[i].setBorderPainted(false);
			btnSkillex[i].setContentAreaFilled(false);
			btnSkillex[i].setBounds(282 +(i*100), 355, 30, 30);
		}
		lblCharater = new JLabel(Charicon);
		lblCharater.setBounds(50, 100, 200, 200);

		for(int i=0;i<mon_num;i++) {
			ImageIcon Monicon = new ImageIcon(GameForm.class.getResource("/image/monster/"+s_num +".png"));
			Monster[i] = new Monster(0, 0, 0);
			Monsterdata = new MonsterDataSet();
			lblMonster[i] = new JLabel(Monicon);
			lblMonster[i].setBounds(420+(i*(10+monsize)), 280-monsize,
					monsize, monsize);
			Monsterdata.addMon(Monster[i], s_num);
	        lblattack[i] = new JLabel(attackicon);
	        lblattack[i].setForeground(Color.white);
	        lblattack[i].setBounds(445+(i*(10+monsize)), 230-monsize, 50, 50);
	        lblattack[i].setHorizontalTextPosition(JLabel.CENTER);
	        lblattack[i].setVerticalTextPosition(JLabel.CENTER);
	        lblattack[i].setText(String.valueOf(Monster[i].getStr()));
			if(s_num ==10) {
				lblMonster[i].setBounds(399, -23,
						monsize, monsize);
			}
			s_mon[i] = Integer.toString(i+1);

		}

		
		btnLookAb = new JButton(exicon);
        btnLookAb.setBounds(40, 40, 30, 30);
        btnLookAb.setBorderPainted(false);
		btnLookAb.setContentAreaFilled(false);
		
		btnTurnend = new JButton(turnicon);
		btnTurnend.setBounds(40, 400, 200, 120);
		btnTurnend.setBorderPainted(false);
		btnTurnend.setContentAreaFilled(false);
		
		Hpbar = new JProgressBar(0, max_hp);
		Hpbar.setStringPainted(true);
		Hpbar.setValue(now_hp);
		Hpbar.setForeground(Color.red);
		Hpbar.setBackground(Color.black);
		Hpbar.setString(now_hp+"/"+max_hp);
		Hpbar.setBounds(40, 343, 200, 25);
		
		Mpbar = new JProgressBar(0, max_mp);
		Mpbar.setStringPainted(true);
		Mpbar.setValue(now_mp);
		Mpbar.setForeground(Color.blue);
		Mpbar.setBackground(Color.black);
		Mpbar.setString(now_mp+"/"+max_mp);
		Mpbar.setBounds(40, 368, 200, 25);
		
		BattleLog = new JTextArea(); 
		BattleLog.setBackground(YColor);//txt1의 배경색상을 노란색으로 지정합니다.
		BattleLog.setForeground(BColor);
		BattleLog.setEditable(false);
        TitledBorder border = new TitledBorder(new LineBorder(BColor, 2), "전투 기록");
        border.setTitleColor(YColor);
        scrollLog = new JScrollPane(BattleLog);
        scrollLog.setOpaque(false);
        scrollLog.setBorder(border);
        scrollLog.setBounds(80, 28, 280, 100);
        
        lblOnedef = new JLabel(shieldicon);
        lblOnedef.setForeground(Color.white);
        lblOnedef.setBounds(230, 200, 50, 50);
        lblOnedef.setHorizontalTextPosition(JLabel.CENTER);
        lblOnedef.setVerticalTextPosition(JLabel.CENTER);
        lblOnedef.setText(String.valueOf(onetime_def));
        lblOnedef.setVisible(false);
	}
	
	private void img_class(String class_no) {
		if(class_no.equals("전사")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/warrior_stand.png"));
		}
		else if(class_no.equals("마법사")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/wizard_stand.png"));
		}
		else if(class_no.equals("도적")) {
			Charicon = new ImageIcon(GameForm.class.getResource("/image/character/rouge_stand.png"));
		}
	}
	
	//버튼 이벤트 항수
	private void addListeners() {
    	btnTurnend.addActionListener(new ActionListener() {
    		private int save_hp;
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        GameForm.this,
                        "턴 종료");   
                
                if(onetime_def >0){
                	save_hp = now_hp;
                	now_hp += onetime_def;
                }
                for(int i=0;i<s_mon.length;i++) {
                	now_hp -= Monster[monster_death(s_mon[i])].getStr();
                	addBattleLog(StrLog(monster_name(s_num),
                			owner.getNickname(), Monster[monster_death(s_mon[i])].getStr())); 
                }
                if(onetime_def >0){
                	if(now_hp > save_hp){
                		now_hp = save_hp;
                	}
                }
                lblOnedef.setVisible(false);
                onetime_def = 0;
                magic_str = 0;
                magic_def = 0;
                now_mp = max_mp;
                Hpbar.setValue(now_hp);
        		Hpbar.setString(now_hp+"/"+max_hp);
            	Mpbar.setValue(now_mp);
        		Mpbar.setString(now_mp+"/"+max_mp);
        		
        		Skill = skilldata.randUskill(skillnum);
        		for(int i=0;i<skillnum;i++) {		
        			ImageIcon skillicon = new ImageIcon(GameForm.class.getResource("/image/skill/"+Skill[i].getId()+".png"));
        			btnSkill[i].setVisible(true);
        			btnSkill[i].setIcon(skillicon);
        			btnSkillex[i].setVisible(true);
        		}
        		if(now_hp<1) {
        			new OverPopup(owner);
        			dispose();
                	new GameOverForm(owner, bf_exp);
        		}
            }
            
        });
        btnLookAb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Ability CAbility = lookab.getNAbility();
            	new AbilityPopup(owner, CAbility);
            	
            }
        });
        for(int btnsk=0;btnsk<skillnum;btnsk++) {
        	int btn = btnsk;
        	btnSkill[btn].addActionListener(new ActionListener() {
    		private int death_num;
    		private int num;
    		private String death_mon;
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(now_mp >= Skill[btn].getUmana()) {
                		if(s_mon.length >0) {
                    		if(Skill[btn].getType().equals("S_attack")) {
                        		num = JOptionPane.showOptionDialog(null, "공격", "몬스터", JOptionPane.YES_NO_CANCEL_OPTION,
                            			JOptionPane.QUESTION_MESSAGE, null, s_mon, null);
                        		num = monster_death(s_mon[num]);
                        		if(Skill[btn].getId().equals("13")) {
                        			Monster[num].damage(skilldata.effectSkill(Skill[btn], Now_Ability.getDef()+magic_def));
                                	addBattleLog( StrLog(owner.getNickname(),monster_name(s_num),
                                			Now_Ability.getDef()+magic_def+Skill[btn].getEffect())); 
                        		}
                        		else {
                        			Monster[num].damage(skilldata.effectSkill(Skill[btn], Now_Ability.getStr()+magic_str));
                        			addBattleLog( StrLog(owner.getNickname(),monster_name(s_num),
                        					Now_Ability.getStr()+magic_str+Skill[btn].getEffect())); 
                        		}
                            }
                        	else if(Skill[btn].getType().equals("W_attack")) {
                        		JOptionPane.showOptionDialog(null, "공격", "몬스터", JOptionPane.YES_NO_CANCEL_OPTION,
                            			JOptionPane.QUESTION_MESSAGE, null, w_mon, null);
                        		for(int i =0;i<s_mon.length;i++) {
                        			num = monster_death(s_mon[i]);
                            		Monster[num].damage(skilldata.effectSkill(Skill[btn], Now_Ability.getStr()+magic_str));
                            		addBattleLog( StrLog(owner.getNickname(),monster_name(s_num),
                        					Now_Ability.getStr()+magic_str+Skill[btn].getEffect())); 
                        			}
                            	}
                        	else if(Skill[btn].getType().equals("Defense")) {
                                JOptionPane.showMessageDialog(
                                        GameForm.this,
                                        ""+Skill[btn].getExplan(),"효과",JOptionPane.PLAIN_MESSAGE
                                        );
                                onetime_def += Skill[btn].getEffect()+ Now_Ability.getDef() + magic_def;
                                lblOnedef.setText(String.valueOf(onetime_def));
                                lblOnedef.setVisible(true);
                                addBattleLog(owner.getNickname()+"(이)가 다음 턴에"+ onetime_def+ "의 데미지를 무시합니다.");
                            	}
                        	else if(Skill[btn].getType().equals("Magic")) {
                                JOptionPane.showMessageDialog(
                                        GameForm.this,
                                        ""+Skill[btn].getExplan(),"효과",JOptionPane.PLAIN_MESSAGE
                                        );  
                                	if(Skill[btn].getId().equals("03")) {
                                		now_hp += 5;
                                		if(now_hp >max_hp) {
                                			now_hp = max_hp;
                                		}
                                    	Hpbar.setValue(now_hp);
                                		Hpbar.setString(now_hp+"/"+max_hp);
                                	}
                                	else if(Skill[btn].getId().equals("15")) {
                                		magic_def = 20;
                                	}
                                	else if(Skill[btn].getId().equals("35")) {
                                		magic_str = 20;
                                	}
                                	addBattleLog(owner.getNickname()+"(이)가"+ Skill[btn].getName() +" 마법을 사용합니다.");
                            	}
                    		for(int i=0;i<s_mon.length;i++) {
                        		death_num = monster_death(s_mon[i]);
                        		death_mon = String.valueOf(death_num+1);
                        		if(Monster[death_num].getHp() == 0) {
                        			lblattack[death_num].setVisible(false);
                            		lblMonster[death_num].setVisible(false);
                            		s_mon = Arrays.stream(s_mon)
                            				.filter(item -> !item.equals(death_mon))
                            				.toArray(String[]::new);
                            		
                            		i--;
                            		users.exp_up(owner.getId(), Monster[death_num].getExp());
                            		addBattleLog(ExpLog(monster_name(s_num), Monster[death_num].getExp()));
                        			}
                    		}
                    		if(isStage_clear()) {
                            	//setVisible(false);
                    			new ClearPopup(owner);
                    			dispose();
                            	new StageClearForm(owner, s_num, bf_exp);
                    			}
                		}
                		now_mp -= Skill[btn].getUmana();
                    	Mpbar.setValue(now_mp);
                		Mpbar.setString(now_mp+"/"+max_mp);
                		btnSkill[btn].setVisible(false);
                		btnSkillex[btn].setVisible(false);
                	}
                	else {
                		JOptionPane.showMessageDialog(
                                GameForm.this,
                                "마나가 부족합니다.","마나 부족",JOptionPane.PLAIN_MESSAGE
                                );  
                	}
                	
                }
                	
    	});
        	btnSkillex[btn].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(
                            GameForm.this,
                            " "+ Skill[btn].getName() + "\n" +
                            "마나소모 :" + Skill[btn].getUmana()+ "\n" +
                            "설명 :" + Skill[btn].getExplan() + "\n",
                            "스킬 정보",JOptionPane.PLAIN_MESSAGE
                            );             
                }
        	});
        }
    }
	private int monster_death(String n) {
    	return Integer.valueOf(n)-1;
    }
    private void addBattleLog(String log) {
    	BattleLog.append(log + "\n");  // 로그 내용을 JTextArea 위에 붙여주고
    	BattleLog.setCaretPosition(BattleLog.getDocument().getLength());  // 맨아래로 스크롤한다.
    }
    private String monster_name(int stage_num) {
    	switch(stage_num) {
    		case 1: return "슬라임";
    		case 2: return "뱀";
    		case 3: return "고블린";
    		case 4: return "고스트";
    		case 5: return "독슬라임";
    		case 6: return "식충식물";
    		case 7: return "스켈레톤";
    		case 8: return "골렘";
    		case 9: return "아이스 골렘";
    		case 10: return "드래곤";
    		default: return "";
    	}
    }
    private String StrLog(String attack, String defense, int damage) {
    	return attack+"(이)가 "+defense+"에게 "+ damage +"의 피해를 줬습니다.";
    }
    private String ExpLog(String monster, int exp) {
    	return monster+"(이)가 죽었습니다. "+ exp +"의 경험치 획득!";
    }
    private boolean isStage_clear() {
    	if(s_mon.length == 0) {
    		return true;
    	}
    	return false;
    }
    private void showFrame() {
        setTitle("텍스트 기반 로그라이크");//타이틀
        setSize(800,600);//프레임의 크기
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
    /*메인함수
	public static void main(String[] args){
    	GameForm frame = new GameForm(1);
        frame.setVisible(true);
	}*/
}
