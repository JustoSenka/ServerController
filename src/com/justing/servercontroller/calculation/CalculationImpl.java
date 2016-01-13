package com.justing.servercontroller.calculation;

/**
 *
 * @author JustInG
 */
public class CalculationImpl implements Calculation {
    
    /**
    * Returns a string which should be sent back to client app. 
    * 
    * @param input message sent from client to server
    * @return       string generated form input message
    */
    @Override
    public String getAnswerMessage(String input){
        String answer = "";
        
        // Code to generate the answer message
        answer = "Client sent us: \"" + input + "\"";
        
        return answer;
    }
}
