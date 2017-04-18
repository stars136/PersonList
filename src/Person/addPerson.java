package Person;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

import Tool.CreateAES;

public class addPerson extends JDialog implements ActionListener {
    JPanel p1, p2;
    JLabel name, gender, age, dbo, phone, email, qq, adress;
    JTextField t_name, t_age, t_dbo, t_phone, t_email, t_qq, t_adress;
    JButton sure, cancel;
    JComboBox t_gender;
    JLabel l_dbo, l_email;
    
    static String AESkey = "000000";
    private Socket sss;
    
    public addPerson(Frame s, String c, Boolean m, String key, Socket ss) {
        super(s, c, m);
      
        AESkey = key;
        sss = ss;
      
        name = new JLabel("姓名：");
        name.setBounds(40, 50, 60, 40);
        gender = new JLabel("性别：");
        gender.setBounds(40, 100, 60, 40);
        age = new JLabel("年龄：");
        age.setBounds(40, 150, 60, 40);
        dbo = new JLabel("生日：");
        dbo.setBounds(40, 200, 60, 40);
        phone = new JLabel("手机：");
        phone.setBounds(40, 250, 60, 40);
        email = new JLabel("Email：");
        email.setBounds(40, 300, 60, 40);
        qq = new JLabel("QQ：");
        qq.setBounds(40, 350, 60, 40);
        adress = new JLabel("地址：");
        adress.setBounds(40, 400, 60, 40);
        
        t_name = new JTextField();
        t_name.setBounds(100, 55, 100, 25);
        String[] Gender = {"男","女"};
        t_gender = new JComboBox(Gender);
        t_gender.setBounds(100, 105, 100, 25);
        t_age = new JTextField();
        t_age.setBounds(100, 155, 100, 25);
        t_dbo = new JTextField();
        t_dbo.setBounds(100, 205, 100, 25);
        l_dbo = new JLabel("样例：1995-09-25");
        l_dbo.setBounds(100, 228, 125, 25);
        t_phone = new JTextField();
        t_phone.setBounds(100, 255, 100, 25);
        t_email = new JTextField();
        t_email.setBounds(100, 305, 200, 25);
        l_email = new JLabel("样例:1356@qq.com");
        l_email.setBounds(100, 328, 250, 25);
        t_qq = new JTextField();
        t_qq.setBounds(100, 355, 100, 25);
        t_adress = new JTextField();
        t_adress.setBounds(100, 405, 200, 25);
   
        sure = new JButton("确定");
        sure.addActionListener(this);
        sure.setBounds(105, 450, 60, 30);
        cancel = new JButton("取消");
        cancel.setBounds(205, 450, 60, 30);
        cancel.addActionListener(this);
      
        this.add(name);
        this.add(gender);
        this.add(age);
        this.add(dbo);
        this.add(phone);
        this.add(email);
        this.add(qq);
        this.add(adress);
        
        this.add(t_name);
        this.add(t_gender);
        this.add(t_age);
        this.add(t_dbo);
        this.add(t_phone);
        this.add(t_email);
        this.add(t_qq);
        this.add(t_adress);
      
      
        this.add(l_dbo);
        this.add(l_email);
        this.add(sure);
        this.add(cancel);
      
        this.setTitle(c);
        this.setSize(370, 580);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String button=e.getActionCommand();
        try {
  			OutputStream os = sss.getOutputStream();
  	        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
  	        PrintWriter pw = new PrintWriter(osw, true);
            if(button.equals("取消")) {
                this.dispose();
                pw.println("cancel");
            }
            else if(button.equals("确定")) {    
       	        if(t_name.getText() != null && t_phone.getText() != null) {
       	        	pw.println("insert%" + CreateAES.aesEncrypt(t_name.getText(), AESkey) + "%"
       	        		+ t_gender.getSelectedItem().toString() + "%"
       	        		+ t_age.getText() + "%" + t_dbo.getText() + "%"
       	        		+ CreateAES.aesEncrypt(t_phone.getText(), AESkey) + "%"
       	        		+ CreateAES.aesEncrypt(t_email.getText(), AESkey) + "%"
       	        		+ CreateAES.aesEncrypt(t_qq.getText(), AESkey) + "%"
       	        		+ CreateAES.aesEncrypt(t_adress.getText(), AESkey));
       	        	this.dispose();
       	        }
       	        else JOptionPane.showMessageDialog(this,"信息输入的格式有误！");
        	 }
        }catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
        	} catch (Exception e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
        	}
    }
        	/*  PreparedStatement ps=null;
        	  Connection ct=null;
        	  ResultSet rs=null;
        	  try{      	
        		 Class.forName("org.gjt.mm.mysql.Driver");	
        		  ct = DriverManager.getConnection(
        			  "jdbc:mysql://127.0.0.1:3306/game?useUnicode=true&characterEncoding=UTF-8", "root", "ryeo");
        		  ps = ct.prepareStatement(
        			  "insert into person (`id`, `name`, `gender`, `age`, `dbo`, `phone`, `email`, `qq`, `address`,`uid`) values(NULL,?,?,?,?,?,?,?,?,?)");
		          ps.setString(1,CreateAES.aesEncryptToBytes(t_name.getText(), AESkey));
		          ps.setString(2,t_gender.getSelectedItem().toString());
		          ps.setString(3,t_age.getText());
		          ps.setString(4,t_dbo.getText());
		          ps.setString(5,CreateAES.aesEncryptToBytes(t_phone.getText(), AESkey));
		          ps.setString(6,CreateAES.aesEncryptToBytes(t_email.getText(), AESkey));
		          ps.setString(7,CreateAES.aesEncryptToBytes(t_qq.getText(), AESkey));
		          ps.setString(8,CreateAES.aesEncryptToBytes(t_adress.getText(), AESkey));
		          //ps.setString(9,String.valueOf(uid));
		          System.out.println(ps);
		          ps.executeUpdate();
		          System.out.println("已成功添加新联系人。");
		          this.dispose();
	           
        	  }catch(ClassNotFoundException  e1){
        		  System.out.println("ERROR:"+e1);
        	  }catch(SQLException e2Exception){
        		  JOptionPane.showMessageDialog(this,"信息输入的格式有误！");
        	  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
        		  }
              catch(SQLException e2){}
        	  }
          }*/ 
      
}
