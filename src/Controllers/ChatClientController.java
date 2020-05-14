/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import Models.User;
import Views.Chat;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



/**
 *
 * @author natan
 */
public class ChatClientController{
    private static User user;

 public static void main(String []args) throws IOException{
       
    JLabel lblMessage = new JLabel("Login!");
    JLabel lblIP = new JLabel("IP:");
    JTextField txIP = new JTextField("127.0.0.1");
    txIP.setEditable(false);
    JLabel lblPort = new JLabel("Porta:");
    JTextField txPort = new JTextField("");
    JLabel lblUserName = new JLabel("Nome de Usuário:");
    JTextField txUserName = new JTextField("");                
    Object[] texts = {lblMessage,lblIP, txIP, lblPort,txPort,lblUserName, txUserName };  
    JOptionPane.showMessageDialog(null, texts);        
    
    user = new User();
    user.setName(txUserName.getText());
    Chat app = new Chat(user,Integer.parseInt(txPort.getText()));
    app.connect();
    app.receiveMessages();
}
}
