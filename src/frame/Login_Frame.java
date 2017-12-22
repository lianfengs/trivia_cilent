package frame;
import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.*;   //导入必要的包
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Login_Frame extends JFrame{
	Socket socket;
	Register_Frame rf;
	Login_Fail_Frame fail;
    public Login_Frame(Socket _socket){
    	fail=new Login_Fail_Frame();
    	socket=_socket;
    	rf = new Register_Frame(_socket,this);
    	
    	JTextField name = new JTextField(12);
    	JPasswordField password = new JPasswordField(12);
    	JLabel jLabel1 = new JLabel("用户名");
    	JLabel jLabel2 = new JLabel("密码    ");
        JButton confirm = new JButton("确认");
        JButton register  = new JButton("注册");
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        
        register.addActionListener(new ActionListener() {
          	 public void actionPerformed(ActionEvent e) {
          		setVisible(false);
          		rf.setVisible(true);
          	 }
        });
        confirm.addActionListener(new ActionListener() {
       	 public void actionPerformed(ActionEvent e) {
       		 try {
       			 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       			 BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       			 String your_name=name.getText();
       			 String your_password=password.getText();
       			 JsonObject jsonObject = new JsonObject();
       			 
       			 jsonObject.addProperty("command", "login");
       			 jsonObject.addProperty("account", your_name);
       			 jsonObject.addProperty("password", your_password);
       			 
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
        //设置布局
        this.setLayout(new GridLayout(3,1));
        
        jp1.add(jLabel1); 
        jp1.add(name);//第一块面板添加用户名和文本框 
        
        jp2.add(jLabel2);
        jp2.add(password);//第二块面板添加密码和密码输入框
        
        jp3.add(confirm);
        jp3.add(register); //第三块面板添加确认和取消
        
        //        jp3.setLayout(new FlowLayout());  　　//因为JPanel默认布局方式为FlowLayout，所以可以注销这段代码.
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);  //将三块面板添加到登陆框上面
        //设置显示
        this.setSize(300, 200);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("登陆");
         
    }
    public void login_succ() {
        this.setVisible(false);
    }   
    public void login_fail() {
        fail.setVisible(true);
    }
    public void register_succ() {
    	rf.Register_succ();
    }   
    public void register_fail() {
    	rf.Register_fail();
    }  
}