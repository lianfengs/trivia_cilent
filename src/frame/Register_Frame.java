package frame;
import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.ClientReadThread;
import client.ClientWriteThread;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Register_Frame extends JFrame{
	Socket socket;
	Register_Hint_Fail_Frame fail;
	Register_Hint_Succ_Frame succ;
	Login_Frame lf;
    public Register_Frame(Socket _socket,Login_Frame _lf){
    	
    	fail = new Register_Hint_Fail_Frame();
    	succ = new Register_Hint_Succ_Frame();
    	
    	lf=_lf;
    	socket=_socket;
    	JTextField name = new JTextField(12);
    	JPasswordField password = new JPasswordField(12);
    	JTextField nickname = new JTextField(12);
    	JLabel jLabel1 = new JLabel("用户名");
    	JLabel jLabel2 = new JLabel("密码    ");
    	JLabel jLabel3 = new JLabel("昵称    ");
        JButton confirm = new JButton("确认");
        JButton cancel  = new JButton("取消");
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        confirm.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 try {
        			 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        			 BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        			 String your_name=name.getText();
        			 String your_password=password.getText();
        			 String your_nickname=nickname.getText();
        			 JsonObject jsonObject = new JsonObject();
        			 
        			 jsonObject.addProperty("command", "register");
        			 jsonObject.addProperty("account", your_name);
        			 jsonObject.addProperty("password", your_password);
        			 jsonObject.addProperty("nickname", your_nickname);
        			 
        			 String json = new Gson().toJson(jsonObject);
        			 //System.out.println(json);
        			 output.write(json);
        			 output.newLine();
        			 output.flush();
        			 
        			        			 
        		 }catch (IOException ee){
        			 ee.printStackTrace();
        		 }
        	 }
        });
        
        cancel.addActionListener(new ActionListener() {
       	 public void actionPerformed(ActionEvent e) {
       		setVisible(false);
       		lf.setVisible(true);
       	 }
       });
        //设置布局
        this.setLayout(new GridLayout(4,1));
        
        jp1.add(jLabel1); 
        jp1.add(name);//第一块面板添加用户名和文本框 
        
        jp2.add(jLabel2);
        jp2.add(password);//第二块面板添加密码和密码输入框

        jp3.add(jLabel3); 
        jp3.add(nickname);//第一块面板添加用户名和文本框
        
        jp4.add(confirm);
        jp4.add(cancel); //第三块面板添加确认和取消
        
        //        jp3.setLayout(new FlowLayout());  　　//因为JPanel默认布局方式为FlowLayout，所以可以注销这段代码.
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);  
        this.add(jp4); 
        //设置显示
        this.setSize(300, 200);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setTitle("注册");
         
    }
    void Register_succ() {
		 setVisible(false);
		 lf.setVisible(true);
		 succ.setVisible(true);
    }
    void Register_fail() {
		 fail.setVisible(true);
    }
}