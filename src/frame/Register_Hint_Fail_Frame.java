package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Register_Hint_Fail_Frame extends JFrame {
    public Register_Hint_Fail_Frame(){
    	JLabel jLabel1 = new JLabel("该用户名已被注册");
        JButton confirm = new JButton("确认");
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        
        confirm.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 setVisible(false);
        	     setLocationRelativeTo(null);
        	 }
        });
        //设置布局
        this.setLayout(new GridLayout(3,1));
        
        jp1.add(jLabel1);  
                
        jp2.add(confirm);
        
        this.add(jp1);
        this.add(jp2);
        //设置显示
        this.setSize(300, 200);
        //this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setAlwaysOnTop(true);
        this.setVisible(false);
         
    }
}
