package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import frame.Game_Frame;
import frame.Login_Frame;
import frame.Ready_Frame;
import frame.Register_Frame;
import frame.Room_Frame;

public class ClientReadThread extends Thread {

    private BufferedReader input;
    Socket socket;
	static Login_Frame loginf;
	static Room_Frame roomf; 
	static Ready_Frame readyf;
	static Game_Frame gamef;
	int your_id;
	int table=-1;
	String your_nickname;
    /**
     * constructor.
     */
    public ClientReadThread(BufferedReader reader,Socket _socket) {
        this.input = reader;
        socket=_socket;
        loginf=new Login_Frame(socket);
        roomf=new Room_Frame(socket);
        readyf=new Ready_Frame(socket);
        gamef=new Game_Frame(socket);
    }

    public void run() {

			 System.out.println("read run");
        try {
            while (true) {
                //read the data
      			 String reply=input.readLine();
       			 System.out.println("reply="+reply);
       			 
      			 JsonObject jsreply=new JsonParser().parse(reply).getAsJsonObject();
      			 
      			 String command=jsreply.get("command").getAsString();
      			 if (command.equals("login")) {
	      			 String result=jsreply.get("result").getAsString();
	       			 
	       			 if (result.equals("success")) {
	       				 
	       				 your_id=jsreply.get("player_id").getAsInt();
	       				 your_nickname=jsreply.get("nickname").getAsString();
	       				 loginf.login_succ();
	       				 
	       			 }else {
	       				 
	       				 loginf.login_fail();
	       			 }
      			 }else if (command.equals("register")) {
	      			 String result=jsreply.get("result").getAsString();
	       			 
	       			 if (result.equals("success")) {
	       				 
	       				 loginf.register_succ();
	       				 
	       			 }else {
	       				 
	       				 loginf.register_fail();
	       			 }
      			 } else if (command.equals("hall_info")) {
      				 
      	   			JsonArray ja=jsreply.get("games").getAsJsonArray();
      	   			
      				roomf.Init(socket, your_id,ja);
      			 }else if (command.equals("join_room")) {
      				
      				String target=jsreply.get("target").getAsString();
      				
	       			
      				if (target.equals("joiner")) {

          				String result=jsreply.get("result").getAsString();
          				
	      				if (result.equals("false")) {
		       				roomf.join_room_fail();
		       			}else {
		       				JsonArray ja = jsreply.get("other_players").getAsJsonArray();
		       				
		       				int table_id = jsreply.get("game_id").getAsInt();
		       				
		       				int host_id = jsreply.get("host_id").getAsInt();
		       						       				
		       				table=table_id;
		       				
		       				//roomf.join_room_succ();
		       				
		       				readyf.Init(ja,table_id,host_id,your_id,your_nickname);
		       				
		       			}
      				}else {
	       				int table_id = jsreply.get("game_id").getAsInt();
	       				
	       				int joiner_id = jsreply.get("joiner_id").getAsInt();
	       				
	       				String joiner_nickname=jsreply.get("joiner_nickname").getAsString();
	       				      				
      					roomf.join_room(joiner_id,table_id);
      					
      					if (table==table_id) readyf.join_room(joiner_id,joiner_nickname);
      				}
      			 }else if (command.equals("leave_room")) {

	       				int table_id = jsreply.get("game_id").getAsInt();
	       				
	       				int leaver_id = jsreply.get("leaver_id").getAsInt();
	       				
	       				int host_id = jsreply.get("new_host").getAsInt();
	       				
	       				roomf.leave_room(leaver_id,table_id);
	       				
	       				if(table==table_id) readyf.leave_room(leaver_id,host_id);
	       				
	       				if (leaver_id==your_id) table=-1;
      			 }else if (command.equals("update_ready")) {

	       			int player_id = jsreply.get("player_id").getAsInt();
	       			
	       			readyf.player_ready(player_id);
      			 }
      			 

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
