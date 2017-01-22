
package bankserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Mainframe extends javax.swing.JFrame { 
    
    public static String accountno;
    public static String type;
    
    public Mainframe() {
        initComponents();
        idButton1.setEnabled(false);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JTextField();
        idButton = new javax.swing.JButton();
        nameButton = new javax.swing.JButton();
        showAll = new javax.swing.JButton();
        idButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        users = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        history = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        historyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server Panel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        idButton.setText("Search By ID");
        idButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idButtonActionPerformed(evt);
            }
        });

        nameButton.setText("Clear");
        nameButton.setActionCommand("");
        nameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameButtonActionPerformed(evt);
            }
        });

        showAll.setText("Show All Users");
        showAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllActionPerformed(evt);
            }
        });

        idButton1.setText("Show Details");
        idButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(idButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addComponent(nameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(idButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(showAll, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        );

        users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        users.setCellSelectionEnabled(true);
        users.getTableHeader().setResizingAllowed(false);
        users.getTableHeader().setReorderingAllowed(false);
        users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(users);
        users.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Users");

        history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "TID", "AMOUNT", "TRANSATION TYPE", "TRANSFERED TO", "ACCOUNT TYPE", "DATE", "TIME", "JUSERNAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(history);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("History");

        historyButton.setText("Show History");
        historyButton.setActionCommand("");
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(historyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 165, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(historyButton, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
             idButton1.setEnabled(false);
             id.setText("");
             DefaultTableModel dmod=(DefaultTableModel)history.getModel();
             while(dmod.getRowCount()>0)
             {
                for(int j=0;j<dmod.getRowCount();j++)
                  dmod.removeRow(j);
             }
             dmod.setRowCount(20);
             DefaultTableModel d=(DefaultTableModel)users.getModel();
             while(d.getRowCount()>0)
             {
                for(int j=0;j<d.getRowCount();j++)
                  d.removeRow(j);
             }
             d.setRowCount(20);
             try
             {
                 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
             PreparedStatement pr=con.prepareStatement("select*from [Transaction] order by TransactionId");
             ResultSet res=pr.executeQuery();
             int i=0;
             while(res.next())
             {
                 history.setValueAt(res.getString(6), i, 0);
                 history.setValueAt(res.getString(2), i, 1);
                 history.setValueAt(res.getString(3), i, 2);
                 history.setValueAt(res.getString(4), i, 3);
                 history.setValueAt(res.getString(5), i, 4);
                 history.setValueAt(res.getString(7), i, 5);
                 history.setValueAt(res.getString(8), i, 6);
                 history.setValueAt(res.getString(9), i, 7);
                 i++;
             }
             }
             catch(Exception e)
             {
                 
             }
             
    }//GEN-LAST:event_historyButtonActionPerformed

    private void idButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idButtonActionPerformed
        if(id.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Enter ID");
        }
        else
        {
                
            DefaultTableModel dmod=(DefaultTableModel)history.getModel();
            while(dmod.getRowCount()>0)
            {
              for(int j=0;j<dmod.getRowCount();j++)
              dmod.removeRow(j);
            }
            dmod.setRowCount(20);
            DefaultTableModel d=(DefaultTableModel)users.getModel();
            while(d.getRowCount()>0)
            {
              for(int j=0;j<d.getRowCount();j++)
              d.removeRow(j);
            }
            d.setRowCount(20);
            try 
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                PreparedStatement pr=con.prepareStatement("select*from [User] where AccountNo='"+id.getText().toString()+"'");
                ResultSet res=pr.executeQuery();
                if(res.next())
                {
                    users.setValueAt(res.getString(1), 0, 0);
                    users.setValueAt(res.getString(2), 0, 1);
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection conn =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                    PreparedStatement prr=conn.prepareStatement("select*from [Transaction] where AccountNo='"+id.getText().toString()+"'order by TransactionId");
                    ResultSet ress=prr.executeQuery();
                    int i=0;
                    while(ress.next())
                    {
                        history.setValueAt(ress.getString(6), i, 0);
                        history.setValueAt(ress.getString(2), i, 1);
                        history.setValueAt(ress.getString(3), i, 2);
                        history.setValueAt(ress.getString(4), i, 3);
                        history.setValueAt(ress.getString(5), i, 4);
                        history.setValueAt(ress.getString(7), i, 5);
                        history.setValueAt(ress.getString(8), i, 6);
                        history.setValueAt(ress.getString(9), i, 7);
                        i++;
                    }
                    
                    Connection c1 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
                    PreparedStatement p1=c1.prepareStatement("select*from User_Info where AccountNo='"+id.getText().toString()+"'");
                    ResultSet r1=p1.executeQuery();
                    if(r1.next())
                    {
                        type=r1.getString("AccountType");
                    }
                    idButton1.setEnabled(true);
                    accountno=id.getText().toString();
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Not Found");
                    id.setText("");
                }
             
             }
            catch(Exception e)
            {
            }
        }
    }//GEN-LAST:event_idButtonActionPerformed

    private void showAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllActionPerformed
        try
        {
            DefaultTableModel dmod=(DefaultTableModel)history.getModel();
            while(dmod.getRowCount()>0)
            {
              for(int j=0;j<dmod.getRowCount();j++)
              dmod.removeRow(j);
            }
            dmod.setRowCount(20);
            DefaultTableModel d=(DefaultTableModel)users.getModel();
            while(d.getRowCount()>0)
            {
              for(int j=0;j<d.getRowCount();j++)
              d.removeRow(j);
            }
            d.setRowCount(20);
            idButton1.setEnabled(false);
            id.setText("");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
             PreparedStatement pr=con.prepareStatement("select*from [User] order by AccountNo");
             ResultSet res=pr.executeQuery();
             int i=0;
             while(res.next())
             {
                 users.setValueAt(res.getString(1), i, 0);
                 users.setValueAt(res.getString(2), i, 1);
                 i++;
             }
        }
        catch(Exception e)
        {
            
        }
    }//GEN-LAST:event_showAllActionPerformed

    private void nameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameButtonActionPerformed
        DefaultTableModel dmod=(DefaultTableModel)history.getModel();
            while(dmod.getRowCount()>0)
            {
              for(int j=0;j<dmod.getRowCount();j++)
              dmod.removeRow(j);
            }
            dmod.setRowCount(20);
            DefaultTableModel d=(DefaultTableModel)users.getModel();
            while(d.getRowCount()>0)
            {
              for(int j=0;j<d.getRowCount();j++)
              d.removeRow(j);
            }
            d.setRowCount(20);
            idButton1.setEnabled(false);
    }//GEN-LAST:event_nameButtonActionPerformed

    private void usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersMouseClicked
        DefaultTableModel dmod=(DefaultTableModel)history.getModel();
                    while(dmod.getRowCount()>0)
                    {
                        for(int j=0;j<dmod.getRowCount();j++)
                            dmod.removeRow(j);
                    }
        dmod.setRowCount(20);
        try
        {
            int row=users.rowAtPoint(evt.getPoint());
            int col= 0;
            accountno=users.getValueAt(row, col).toString();
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
             PreparedStatement pr=con.prepareStatement("select*from [Transaction] where AccountNo='"+accountno+"'order by TransactionId");
             ResultSet res=pr.executeQuery();
             int i=0;
             while(res.next())
             {
                 history.setValueAt(res.getString(6), i, 0);
                 history.setValueAt(res.getString(2), i, 1);
                 history.setValueAt(res.getString(3), i, 2);
                 history.setValueAt(res.getString(4), i, 3);
                 history.setValueAt(res.getString(5), i, 4);
                 history.setValueAt(res.getString(7), i, 5);
                 history.setValueAt(res.getString(8), i, 6);
                 history.setValueAt(res.getString(9), i, 7);
                 i++;
             }
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection c1 =DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=BankDB;user=sa;password=123456789;");
             PreparedStatement p1=c1.prepareStatement("select*from User_Info where AccountNo='"+accountno+"'");
             ResultSet r1=p1.executeQuery();
             if(r1.next())
             {
                 type=r1.getString("AccountType");
             }
             idButton1.setEnabled(true);
            
        }
        catch(Exception e)
        {
            idButton1.setEnabled(false);
        }
    }//GEN-LAST:event_usersMouseClicked

    private void idButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idButton1ActionPerformed
        if(type.equals("Saving"))
        {
            ShowSaving s=new ShowSaving(accountno);
            s.setVisible(true);
            this.setVisible(false);
        }
        else
        {
            ShowJoint s=new ShowJoint(accountno);
            s.setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_idButton1ActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed
    
    
    public static void main(String args[])throws Exception {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainframe().setVisible(true);
            }
        });
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable history;
    private javax.swing.JButton historyButton;
    private javax.swing.JTextField id;
    private javax.swing.JButton idButton;
    private javax.swing.JButton idButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton nameButton;
    private javax.swing.JButton showAll;
    private javax.swing.JTable users;
    // End of variables declaration//GEN-END:variables
}

