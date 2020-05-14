package Controllers;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import Models.User;
import Views.Server;


/**
 *
 * @author natan
 */
public class ChatServerController extends Thread{
    
    private static User user = new User();
    private Socket client;
    private BufferedReader reader;
    private static ServerSocket server; 
    private static ArrayList<BufferedWriter>clients;           
    
    public ChatServerController(Socket client){
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
               e.printStackTrace();
        }                          
    }
    public void receivedMessages(BufferedWriter writerOut, String message) throws  IOException 
    {
      BufferedWriter bw;

      for(BufferedWriter writer : clients){
       bw = (BufferedWriter)writer;
       //pode 
       if(writerOut != bw){
         writer.write(user.getUserName() + " : " + message+"\r\n");
         writer.flush(); 
       }
      }          
    }
    @Override
    public void run(){                 
    try{
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream())); 
      clients.add(writer);
      String message;
      String name = message = reader.readLine();
      user.setUserName(name);
      
      while(message != null && !"Exit".equalsIgnoreCase(message))
        {           
         message = reader.readLine();
         receivedMessages(writer, message);
         System.out.println(message);                                              
         }
     }catch (Exception e) {
       e.printStackTrace();
     }                       
    }
    
    public static void main(String []args) {
      Server server = new Server();
      server.setVisible(true);
     }                    
    }
