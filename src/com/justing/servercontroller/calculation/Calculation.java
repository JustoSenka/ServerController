package com.justing.servercontroller.calculation;

/**
 *
 * @author JustInG
 */
public interface Calculation {
     
    /**
    * Returns a string which should be sent back to client app. 
    * 
    * @param input message sent from client to server
    * @return       string generated form input message
    */
    public String getAnswerMessage(String input);
}
