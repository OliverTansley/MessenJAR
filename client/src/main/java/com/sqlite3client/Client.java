package com.sqlite3client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client represents the machine connected to the server. A message reciever
 * object is instantiated which will output the messages recieved on a different
 * thread.
 * 
 * Meanwhile the main thread continues to take input for new messages and send
 * those to the server.
 */
public class Client {

    static String name;

    /**
     * 
     * @param s   socket to send message to
     * @param msg message to send
     * @throws IOException
     */
    public static void sendMessage(Socket s, String msg) throws IOException {
        if (msg != null) {
            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println(msg);
            pr.flush();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // get username as input
        System.out.println("<Enter Client Username>");
        name = sc.nextLine();

        try {
            //
            Socket s = new Socket("localhost", 4999);
            sendMessage(s, name);
            ClientReciever reciever = new ClientReciever(s, name);
            reciever.start();
            String msg = "";
            System.out.println("<Session Started>");

            while (msg != "quit\n") {
                System.out.print(">" + name + ":");
                msg = sc.nextLine();
                sendMessage(s, "|" + name + ":" + msg);
            }
            sc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            System.out.println("<Chatroom is currently full try again later>");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}