package com.justing.servercontroller.server;

import com.justing.servercontroller.utils.Logger;
import com.justing.servercontroller.utils.*;
import java.net.*;
import java.io.*;

public class Server {
    private final static Server Instance = new Server();
    
    public static final int FPS = 50;
    public static final int SERVER_TIMEOUT = 360000;
    public static final int CLIENT_TIMEOUT = 3000;

    private ServerSocket serverSocket = null;
    private boolean run = false;
    private boolean logOnlyMessages = false;
    private String localAddress;
    
    // Default interface to log information got to server, can be overriden
    private Logger logger = (String str) -> {
        System.out.println(str);
    };
    
    private Server() {
        try {
            localAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            localAddress = "Invisible";
        }
    }
    /**
    * Get single instance of Server class (Singleton).
    * @return Server class object.
    */
    public static final Server getInstance() {
        return Instance;
    }
    
    /**
    * Creates socket on specific port. 
    * Thread starts waiting for connection. 
    * @throws IOException
    * @param port Port to create the socket on.
    */
    public void createSocket(int port) throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(SERVER_TIMEOUT * 1000);
            run = true;
            startWaitingThread();
        }
        else {
            throw new IOException("Server socket is already created, you need to destroy it first.");
        }
    }

    /**
    * Destroy the socket.
    * Checks if serverSocket is not null. Catches IOException and prints to logger
    * All threads which wait for Connection to accept will throw an exception.
    */
    public void destroySocket() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocket = null;
            } catch (IOException ex) {
                logger.log(ex.getMessage());
            }
        }
        run = false;
    }
    
    /**
    * Override default logger, all network messages, error and warning messages
    * will be output using this logger
    * 
    * Default logger: System.out.println.
    * @param  logger logger to override
    */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public String getLocalAddress(){
        return localAddress;
    }
    
    /**
    * Method to override default logOnlyMessages variable. <p>
    * if logOnlyMessages is set to true - server will log only messages from client
    * and will not log connected, disconnected messages.
    * Error messages will be logged anyway. <p>
    * Default: logOnlyMessages = false
    * @param  logOnlyMessages boolean value to override logOnlyMessages variable.
    */
    public void setLogOnlyMessages(boolean logOnlyMessages){
        this.logOnlyMessages = logOnlyMessages;
    }
   
// <editor-fold defaultstate="collapsed" desc="Private methods">   
    private void startWaitingThread() {
        new Thread(() -> {
            while (run) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(getRunnable(socket)).start();
                    trySleep(5);
                } catch (SocketTimeoutException e) {
                    logger.log(e.getMessage());
                } catch (IOException e) {
                    logger.log(e.getMessage());
                }
            }
        }).start();
    }

    private Runnable getRunnable(Socket socket) {
        return () -> {
            IntClass clientTimeout = new IntClass(CLIENT_TIMEOUT);
            try {
                // Initialize
                if (!logOnlyMessages)
                    logger.log(socket.getRemoteSocketAddress() + ": - Connected.");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Sends int class, time after which connection should be closed
                startCloseAfterThread(clientTimeout, out, in, socket);
                
                // Read incomming data
                String message;
                while ((message = in.readLine()) != null && run) {
                    logger.log(socket.getRemoteSocketAddress() + ": " + message);
                    if (message != null || !"".equals(message)) {
                        // Sends modified answer according to message
                        sendAnswer(out, DI.getCalculation().getAnswerMessage(message));
                        // Alters int class not to close connection so fast.
                        clientTimeout.value = CLIENT_TIMEOUT;
                    }
                    trySleep(1000 / FPS);
                }

                closeStreams(out, in, socket);
                
                if (!logOnlyMessages)
                    logger.log(socket.getRemoteSocketAddress() + ": - Connection end.");
            } catch (IOException e) {
                if ((clientTimeout.value > 0)){
                    logger.log(e.getMessage());    
                }
            }
        };
    }
    
    private void startCloseAfterThread(IntClass intClass, BufferedWriter out, BufferedReader in, Socket socket) {
        new Thread(() -> {
            while (intClass.value > 0) {
                trySleep(100);
                intClass.value -= 100;
            }
            try {
                if (socket != null && !socket.isClosed()){
                    if (!logOnlyMessages)
                        logger.log(socket.getRemoteSocketAddress() + ": - Closing connection, no data received.");
                    sendAnswer(out, "Closing connection, no data received.");
                    closeStreams(out, in, socket);
                }
            } catch (IOException e) {
            }
        }).start();
    }

    private void closeStreams(BufferedWriter out, BufferedReader in, Socket socket) throws IOException {
        out.close();
        in.close();
        socket.close();
    }
    
    private <T> void sendAnswer(BufferedWriter out, T message) throws IOException {
        out.write(message.toString());
        out.newLine();
        out.flush();
    }
    
    private void trySleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.log(e.getMessage());
        }
    }
    //</editor-fold>
}