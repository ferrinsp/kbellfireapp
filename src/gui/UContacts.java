package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class UContacts extends javax.swing.JFrame {

    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    int id = -1;

    public UContacts() {
        initComponents();
    }

    public UContacts(int id) {
        initComponents();
        this.id=id;
        pullContactFromVContacts();
    }
    
    private void updateContact() {
        
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            String query;
            if (id==-1)
                query ="insert into contact (name,phone,status) values (?,?,?);"; 
            else    
                query = "UPDATE contact set name=?, phone=?, status=? where contactid = " + id + ";";
            PreparedStatement preparedStmt =connObj.prepareStatement(query);
            String contactStatus= null;
                for (Enumeration<AbstractButton> buttons = statusGroup.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        contactStatus = button.getText();
                    }
                }
            preparedStmt.setString (1, contactName.getText());    
            preparedStmt.setString (2, contactPhone.getText());    
            preparedStmt.setString (3, contactStatus);
            if (id ==-1) {
                preparedStmt.execute();
                JOptionPane.showMessageDialog(null, "New Contact added for "+contactName.getText()+".");
            }
            else {
                preparedStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Contact # "+id+" was updated.");
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void pullContactFromVContacts() {
        if(id !=-1) {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select contactid, name, phone, status from contact where contactid = "+id);
            while (resultObj.next()){
                contactName.setText(resultObj.getString("name"));
                contactPhone.setText(resultObj.getString("phone"));
                String contactStatus = resultObj.getString("status");
                if (contactStatus.equals("Active")) {
                    contactActive.setSelected(true);
                } else if (contactStatus.equals("Inactive")) {
                    contactInactive.setSelected(true);
                }
            }
            connObj.close();
        } catch (SQLException ex) {    
            Logger.getLogger(NSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        updateName = new javax.swing.JLabel();
        contactPhone = new javax.swing.JTextField();
        updatePhone = new javax.swing.JLabel();
        contactStatusLabel = new javax.swing.JLabel();
        contactName = new javax.swing.JTextField();
        contactActive = new javax.swing.JRadioButton();
        contactInactive = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Contact");
        setResizable(false);

        updateName.setText("Contact Name:");

        contactPhone.setText("Phone Number");
        contactPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contactPhoneFocusGained(evt);
            }
        });

        updatePhone.setText("Phone:");

        contactStatusLabel.setText("Status:");

        contactName.setText("Contact Name");
        contactName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contactNameFocusGained(evt);
            }
        });

        statusGroup.add(contactActive);
        contactActive.setSelected(true);
        contactActive.setText("Active");

        statusGroup.add(contactInactive);
        contactInactive.setText("Inactive");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(updateName)
                        .addGap(18, 18, 18)
                        .addComponent(contactName, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updatePhone)
                            .addComponent(contactStatusLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(contactActive)
                                .addGap(18, 18, 18)
                                .addComponent(contactInactive))
                            .addComponent(contactPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateName)
                    .addComponent(contactName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updatePhone)
                    .addComponent(contactPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactStatusLabel)
                    .addComponent(contactActive)
                    .addComponent(contactInactive))
                .addContainerGap())
        );

        saveButton.setText("Update Contact");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(closeButton))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if((contactName.getText().equals("Contact Name") || contactName.getText().equals(""))
                ||(contactPhone.getText().equals("Phone Number") || contactPhone.getText().equals("")))
            JOptionPane.showMessageDialog(null, "Please fill in contact information.");
        else {
            updateContact();
            id=-1;
            this.dispose();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void contactNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactNameFocusGained
        contactName.setText("");
    }//GEN-LAST:event_contactNameFocusGained

    private void contactPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactPhoneFocusGained
        contactPhone.setText("");
    }//GEN-LAST:event_contactPhoneFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UContacts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new UContacts().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JRadioButton contactActive;
    private javax.swing.JRadioButton contactInactive;
    private javax.swing.JTextField contactName;
    private javax.swing.JTextField contactPhone;
    private javax.swing.JLabel contactStatusLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton saveButton;
    private javax.swing.ButtonGroup statusGroup;
    private javax.swing.JLabel updateName;
    private javax.swing.JLabel updatePhone;
    // End of variables declaration//GEN-END:variables
}
