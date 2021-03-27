package URL;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {
    public static void main(String[] args) {
        InputStream is = null;
        URLConnection uc = null;
        try {
            URL url = new URL("https","it.wikipedia.org",-1,"/wiki/Pagina_principale");
            is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true){
                String line = br.readLine();
                if (line==null) break;
                System.out.println(line);
            }
            br.close();
            System.out.println("STOP");
            System.out.println();
            uc = url.openConnection();
            uc.connect();
            System.out.println(uc.getContentType());
            System.out.println(uc.getExpiration());
            System.out.println(uc.getLastModified());
            File f = new File("files//out.html");
            if(f.createNewFile()) System.out.println("New file created");;
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            InputStream is2 = uc.getInputStream();
            br=new BufferedReader(new InputStreamReader(is2));
            String line = null ;
            while(true){
                line = br.readLine();
                if(line==null) break;
                pw.println(line);
            }
            pw.close();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
