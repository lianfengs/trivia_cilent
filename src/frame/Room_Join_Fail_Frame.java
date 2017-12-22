package frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Room_Join_Fail_Frame extends JFrame {
    public Room_Join_Fail_Frame(){
    	JLabel jLabel1 = new JLabel("该房间人数已满");
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
        this.setLayout(new GridLayout(4,1));
        
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
