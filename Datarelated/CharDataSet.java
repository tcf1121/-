package Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class CharDataSet {
    private Connection conn;
    private Ability CAbility = new Ability("" ,0, 0, 0, 0);
	public CharDataSet() {
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
	public Ability ClassAbility(String id) {
    	try {
    		String sql = "select strength, defense, health, mana from character where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			CAbility.setStr(rs.getInt("strength"));
    			CAbility.setDef(rs.getInt("defense"));
    			CAbility.setHp(rs.getInt("health"));
    			CAbility.setMp(rs.getInt("mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return CAbility;
	}
	// 저장데이터 확인
    public boolean isSaveAP(String id) {
    	String sql = "select u_id from APsave";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			if(rs.getString("u_id").equals(id)) {
    				pstmt.close();
    				rs.close();
    				return true;
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    public void setNAbility(String class_id, String id) {
    	Ability NAbility = new Ability("" ,0, 0, 0, 0);
		String class_name = null;
		if(class_id.equals("01")) {
			class_name = "전사";
		}
		else if(class_id.equals("02")) {
			class_name = "마법사";
		}
		else if(class_id.equals("03")) {
			class_name = "도적";
		}
		Ability classAbility = this.ClassAbility(class_id);
		Ability LoadAbility = new Ability("" ,0, 0, 0, 0);
		if(this.isSaveAP(id)) {
			LoadAbility = this.LoadAP(id);
	    	}
		
		NAbility.setcl(class_name);
		NAbility.setStr(classAbility.getStr()+LoadAbility.getStr());
		NAbility.setDef(classAbility.getDef()+LoadAbility.getDef());
		NAbility.setHp(classAbility.getHp()+LoadAbility.getHp());
		NAbility.setMp(classAbility.getMp()+LoadAbility.getMp());
		
		try {
			String sql = ""+
					"insert into nowability(class, strength, defense, health, mana)" +
					"values (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, NAbility.getcl());
	    	pstmt.setInt(2, NAbility.getStr());
	    	pstmt.setInt(3, NAbility.getDef());
	    	pstmt.setInt(4, NAbility.getHp());
	    	pstmt.setInt(5, NAbility.getMp());
	    	pstmt.executeUpdate();
	    	pstmt.close();
		}catch(Exception e) {
    		e.printStackTrace();
		}
    }
    public Ability getNAbility() {
    	Ability NAbility = new Ability("" ,0, 0, 0, 0);
		try {
    		String sql = "select class, strength, defense, health, mana from nowability";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			NAbility.setcl(rs.getString("class"));
    			NAbility.setStr(rs.getInt("strength"));
    			NAbility.setDef(rs.getInt("defense"));
    			NAbility.setHp(rs.getInt("health"));
    			NAbility.setMp(rs.getInt("mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return NAbility;
    }
	public void deleteNAbility() {
		try {
    		String sql = "delete from nowability";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	//AP 정보 저장
	public void SaveAP(String id, Ability save) {
		if(this.isSaveAP(id)) {
			try {
				String sql = ""+
						"delete from APsave where u_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
		    	pstmt.executeUpdate();
		    	pstmt.close();
			}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
		try {
			String sql = ""+
					"insert into APsave(u_id, strength, defense, health, mana)" +
					"values (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
	    	pstmt.setInt(2, save.getStr());
	    	pstmt.setInt(3, save.getDef());
	    	pstmt.setInt(4, save.getHp());
	    	pstmt.setInt(5, save.getMp());
	    	pstmt.executeUpdate();
	    	pstmt.close();
		}catch(Exception e) {
    		e.printStackTrace();
    	}
		
	}
	//AP 정보 불러오기
	public Ability LoadAP(String id) {
		Ability LAbility = new Ability("" ,0, 0, 0, 0);
		try {
    		String sql = "select strength, defense, health, mana from APsave where u_id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();    		
    		while(rs.next()) {
    			LAbility.setStr(rs.getInt("strength"));
    			LAbility.setDef(rs.getInt("defense"));
    			LAbility.setHp(rs.getInt("health"));
    			LAbility.setMp(rs.getInt("mana"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return LAbility;
	}
}

