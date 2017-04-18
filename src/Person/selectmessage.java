package Person;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Tool.CreateAES;

final class selectmessage extends AbstractTableModel{
    Vector ziduan, jilu;
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;

    /**
     * 将字符串以“, ”为分隔符转换为字符串数组
     * @param source
     * @return 字符数组
     */
 	public static String[] splitStringByComma(String source) {
		if(source == null || source.trim().equals("")) return null;
		StringTokenizer commaToker=new StringTokenizer(source, ", ");
		String[] result = new String[commaToker.countTokens()];
		int i=0;
		while(commaToker.hasMoreTokens()){
			result[i] = commaToker.nextToken();
			i++;
		}
		return result;
	} 
 	 
    public selectmessage(String key, Socket s) {        
        ziduan = new Vector();
        ziduan.add("ID");
        ziduan.add("姓名");  
        ziduan.add("性别");
        ziduan.add("年龄"); 
        ziduan.add("生日");
        ziduan.add("手机");
        ziduan.add("Email");
        ziduan.add("QQ");
        ziduan.add("地址");
        
        jilu=new Vector();
		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
		    BufferedReader br = new BufferedReader(isr);		     
	     	String result = br.readLine();
	     	result = result.substring(1,result.length()-1);
	     	 
	     	if(!result.equals("")) {
	     		String[] tp = splitStringByComma(result);
	     		for(int i = 0; i < tp.length/9; i++) {
	     			Vector<String> temp = new Vector<String>();
	     			for(int j = 0; j < 9; j++){
	     				if(j == 0 || j == 2 || j == 3 || j == 4) temp.add(tp[j+9*i]);
	     				else temp.add(CreateAES.aesDecrypt(tp[j+9*i], key));
	     			}
	     			jilu.add(temp);//System.out.println(jilu);
	     		}
	     	}else ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
     	 
        /*
         * 从文件读取      
        int i;
        Sheet sheet;
        Workbook book;
        Cell cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9;
        try { 
            //t.xls为要读取的excel文件名
            book= Workbook.getWorkbook(new File("E:\\test.xls"));         
            sheet=book.getSheet("test"); 
            
            i=0;
            while(true)
            {
                //获取每一行的单元格 
                cell1=sheet.getCell(0,i);//（列，行）
                cell2=sheet.getCell(1,i);
                cell3=sheet.getCell(2,i);
                cell4=sheet.getCell(3,i);
                cell5=sheet.getCell(4,i);
                cell6=sheet.getCell(5,i);
                cell7=sheet.getCell(6,i);
                cell8=sheet.getCell(7,i);
                cell9=sheet.getCell(8,i);
                
                if("".equals(cell1.getContents())==true) break;   //如果读取的数据为空
                Vector hang= new Vector();
                hang.add(cell1.getContents());
                hang.add(cell2.getContents());
                hang.add(cell3.getContents());
                hang.add(cell4.getContents());
                hang.add(cell5.getContents());
                hang.add(cell6.getContents());
                hang.add(cell7.getContents());
                hang.add(cell8.getContents());
                hang.add(cell9.getContents());
                jilu.add(hang);   
                i++;
            }
            book.close(); 
        }
        catch(Exception e)  { } 
        */
        /*
         * 直接从数据库读取
        try{
        	Class.forName("org.gjt.mm.mysql.Driver");	
            ct = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/game?useUnicode=true&characterEncoding=utf8", "root", "ryeo");
            ps = ct.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Vector<String> hang= new Vector<String>();             
                hang.add(rs.getString(1));
                hang.add(CreateAES.aesDecrypt(rs.getString(2), key));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                hang.add(rs.getString(5));
                hang.add(CreateAES.aesDecrypt(rs.getString(6), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(7), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(8), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(9), key));
                jilu.add(hang);
                //System.out.println(hang);                   
            }         
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error "+e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(ct!=null){
                    ct.close();
                }
            }catch(SQLException e){}
        }
        */
    }


     @Override
    public int getRowCount() {//对行进行计数，横着的有几个，一旦数目多余存在的，就无法查出来
        return this.jilu.size();
    }

    @Override
    public int getColumnCount() {//对列进行计数，返回的值是什么就说明有几列,竖着的数目有几个
    	return this.ziduan.size();
    }

    @Override
    public Object getValueAt(int hang, int lie) {//获得某行某列里面的值
        return ((Vector)this.jilu.get(hang)).get(lie);
    }
     
     @Override
    public String getColumnName(int e){//得到最上面的一行的表头
    	 return (String)this.ziduan.get(e);
    }


}
