package com.outhtmltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pojo.Article;
import pojo.Datasetstatus.status;

import com.database.HibernateUtil;

public class htmltoxml {

	static String 	proofs_uploadPath;
	static String 	localUploadPath;
	static String  	Downloadpicktime;
	static String 	server;
	static String 	user;
	static String 	pass;
	static String 	rootdir;
	static String 	backupPath;
	static String 	XSLTPath;
	static String 	XSLTPath_report;
	static String 	iniPath;
	static String FTPoutdir;
	static int 		port;

	
	public static void main(String args[]) throws IOException, InterruptedException{
		getoutgeneratedarticledetails();
		//File srcDir=new File("/trans/ETAPS/mahi/epl17032");
		//File desDir=new File("/trans/ETAPS/mahi/epl17032_out");
		//copyDirectory1(desDir);
	}
	
	private static void getIniDetails() {
		
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
		XSLTPath			=	properties.getProperty("XSLTPath_OUT");
		XSLTPath_report			=	properties.getProperty("XSLTPath_report");
		iniPath				=	properties.getProperty("iniPath");
		port				=	Integer.parseInt(properties.getProperty("FTPPort").toString());
		
	}
	

	public static void getoutgeneratedarticledetails() throws InterruptedException {

		System.out.println("=======================================");
		System.out.println("HTMLtoXML Process Started..............");
		System.out.println("=======================================");
		getIniDetails();
		Session session=null;
		Transaction transaction=null;

		try{
			session=HibernateUtil.getSessionFactory().openSession();
			transaction=session.beginTransaction();
			String sql="SELECT * FROM article where StatusId = :StatusId LIMIT 1";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Article.class);
			query.setParameter("StatusId", 4);
			//query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			@SuppressWarnings("unchecked")
			List<Article> obj=(List<Article>)query.list();
			
			if(obj.size() != 0){
				for(Article obj1 :obj) {
					if(obj1.getStatusId() == 4){
						System.out.println("JID::"+obj1.getJid() +"\nAID::"+ obj1.getAid());
						copyDirectory1(obj1.getJid(), obj1.getAid());
						converthtmltoxml(obj1.getArticleKey(), obj1.getJid(), obj1.getAid());
					}
				}
			}else{
				System.out.println("NO files in Queue.....");
			}
		}catch(Exception e){
			System.out.println("Exception for getoutgeneratedarticledetails ---->"+ e);
		} finally{
			transaction.commit();
			session.clear();
			session.close();			
		}
		
		System.out.println("=======================================");
		System.out.println("HTMLtoXML Process Closed..............");
		System.out.println("=======================================\n\n\n");
		Thread.sleep(7000);
		htmltoxml.getoutgeneratedarticledetails();
	}

	
	private static void converthtmltoxml(int articlekey, String jid, String aid){
		System.out.println("Current running file.....\n");
		System.out.println(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+".html\n");
		Source xmlInput = new StreamSource(new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+".html"));
		Source xsl = new StreamSource(new File(XSLTPath));
		Result xmlOutput = new StreamResult(new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+"_out.xml"));

		try {
			System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
		    Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
		    transformer.transform(xmlInput, xmlOutput);
		    
		    System.out.println("Converted......."+articlekey);
		    outxmlstatus("success", articlekey, jid, aid);
		    reportgeneration(articlekey, jid, aid);
		} catch (TransformerException e) {
			System.out.println("<==== Exception Out Start ====>\n\n"+ e);
			outxmlstatus("failure", articlekey, jid, aid);
			reportgeneration(articlekey, jid, aid);
			System.out.println("<==== Exception Out END /====>\n\n"+ e);
		}
		
	}
	
	public static void reportgeneration(int articlekey, String jid, String aid){
		System.out.println("Current running file.....\n");
		System.out.println(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+".html\n");
		Source xmlInput = new StreamSource(new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+".html"));
		Source xsl = new StreamSource(new File(XSLTPath_report));
		Result xmlOutput = new StreamResult(new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/"+jid+aid+"_report.html"));

		try {
			System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
		    Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
		    transformer.transform(xmlInput, xmlOutput);
		    System.out.println("Converted......."+articlekey);   
		} catch (TransformerException e) {
			System.out.println("<==== Exception report generation /====>\n\n"+ e);
		}
		
		
	}
	private static void outxmlstatus(String status, int articlekey, String jid, String aid) {

		Session session=null;
		Transaction transaction=null;
		
		try{
			session=HibernateUtil.getSessionFactory().openSession();
			transaction=session.beginTransaction();
			String sql="UPDATE article SET StatusId=7, OUTXMLStatus = :status WHERE StatusId=4 AND ArticleKey=:articlekey";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Article.class);
			query.setParameter("status", status);
			query.setParameter("articlekey", articlekey);
			int rowCount = query.executeUpdate();
			transaction.commit();
			if(rowCount == 1){
				System.out.println("outxml status ==>" + status);
				String srcFolder = proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out/";
				String destZipFile = proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out.zip";
				String tofolder = FTPoutdir +jid+aid+"_out.zip";
				FolderZiper.zipFolder(srcFolder, destZipFile);
				FTPUploadFile ftpupload = new FTPUploadFile();
				ftpupload.Upload(destZipFile, tofolder);
				System.out.println("out xml completed..............");
			}
			
		} catch(Exception e) {
			System.out.println("Exception outxmlstatus ----->"+ e);
		} finally {
			session.clear();
			session.close();			
		}
	}
	
	
	private static void copyDirectory1(String jid, String aid) throws IOException{
		File dest = new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+"_out");
		if(!dest.exists()){
			dest.mkdir();
		}
		
        File source = new File(proofs_uploadPath + jid+"/"+aid+"/"+jid+aid+"/"+jid+aid+".html");
        File dest1 = new File(dest+"/"+jid+aid+".html");
		copyFileUsingFileStreams(source, dest1);
	}
	
	private static void copyFileUsingFileStreams(File source, File dest)
	          throws IOException {
	      InputStream input = null;
	      OutputStream output = null;
	      try {
	          input = new FileInputStream(source);
	          output = new FileOutputStream(dest);
	          byte[] buf = new byte[1024];
	          int bytesRead;
	          while ((bytesRead = input.read(buf)) > 0) {
	              output.write(buf, 0, bytesRead);
	          }
	      } finally {
	          input.close();
	          output.close();
	      }
	  }
	
	}
	
	

