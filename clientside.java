import java.util.*;
import java.net.*;
import java.io.*;

public class clientside {
    public static void main(String[] args) throws Exception
    {
        System.out.println("Client is running");
        Socket s = new Socket("localhost", 8080);
        //Output to server
        PrintStream ps = new PrintStream(s.getOutputStream());
        //Input from server
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter a message: ");
            String str = sc.nextLine();
            ps.println(str);

            if(str.equalsIgnoreCase("exit"))
            {
                break;
            }

            String response = bf.readLine();
            System.out.println(response);
        };
        sc.close();
        bf.close();
        ps.close();
        s.close();
    }
}
