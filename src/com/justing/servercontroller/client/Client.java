package com.justing.servercontroller.client;

import com.justing.servercontroller.utils.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final Client Instance = new Client();
    
    public static final int FPS = 50;
    
    private String ip;
    private int port;
    private volatile boolean busy = false;

    private Socket client = null;
    private BufferedWriter out = null;
    private BufferedReader in = null;

    // Default interface to log information got to server, can be overriden
    private Logger logger = (String str) -> {
        System.out.println(str);
    };
    
    private Client() {}
    /**
    * Get single instance of Client class (Singleton).
    * @return Client class object.
    */
    public static Client getInstance() {
        return Instance;
    }

    /**
    * Connect to a server using ip and port.
    * @param ip Ip adress of server machine.
    * @param port Port the server is hosted on.
    * @return 0 - if connected successfully. <br>
    *         1 - if UnknownHostException error occurred. <br>
    *         2 - if IOException error occurred. <br>
    */
    public int connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        
        int success;
        try {
            client = new Socket(ip, port);
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            logger.log("Successfuly connected to: " + client.getRemoteSocketAddress());
            success = 0;
        } catch (UnknownHostException e) {
            logger.log(e.getMessage());
            success = 1;
        } catch (IOException e) {
            logger.log(e.getMessage());
            success = 2;
        }
        return success;
    }

    /**
    * Closes all streams and active client socket. <p>
    * 
    * Does not throw NullPointerException. <br>
    * Catches IOExceptions and prints its message to Logger.
    */
    public void disconect() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                logger.log(e.getMessage());
            }
        }
    }

    /**
    * Send message to server. <p>
    * 
    * Works on separate thread, sends object according to its method toString(). <br>
    * Catches IOExceptions and prints its message to Logger. <br>
    * Adds new line to BufferedReader and flushes it automatically.
    * @param obj Message object to send
    */
    public <T> void send(final T obj) {

        if (!busy){
            busy = true;
            new Thread(() -> {
                try {
                    out.write(obj.toString());
                    out.newLine();
                    out.flush();
                    logger.log("Message successfully sent.");
                } catch (IOException e) {
                    logger.log(e.getMessage());
                    busy = false;
                }

                busy = false;
            }).start();
        }
        else {
            logger.log("Client is busy.");
        }
    }
    
    /**
    * Get line sent from server. <p>
    * 
    * Will block main thread till get new line from stream.
    * Catches IOExceptions and prints its message to Logger.
    * @return  String line got from server. <br>
    *          if exception was thrown, will return empty string. <br>
    *          if stream end, will return null.
    */
    public String get(){
        String message = "";
        
        try {
            message = in.readLine();
        } catch (IOException ex) {
            logger.log(ex.getMessage());
        }
        
        return message;
    }
    
    /**
    * @return  true - if trying to send some data to the server at this moment. <br>
    *          false - if idle.
    */
    public boolean isBusy() {
        return busy;
    }

    public boolean isConnected() {
        return client != null;
    }
    
    /**
    * Override default logger. <p> 
    * 
    * All network messages, error and warning messages
    * will be output using this logger. <br>
    * Default logger: System.out.println.
    * 
    * @param  logger logger to override
    */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Private methods">  
    private void trySleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.log(e.getMessage());
        }
    }
    // </editor-fold>
}
