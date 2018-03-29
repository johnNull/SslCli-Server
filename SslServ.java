import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.Charset;
import java.math.BigInteger;

public class SslServ{
	public static void main(String []args){
		System.setProperty("javax.net.ssl.keyStore", "myCertificate");
		System.setProperty("javax.net.ssl.keyStorePassword", "cs45801");
		SSLServerSocketFactory sslfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try{
			SSLServerSocket sslsocket = (SSLServerSocket) sslfactory.createServerSocket(Integer.parseInt(args[0]));
			Socket socket;
			String line;
			String hashpass;
			while(true){
			socket = sslsocket.accept();
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if((line = in.readLine()) != null){
					String user = line;
					if(userExists(line)){
						line = in.readLine();
							hashpass = computeHash(line);
							System.out.println(user + " " + line + " " + hashpass);
						if(passwordCorrect(user, hashpass)){
							out.println("OK");
						}
						else{
							out.println("incorrect login info");
						}
					}
					else{
						line = in.readLine();
						System.out.println(user + " " + line + " " + computeHash(line));
						out.println("incorrect login info");
					}
				}	
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	public static boolean userExists(String user){
		try{
			BufferedReader file = new BufferedReader(new FileReader( new File("pass.txt")));
			String line;
			while((line =file.readLine()) != null){
				if(line.contains(user)){
					file.close();
					return true;
				}
			}
			file.close();
			return false;
		}
		catch(IOException e){
			return false;
		}
	}

	public static String computeHash(String pass){
		try{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(pass.getBytes(Charset.forName("UTF8")));
			return String.format("%032x", new BigInteger(1, m.digest()));
		}
		catch(NoSuchAlgorithmException e){
			return null;
		}
	}

	//Yes, I know this is bad because code repetition
	public static boolean passwordCorrect(String user, String pass){
		try{
			BufferedReader file = new BufferedReader(new FileReader( new File("pass.txt")));
			String line;
			while((line = file.readLine()) != null){
				if(line.contains(user) && line.contains(pass)){
					file.close();
					return true;
				}
			}
			file.close();
			return false;
		}
		catch(IOException e){
			return false;
		}	
	}
}
