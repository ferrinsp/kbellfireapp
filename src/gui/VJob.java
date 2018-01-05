/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ferrinsp
 */
public class VJob extends javax.swing.JFrame {

    /**
     * Creates new form VJob
     */
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
        
    public void getJobs()    {
        try {
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
        stateObj = connObj.createStatement();
        resultObj = stateObj.executeQuery("Select name, address,city,state,zip,bidamount,status from job");
        viewJob.setModel(DbUtils.resultSetToTableModel(resultObj));
        viewJob.getColumn("name").setHeaderValue("Job Name");
        viewJob.getColumn("address").setHeaderValue("Address");
        viewJob.getColumn("city").setHeaderValue("City");
        viewJob.getColumn("state").setHeaderValue("State");
        viewJob.getColumn("zip").setHeaderValue("Postal Code");
        viewJob.getColumn("bidamount").setHeaderValue("Bid Amount");
        viewJob.getColumn("status").setHeaderValue("Status");
        viewJob.repaint();
        //meta = resultObj.getMetaData();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public VJob() {
        initComponents();
        getJobs();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TabbedView = new javax.swing.JTabbedPane();
        view_job = new javax.swing.JScrollPane();
        viewJob = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        TabbedView.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        viewJob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Job Name", "Address", "City", "State", "Postal Code", "Bid Amount", "Status", "Comments"
            }
        ));
        view_job.setViewportView(viewJob);

        TabbedView.addTab("View Job", view_job);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedView, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedView, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 751, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VJob().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabbedView;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable viewJob;
    private javax.swing.JScrollPane view_job;
    // End of variables declaration//GEN-END:variables
}
