package com.outhtmltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class IniFileReader {

	static String proofs_uploadPath;
	public static String getProofs_uploadPath() {
		return proofs_uploadPath;
	}


	public static void setProofs_uploadPath(String proofs_uploadPath) {
		IniFileReader.proofs_uploadPath = proofs_uploadPath;
	}


	static String 	localUploadPath;
	public static String getLocalUploadPath() {
		return localUploadPath;
	}


	public static void setLocalUploadPath(String localUploadPath) {
		IniFileReader.localUploadPath = localUploadPath;
	}


	static String  	Downloadpicktime;
	public static String getDownloadpicktime() {
		return Downloadpicktime;
	}


	public static void setDownloadpicktime(String downloadpicktime) {
		Downloadpicktime = downloadpicktime;
	}


	static String 	server;
	public static String getServer() {
		return server;
	}


	public static void setServer(String server) {
		IniFileReader.server = server;
	}


	static String 	user;
	public static String getUser() {
		return user;
	}


	public static void setUser(String user) {
		IniFileReader.user = user;
	}


	static String 	pass;
	public static String getPass() {
		return pass;
	}


	public static void setPass(String pass) {
		IniFileReader.pass = pass;
	}


	static String 	rootdir;
	public static String getRootdir() {
		return rootdir;
	}


	public static void setRootdir(String rootdir) {
		IniFileReader.rootdir = rootdir;
	}


	static String 	backupPath;
	public static String getBackupPath() {
		return backupPath;
	}


	public static void setBackupPath(String backupPath) {
		IniFileReader.backupPath = backupPath;
	}


	static String 	XSLTPath;
	public static String getXSLTPath() {
		return XSLTPath;
	}


	public static void setXSLTPath(String xSLTPath) {
		XSLTPath = xSLTPath;
	}


	static String 	iniPath;
	public static String getIniPath() {
		return iniPath;
	}


	public static void setIniPath(String iniPath) {
		IniFileReader.iniPath = iniPath;
	}


	static String 	FTPoutdir;
	public static String getFTPoutdir() {
		return FTPoutdir;
	}


	public static void setFTPoutdir(String fTPoutdir) {
		FTPoutdir = fTPoutdir;
	}


	static int port;
	public static int getPort() {
		return port;
	}


	public static void setPort(int port) {
		IniFileReader.port = port;
	}


	public IniFileReader() {
		
		Properties properties=new Properties();		
		try {
			properties.load(new FileInputStream(new File("path.ini")));
		} catch (IOException e) {
			System.out.println("Exception in reading ini file : "+e);			
		}

		proofs_uploadPath	=	properties.getProperty("proofs_uploadPath");
		localUploadPath		=	properties.getProperty("Local_uploadPath");
		server				=	properties.getProperty("FTP-HOST-Server");
		user				=	properties.getProperty("FTPUser");
		pass				=	properties.getProperty("FTPPassword");
		rootdir				=	properties.getProperty("FTProotdir");
		FTPoutdir			= 	properties.getProperty("FTPoutdir");
		backupPath			=	properties.getProperty("BackupPath");
		XSLTPath			=	properties.getProperty("XSLTPath");
		iniPath				=	properties.getProperty("iniPath");
		port				=	Integer.parseInt(properties.getProperty("FTPPort").toString());
		
	}
	
	
}
