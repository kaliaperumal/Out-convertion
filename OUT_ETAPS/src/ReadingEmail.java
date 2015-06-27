import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReadingEmail {
    public static void main(String[] args) throws IOException, InterruptedException {
    	Process proc = Runtime.getRuntime().exec("java -cp /home/likewise-open/TRANS/tec0157/workspace/OUT_ETAPS/lib/saxon9.jar"
				+ " net.sf.saxon.Transform -o "
				+ "/trans/ETAPS/mahi/epl17099_report.html "
				+ "/trans/ETAPS/mahi/epl17099.html "
				+ "/trans/ETAPS/mahi/transform_rp.xsl");
    	proc.waitFor();
    	if(proc.exitValue() == 0){
    		System.out.println("Success.....");
    	}else{
    		System.out.println("\n\nIn::"+proc.getInputStream());
    		System.out.println("\n\nOut::"+proc.getOutputStream());
    		System.out.println("\n\nError::" + proc.getErrorStream().toString());
    	}

    }
}
