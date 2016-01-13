package com.justing.servercontroller.main;

import com.justing.servercontroller.client.ClientJFrame;
import com.justing.servercontroller.server.ServerJFrame;

/**
 *
 * @author JustInG
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length > 0){
            if (args[0].equalsIgnoreCase("-server")){
                new ServerJFrame().setVisible(true);
            }
            else if (args[0].equalsIgnoreCase("-client")){
                new ClientJFrame().setVisible(true);
            }
            else {
                System.out.println("Server controller   -   © JustInG 2016");
                System.out.println("");
                System.out.println("Command line args: ");
                System.out.println("   -server      Start directly Server GUI");
                System.out.println("   -client      Start directly Client GUI");
                System.out.println("");
            }
        }
        else {
            java.awt.EventQueue.invokeLater(() -> {
                new MainJFrame().setVisible(true);
            });
        }
    }
    
}
