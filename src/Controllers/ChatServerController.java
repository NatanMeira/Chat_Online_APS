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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


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
    public ChatServerController(){
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
         writer.write(user.getName() + " : " + message+"\r\n");
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
      user.setName(name);
      
      while(message != null)
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
      try{
    //Cria os objetos necessário para instânciar o servidor
    JLabel lbMessage = new JLabel("Escolha uma porta para hospedar o servidor");
    JTextField txPort = new JTextField("");
    Object[] texts = {lbMessage, txPort };  
    JOptionPane.showMessageDialog(null, texts);
    
    server = new ServerSocket(Integer.parseInt(txPort.getText()));
    clients = new ArrayList<BufferedWriter>();
    JOptionPane.showMessageDialog(null,"Servidor hospedado na porta: "+         
    txPort.getText());
    
     while(true){
       System.out.println("Aguardando a conexão de um cliente...");
       Socket cliente = server.accept();
       System.out.println("Cliente conectado...");
       Thread t = new ChatServerController(cliente);
        t.start();   
    }
                              
    }catch (Exception e) {

      e.printStackTrace();
    }          
     }                    

  
}
