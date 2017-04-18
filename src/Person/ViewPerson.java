package Person;

import javax.swing.*;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPerson extends JDialog implements ActionListener {

    JPanel p1, p2;
    JLabel name, gender, age, dbo, phone, email, qq, adress;
    JTextField t_name, t_gender, t_age, t_dbo, t_phone, t_email, t_qq, t_adress;
    JButton sure, cancel;
    String id;
    JLabel l_dbo, l_email;

    public ViewPerson(Frame s, String c, Boolean m, selectmessage sel, int hang) {
        super(s, c, m);

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

      	id = (String)sel.getValueAt(hang,0);
       
        t_name = new JTextField();
        t_name.setBounds(100, 55, 100, 25);
        String st = (String) sel.getValueAt(hang, 1);
        t_name.setText(st);
        t_name.setEditable(false);

        t_gender = new JTextField();
        t_gender.setBounds(100, 105, 100, 25);
        String st1 = (String) sel.getValueAt(hang, 2);
        t_gender.setText(st1);
        t_gender.setEditable(false);

        t_age = new JTextField();
        t_age.setBounds(100, 155, 100, 25);
        String st2 = (String) sel.getValueAt(hang, 3).toString();
        t_age.setText(st2);
        t_age.setEditable(false);

        t_dbo = new JTextField();
        t_dbo.setBounds(100, 205, 100, 25);
        String st3 = (String) sel.getValueAt(hang, 4).toString();
        t_dbo.setText(st3);
        t_dbo.setEditable(false);
      
        t_phone = new JTextField();
        t_phone.setBounds(100, 255, 100, 25);
        String st4 = (String) sel.getValueAt(hang, 5).toString();
        t_phone.setText(st4);
        t_phone.setEditable(false);

        t_email = new JTextField();
        t_email.setBounds(100, 305, 200, 25);
        String st5 = (String) sel.getValueAt(hang, 6).toString();
        t_email.setText(st5);
        t_email.setEditable(false);
        
        t_qq = new JTextField();
        t_qq.setBounds(100, 355, 100, 25);
        String st6 = (String) sel.getValueAt(hang, 7).toString();
        t_qq.setText(st6);
        t_qq.setEditable(false);
        
        t_adress = new JTextField();
        t_adress.setBounds(100, 405, 200, 25);
        String st7 = (String) sel.getValueAt(hang, 8).toString();
        t_adress.setText(st7);  
        t_adress.setEditable(false);

        cancel=new JButton("取消");
        cancel.setBounds(155, 450, 60, 30);
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
      	this.add(cancel);

        this.setTitle(c);
        this.setSize(370, 580);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
	    if (button.equals("取消")) {
	        this.dispose();
	    }
    }
        
}
