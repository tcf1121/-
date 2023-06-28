package Datarelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MonsterDataSet {
    private Connection conn;
    
    public MonsterDataSet() {
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

    
    public void addMon(Monster mon, int Stage_num) {
    	try {
    		String sql = "select strength, health, experience from monster where Lv = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Stage_num);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			mon.setStr(rs.getInt("strength"));
    			mon.setHp(rs.getInt("health"));
    			mon.setExp(rs.getInt("experience"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
