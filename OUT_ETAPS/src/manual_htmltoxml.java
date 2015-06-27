import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.metamodel.domain.Superclass;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class parentclass {
	
	parentclass(){
		System.out.println("\n\nsuper class.........\n\n");
	}
}

public class manual_htmltoxml extends parentclass {

	public manual_htmltoxml() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public static void main(String args[]){
			System.out.println("Current running file.....\n");
//			Source xmlInput = new StreamSource(new File("/trans/ETAPS/mahi/epl17099.html"));
//			Source xsl = new StreamSource(new File("/trans/ETAPS/mahi/transform_rp.xsl"));
//			Result xmlOutput = new StreamResult(new File("/trans/ETAPS/mahi/epl17099_report.html"));
			try {
//				System.setProperty("javax.xml.transform.TransformerFactory","net.sf.saxon.TransformerFactoryImpl");
//			    Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
//			    transformer.transform(xmlInput, xmlOutput);

				String command = "java -cp /home/likewise-open/TRANS/tec0157/workspace/OUT_ETAPS/lib/saxon9.jar"
						+ " net.sf.saxon.Transform -o "
						+ "/trans/ETAPS/mahi/epl17099_report.html "
						+ "/trans/ETAPS/mahi/epl17099.html "
						+ "/trans/ETAPS/mahi/transform_rp.xsl";

				System.out.println("command" + command + "\n\n\n\n");
				
			    manual_htmltoxml obj = new manual_htmltoxml();
			    String output = obj.executeCommand(command);
			    
				System.out.println("Completed.....\n"+ output);			    
			//} catch (TransformerException e) {
			} catch (Exception e) {
				System.out.println("Exception Out Start ====\n\n"+ e);
			}
			
		}
	
	private String executeCommand(String command) {
		 
		StringBuffer output = new StringBuffer();
 
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
 			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output.toString();
 
	}

	
}
