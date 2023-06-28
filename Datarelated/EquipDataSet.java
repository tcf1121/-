package Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EquipDataSet {
	private Connection conn;
	
	public EquipDataSet() {
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
	
	public void Equipped(String equip_num) {//장비 장착
		try {
    		String sql = "insert into Equipped(id)" +
    				"values (?)";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, equip_num);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public void Apply_Stats(int s_num, int effect) {
		String upAb = "";
		if(s_num == 2) {
			upAb = "mana";
		}else if(s_num == 4) {
			upAb = "health";
		}else if(s_num == 6) {
			upAb = "defense";
		}else if(s_num == 8) {
			upAb = "strength";
		}
		
		try {
    		String sql = "update nowability\n"
    				+ "set "+ upAb + "+= ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, effect);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	
	public void deleteEquipped() {
		try {
    		String sql = "delete from Equipped";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	public Equip[] rewardEquip(int Stage_num) {
		int i=0, Re=0;
		Equip rewardequip[] = new Equip[3];
		Re = Stage_num / 2;
		try {
	    	String sql = "select top 3 * from equipment\n"
	    			+ "where id%5=?\n"
	    			+ "order by newid()";
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, Re);
	    	ResultSet rs = pstmt.executeQuery();	    		
	    	while(rs.next()) {
	    			rewardequip[i] = new Equip("", "","","",0,"");
	    			addEquip(rewardequip[i],rs.getString("id"));
	    			i++;
	    	}
	    	rs.close();
	    	pstmt.close();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return rewardequip;
	}
	public void addEquip(Equip equip, String equip_num) {
    	try {
    		String sql = "select * from equipment where id = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, equip_num);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			equip.setId(rs.getString("id"));
    			equip.setName(rs.getString("name"));
    			equip.setExplan(rs.getString("explan"));
    			equip.setEf_ex(rs.getString("ef_ex"));
    			equip.setEffect(rs.getInt("effect"));
    			equip.setRarity(rs.getString("rarity"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
}
