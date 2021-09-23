package com.sqlite3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * ClientHandler manages communication between clients, a list of client users
 * is contained in an array which is iterated through when messages are recieved
 * to be sent to other clients.
 * 
 */
public class ClientHandler extends Thread {

    User[] users;

    /**
     * Constructor
     */
    public ClientHandler() {
        users = new User[5];
    }

    /**
     * Helper function that retrurns a message recieved from s
     * 
     * @param s Socket to recieve message from
     * @return string recieved by s
     * @throws IOException
     */
    public String recieveMessage(Socket s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return br.readLine(); // pauses here to wait for socket input?
    }

    /**
     * sends a message to usr using a {@link PrintWriter} to write to the socket
     * outputstream.
     * 
     * @param usr {@link User} object that is sending the message
     * @param msg the message to be sent
     * @throws IOException
     */
    public void sendMessage(User usr, String msg) throws IOException {
        if (msg != null) {
            PrintWriter pr = new PrintWriter(usr.socket.getOutputStream());
            pr.println(msg);
            pr.flush();
        }
    }

    /**
     * addUser creates a new User object and stores it in the next availabe space in
     * the users array.
     * 
     * @param s Socket for the new user object to communicate with other users
     * @throws IOException
     */
    @Deprecated
    public void addUser(Socket s) throws IOException {
        for (int i = 0; i < 5; i++) {
            if (users[i] == null) {
                users[i] = new User(s);
                this.suspend();
                String name = recieveMessage(s);
                users[i].setName(name);
                System.out.println("| " + name + " joined");
                this.resume();
                break;
            }
        }
    }

    /**
     * run method iterates through each user in users and checks its socket
     * inputstream for new data once detected sends this data to all other clients.
     */
    @Override
    @Deprecated
    public void run() {
        while (true) {
            for (int i = 0; i < 5; i++) {
                try {
                    User user = users[i];
                    if (user != null && user.socket != null) {
                        user.msg = null;
                        try {
                            user.start();
                        } catch (Exception e) {
                            // TODO -- change exception type
                            user.resume();
                        }
                        sleep(1);
                        user.suspend();
                        if (user.msg != null) {
                            for (int j = 0; j < 5; j++) {
                                if (users[j] != null && i != j) {
                                    sendMessage(users[j], user.msg);
                                }
                            }
                        }

                    }
                } catch (IOException e) {
                    // TODO -- handle exception
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    // class MessageReciever extends Thread {

    // Socket s;

    // public void setSocket(Socket s) {
    // this.s = s;
    // }

    // @Override
    // public void run() {

    // }
    // }
}