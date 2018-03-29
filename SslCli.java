import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.util.Scanner;
import javax.net.ssl.SSLSocketFactory;

public class SslCli{
	public static void main(String []args){
		Scanner console = new Scanner(System.in);
		System.setProperty("javax.net.ssl.trustStore", "myCertificate");
		System.setProperty("javax.net.ssl.trustStorePassword", "cs45801");
		SSLSocketFactory sslfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try{
			Socket socket = sslfactory.createSocket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String user;
			System.out.println("Enter User ID");
			user = console.next();
			out.println(user);
			System.out.println("Enter Password");
			out.println(console.next());
			if(in.readLine().equals("OK")){
				System.out.println("the password is correct");
			}
			else
				System.out.println("the password is incorrect");
		}
		catch(IOException e){

		}
	}
}
