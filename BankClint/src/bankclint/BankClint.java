/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankclint;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Pial
 */
public class BankClint {

    public static DataOutputStream outToServer;
    public static InetAddress inetAddress;
    public static Socket clientSocket;
    public static BufferedReader inFromServer;
    
    public static void main(String[] args) {
        try
        {
            inetAddress = InetAddress.getLocalHost();
       
        System.out.println(inetAddress+"ok");

        clientSocket = new Socket(inetAddress, 6782);
        outToServer =
                new DataOutputStream(clientSocket.getOutputStream());

        inFromServer =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
         FirstFrame f=new FirstFrame(inFromServer,outToServer);
         f.setVisible(true);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }
    
}
