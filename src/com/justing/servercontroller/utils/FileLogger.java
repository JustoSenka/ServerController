package com.justing.servercontroller.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Semi-Automated Class to log text to file.
 * @author JustInG
 */
public class FileLogger {
    
    private final String directoryPath;
    private final String fileName;
            
    /**
    * Constructor for file logger.
    * @param directoryPath - Path of file directory, will be created if not exist.
    * @param fileName - File name to create (With suffix).
    */
    public FileLogger(String directoryPath, String fileName){
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }
    
    /**
    * Logs data to a file, automatically adds date of log creation on first line.
    * @param text - Text to write to file.
    * @param sizeOfGeneratedNumber - If there are other logs, a number will be
    *   generated after file name. If value 3, then 001, 002 etc..
    * @return String - Created file full path.
    */
    public String logToFile(String text, int sizeOfGeneratedNumber){
        createDirectory(directoryPath);
        Path logPath = getPathForFile(directoryPath, fileName.split("\\.")[0], fileName.split("\\.")[1], sizeOfGeneratedNumber);
        return writeToFile(logPath, text);
    }
    
    /**
    * Logs data to a file, automatically adds date of log creation on first line.
    * Overwrites existing file.
    * @param text - Text to write to file.
    * @return String - Created file full path.
    */
    public String logToFile(String text){
        createDirectory(directoryPath);
        return writeToFile(Paths.get(directoryPath + "\\" + fileName), text);        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Private methods">  
    private void createDirectory(String path) {
        File logDir = new File(path);
        if (!logDir.exists()){
            logDir.mkdir();
        }
    }
    
    private Path getPathForFile(String path, String pre, String suf, int size) {
        File log;
        suf = "." + suf;
        pre = "\\" + pre + "_";
        
        Path p = Paths.get(path + pre + suf); // Default in case of sth.
        for (int i = 1; i < 1000000; i++){
            log = new File(path + pre + getNumWithZeros(size, i) + suf);
            if (!log.exists()){
                p = Paths.get(path + pre + getNumWithZeros(size, i) + suf);
                break;
            }
        }
        return p;
    }
    
    private String getNumWithZeros(int length, int num){
        String zeros = "";
        for (int i = 0; i < length - String.valueOf(num).length(); i++){
            zeros += "0";
        }
        return zeros + num;
    }
    
    private String writeToFile(Path logPath, String text) {
        String str = "Log created: ";
        str += LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        str += System.lineSeparator() + text;
        
        try {
            Files.write(logPath, str.getBytes(), StandardOpenOption.CREATE_NEW);
            str = logPath.toString();
        } catch (IOException ex) {
            str = ex.getMessage();
        }
        return str;
    }
    // </editor-fold>
}
