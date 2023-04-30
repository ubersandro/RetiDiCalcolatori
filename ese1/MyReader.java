package ese1;

import java.io.BufferedReader;
import java.io.IOException;

public class MyReader extends Thread {
    BufferedReader in;

    public MyReader(BufferedReader in) {
        this.in = in;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        boolean more = true;
        try {
            while (more) {
                String line = in.readLine();
                if (line == null)
                    more = false;
                else
                    System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }

}

