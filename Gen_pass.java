import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.Charset;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gen_pass{
	public static void main(String args[]){
		Scanner console = new Scanner(System.in);
		BufferedReader reader = null;
		boolean inUse = false;
		System.out.println("Input a user ID");
		String userID = console.next();
		String pass;

		try{
			reader = new BufferedReader(new FileReader(new File("pass.txt")));
			String line;
			while((line = reader.readLine()) != null){
				if(line.contains(userID)){
					inUse = true;
					break;
				}
			}
			reader.close();
			if(inUse){
				System.out.println("UserID already in use");
			}
			else{
				System.out.println("Input a password");
				pass = console.next();
				writeToFile(userID, pass);	
			}
		}
		catch(IOException e){
			System.out.println("Input a password");
			pass = console.next();
			writeToFile(userID, pass);
		}
	}

	public static void writeToFile(String user, String pass){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("pass.txt"), true));
			String hashText;
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(pass.getBytes(Charset.forName("UTF8")));
			String result = String.format("%032x", new BigInteger(1, m.digest()));
			String timestamp = new SimpleDateFormat("yyyy,MM,dd_HH:mm:ss").format(new Date());
			writer.write(user + " " + result + " " + timestamp + "\n");
			writer.close();
		}
		catch(IOException e){

		}
		catch(NoSuchAlgorithmException e){

		}
	}
}
