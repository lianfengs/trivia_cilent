package client;

import java.io.*;
import java.net.Socket;

public class ClientWriteThread extends Thread {

    private Socket socket;

    /**
     * constructor.
     */
    public ClientWriteThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //get the input.
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //write the input into socket
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("输入客户端名称：");
            while (true) {
                String data = input.readLine();
                writer.write(data);
                writer.newLine();
                writer.flush();
                if (data.equals("disconnect")) {
                    writer.close();
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
