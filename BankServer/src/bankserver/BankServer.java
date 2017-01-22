/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Pial
 */
public class BankServer {

    public static DataOutputStream outToClient[] = new DataOutputStream[100];
    public static BufferedReader inFromClient[] = new BufferedReader[100];
    public static ServerSocket welcomeSocket;
    public static Socket connectionSocket[] = new Socket[100];
    public static int i;
    public static void main(String[] args) throws Exception{
        FirstFrame f=new FirstFrame();
        f.setVisible(true);
        welcomeSocket = new ServerSocket(6782);
        System.out.println(welcomeSocket.isClosed());
        
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
            PreparedStatement pr=con.prepareStatement("select*from [User] order by AccountNo");
            ResultSet res=pr.executeQuery();
            while(res.next())
            {
                String acc=res.getString("AccountNo");
                new IThread(acc).start();
            }
        }
        catch(Exception e)
        {
            
        }
        
        for (i = 0;; i++) {
            
            System.out.println("waiting\n ");
            connectionSocket[i] = welcomeSocket.accept();
            
            inFromClient[i] =
                    new BufferedReader(new InputStreamReader(
                    connectionSocket[i].getInputStream()));
            outToClient[i] =
                    new DataOutputStream(
                    connectionSocket[i].getOutputStream());
            System.out.println("Connected: "+i);
            
            new SThread(inFromClient[i], outToClient[i], i).start();
        }
    }
    
}

class SThread extends Thread {

    BufferedReader inFromClient;
    DataOutputStream outToClient;
    String clientSentence;
    int srcid,z;
    String username;
    
    public SThread(BufferedReader in, DataOutputStream out, int a) {
        inFromClient = in;
        outToClient = out;
        srcid = a;
        
        
    }

@Override
    public void run() 
    {
        int count=0;
        while (true)
        {
            if(count==18000)
            {
                break;
            }
            else
            {
                try
                {
                    count++;
                    clientSentence = inFromClient.readLine();
                    System.out.println(clientSentence); 
                    
                    if(clientSentence.contains("Login:Saving"))
                    {
                        try
                        {
                            //format(section name,Account Type,Account No,username,password)
                            String[] arr=clientSentence.split(":");
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from User_Info where AccountType='"+arr[1]+"'and AccountNo='"+arr[2]+"'"+"and Pass1='"+arr[4]+"'and Flag=0");
                            ResultSet res=pr.executeQuery();
                            
                            if(res.next())
                            {
                                outToClient.writeBytes("Match"+'\n');
                            }
                            else
                            {
                                outToClient.writeBytes("Not Match"+'\n');
                            }
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("JointLog"))
                    {
                        try
                        {
                            //format(section name,Account Type,Account No,username,password)
                            String[] arr=clientSentence.split(":");
                            System.out.println("bankserver.SThread.run()");
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from User_Info where AccountType='"+arr[1]+"'and AccountNo='"+arr[2]+"'"+"and Pass1='"+arr[4]+"'and Name1='"+arr[3]+"'and Flag=0");
                            ResultSet res=pr.executeQuery();
                            
                            if(res.next())
                            {
                                System.out.println("Done1");
                                outToClient.writeBytes("Match"+'\n');
                                
                            }
                            else
                            {
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                PreparedStatement prr=conn.prepareStatement("select*from User_Info where AccountType='"+arr[1]+"'and AccountNo='"+arr[2]+"'"+"and Pass2='"+arr[4]+"'and Name2='"+arr[3]+"'and Flag=0");
                                ResultSet ress=prr.executeQuery();
                                if(ress.next())
                                {
                                    outToClient.writeBytes("Match"+'\n');
                                }
                                else
                                {
                                    outToClient.writeBytes("Not Match"+'\n');
                                }
                            }
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("Notification"))
                    {
                        try
                        {
                            String flag=null;
                            String FromAccount=null;
                            String amount=null;
                            String countt=null;
                            String Date=null;
                            String Time=null;
                            String user=null;
                            String SenderType=null;
                            String[] arr=clientSentence.split(":");
                            //"Notification:"+type+":"+AccountNo+":"+username;
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from Notification where ToAcc='"+arr[2]+"'and Flag=1");
                            ResultSet res=pr.executeQuery();
                            if(res.next())
                            {
                                flag="1";
                                FromAccount=res.getString("FromAcc");
                                amount=res.getString("Amount");
                                Date=res.getString("Date");
                                Time=res.getString("Time");
                                user=res.getString("username");
                                SenderType=res.getString("AccType");
                            }
                            else
                            {
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                PreparedStatement prr=conn.prepareStatement("select*from Notification where FromAcc='"+arr[2]+"'and Flag=2");
                                ResultSet ress=prr.executeQuery();
                                
                                if(ress.next())
                                {
                                    flag="2";
                                    FromAccount=ress.getString("ToAcc");
                                    amount=ress.getString("Amount");
                                    Date=ress.getString("Date");
                                    Time=ress.getString("Time");
                                    user=ress.getString("username");
                                    SenderType=ress.getString("AccType");
                                }
                                else
                                {
                                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    Connection connn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                    PreparedStatement prrr=connn.prepareStatement("select*from Notification where FromAcc='"+arr[2]+"'and Flag=3");
                                    ResultSet resss=prrr.executeQuery();
                                    if(resss.next())
                                    {
                                        flag="3";
                                        FromAccount=resss.getString("ToAcc");
                                        amount=resss.getString("Amount");
                                        Date=resss.getString("Date");
                                        Time=resss.getString("Time");
                                        user=resss.getString("username");
                                        SenderType=resss.getString("AccType");
                                    }
                                    else
                                    {
                                        flag="0";
                                        FromAccount=null;
                                        amount="0";
                                        Date=null;
                                        Time=null;
                                        user=null;
                                        SenderType=null;
                                    }
                                }
                            }
                            
                            if(flag.equals("0"))
                            {
                                countt="0";
                                String toclint=flag+";"+countt+";"+FromAccount+";"+amount+";"+Date+";"+Time+";"+user+";"+SenderType;
                                outToClient.writeBytes(toclint+'\n');
                            }
                            else if(flag.equals("1"))
                            {
                                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                    PreparedStatement prrrr=connnn.prepareStatement("select COUNT(*) as count from Notification where ToAcc='"+arr[2]+"'and Flag='"+flag+"'");
                                    ResultSet ressss=prrrr.executeQuery();
                                    if(ressss.next())
                                    {
                                        countt=ressss.getString("count");
                                    }
                                    String toclint=flag+";"+countt+";"+FromAccount+";"+amount+";"+Date+";"+Time+";"+user+";"+SenderType;
                                    outToClient.writeBytes(toclint+'\n');
                            }
                            else
                            {
                                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                    PreparedStatement prrrr=connnn.prepareStatement("select COUNT(*) as count from Notification where FromAcc='"+arr[2]+"'and Flag='"+flag+"'");
                                    ResultSet ressss=prrrr.executeQuery();
                                    if(ressss.next())
                                    {
                                        countt=ressss.getString("count");
                                    }
                                    String toclint=flag+";"+countt+";"+FromAccount+";"+amount+";"+Date+";"+Time+";"+user+";"+SenderType;
                                    outToClient.writeBytes(toclint+'\n');
                            }
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    
                    
                    else if(clientSentence.contains("Depsite"))
                    {
                        try
                        {
                            //"Depsite:"+type+":"+AccountNo+":"+amount+":"+username;
                            String[] arr=clientSentence.split(":");
                            double amount=Double.parseDouble(arr[3]);
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("update [User] set Balance=Balance+'"+amount+"' where AccountNo='"+arr[2]+"'");
                            pr.executeUpdate();
                            
                            DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                            DateFormat time = new SimpleDateFormat("h:mm:ss a");
                            Calendar cal = Calendar.getInstance();
                            String a=date.format(cal.getTime());
                            String b=time.format(cal.getTime());
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prr=conn.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,AccType,Date,Time,Username) values('"+arr[2]+"','"+amount+"','"+arr[0]+"','"+arr[1]+"','"+a+"','"+b+"','"+arr[4]+"')");
                            prr.execute();
                            
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("Withdraw"))
                    {
                        try
                        {
                            double balance=0;
                            double amount=0;
                            double result=0;
                            String[] arr=clientSentence.split(":");
                            //"Depsite:"+type+":"+AccountNo+":"+amount+":"+username;
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from [User] where AccountNo='"+arr[2]+"'");
                            ResultSet res=pr.executeQuery();
                            if(res.next())
                            {
                                balance=res.getDouble("Balance");
                                amount=Double.parseDouble(arr[3]);
                                result=balance-amount;
                            }
                            if(result<500)
                            {
                                outToClient.writeBytes("You Don't have saficent balance"+'\n');
                            }
                            else
                            {
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection co =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                PreparedStatement p=co.prepareStatement("update [User] set Balance=Balance-'"+amount+"' where AccountNo='"+arr[2]+"'");
                                p.executeUpdate();
                            
                                DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                                DateFormat time = new SimpleDateFormat("h:mm:ss a");
                                Calendar cal = Calendar.getInstance();
                                String a=date.format(cal.getTime());
                                String b=time.format(cal.getTime());
                            
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                PreparedStatement prr=conn.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,AccType,Date,Time,Username) values('"+arr[2]+"','"+amount+"','"+arr[0]+"','"+arr[1]+"','"+a+"','"+b+"','"+arr[4]+"')");
                                prr.execute();
                                outToClient.writeBytes("Done"+'\n');
                            }
                            
                            
                        }
                        catch(Exception e)
                        {
                            outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                            
                        }
                    }
                    
                    else if(clientSentence.contains("SendMoney"))
                    {
                        try
                        {
                            double balance=0;
                            double amount;
                            double result=0;
                            String[] arr=clientSentence.split(":");
                            //"SendMoney::"+type+":"+AccountNo+":"+amount+":"+ToAccount+":"+username;
                            amount=Double.parseDouble(arr[3]);
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from [User] where AccountNo='"+arr[4]+"'except select*from [User] where AccountNo='"+arr[2]+"'");
                            ResultSet res=pr.executeQuery();
                            if(res.next())
                            {
                                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                Connection co =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                PreparedStatement p=co.prepareStatement("select*from [User] where AccountNo='"+arr[2]+"'");
                                ResultSet re=p.executeQuery();
                                if(re.next())
                                {
                                    balance=re.getDouble("Balance");
                                    
                                    result=balance-amount;
                                }
                                if(result<500)
                                {
                                    outToClient.writeBytes("You Don't have saficent balance"+'\n');
                                }
                                else
                                {
                                    DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                                    DateFormat time = new SimpleDateFormat("h:mm:ss a");
                                    Calendar cal = Calendar.getInstance();
                                    String a=date.format(cal.getTime());
                                    String b=time.format(cal.getTime());
                                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                                    PreparedStatement prr=conn.prepareStatement("insert into Notification(FromAcc,ToAcc,Amount,Date,Time,username,AccType) values('"+arr[2]+"','"+arr[4]+"','"+amount+"','"+a+"','"+b+"','"+arr[5]+"','"+arr[1]+"')");
                                    prr.execute();
                                    outToClient.writeBytes("Done, Plz Wait For confirmation message"+'\n');
                                }
                                
                                
                            }
                            else
                            {
                                outToClient.writeBytes("Account number not found"+'\n');
                            }
                            
                            
                            
                        }
                        catch(Exception e)
                        {
                            outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("Accept"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(";");
                            String TransationType1="Send";
                            String TransationType2="Receive";
                            String user=null;
                            double amount;
                                
                         //"Accept;"+type+";"+AccountNo+";"+amount+";"+FromAccount+";"+username+";"+Date+";"+Time+";"+user+";"+senderType;
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,TranAccNo,AccType,Date,Time,Username) values('"+arr[4]+"','"+arr[3]+"','"+TransationType1+"','"+arr[2]+"','"+arr[9]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"')");
                            pr.execute();
                            System.out.println("Done1");
                            
                            DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                            DateFormat time = new SimpleDateFormat("h:mm:ss a");
                            Calendar cal = Calendar.getInstance();
                            String a=date.format(cal.getTime());
                            String b=time.format(cal.getTime());
                            System.out.println("Done2");
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prr=conn.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,TranAccNo,AccType,Date,Time,Username) values('"+arr[2]+"','"+arr[3]+"','"+TransationType2+"','"+arr[4]+"','"+arr[1]+"','"+a+"','"+b+"','"+arr[5]+"')");
                            prr.execute();
                            System.out.println("Done3");
                            
                            amount=Double.parseDouble(arr[3]);
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection co =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement p=co.prepareStatement("update [User] set Balance=Balance+'"+amount+"' where AccountNo='"+arr[2]+"'");
                            p.executeUpdate();
                            System.out.println("Done4");
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection connn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prrr=connn.prepareStatement("update [User] set Balance=Balance-'"+amount+"' where AccountNo='"+arr[4]+"'");
                            prrr.executeUpdate();
                            System.out.println("Done5");
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prrrr=connnn.prepareStatement("update Notification set Flag=2 where ToAcc='"+arr[2]+"'and FromAcc='"+arr[4]+"'and Date='"+arr[6]+"'and Time='"+arr[7]+"'and Flag=1");
                            prrrr.executeUpdate();
                            System.out.println("Done6");
                            
                            outToClient.writeBytes("Done"+'\n');
                            
                            
                        }
                        catch(Exception e)
                        {
                            outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                        }
                    }
                    else if(clientSentence.contains("MsgPositive"))
                    {
                        try
                        {
                            
                            String[] arr=clientSentence.split(";");
                            //"MsgAccept;"+type+";"+AccountNo+";"+FromAccount+";"+username+";"+Date+";"+Time;
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prrrr=connnn.prepareStatement("update Notification set Flag=0 where ToAcc='"+arr[3]+"'and FromAcc='"+arr[2]+"'and Date='"+arr[5]+"'and Time='"+arr[6]+"'and Flag=2");
                            prrrr.executeUpdate();
                            
                        }
                        catch(Exception e)
                        {
                            //outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("MsgNegetive"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(";");
                            //"MsgAccept;"+type+";"+AccountNo+";"+FromAccount+";"+username+";"+Date+";"+Time;
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prrrr=connnn.prepareStatement("update Notification set Flag=0 where ToAcc='"+arr[3]+"'and FromAcc='"+arr[2]+"'and Date='"+arr[5]+"'and Time='"+arr[6]+"'and Flag=3");
                            prrrr.executeUpdate();
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    else if(clientSentence.contains("Reject"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(";");
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection connnn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prrrr=connnn.prepareStatement("update Notification set Flag=3 where ToAcc='"+arr[2]+"'and FromAcc='"+arr[4]+"'and Date='"+arr[6]+"'and Time='"+arr[7]+"'and Flag=1");
                            prrrr.executeUpdate();
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("History"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(":");
                            String balance = null;
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select*from [User] where AccountNo='"+arr[2]+"'");
                            ResultSet res=pr.executeQuery();
                            
                            if(res.next())
                            {
                                balance=res.getString("Balance");
                            }
                            String[] val1 = new String[20];
                            String[] val2 = new String[20];
                            String[] val3 = new String[20];
                            String[] val4 = new String[20];
                            String[] val5 = new String[20];
                            String[] val6 = new String[20];
                            String[] val7 = new String[20];
                            String[] val8 = new String[20];
                            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prr=conn.prepareStatement("select*from [Transaction] where AccountNo='"+arr[2]+"'ORDER by TransactionId");
                            ResultSet ress=prr.executeQuery();
                            int z = 0;
                            while(ress.next())
                            {
                                val1[z]=ress.getString("TransactionId");
                                val2[z]=ress.getString("Amount");
                                val3[z]=ress.getString("TranType");
                                val4[z]=ress.getString("TranAccNo");
                                val5[z]=ress.getString("AccType");
                                val6[z]=ress.getString("Date");
                                val7[z]=ress.getString("Time");
                                val8[z]=ress.getString("Username");
                                z++;
                                
                            }
                            
                            String toclintvalue1=balance+";"+String.valueOf(z);
                            outToClient.writeBytes(toclintvalue1+'\n');
                            for(int t=0;t<z;t++)
                            {
                                String toclintvalue2=null;
                                toclintvalue2=val1[t]+";"+val2[t]+";"+val3[t]+";"+val4[t]+";"+val5[t]+";"+val6[t]+";"+val7[t]+";"+val8[t];
                                outToClient.writeBytes(toclintvalue2+'\n');
                            }
                            
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("Count"))
                    {
                        try
                        {
                            Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement pr=con.prepareStatement("select COUNT(*) as count from User_Info");
                            ResultSet res=pr.executeQuery();
                            
                            if(res.next())
                            {   
                                String c=res.getString("count");
                                System.out.println(c);
                                outToClient.writeBytes(c+'\n');
                            }
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }
                    
                    else if(clientSentence.contains("Reg;Saving"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(";");
                            //String ToServer="Reg0;Saving1;"+AccountNo2+";"+jTextField1.getText()3+";"+jTextField2.getText()4+";"+jTextField3.getText()5+";"+jTextField4.getText()6+";"+jTextField5.getText()7+";"+jTextField6.getText()8+";"+jTextArea1.getText()9+";"+pass1_10+";"+path11;
                            
                            DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                            DateFormat time = new SimpleDateFormat("h:mm:ss a");
                            Calendar cal = Calendar.getInstance();
                            String a=date.format(cal.getTime());
                            String b=time.format(cal.getTime());
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prr=conn.prepareStatement("insert into User_Info(AccountType,AccountNo,Name1,Father1,Mother1,DateOfBirth1,VoterNo1,PhoneNo1,Address1,Pass1,Img1,OpeningDate,OpeningTime,Flag) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            //'"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+arr[9]+"','"+arr[10]+"','"+arr[11]+"','"+a+"','"+b+"','"+1+"'
                            InputStream is=new FileInputStream(new File(arr[11]));
                            prr.setString(1, arr[1]);
                            prr.setString(2, arr[2]);
                            prr.setString(3, arr[3]);
                            prr.setString(4, arr[4]);
                            prr.setString(5, arr[5]);
                            prr.setString(6, arr[6]);
                            prr.setString(7, arr[7]);
                            prr.setString(8, arr[8]);
                            prr.setString(9, arr[9]);
                            prr.setString(10, arr[10]);
                            prr.setBlob(11, is);
                            prr.setString(12, a);
                            prr.setString(13, b);
                            prr.setInt(14, 0);
                            prr.execute();
                            
                            Connection c1 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement p1=c1.prepareStatement("insert into [User](AccountNo,Balance) values('"+arr[2]+"',500)");
                            p1.execute();
                            String Depsite="Depsite";
                            String Saving="Saving";
                            String Username=null;
                            Connection c2 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement p2=c2.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,AccType,Date,Time,Username) values('"+arr[2]+"','"+500+"','"+Depsite+"','"+Saving+"','"+a+"','"+b+"','"+Username+"')");
                            p2.execute();
                            outToClient.writeBytes("Done,Your Account No is "+arr[2]+'\n');
                        
                        }
                        catch(Exception e)
                        {
                            outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                        }
                    }
                    else if(clientSentence.contains("Reg;Joint"))
                    {
                        try
                        {
                            String[] arr=new String[30];
                            arr=clientSentence.split(";");
                            //String ToServer="Reg0;Saving1;"+AccountNo2+";"+jTextField1.getText()3+";"+jTextField2.getText()4+";"+jTextField3.getText()5+";"+jTextField4.getText()6+";"+jTextField5.getText()7+";"+jTextField6.getText()8+";"+jTextArea1.getText()9+";"+pass1_10+";"+path11;
                            //jTextField8.getText()12+";"+jTextField9.getText()13+";"+jTextField10.getText()14+";"+jTextField11.getText()15+";"+jTextField12.getText()16+";"+jTextField13.getText()17+";"+jTextArea2.getText()18+";"+pass2_19+";"+path2+20+";"+jTextField7.getText()21;
                            System.out.println(arr.length);
                            for(int e=0;e<arr.length;e++)
                            {
                                System.out.println(arr[e]);
                            }
//21
                            DateFormat date = new SimpleDateFormat("dd/MMMM/yyyy");
                            DateFormat time = new SimpleDateFormat("h:mm:ss a");
                            Calendar cal = Calendar.getInstance();
                            String a=date.format(cal.getTime());
                            String b=time.format(cal.getTime());
                            
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement prr=conn.prepareStatement("insert into User_Info(AccountType,AccountNo,Name1,Father1,Mother1,DateOfBirth1,VoterNo1,PhoneNo1,Address1,Pass1,Img1,OpeningDate,OpeningTime,Flag,Name2,Fathe2,Mother2,DateOfBirth2,VoterNo2,PhoneNo2,Address2,Pass2,Img2,Releation) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            //'"+arr[1]+"','"+arr[2]+"','"+arr[3]+"','"+arr[4]+"','"+arr[5]+"','"+arr[6]+"','"+arr[7]+"','"+arr[8]+"','"+arr[9]+"','"+arr[10]+"','"+arr[11]+"','"+a+"','"+b+"','"+1+"'
                            //1AccountType,2AccountNo,3Name1,4Father1,5Mother1,6DateOfBirth1,7VoterNo1,8PhoneNo1,9Address1,10Pass1,11Img1,12OpeningDate,13OpeningTime,14Flag,15Name2,16Father2,17Mother2,18DateOfBirth2,19VoterNo2,20PhoneNo2,21Address2,22Pass2,23Img2,24Releation
                            
                            InputStream is1=new FileInputStream(new File(arr[11]));
                            InputStream is2=new FileInputStream(new File(arr[20]));
                            prr.setString(1, arr[1]);
                            prr.setString(2, arr[2]);
                            prr.setString(3, arr[3]);
                            prr.setString(4, arr[4]);
                            prr.setString(5, arr[5]);
                            prr.setString(6, arr[6]);
                            prr.setString(7, arr[7]);
                            prr.setString(8, arr[8]);
                            prr.setString(9, arr[9]);
                            prr.setString(10, arr[10]);
                            prr.setBlob(11, is1);
                            prr.setString(12, a);
                            prr.setString(13, b);
                            prr.setInt(14, 0);
                            prr.setString(15, arr[12]);
                            prr.setString(16, arr[13]);
                            prr.setString(17, arr[14]);
                            prr.setString(18, arr[15]);
                            prr.setString(19, arr[16]);
                            prr.setString(20, arr[17]);
                            prr.setString(21, arr[18]);
                            prr.setString(22, arr[19]);
                            prr.setBlob(23, is2);
                            prr.setString(24, arr[21]);
                            
                            
                            prr.execute();
                            
                            Connection c1 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement p1=c1.prepareStatement("insert into [User] (AccountNo,Balance) values('"+arr[2]+"',500)");
                            p1.execute();
                            
                            String Depsite="Depsite";
                            String Joint="Joint";
                            String Username="Starting";
                            Connection c2 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                            PreparedStatement p2=c2.prepareStatement("insert into [Transaction](AccountNo,Amount,TranType,AccType,Date,Time,Username) values('"+arr[2]+"','"+500+"','"+Depsite+"','"+Joint+"','"+a+"','"+b+"','"+Username+"')");
                            p2.execute();
                            outToClient.writeBytes("Done,Your Account No is "+arr[2]+'\n');
                        
                        }
                        catch(Exception e)
                        {
                            outToClient.writeBytes(e.toString()+'\n');
                            System.err.println(e);
                        }
                    }
                    /*else if(clientSentence.contains("Notification:Saving"))
                    {
                        try
                        {
                            String[] arr=clientSentence.split(":");
                        }
                        catch(Exception e)
                        {
                            System.err.println(e);
                        }
                    }*/
                    
                }
                catch(Exception e)
                {
                    //System.err.println(e);
                }
            }
       
        }
    }
}

class IThread extends Thread {

    String AccountNo;
    
    public IThread(String a) {
        AccountNo=a;
     
    }

@Override
    public void run() 
    {
        
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMMM/yyyy");
            Calendar cal=Calendar.getInstance();
           String CurrentDate = formatter.format(cal.getTime());
           Date date2 = formatter.parse(CurrentDate);
           if(CurrentDate.contains("16/August/"))
           {
            
            double amount,interest=0;
           
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection co =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
            PreparedStatement p=co.prepareStatement("select * from [Transaction] where AccountNo='"+AccountNo+"' and (TranType='Depsite'or TranType='Receive') Order by TransactionId;");
            ResultSet re=p.executeQuery();
           
            while(re.next())
            {
              String date=re.getString(7);
              Date date1 = formatter.parse(date);
              amount=re.getDouble(2);
              long dif=date2.getTime()-date1.getTime();
              dif=dif/(1000*60*60*24);
              interest=interest+(((5*amount)/(365*100))*dif);
              
              //System.out.println(interest);
            }
           
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
            PreparedStatement prr=conn.prepareStatement("Select * FROM [Transaction] where AccountNo='"+AccountNo+"' and (TranType='Send' or TranType='Withdraw')  order by TransactionId desc;");
            ResultSet ress=prr.executeQuery();
            //System.out.println("bankserver.IThread.run()");
            double Wamount=0,Damount=0,Winterest=0,D=0;
            Date Wdate = null,Ddate;
           
            while(ress.next())
            {
               //System.out.println("withdraw");
               int tranid=ress.getInt(6);
               Wamount=ress.getDouble(2);
               String date1=ress.getString(7);
               Wdate = formatter.parse(date1);
               //System.out.println(tranid);
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
               PreparedStatement pr=con.prepareStatement("Select * FROM [Transaction] where AccountNo='"+AccountNo+"' and TransactionId<'"+tranid+"'order by TransactionId desc");
               ResultSet res=pr.executeQuery();
               while(res.next())
               {
                    Damount=res.getDouble(2);
                    String date=res.getString(7);
                    Ddate = formatter.parse(date);
                    long dif=Wdate.getTime()-Ddate.getTime();
                    dif=dif/(1000*60*60*24);
                        //System.out.println(dif);
                    if(dif<=30)
                    {
                        D=Damount;
                        if(D<Wamount)
                        {
                            long dif1=date2.getTime()-Ddate.getTime();
                            dif1=dif1/(1000*60*60*24);
                            Winterest=Winterest+(((5*D)/(365*100))*dif1);
                            Wamount=Wamount-D;
                            //System.out.println("1="+Wamount);
                                //System.out.println("and="+(((5*D)/(365*100))*dif1));
                        }
                        else if(D>=Wamount)
                        {
                            long dif1=date2.getTime()-Ddate.getTime();
                            dif1=dif1/(1000*60*60*24);
                            Winterest=Winterest+(((5*Wamount)/(365*100))*dif1);
                            //System.out.println("2="+D);
                            break;
                        }
                    }
                    else
                    {
                           
                                
                        long dif1=date2.getTime()-Wdate.getTime();
                        dif1=dif1/(1000*60*60*24);
                                
                        Winterest=Winterest+(((5*Wamount)/(365*100))*dif1);
                        //System.out.println("3="+(((5*Wamount)/(365*100))*dif1));
                        break;
                           
                            
                    }
                }
               
             }
                System.out.println(AccountNo+": "+(interest-Winterest));
            }
           else
           {
               
           }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
}



