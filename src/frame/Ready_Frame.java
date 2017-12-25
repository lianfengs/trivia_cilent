package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Ready_Frame {
	private JPanel contentPane;
	private JFrame jf;
	private Socket socket;
	private JLabel[] ready = new JLabel[4];
	private JLabel[] id = new JLabel[4];
	private int table_id;
	private int host_id;
	private int your_id;
	private int[] player_id = {-1,-1,-1,-1};
	private boolean[] player_ready = {false,false,false,false};
	private String[] player_nickname= {"","","",""};
	private JButton button_ready;
	private JButton button_leave;
	private JLabel label_table_id;
	private String your_nickname;
	public Ready_Frame(Socket _socket) {
		socket=_socket;
		jf = new JFrame();
		jf.setResizable(false);
		jf.setTitle("准备室");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jf.setContentPane(contentPane);
		contentPane.setLayout(null);
		for (int i=0;i<4;i++) {
			Color border=new Color(0,88,169);
			Color back=new Color(199,229,248);
			ready[i]=new JLabel();
			ready[i].setOpaque(true);
			ready[i].setBackground(back);
			ready[i].setBounds(90, 110+110*i, 40, 40);
			ready[i].setBorder(BorderFactory.createLineBorder(border));
			contentPane.add(ready[i]);
		}
		for (int i=0;i<4;i++) {
			Color border=new Color(0,88,169);
			Color back=new Color(199,229,248);
			id[i]=new JLabel();
			id[i].setOpaque(true);
			id[i].setBackground(back);
			id[i].setBounds(170, 110+110*i, 311, 40);
			id[i].setBorder(BorderFactory.createLineBorder(border));
			contentPane.add(id[i]);
		}
		
		button_ready = new JButton();
		button_ready.setBounds(549, 173, 151, 40);
		button_ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (your_id==host_id) {
					boolean everybody_are_ready=true;
					for (int i=0;i<4;i++) {
						if (player_id[i]==-1) continue;
						if (player_id[i]==host_id) continue;
						if (player_ready[i]) continue;
						everybody_are_ready =false;
					}
					if (everybody_are_ready) {
			       		 try {
			    			 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    			 BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			    			 JsonObject jsonObject = new JsonObject();
			    			 
			    			 jsonObject.addProperty("command", "start");
			    			 
			    			 String json = new Gson().toJson(jsonObject);
			    			 //System.out.println(json);
			    			 output.write(json);
			    			 output.newLine();
			    			 output.flush();
			    		
			    			        			 
			    		 }catch (IOException ee){
			    			 ee.printStackTrace();
			    		 }
					}else {
						
					}
				}else {
		       		 try {
		    			 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    			 BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		    			 JsonObject jsonObject = new JsonObject();
		    			 
		    			 jsonObject.addProperty("command", "update_ready");
		    			 
		    			 String json = new Gson().toJson(jsonObject);
		    			 //System.out.println(json);
		    			 output.write(json);
		    			 output.newLine();
		    			 output.flush();
		    		
		    			        			 
		    		 }catch (IOException ee){
		    			 ee.printStackTrace();
		    		 }
				}
			}
		});
		contentPane.add(button_ready);
		
		button_leave = new JButton("离开房间");
		button_leave.setBounds(549, 388, 151, 40);
		button_leave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	       		 try {
	    			 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    			 BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	
	    			 JsonObject jsonObject = new JsonObject();
	    			 
	    			 jsonObject.addProperty("command", "leave_room");
	    			 
	    			 String json = new Gson().toJson(jsonObject);
	    			 //System.out.println(json);
	    			 output.write(json);
	    			 output.newLine();
	    			 output.flush();
	    		
	    			 jf.setVisible(false);
	    			        			 
	    		 }catch (IOException ee){
	    			 ee.printStackTrace();
	    		 }
			}
		});
		contentPane.add(button_leave);
		
		label_table_id = new JLabel();
		label_table_id.setBounds(10, 10, 79, 45);
		contentPane.add(label_table_id);
	}
	public void Init(JsonArray ja,int _table_id,int _host_id,int _your_id,String _your_nickname) {
		for (int i=0;i<4;i++) {
			player_id[i]=-1;
			player_nickname[i]="";
			player_ready[i]=false;
		}
		table_id=_table_id;
		label_table_id.setText("桌号："+Integer.toString(table_id));
		host_id=_host_id;
		your_id=_your_id;
		your_nickname=_your_nickname;
		Iterator it = ja.iterator();
		
		for (int i=0; it.hasNext();i++) {
			
			JsonObject jsreply = (JsonObject) it.next();

			player_id[i] = jsreply.get("player_id").getAsInt();
			player_nickname[i] = jsreply.get("nickname").getAsString();
			player_ready[i] = jsreply.get("ready").getAsBoolean();

		}
		for (int i=0;i<4;i++) {
			if (player_id[i]==-1) continue;
			if (player_id[i]==host_id) {
				ready[i].setIcon(new ImageIcon("pic/host.jpg"));
			}else if (player_ready[i]) {
				ready[i].setIcon(new ImageIcon("pic/ready.png"));
			}else {
				ready[i].setIcon(null);
			}
			id[i].setText(player_nickname[i]);
		}
		for (int i=0;i<4;i++)
			if (player_id[i]==-1) {
				if (your_id==host_id) {
					ready[i].setIcon(new ImageIcon("pic/host.jpg"));
				}else if (player_ready[i]) {
					ready[i].setIcon(new ImageIcon("pic/ready.png"));
				}else {
					ready[i].setIcon(null);
				}
				id[i].setText(your_nickname);
				break;
			}
		contentPane.updateUI();
		jf.repaint();
		jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setAlwaysOnTop(true);
	}
}
