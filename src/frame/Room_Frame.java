package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Room_Frame {
	int your_id;
	int[][] table = new int[16][5];
	Socket socket;
	JFrame jf;
	JPanel[] jp = new JPanel[16];
	JButton[] jb = new JButton[16];
	JLabel[] jl = new JLabel[16];
	Room_Join_Fail_Frame fail;

	public Room_Frame(Socket _socket) {
		socket = _socket;
	}

	public void Init(Socket _socket, int _id, JsonArray ja) {
		fail = new Room_Join_Fail_Frame();
		socket = _socket;
		your_id = _id;

		Iterator it = ja.iterator();

		for (; it.hasNext();) {
			JsonObject jsreply = (JsonObject) it.next();
			int table_id = jsreply.get("game_id").getAsInt();

			JsonArray players = jsreply.get("players").getAsJsonArray();

			Iterator player = players.iterator();

			table[table_id][0] = 0;

			for (; player.hasNext();) {
				jsreply = (JsonObject) player.next();

				int player_id = jsreply.get("player_id").getAsInt();

				table[table_id][++table[table_id][0]] = player_id;

			}

		}

		jf = new JFrame();

		jf.setLayout(new GridLayout(4, 1));

		for (int i = 0; i < 16; i++) {
			String path = "pic/table_" + Integer.toString(table[i][0]) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			jp[i] = new JPanel();
			jb[i] = new JButton();
			// 设定透明效果
			jb[i].setOpaque(false);
			// 去掉背景点击效果
			jb[i].setContentAreaFilled(false);
			// 去掉聚焦线
			jb[i].setFocusPainted(false);
			// 去掉边框
			jb[i].setBorder(null);
			// 设置显示的图片
			jb[i].setIcon(icon);
			jl[i] = new JLabel(Integer.toString(i));
			jb[i].addActionListener(new Join(i));
			jp[i].add(jb[i]);
			jp[i].add(jl[i]);
			jf.add(jp[i]);
		}
		jf.setSize(800, 600);
		// this.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}

	public void join_room_fail() {
		fail.setVisible(true);
	}

	public void join_room_succ() {
		jf.setVisible(false);
	}

	public void join_room(int joiner_id, int table_id) {
		System.out.println("join_room("+joiner_id+","+table_id+")");
		table[table_id][++table[table_id][0]] = joiner_id;
		String path = "pic/table_" + Integer.toString(table[table_id][0]) + ".jpg";
		ImageIcon icon = new ImageIcon(path);
		jb[table_id].setIcon(icon);
		jp[table_id].updateUI();
		jf.repaint();
	}

	public void leave_room(int leaver_id,int table_id) {
		System.out.println("leave_room("+leaver_id+","+table_id+")");
		int[] tmp= {0,-1,-1,-1};
		
		for (int i=1;i<=table[table_id][0];i++) {
			if (table[table_id][i]!=leaver_id)
				tmp[++tmp[0]]=table[table_id][i];
		}
		
		for (int i=0;i<=tmp[0];i++) {
			table[table_id][i]=tmp[i];
		}
				
		String path = "pic/table_" + Integer.toString(table[table_id][0]) + ".jpg";
		ImageIcon icon = new ImageIcon(path);
		jb[table_id].setIcon(icon);
		jp[table_id].updateUI();
		jf.repaint();
	}
	
	class Join implements ActionListener {
		int table_id;

		public Join(int _table_id) {
			table_id = _table_id;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				JsonObject jsonObject = new JsonObject();

				jsonObject.addProperty("command", "join_room");
				jsonObject.addProperty("game_id", Integer.toString(table_id));

				String json = new Gson().toJson(jsonObject);
				// System.out.println(json);
				output.write(json);
				output.newLine();
				output.flush();

			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}