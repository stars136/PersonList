package Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import Tool.CreateMD5;

public class Register extends JFrame {

	private JFrame frame, framee;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField rePasswordField;


	/**
	 * 创建界面
	 */
	public Register(JFrame frame1) {
		frame = this;
		framee = frame1;

		// 窗口标题
		setTitle("注册");
		// 获取屏幕宽度，与窗口宽度进行计算，使得窗口居中
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		int screenWidth = screen.width;
		int screenHeight = screen.height;
		int frameWidth = 282;
		int frameHeight = 259;

		setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		setResizable(false);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 282, 0 };
		gridBagLayout.rowHeights = new int[] { 63, 230, 132, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel title = new JLabel("注册信息");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.insets = new Insets(0, 0, 5, 0);
		gbc_title.fill = GridBagConstraints.VERTICAL;
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		getContentPane().add(title, gbc_title);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(null);
		GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.fill = GridBagConstraints.BOTH;
		gbc_btnPanel.gridx = 0;
		gbc_btnPanel.gridy = 1;
		getContentPane().add(btnPanel, gbc_btnPanel);

		JButton saveBtn = new JButton("注册");
		saveBtn.setBounds(50, 110, 84, 23);
		saveBtn.addActionListener(new RegisterController());
		btnPanel.add(saveBtn);

		JButton reSetBtn = new JButton("重置");
		reSetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		reSetBtn.setBounds(147, 110, 81, 23);
		// reSetBtn.addActionListener(new ReSetController());
		btnPanel.add(reSetBtn);

		JLabel label = new JLabel("密  码：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setBounds(51, 42, 60, 15);
		btnPanel.add(label);

		JLabel label_1 = new JLabel("确认密码：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(51, 72, 84, 15);
		btnPanel.add(label_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(114, 40, 114, 21);
		btnPanel.add(passwordField);

		JLabel usernameLabel = new JLabel("用户名:");
		usernameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		usernameLabel.setBounds(51, 13, 60, 15);
		btnPanel.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setBounds(114, 10, 114, 21);
		btnPanel.add(usernameField);
		usernameField.setColumns(10);

		rePasswordField = new JPasswordField();
		rePasswordField.setBounds(128, 70, 100, 21);
		btnPanel.add(rePasswordField);

		setVisible(true);

	}

	class RegisterController implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!passwordField.getText().equals(rePasswordField.getText())) {
				JOptionPane.showMessageDialog(frame, "两次输入密码不一致，请确认后重试");
			}else {
				String user = usernameField.getText();
			    String pass = CreateMD5.getMd5(passwordField.getText(), 16);
				try {
					Socket s = new Socket("127.0.0.1",8001);
					OutputStream os = s.getOutputStream();
				    OutputStreamWriter osw = new OutputStreamWriter(os);
				    PrintWriter pw = new PrintWriter(osw , true);
									
				    pw.println(user+"%"+pass+"%r");

				    InputStream is = s.getInputStream();
				    InputStreamReader isr = new InputStreamReader(is);
				    BufferedReader br = new BufferedReader(isr);
				        
				    String flag = br.readLine();
				    if(flag.equals("ok")) { 
				        JOptionPane.showMessageDialog(frame,usernameField.getText() + "注册成功。");
				        new Person(pass, s);
				        frame.setVisible(false);
				        framee.setVisible(false);
				    }
				    else JOptionPane.showMessageDialog(frame, "注册失败。");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
		}
	}
}
