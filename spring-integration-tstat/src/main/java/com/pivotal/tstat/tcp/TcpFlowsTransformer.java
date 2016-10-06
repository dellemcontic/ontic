package com.pivotal.tstat.tcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.pivotal.tstat.ingester.MessageTransformer;

public class TcpFlowsTransformer implements MessageTransformer{
	
	private static final String MESSAGE = "MESSAGE";
	private boolean supressCommentLines = false;
	
	public boolean isSupressCommentLines() {
		return supressCommentLines;
	}

	public void setSupressCommentLines(boolean supressCommentLines) {
		this.supressCommentLines = supressCommentLines;
	}
	
	private InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() 
                     && !ia.isLoopbackAddress()
                     && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("unable to get current IP " + e.getMessage());
        }
        return null;
    }

	@Override
	public Message<byte[]> transform(Message<?> message) throws TstatException {
				
		if (message.getPayload() instanceof File){
			// Open the file
			FileInputStream fs = null; 
			InputStreamReader is = null;
			BufferedReader br = null;
			File file= (File)message.getPayload();

			try{
				fs = new FileInputStream(file);
				is = new InputStreamReader(fs);
				br = new BufferedReader(is);
				String strLine;
				StringBuilder sb = new StringBuilder();
				
				//Read File Line By Line
				while ((strLine = br.readLine()) != null)  
				{
					 if(!(strLine.startsWith("#") && supressCommentLines))
					 {					 
						 sb.append(strLine);
						 sb.append(System.lineSeparator());
					 }
				}
				
				File parent_file= file.getParentFile();
				
				String parent_name=(parent_file.getName());
				parent_name=parent_name.substring(0,parent_name.indexOf(".out"));
			    
				String ip =getCurrentIp().toString();
				if (ip.startsWith("/"))
				{
					ip=ip.substring(1);
				}
				
			
				Message<byte[]> message1= MessageBuilder.withPayload(sb.toString().getBytes())
				.setHeader("TSTAT_TIME", parent_name)
				.setHeader("TSTAT_IP", ip)
				.build();
				
				
               
              	return message1;
				
			}catch(IOException e){
				throw new TstatException("Error reading the file");
			}finally{
				if (br != null) { try { br.close(); } catch(IOException t) { } }
				if (is != null) { try { is.close(); } catch(IOException t) { } }
				if (fs != null) { try { fs.close(); } catch(IOException t) { } }
			}
		}
		else{
			throw new TstatException("Invalid message format from Syslog, expected payload type of File");
		}
	}
}
