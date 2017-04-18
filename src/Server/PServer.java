package Server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import Tool.CreateAES;

public class PServer {
	
	static PreparedStatement ps=null;
    static Connection ct=null;
    static ResultSet rs=null;
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8001);
            while(true){
	        	System.out.println("服务器正在8001端口监听……");
	        	Socket s = ss.accept();
	        	MyService t=new MyService();
	        	t.setSocket(s);
	        	t.start();
	        }
         }catch (Exception e){}    
    }
}

class MyService extends Thread{
	private Socket s;
	public void setSocket(Socket s) {
		this.s=s;
	}
		
	public void run() {
		try{
			InputStream is = s.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is,"utf-8");
	        BufferedReader br = new BufferedReader(isr);
	        
	        String uandp = br.readLine();
	        System.out.println(uandp);
				
	        String u = "", p = "";

	        u = uandp.split("%")[0];
            p = uandp.split("%")[1];
	            
            OutputStream os = s.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os,"utf-8");
	        PrintWriter pw = new PrintWriter(osw , true);
		        
            Class.forName("org.gjt.mm.mysql.Driver");	
	   		Connection cn = DriverManager.getConnection(
	   			"jdbc:mysql://127.0.0.1:3306/game?useUnicode=true&characterEncoding=utf8", "root", "ryeo");
	   		PreparedStatement ps;
	   		
	   		if(uandp.split("%")[2].equals("r")) {
	   			ps = cn.prepareStatement("insert into user values(NULL,?,?,0)");
	   	        ps.setString(1, u);
	   	        ps.setString(2, p);
	   	        ps.executeUpdate();
	   	        System.out.println(u + "注册成功。");
	   		}
	   		
   			ps = cn.prepareStatement("select * from user where username=? and pwd=?");
   			ps.setString(1, u);
   			ps.setString(2, p);
   			ResultSet rs = ps.executeQuery();
	   		if(rs.next()){
	   			String id = rs.getString(1);
	   			pw.println("ok");
	   			String sql = "select * from person where uid=" + id;
	   			String jilu = selectmessage(sql,cn);
	   			pw.println(jilu);
	   			/*
	   			Vector re = new Vector();
	   	    	for(int k = 0; k < jilu.size()/9; k++){
	   	    		Vector temp = new Vector();
	   	    		for(int j = 0; j < 9; j++){
	   	    			temp.add(jilu.get(j+jilu.size()/9*k));
	   	    		}
	   	    		re.add(temp);
	   	    	}
	   	    	System.out.println(re);
	   	    	*/
	   			System.out.println(u + "登录成功。");
	   			while(true){
		    		String opretion = br.readLine();
		    		System.out.println(opretion);
		    		if(opretion.split("%")[0].equals("select")) {
		    			//System.out.println(sql);
		    			if(opretion.split("%")[1].equals("name")) 
		    				sql = "select * from person where name='" + opretion.split("%")[2] +"'and uid=" + id;
		    			else if(opretion.split("%")[1].equals("age")) 
		    				sql = "select * from person where age=" + opretion.split("%")[2] + " and uid=" + id;
		    			else if(opretion.split("%")[1].equals("sex")) 
		    				sql = "select * from person where gender='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("dbo")) 
		    				sql = "select * from person where dbo='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("phone")) 
		    				sql = "select * from person where phone='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("qq")) 
		    				sql = "select * from person where qq='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("email")) 
		    				sql = "select * from person where email='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("address")) 
		    				sql = "select * from person where address='" + opretion.split("%")[2] + "' and uid=" + id;
		    			else if(opretion.split("%")[1].equals("*")) 
		    				sql = "select * from person where uid=" + id;
		    			System.out.println("用户进行了查询操作。");
		    			jilu = selectmessage(sql,cn);
			   			pw.println(jilu);
		    		}
		    		else if(opretion.split("%")[0].equals("insert")) {
		    			sql = "insert into person (`id`, `name`, `gender`, `age`, `dbo`, `phone`, `email`, `qq`, `address`,`uid`) values(NULL,'"
		    					+ opretion.split("%")[1] + "','" + opretion.split("%")[2] + "',"
		    					+ Integer.valueOf(opretion.split("%")[3]) + ",'"
		    					+ opretion.split("%")[4] + "','" + opretion.split("%")[5] + "','"
		    					+ opretion.split("%")[6] + "','" + opretion.split("%")[7] + "','"
		    					+ opretion.split("%")[8] + "'," + id + ")";
		    			System.out.println(sql);
		    			try{
		    				PreparedStatement ps3 = cn.prepareStatement(sql);
		    				ps3.executeUpdate();
		    			}catch (SQLException e) {
		    				e.printStackTrace();
		    			}
		    			System.out.println("用户进行了增加操作。");
		    			sql = "select * from person where uid="+id;
		    			jilu = selectmessage(sql,cn);
			   			pw.println(jilu);
		    		}
		    		else if(opretion.split("%")[0].equals("update")) {
		    			sql = "update person set name='" + opretion.split("%")[1]
		    					+ "',gender='" + opretion.split("%")[2]
		    					+ "',age=" + Integer.valueOf(opretion.split("%")[3])
		    					+ ",dbo='" + opretion.split("%")[4]
		    					+ "',phone='" + opretion.split("%")[5]
		    					+ "',email='" + opretion.split("%")[6]
		    					+ "',qq='" + opretion.split("%")[7]
		    					+ "',address='" + opretion.split("%")[8]
		    					+ "' where id=" + Integer.valueOf(opretion.split("%")[9])
		    					+ " and uid=" + id;
		    			System.out.println(sql);
		    			try{
		    				PreparedStatement ps3 = cn.prepareStatement(sql);
		    				ps3.executeUpdate();
		    			}catch (SQLException e) {
		    				e.printStackTrace();
		    			}
		    			System.out.println("用户进行了更新操作。");
		    			sql = "select * from person where uid="+id;
		    			jilu = selectmessage(sql,cn);
			   			pw.println(jilu);
		    		}
		    		else if(opretion.split("%")[0].equals("delete")) {
		    			sql = "delete from person where id="+Integer.valueOf(opretion.split("%")[1]);
		    			try{
		    				PreparedStatement ps3 = cn.prepareStatement(sql);
		    				ps3.executeUpdate();
		    			}catch (SQLException e) {
		    				e.printStackTrace();
		    			}
		    			System.out.println("用户进行了删除操作。");
		    			sql = "select * from person where uid=" + id;
		    			jilu = selectmessage(sql,cn);
			   			pw.println(jilu);
		    		}
		    		else if(opretion.equals("save")) {
		    			savemessage(cn,"select * from person where uid="+rs.getString(1),u,p);
		    			System.out.println("用户进行了备份操作。通讯录已备份到本地，文件名为:"+u+".xls");
		    			pw.println("ok");
		    		}
		    		else if(opretion.equals("cancel")) {
		    			System.out.println("用户撤销了操作。");
		    			sql = "select * from person where uid="+id;
		    			jilu = selectmessage(sql,cn);
			   			pw.println(jilu);
		    		}
	   			}
	    	}
	    	else{
	    		pw.println("error");
	    	}
	   		
	    }catch(Exception ee){}
	}
	
	private String selectmessage(String sql, Connection cn) {
		ArrayList<String> jilu = new ArrayList<String>();  
		try {
			PreparedStatement ps1 = cn.prepareStatement(sql);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){           
				jilu.add(rs1.getString(1));
				jilu.add(rs1.getString(2));
				jilu.add(rs1.getString(3));
				jilu.add(rs1.getString(4));
				jilu.add(rs1.getString(5));
				jilu.add(rs1.getString(6));
				jilu.add(rs1.getString(7));
				jilu.add(rs1.getString(8));
				jilu.add(rs1.getString(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(jilu);
		return jilu.toString();
	}

	private void savemessage(Connection ct,String sql,String name,String pass){
		Vector jilu = new Vector();
		try{
    		PreparedStatement ps2=ct.prepareStatement(sql);
    		ResultSet rss=ps2.executeQuery();
    		
    		OutputStream fos = new FileOutputStream("E:\\"+name+".xls");
    	
			WritableWorkbook wwb = Workbook.createWorkbook(fos);
			//创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet(name,0);
            while(rss.next()){
                Vector<String> hang= new Vector<String>();
                hang.add(rss.getString(1));               
                hang.add(CreateAES.aesDecrypt(rss.getString(2), pass));
                hang.add(rss.getString(3));
                hang.add(rss.getString(4));
                hang.add(rss.getString(5));
                hang.add(CreateAES.aesDecrypt(rss.getString(6), pass));
                hang.add(CreateAES.aesDecrypt(rss.getString(7), pass));
                hang.add(CreateAES.aesDecrypt(rss.getString(8), pass));
                hang.add(CreateAES.aesDecrypt(rss.getString(9), pass));
                jilu.add(hang);  
                	
            }
        	
            for(int i = 0; i < jilu.size(); i++){
    			Vector vec = (Vector)jilu.elementAt(i);
    			for(int j=0;j<9;j++){
    				String element = (String)vec.get(j);
    				Label label = new Label(j,i,element);
    				ws.addCell(label);
    			}
    		}
    		//写入工作表
    		wwb.write();
    		wwb.close();
    		fos.close();
        }catch(SQLException e){
            System.out.println("Error "+e);
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	 
}
