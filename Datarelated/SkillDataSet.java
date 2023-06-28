package Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SkillDataSet {
	private Connection conn;
	
	public SkillDataSet() {
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
	}
	public void SGskill(String class_n) {
		if(class_n.equals("01")) {
			addUskill("11");
			addUskill("12");
		}
		else if(class_n.equals("02")) {
			addUskill("21");
			addUskill("22");
		}
		else if(class_n.equals("03")) {
			addUskill("31");
			addUskill("32");
		}
		addUskill("01");
		addUskill("02");
		addUskill("03");
	}
	
	public void addUskill(String skill_num) {
		try {
    		String sql = "insert into Usingskill(id)" +
    				"values (?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, skill_num);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void deleteUskill() {
		try {
    		String sql = "delete from Usingskill";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public Skill[] randUskill(int card_num) {
		int i=0;
		Skill randUs[] = new Skill[card_num];
    	try {
    		String sql = "select top (?) * from Usingskill order by newid()";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, card_num);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    				randUs[i] = new Skill("", "", "", "", 0, 0);
    				addSkill(randUs[i],rs.getString("id"));
    				i++;   				
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return randUs;
	}
	public Skill[] rewardSkill(String class_no) {
		int i=0;
		Skill rewardskill[] = new Skill[3];
		if(class_no.equals("01")) {//전사
			try {
	    		String sql = "select top 3 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=11 and id<=20)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill[i] = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill[i],rs.getString("id"));
	    				i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("02")) {//마법사
			try {
	    		String sql = "select top 3 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=21 and id<=30)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill[i] = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill[i],rs.getString("id"));
	    				i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else if(class_no.equals("03")) {//도적
			try {
	    		String sql = "select top 3 * from skill\n"
	    				+ "where (id>=01 and id<=10) or (id>=31 and id<=40)\n"
	    				+ "order by newid()";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();	    		
	    		while(rs.next()) {
	    				rewardskill[i] = new Skill("", "", "", "", 0, 0);
	    				addSkill(rewardskill[i],rs.getString("id"));
	    				i++;
	    		}
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		return rewardskill;
	}
	public void addSkill(Skill skill, String skill_num) {
    	try {
    		String sql = "select * from skill where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, skill_num);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			skill.setId(rs.getString("id"));
    			skill.setName(rs.getString("name"));
    			skill.setType(rs.getString("type"));
    			skill.setExplan(rs.getString("explan"));
    			skill.setEffect(rs.getInt("effect"));
    			skill.setUmana(rs.getInt("use_mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public int effectSkill(Skill skill,int Ap) {
		if(skill.getType().equals("S_attack")) {
			return skill.getEffect()+ Ap;
		}
		else if(skill.getType().equals("W_attack")) {
			return skill.getEffect()+ Ap;
		}
		else if(skill.getType().equals("Defense")) {
			return skill.getEffect()+ Ap;
		}
		else{
			return skill.getEffect();
		}
	}
}
