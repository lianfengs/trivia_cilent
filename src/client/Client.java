package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import frame.Login_Frame;

public class Client {

    /**
     * main.
     */
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8899);
            //new ClientWriteThread(socket).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new ClientReadThread(reader,socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
