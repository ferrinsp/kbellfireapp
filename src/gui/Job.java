/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;


public class Job extends javax.swing.JFrame {
    
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String name;
    int id;

    public Job(String name) {
        this.name=name;
        initComponents();
        getJob();
    }
        
    private void getJob() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select name,address,city,state,zip,bidamount,status,comments,jobid from job where name like '%"+EscapeCharacter.escape(name)+"%';");
            while (resultObj.next()){
                jobName.setText(resultObj.getString("name"));
                address.setText(resultObj.getString("address"));
                city.setText(resultObj.getString("city"));
                state.setText(resultObj.getString("state"));
                postalCode.setText(resultObj.getString("zip"));
                bidAmount.setText(resultObj.getString("bidamount"));
                id = resultObj.getInt("jobid");
                System.out.println(id);
                String selected = resultObj.getString("status");
                if(selected.equals("Active")) {
                    rdbActive.setSelected(true);
                } else if (selected.equals("Inactive")) {
                    rdbInactive.setSelected(true);
                }
                jobComments.setText(resultObj.getString("comments"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insertJob()    {
        try {
            if(jobName.getText().equals("Job Name")||address.getText().equals("Address")||city.getText().equals("City")
                    ||state.getText().equals("State")||postalCode.getText().equals("Postal Code"))
                JOptionPane.showMessageDialog(null, "Please fill in necessary fields.");
            else {
                if (name.equals("Add")) {
                //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
                connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
                String query = "INSERT into job (name,address,city,state,zip,bidamount,status,comments)"
                        + "values(?,?,?,?,?,?,?,?)";
                String status =null;
                if (rdbActive.isSelected())
                    status ="Active";
                else if (rdbInactive.isSelected())
                    status = "Inactive";
                //Get Values to insert
                PreparedStatement preparedStmt =connObj.prepareStatement(query);
                preparedStmt.setString (1, jobName.getText());
                preparedStmt.setString (2, address.getText());
                preparedStmt.setString (3, city.getText());
                preparedStmt.setString (4, state.getText());
                preparedStmt.setString (5, postalCode.getText());
                preparedStmt.setInt (6, Integer.parseInt(bidAmount.getText()));
                preparedStmt.setString (7, status);
                preparedStmt.setString (8, jobComments.getText());
                preparedStmt.execute();
                //Close Connection
                connObj.close();
                JOptionPane.showMessageDialog(null, "Job created for "+jobName.getText()+".");
            }
            else
                updateJob();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateJob(){
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
            String query = "Update job set name =?, address=?,city=?,state=?,zip=?,bidamount=?,status=?, comments=? where jobid = "+id+" and name like '%"+EscapeCharacter.escape(name)+"%';";
            //Get Values to insert
            PreparedStatement preparedStmt =connObj.prepareStatement(query);
            preparedStmt.setString (1, jobName.getText());
            preparedStmt.setString (2, address.getText());
            preparedStmt.setString (3, city.getText());
            preparedStmt.setString (4, state.getText());
            preparedStmt.setString (5, postalCode.getText());
            preparedStmt.setInt (6, Integer.parseInt(bidAmount.getText()));
            String jobStatus= null;
            for (Enumeration<AbstractButton> buttons = statusGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    jobStatus = button.getText();
                }
            }
            preparedStmt.setString (7,jobStatus );
            preparedStmt.setString (8, jobComments.getText());
            preparedStmt.executeUpdate();
      
            connObj.close();
            
            } catch (SQLException e) {
                e.printStackTrace();
            }
                JOptionPane.showMessageDialog(null, "Job "+name+" was updated.");
        this.name = "";
            this.dispose();
    }
    public Job() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        new_vendor_panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        city = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        postalCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jobName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        bidAmount = new javax.swing.JTextField();
        rdbActive = new javax.swing.JRadioButton();
        rdbInactive = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jobComments = new javax.swing.JTextArea();
        save_cancel_buttonpanel = new javax.swing.JPanel();
        saveJob = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Job");

        jLabel3.setText("Address");

        address.setText("Address");
        address.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addressFocusLost(evt);
            }
        });

        jLabel4.setText("City");

        city.setText("City");
        city.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cityFocusLost(evt);
            }
        });

        jLabel5.setText("State");

        state.setText("State");
        state.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stateFocusLost(evt);
            }
        });

        jLabel6.setText("Postal Code");

        postalCode.setText("Postal Code");
        postalCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                postalCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                postalCodeFocusLost(evt);
            }
        });

        jLabel7.setText("Job Name");

        jobName.setText("Job Name");
        jobName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jobNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jobNameFocusLost(evt);
            }
        });

        jLabel8.setText("Bid Amount");

        jLabel9.setText("Status");

        bidAmount.setText("0");
        bidAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bidAmountFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bidAmountFocusLost(evt);
            }
        });

        statusGroup.add(rdbActive);
        rdbActive.setSelected(true);
        rdbActive.setText("Active");

        statusGroup.add(rdbInactive);
        rdbInactive.setText("Inactive");

        jLabel11.setText("Comments");

        jobComments.setColumns(20);
        jobComments.setRows(5);
        jScrollPane1.setViewportView(jobComments);

        javax.swing.GroupLayout new_vendor_panelLayout = new javax.swing.GroupLayout(new_vendor_panel);
        new_vendor_panel.setLayout(new_vendor_panelLayout);
        new_vendor_panelLayout.setHorizontalGroup(
            new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(new_vendor_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jobName)
                    .addComponent(address)
                    .addComponent(city)
                    .addComponent(state)
                    .addComponent(postalCode)
                    .addComponent(bidAmount)
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(rdbActive)
                        .addGap(18, 18, 18)
                        .addComponent(rdbInactive))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        new_vendor_panelLayout.setVerticalGroup(
            new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, new_vendor_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(postalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(bidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbActive)
                    .addComponent(rdbInactive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        saveJob.setText("Save");
        saveJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJobActionPerformed(evt);
            }
        });

        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout save_cancel_buttonpanelLayout = new javax.swing.GroupLayout(save_cancel_buttonpanel);
        save_cancel_buttonpanel.setLayout(save_cancel_buttonpanelLayout);
        save_cancel_buttonpanelLayout.setHorizontalGroup(
            save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, save_cancel_buttonpanelLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(saveJob, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Cancel)
                .addGap(94, 94, 94))
        );
        save_cancel_buttonpanelLayout.setVerticalGroup(
            save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(save_cancel_buttonpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cancel)
                    .addComponent(saveJob))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(new_vendor_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(326, 326, 326))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(save_cancel_buttonpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(new_vendor_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save_cancel_buttonpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressFocusGained
        if (address.getText().equals("Address"))
            address.setText("");
    }//GEN-LAST:event_addressFocusGained

    private void addressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressFocusLost
        if(address.getText().equals(""))
        address.setText("Address");
    }//GEN-LAST:event_addressFocusLost

    private void cityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFocusGained
        if (city.getText().equals("City"))
            city.setText("");
    }//GEN-LAST:event_cityFocusGained

    private void cityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFocusLost
        if(city.getText().equals(""))
        city.setText("City");
    }//GEN-LAST:event_cityFocusLost

    private void stateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFocusGained
        if (state.getText().equals("State"))
            state.setText("");
    }//GEN-LAST:event_stateFocusGained

    private void stateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFocusLost
        if(state.getText().equals(""))
        state.setText("State");
    }//GEN-LAST:event_stateFocusLost

    private void postalCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalCodeFocusGained
        if (postalCode.getText().equals("Postal Code"))
        postalCode.setText("");
    }//GEN-LAST:event_postalCodeFocusGained

    private void postalCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalCodeFocusLost
        if(postalCode.getText().equals(""))
        postalCode.setText("Postal Code");
    }//GEN-LAST:event_postalCodeFocusLost

    private void jobNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobNameFocusGained
        if (jobName.getText().equals("Job Name"))
            jobName.setText("");
    }//GEN-LAST:event_jobNameFocusGained

    private void jobNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobNameFocusLost
        if(jobName.getText().equals(""))
        jobName.setText("Job Name");
    }//GEN-LAST:event_jobNameFocusLost

    private void bidAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bidAmountFocusGained
        bidAmount.setText("");
    }//GEN-LAST:event_bidAmountFocusGained

    private void bidAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bidAmountFocusLost
        if(bidAmount.getText().equals(""))
        bidAmount.setText("0");
    }//GEN-LAST:event_bidAmountFocusLost

    private void saveJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJobActionPerformed
        insertJob();
        this.dispose();
    }//GEN-LAST:event_saveJobActionPerformed

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Job.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Job().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel;
    private javax.swing.JTextField address;
    private javax.swing.JTextField bidAmount;
    private javax.swing.JTextField city;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jobComments;
    private javax.swing.JTextField jobName;
    private javax.swing.JPanel new_vendor_panel;
    private javax.swing.JTextField postalCode;
    private javax.swing.JRadioButton rdbActive;
    private javax.swing.JRadioButton rdbInactive;
    private javax.swing.JButton saveJob;
    private javax.swing.JPanel save_cancel_buttonpanel;
    private javax.swing.JTextField state;
    private javax.swing.ButtonGroup statusGroup;
    // End of variables declaration//GEN-END:variables
}
