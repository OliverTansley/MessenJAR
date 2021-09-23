package com.sqlite3client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Client reciever handles the output of all recieved messages. It holds the
 * users name and socket representing the server.
 */
public class ClientReciever extends Thread {

    String name;
    Socket s;

    public ClientReciever(Socket s, String n) {
        this.name = n;
        this.s = s;
    }

    /**
     * run method uses a {@link BufferedReader} to read from the sockets input
     * stream and output the message to the terminal.
     * 
     * The input prompt is also deleted from the terminal and re-displayed.
     */
    @Override
    public void run() {
        try {
            String msg;
            BufferedReader br;
            while (true) {
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                msg = br.readLine();
                for (int i = 0; i < this.name.length() + 2; i++) {
                    System.out.print("\b");
                }
                if (msg != null) {
                    System.out.println(msg);
                    System.out.print(">" + this.name + ":");
                }
            }
        } catch (SocketException e) {
            System.out.println("<Room Closed by Host>");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
