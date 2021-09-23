package com.sqlite3server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server object
 * 
 * recieves incoming connections and passes the client sockets to a
 * clienthandler object.
 *
 */
public class Server {

    // Open port
    static int PORT = 4999;

    public static void main(String[] args) {
        try {
            ClientHandler c = new ClientHandler();
            c.start();
            System.out.println("<Session Started>");
            ServerSocket ss = new ServerSocket(PORT);
            ss.setReuseAddress(true);
            // TODO -- add a close command
            while (true) {
                Socket s = ss.accept();
                c.addUser(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}