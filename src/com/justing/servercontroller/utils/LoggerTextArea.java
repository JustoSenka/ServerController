package com.justing.servercontroller.utils;

/**
 *
 * @author JustInG
 */
public class LoggerTextArea implements Logger{
    
    javax.swing.JTextArea textArea;
    public LoggerTextArea(javax.swing.JTextArea textArea){
        this.textArea = textArea;
    }
    
    @Override
    public void log(String str) {
        textArea.setText(textArea.getText() + "\n" + str);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

}
