import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
//            input  = new DataInputStream(System.in);
            input = new DataInputStream((socket.getInputStream()));
            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";
        String str="";
        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
//                line = br.readLine();
//                out.writeUTF(line);
//                out.flush();
                str = input.readUTF();
                System.out.println("Server says: "+str);

            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }



    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}

