package ese1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoServerClientAd {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 8189);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			Thread t = new MyReader(in);
			t.start();
			Scanner sc = new Scanner(System.in);

			while (true) {
				String line = sc.nextLine();
				if (line.equals("EXIT")) {
					out.println("BYE");
					break;
				}
				out.println(line);
			}

		} catch (IOException e) {
			System.out.println("Error" + e);
		}
	}
}



