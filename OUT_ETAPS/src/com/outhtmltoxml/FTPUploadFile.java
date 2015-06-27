package com.outhtmltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
 
/**
 * A program that demonstrates how to upload files from local computer
 * to a remote FTP server using Apache Commons Net API.
 * @author kaliaperumal
 */
public class FTPUploadFile {
 
	public void Upload(String source, String todest) {

    	IniFileReader res = new IniFileReader();
    	String server = IniFileReader.server;
        int port = IniFileReader.port;
        String user = IniFileReader.user;
        String pass = IniFileReader.pass;
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(source);
			 if(firstLocalFile.exists()){
			     String firstRemoteFile = todest;
			     InputStream inputStream = new FileInputStream(firstLocalFile);
			
			     System.out.println("Start uploading " + source);
			     boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			     inputStream.close();
			     if (done) {
			         System.out.println(source + "file is uploaded successfully.");
			     }
				 
			 }
    
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
 
}