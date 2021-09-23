package com.sqlite3server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * User acts as a container to keep a clients socket and its name together
 * allowing each client to be identified in a more human way
 */
public class User extends Thread {

    Socket socket;
    String name;
    String msg = null;

    /**
     * Constructor
     * 
     * @param s Scocket for the current instance of user
     */
    public User(Socket s) {
        this.socket = s;
    }

    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                msg = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
