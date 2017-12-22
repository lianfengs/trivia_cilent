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
    	JLabel jLabel1 = new JLabel("�û���");
    	JLabel jLabel2 = new JLabel("����    ");
    	JLabel jLabel3 = new JLabel("�ǳ�    ");
        JButton confirm = new JButton("ȷ��");
        JButton cancel  = new JButton("ȡ��");
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
        //���ò���
        this.setLayout(new GridLayout(4,1));
        
        jp1.add(jLabel1); 
        jp1.add(name);//��һ���������û������ı��� 
        
        jp2.add(jLabel2);
        jp2.add(password);//�ڶ�����������������������

        jp3.add(jLabel3); 
        jp3.add(nickname);//��һ���������û������ı���
        
        jp4.add(confirm);
        jp4.add(cancel); //������������ȷ�Ϻ�ȡ��
        
        //        jp3.setLayout(new FlowLayout());  ����//��ΪJPanelĬ�ϲ��ַ�ʽΪFlowLayout�����Կ���ע����δ���.
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);  
        this.add(jp4); 
        //������ʾ
        this.setSize(300, 200);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);
        this.setVisible(false);
        this.setTitle("ע��");
         
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