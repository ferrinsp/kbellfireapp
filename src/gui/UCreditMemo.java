package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.swing.AbstractButton;

public class UCreditMemo extends javax.swing.JFrame {
    
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    int memoid;
    
    public UCreditMemo() {
        initComponents();
    }
    
    public UCreditMemo(int memoid) {
        this.memoid = memoid;
        initComponents();
        updateCreditMemo();
    }
    
    private void updateCreditMemo() {
        try {
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
        String query = "UPDATE creditmemo set status=?, comments=? where memoid = " + memoid + ";";
        PreparedStatement preparedStmt =connObj.prepareStatement(query);
        String creditMemoStatus= null;
            for (Enumeration<AbstractButton> buttons = statusButtons.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    creditMemoStatus = button.getText();
                }
            }
        preparedStmt.setString (1, creditMemoStatus);
        preparedStmt.setString (2, commentsTextArea.getText());
        preparedStmt.executeUpdate();
        connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusButtons = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        purchaseOrderTextField = new javax.swing.JTextField();
        jobTextField = new javax.swing.JTextField();
        supplierTextField = new javax.swing.JTextField();
        taxTextField = new javax.swing.JTextField();
        puchaseOrderLabel = new javax.swing.JLabel();
        supplierLabel = new javax.swing.JLabel();
        jobLabel = new javax.swing.JLabel();
        taxLabel = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        totalTextField = new javax.swing.JTextField();
        createdByLabel = new javax.swing.JLabel();
        createdByTextField = new javax.swing.JTextField();
        createdDateLabel = new javax.swing.JLabel();
        commentsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        issuedStatusButton = new javax.swing.JRadioButton();
        completedStatusButton = new javax.swing.JRadioButton();
        createdDateField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        updateCreditMemoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Credit Memo");

        purchaseOrderTextField.setEditable(false);
        purchaseOrderTextField.setText("Purchase Order #");

        jobTextField.setEditable(false);
        jobTextField.setText("Job");

        supplierTextField.setEditable(false);
        supplierTextField.setText("Supplier");

        taxTextField.setEditable(false);
        taxTextField.setText("Tax");

        puchaseOrderLabel.setText("Purchase Order #:");

        supplierLabel.setText("Supplier:");

        jobLabel.setText("Job:");

        taxLabel.setText("Tax:");

        totalLabel.setText("Total:");

        totalTextField.setEditable(false);
        totalTextField.setText("Total");

        createdByLabel.setText("Created By: ");

        createdByTextField.setEditable(false);
        createdByTextField.setText("Created By");
        createdByTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createdByTextFieldActionPerformed(evt);
            }
        });

        createdDateLabel.setText("Created Date:");

        commentsLabel.setText("Comments:");

        commentsTextArea.setColumns(20);
        commentsTextArea.setRows(5);
        jScrollPane1.setViewportView(commentsTextArea);

        jLabel1.setText("Status:");

        statusButtons.add(issuedStatusButton);
        issuedStatusButton.setText("Issued");

        statusButtons.add(completedStatusButton);
        completedStatusButton.setText("Completed");

        createdDateField.setEditable(false);
        createdDateField.setText("Created Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addComponent(createdDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createdByLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jobLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taxLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(puchaseOrderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commentsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(purchaseOrderTextField)
                    .addComponent(jobTextField)
                    .addComponent(taxTextField)
                    .addComponent(supplierTextField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(issuedStatusButton)
                                .addGap(18, 18, 18)
                                .addComponent(completedStatusButton))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(createdDateField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(totalTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(createdByTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(purchaseOrderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(puchaseOrderLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jobTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jobLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createdByLabel)
                    .addComponent(createdByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createdDateLabel)
                    .addComponent(createdDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(issuedStatusButton)
                    .addComponent(completedStatusButton))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commentsLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        updateCreditMemoButton.setText("Update Credit Memo");
        updateCreditMemoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCreditMemoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateCreditMemoButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateCreditMemoButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createdByTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createdByTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createdByTextFieldActionPerformed

    private void updateCreditMemoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCreditMemoButtonActionPerformed
        updateCreditMemo();
    }//GEN-LAST:event_updateCreditMemoButtonActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UCreditMemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new UCreditMemo().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JTextArea commentsTextArea;
    private javax.swing.JRadioButton completedStatusButton;
    private javax.swing.JLabel createdByLabel;
    private javax.swing.JTextField createdByTextField;
    private javax.swing.JTextField createdDateField;
    private javax.swing.JLabel createdDateLabel;
    private javax.swing.JRadioButton issuedStatusButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jobLabel;
    private javax.swing.JTextField jobTextField;
    private javax.swing.JLabel puchaseOrderLabel;
    private javax.swing.JTextField purchaseOrderTextField;
    private javax.swing.ButtonGroup statusButtons;
    private javax.swing.JLabel supplierLabel;
    private javax.swing.JTextField supplierTextField;
    private javax.swing.JLabel taxLabel;
    private javax.swing.JTextField taxTextField;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JTextField totalTextField;
    private javax.swing.JButton updateCreditMemoButton;
    // End of variables declaration//GEN-END:variables
}
