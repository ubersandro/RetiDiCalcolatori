package eserc1;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketTest {
    public static void main(String[] args) {
        try
        {
            String URL = "www.folino.it";

            Socket s= new Socket(URL, 80);
            System.out.println("And the socket is --> "+s);
            String path = "files\\"+args[0];
            File f = new File(path);
            if(f.exists()){
                f.renameTo(new File(path+(String.format("%d", System.currentTimeMillis())).substring(0,2)+".html"));
            }
            else {
                //System.out.println("No");

                f.createNewFile();
            }
            PrintWriter osw = new PrintWriter(s.getOutputStream(), true); /*mind flushing porcodio*/
            osw.println("GET /curriculum.php HTTP/1.1");
            osw.println("Host:" + URL);
            osw.println();
            /*lettura risultato (con timeout)*/
            s.setSoTimeout(10000);
            BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line=null;
            long startTime=System.currentTimeMillis();
            PrintWriter filePw=new PrintWriter(f);
            while(true){
                line=is.readLine();
                if(line==null) break;
                System.out.println(line);
                if(line.matches(".*301.*")) System.out.println("Redirection needed!");
                filePw.println(line);
            }
            System.out.println((System.currentTimeMillis()-startTime) / 1000 + " SECONDS TIME");
            is.close();
            filePw.close();/*chiudi il gas e vieni via --> flush*/
            s.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


}
