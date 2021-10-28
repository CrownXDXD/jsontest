import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
//    private DataInputStream din       =  null;

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

//             takes input from the client socket
//            in = new DataInputStream(
//                    new BufferedInputStream(socket.getInputStream()));
//            DataInputStream din=new DataInputStream(socket.getInputStream());
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));


            String line = "";
            String str="";

            File message_file = new File("message2.txt");
            Scanner scan_m = new Scanner(message_file);
            StringBuilder message_1 = new StringBuilder();
            while (scan_m.hasNextLine()) {
                message_1.append(scan_m.nextLine());
            }
            String kkk = message_1.toString();


            JsonObject jobj = new Gson().fromJson(kkk, JsonObject.class);

            String result = jobj.get("IP").toString().replaceAll("^\"|\"$", "");
            String result2 = jobj.get("Date").toString().replaceAll("^\"|\"$", "");
            String result3 = jobj.get("Port").toString().replaceAll("^\"|\"$", "");
            String result4 = jobj.get("Cache-Control").toString().replaceAll("^\"|\"$", "");


            try {
                File txt = new File("ip.txt");
                Scanner scan = new Scanner(txt);
                ArrayList<String> data = new ArrayList<String>();
                while (scan.hasNextLine()) {
                    data.add(scan.nextLine());
                }

                boolean safe = true;
                for (int i = 0; i < data.size(); i++) {

                    if (result.equals(data.get(i))) {
                        safe = false;

                    }

                }
                if (safe) {
                    dout.writeUTF("It is a safe ip.");
                    dout.writeUTF("The IP packet is: "+kkk);
                    dout.writeUTF("These are the more important messages: ");
                    dout.writeUTF("Ip: "+result);
                    dout.writeUTF("Date: "+result2);
                    dout.writeUTF("Port: "+result3);
                    dout.writeUTF("Cache-Control: "+result4);
                    dout.flush();
                } else {
                    dout.writeUTF("This IP has potential dangers that could compromise our system.Therefore,it is a not safe IP.");
                    dout.flush();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
//                    line = din.readUTF();
//                    System.out.println("client says: "+line);

                    str=br.readLine();
                    dout.writeUTF(str);
                    dout.flush();
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection

            dout.close();
            socket.close();
            server.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}
