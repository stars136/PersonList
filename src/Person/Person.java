package Person;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.sql.*;

import Tool.CreateAES;

public class Person extends JFrame implements ActionListener {

    JPanel Panel, Panel2;
    JButton View, Add, Delete, Update, select, show,Backup;
    JComboBox sort, way;
    JTextField message;
    JScrollPane gdt;
    JLabel tip;
    JTable bg1;
    selectmessage sel;
    Vector ziduan, jilu;
    PreparedStatement ps = null;
    Connection ct = null;
    ResultSet rs = null;
    static String AESkey = "000000";
    private Socket sss;
    private int row;

    public Person(String key,Socket ss) {
    	sss=ss;
    	AESkey = key;
    	//System.out.println(AESkey);
    	//添加查询面版
        Panel2 = new JPanel();
        String[] choice1 = {"查询方式", "姓名", "性别", "年龄", "生日","手机", "Email","QQ","地址"};

        Panel2.setOpaque(false);//透明   

        way = new JComboBox(choice1); 
        way.setBackground(new Color(168, 211, 255));
        way.addActionListener(this);
        message = new JTextField(15);
        select = new JButton("查询");
        select.setBackground(new Color(168, 211, 255)); 
        show = new JButton("显示全部");
        show.setBackground(new Color(168, 211, 255)); 
        show.addActionListener(this);
        select.addActionListener(this);
        
        Panel2.add(way);
        Panel2.add(message);
        Panel2.add(select);
        Panel2.add(show);

        sel = new selectmessage(AESkey, ss);
        row = sel.getRowCount();
        bg1 = new JTable(sel);
        
        setcolimnwiddth(bg1);
        
        gdt = new JScrollPane(bg1);
        gdt.setOpaque(false);

        Panel = new JPanel();
        
        Panel.setOpaque(false);
        
        View = new JButton("查看详情");
        View.setBackground(new Color(168, 211, 255)); 
        View.addActionListener(this);
        Add = new JButton("添加");
        Add.addActionListener(this);
        Add.setBackground(new Color(168, 211, 255)); 
        Delete = new JButton("删除");
        Delete.addActionListener(this);
        Delete.setBackground(new Color(168, 211, 255)); 
        Update = new JButton("更新");
        Update.addActionListener(this);
        Update.setBackground(new Color(168, 211, 255)); 
        Backup = new JButton("备份");
        Backup.addActionListener(this);
        Backup.setBackground(new Color(168, 211, 255)); 

        Panel.add(View);
        Panel.add(Add);
        Panel.add(Delete);
        Panel.add(Update);
        Panel.add(Backup);
        
        //加载背景图片 
        ImageIcon bg = new ImageIcon("img.jpg"); // 把背景图片显示在一个标签里 
        JLabel label = new JLabel(bg); //把标签的大小位置设置为图片刚好填充整个面
        label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight()); //添加图片到frame的第二层 
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE)); //获取frame的最上层面板为了设置其背景颜色（JPanel有设置透明的方法） 
        JPanel jp=(JPanel)this.getContentPane(); 
        jp.setOpaque(false);//设置透明 


        this.add(gdt);
        this.add(Panel, BorderLayout.SOUTH);
        this.add(Panel2, BorderLayout.NORTH);

        this.setTitle("个人通讯录管理");
        this.setSize(675, 650);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);

    }

    /**
     * 调整列宽
     * @param bg1
     */
    private void setcolimnwiddth(JTable bg1) {
    	bg1.getColumnModel().getColumn(0).setPreferredWidth(20);
        bg1.getColumnModel().getColumn(1).setPreferredWidth(50);
        bg1.getColumnModel().getColumn(2).setPreferredWidth(35);
        bg1.getColumnModel().getColumn(3).setPreferredWidth(35);
        bg1.getColumnModel().getColumn(4).setPreferredWidth(71);
        bg1.getColumnModel().getColumn(5).setPreferredWidth(90);
        bg1.getColumnModel().getColumn(6).setPreferredWidth(130);
        bg1.getColumnModel().getColumn(7).setPreferredWidth(75);
        bg1.getColumnModel().getColumn(8).setPreferredWidth(160);
        bg1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        try {
			OutputStream os = sss.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
	        PrintWriter pw = new PrintWriter(osw, true);
	        String selway = (String) way.getSelectedItem();
 
	        if (s.equals("查询")) {
	            String choices = message.getText().trim();
	            String sql;
	            try{
	            	if (selway.equals("姓名")) {
	            		//sql = "select * from person where name='" + CreateAES.aesEncryptToBytes(choices, AESkey) + "'and uid="+uid;
	            		sql = "select%name%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("性别")) {
	            		//sql = "select * from person where gender ='" + choices + "'and uid="+uid;
	            		//selectmessage sq = new selectmessage(sql,AESkey);
	            		sql = "select%sex%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("年龄")) {
	            		//sql = "select * from person where age=" + choices + " and uid="+uid;
	            		sql = "select%age%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("生日")) {
	            		//sql = "select * from person where dbo=" + choices + " and uid="+uid;
	            		sql = "select%dbo%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("手机")) {
	            		//sql = "select * from person where phone='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%phone%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("Email")) {
	            		//sql = "select * from person where email='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%email%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("QQ")) {
	            		//sql = "select * from person where qq='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%qq%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("地址")) {
	            		//sql = "select * from person where address='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%address%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else {
	            		JOptionPane.showMessageDialog(this, "请选择查找方式！");
	            		return;
	            	}
	            }catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }    
	        else if (s.equals("查看详情")) {
	            int iii = this.bg1.getSelectedRow();
	            System.out.println("查看第" + iii + "行的联系人。");
	            if (iii == -1) {
	                JOptionPane.showMessageDialog(this, "请选中要查看的行！");
	                return;
	            }
	            new ViewPerson(this, "查看人员信息", true, sel, iii);
	        }    
	        else if (s.equals("添加")) {
	            new addPerson(this, "添加人员信息",true, AESkey, sss);
	            sel = new selectmessage(AESkey, sss);
	            
	            if(sel.getRowCount() == row){
	            	JOptionPane.showMessageDialog(this, "未成功添加！");
	            }
	            else row++;
	                
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("显示全部")) {
            	pw.println("select%*");
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("更新")) {
	            int iii = this.bg1.getSelectedRow();
	            System.out.println("更新第" + iii + "行的联系人。");
	            if (iii == -1) {
	                JOptionPane.showMessageDialog(this, "请选中要更新的行！");
	                return;
	            }
	            new Update(this, "修改人员信息", true, sel, iii, AESkey, sss);
	            sel = new selectmessage(AESkey, sss);
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("删除")) {
	            int ii = this.bg1.getSelectedRow();
	            if (ii == -1) {
	                JOptionPane.showMessageDialog(this, "请选中删除的行！");
	                return;
	            }
	            String st = (String) sel.getValueAt(ii, 0);
	            System.out.println("删除第为" + st + "行的联系人。");
	            int n = JOptionPane.showConfirmDialog(this, "确认删除吗?", "确认", JOptionPane.YES_NO_OPTION);
	            if (n == JOptionPane.YES_OPTION) {
	            	pw.println("delete%"+st);
		            
	            	sel = new selectmessage(AESkey, sss);
		            if(sel.getRowCount() == row){
		            	JOptionPane.showMessageDialog(this,"发生未知错误！");
		            }
		            else row--;
		            
		            bg1.setModel(sel);
		            setcolimnwiddth(bg1);
	            } else if (n == JOptionPane.NO_OPTION){} 
	        }    
	        else if (s.equals("备份")) {
	        	pw.println("save");
	        	InputStream is =sss.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
	        	String result = br.readLine();
	        	if(result.equals("ok")) JOptionPane.showMessageDialog(this, "已成功备份到服务器。");
	        }
		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }
}
