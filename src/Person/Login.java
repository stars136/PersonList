package Person;

import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import Tool.CreateMD5;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * 运行
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建
	 */
	public Login() {
		initialize();
	}

	/**
	 * 初始化框架内容
	 */
	private void initialize() {
		// 获取屏幕的宽度、高度，以居中窗口
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		int screenWidth = screen.width;
		int screenHeight = screen.height;
		int frameWidth = 518;
		int frameHeight = 345;

		frame = new JFrame();
		frame.setTitle("个人通讯录管理");
		frame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(518, 345);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		// 加载背景图片 
        ImageIcon bg = new ImageIcon("iomg.jpg"); // 把背景图片显示在一个标签里 
        JLabel label = new JLabel(bg); //把标签的大小位置设置为图片刚好填充整个面
        label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight()); //添加图片到frame的第二层 
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE)); //获取frame的最上层面板为了设置其背景颜色（JPanel有设置透明的方法） 
        JPanel jp=(JPanel)frame.getContentPane(); 
        jp.setOpaque(false);
        /*
		JPanel titelPanel = new JPanel();
		titelPanel.setBounds(0, 0, 512, 178);
		frame.getContentPane().add(titelPanel);
		titelPanel.setBackground(new Color(68, 119, 136));
		titelPanel.setLayout(null);

		//JLabel label = new JLabel("个 人 通 讯 录 管 理");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 38));
		label.setBounds((512 - 332) / 2, (178 - 42) / 2, 332, 42);
		label.setForeground(Color.WHITE);
		titelPanel.add(label);
*/
		JPanel loginPanel = new JPanel();
		loginPanel.setBounds(0, 177, 512, 139);
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(178, 32, 150, 21);
		loginPanel.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(178, 63, 150, 21);
		loginPanel.add(passwordField);

		JLabel usernameLabel = new JLabel("用户名：");
		usernameLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 14));
		usernameLabel.setBounds(110, 34, 60, 15);
		loginPanel.add(usernameLabel);

		JLabel passwordLabel = new JLabel("密    码：");
		passwordLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 14));
		passwordLabel.setBounds(110, 66, 60, 15);
		loginPanel.add(passwordLabel);

		JButton submitButton = new JButton("登陆");
		submitButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		submitButton.addActionListener(new LoginController());
		submitButton.setBounds(345, 31, 70, 52);
		submitButton.setContentAreaFilled(false);
		submitButton.setForeground(Color.BLACK);
		loginPanel.add(submitButton);

		JPanel submitButtonBackground = new JPanel();
		submitButtonBackground.setBounds(345, 31, 70, 52);
		submitButtonBackground.setBackground(new Color(130, 192, 255));
		loginPanel.add(submitButtonBackground);
		
		JLabel regBtn = new JLabel("注册");
		regBtn.setFont(new Font("宋体", Font.PLAIN, 12));
		regBtn.setBounds(460, 114, 34, 15);
		regBtn.addMouseListener(new RegBtnController());
		loginPanel.add(regBtn);
		
	}
	
	/**
	 * 判断字符串是否为数字 
	 * @param str字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length();--i>=0;) {  
			 if (!Character.isDigit(str.charAt(i))) {
			    return false;
			 }
		}
		return true;
	}
	
	//处理登陆事件
	class LoginController implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			String user = usernameField.getText();
	        String pass = CreateMD5.getMd5(passwordField.getText(), 16);
			try {
				Socket s = new Socket("127.0.0.1",8001);
				OutputStream os = s.getOutputStream();
		        OutputStreamWriter osw = new OutputStreamWriter(os);
		        PrintWriter pw = new PrintWriter(osw , true);
							
		        pw.println(user+"%"+pass+"%l");
		        
		        InputStream is = s.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
		        
		        String flag = br.readLine();
		        //System.out.println("用户id为："+flag);isNumeric(flag)
		        if(flag.equals("ok")) { 
		        	new Person(pass, s);
		        	frame.setVisible(false);
		        }
		        else JOptionPane.showMessageDialog(frame, "用户名或密码错误，请重试。");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class RegBtnController implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			new Register(frame);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
