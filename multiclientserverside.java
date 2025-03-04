import java.net.*;
import java.io.*;
import java.util.*;

public class multiclientserverside {
    public static int counter = 0;
    public static ServerSocket ss ;
    public static void main(String[] args) throws Exception
    {
        System.out.println("Server is running");
        ServerSocket ss = new ServerSocket(8080);
        while(true)
        {
            Socket s = ss.accept();
            System.out.println("New client connected: "+s);
            counter++;
            System.out.println("Total clients connected: "+counter);

            new ClientHandler(s).start();

        }
        
    }
    public static class ClientHandler extends Thread
    {
        private Socket s;
        private BufferedReader bf;
        private PrintStream ps;

        public ClientHandler(Socket s)
        {
            this.s = s;
        }
        public void run()
        {
            try
            {
                bf  =new BufferedReader(new InputStreamReader(s.getInputStream()));
                ps = new PrintStream(s.getOutputStream());
                String str;

                while((str=bf.readLine())!=null)
                {
                    if(str.equalsIgnoreCase("exit"))
                    {
                        System.out.println("Client closed the connection "+s);
                        counter--;
                        System.out.println("Active clients: "+counter);
                        break;
                    }
                    System.out.println("Client ["+s+"]: "+str);
                    ps.println("Server recieved: "+str);

                }
                bf.close();
                ps.close();
                s.close();

                if(counter==0)
                {
                    System.out.println("No active clients...closing server");
                    ss.close(); //close the server
                    System.exit(0);
                }
            }
            catch(Exception e)
            {
                System.out.println("An error occured" + e);
            }
        }
    }
}

