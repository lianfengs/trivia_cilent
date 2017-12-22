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
    /**
     * constructor.
     */
    public ClientReadThread(BufferedReader reader,Socket _socket) {
        this.input = reader;
        socket=_socket;
        loginf=new Login_Frame(socket);
        roomf=new Room_Frame(socket);
        readyf=new Ready_Frame();
        gamef=new Game_Frame();
    }

    public void run() {
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
      				
      				String result=jsreply.get("result").getAsString();
	       			
      				if (target.equals("joiner")) {
      				
	      				if (result.equals("failure")) {
		       				roomf.join_room_fail();
		       			}else {
		       				JsonArray ja = jsreply.get("other_players").getAsJsonArray();
		       				
		       				int table_id = jsreply.get("game_id").getAsInt();
		       				
		       				int host_id = jsreply.get("host_id").getAsInt();
		       				
		       				table=table_id;
		       				
		       				//roomf.join_room_succ();
		       				
	      					roomf.join_room(your_id,table_id);
		       			}
      				}else {
	       				int table_id = jsreply.get("game_id").getAsInt();
	       				
	       				int joiner_id = jsreply.get("joiner_id").getAsInt();
	       				
	       				String joiner_nickname=jsreply.get("joiner_nickname").getAsString();
	       				      				
      					roomf.join_room(joiner_id,table_id);
      				}
      			 }
      			 

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
