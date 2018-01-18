/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class PreviewPurchaseOrder extends javax.swing.JFrame {

    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String [][] supplier = null;
    String [][] category = null;
    String [][] contact= null;
    String [][] job = null;
    
    public PreviewPurchaseOrder() {
        initComponents();
        getComboJob();
        getComboContact();
        getComboSupplier();
        getItemsTable();
    }
        
    @SuppressWarnings("null")
    private void getItemsTable() {
        BufferedReader bfw = null;
        try {
            DefaultTableModel tm = (DefaultTableModel) previewItemsAddedTable.getModel();
            bfw = new BufferedReader(new FileReader("C:\\temp\\ItemsAddedData.txt"));
            String line;
            while( (line = bfw.readLine() ) != null ) {   
                tm.addRow( line.split("\t") );
            }
            bfw.close();
        } catch (Exception ex) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bfw.close();
            } catch (IOException ex) {
                Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
    
    private void getComboContact() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select contactid, name,phone from contact ORDER BY name;");
            //Dynamically set contact list size
            resultObj.last();
            contact = new String[resultObj.getRow()][3];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                contact[i][0] =Integer.toString(resultObj.getInt("contactid"));
                contact[i][1]=resultObj.getString("name");
                contact[i][2]=resultObj.getString("phone");
                i++;
                deliveryContactCombo.addItem(resultObj.getString("name")+" " + resultObj.getString("phone"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getComboJob() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select jobid, name from job ORDER BY name;");
            //Dynamically set supplier list size
            resultObj.last();
            job = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                job[i][0] =Integer.toString(resultObj.getInt("jobid"));
                job[i][1]=resultObj.getString("name");
                i++;
                JobCombo.addItem(resultObj.getString("name"));
                ShipToCombo.addItem(resultObj.getString("name"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getComboSupplier() {
    try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select supplierid, companyname from kbell.supplier ORDER BY companyname;");
            //Dynamically set supplier list size
            resultObj.last();
            supplier = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                supplier[i][0] =Integer.toString(resultObj.getInt("supplierid"));
                supplier[i][1]=resultObj.getString("companyname");
                i++;
                selectSupplierCombo.addItem(resultObj.getString("companyname"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        newPurchaseOrderTab = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        ShipToCombo = new javax.swing.JComboBox<>();
        JobCombo = new javax.swing.JComboBox<>();
        expectedDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        deliveryContactCombo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        selectSupplierCombo = new javax.swing.JComboBox<>();
        itemsAddedToPO = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        createPurchaseOrderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        previewItemsAddedTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Preview Purchase Order");

        jLabel2.setText("Ship To:");

        jLabel1.setText("Job:");

        ShipToCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ship To" }));

        JobCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Job List" }));

        expectedDatePicker.setDate(new Date());

        jLabel3.setText("Expected Date");

        jLabel4.setText("Delivery Contact");

        deliveryContactCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Delivery Contact" }));

        jLabel5.setText("Supplier:");

        selectSupplierCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JobCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShipToCombo, 0, 260, Short.MAX_VALUE)
                    .addComponent(selectSupplierCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(expectedDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deliveryContactCombo, 0, 171, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JobCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(expectedDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ShipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deliveryContactCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(selectSupplierCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout newPurchaseOrderTabLayout = new javax.swing.GroupLayout(newPurchaseOrderTab);
        newPurchaseOrderTab.setLayout(newPurchaseOrderTabLayout);
        newPurchaseOrderTabLayout.setHorizontalGroup(
            newPurchaseOrderTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPurchaseOrderTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        newPurchaseOrderTabLayout.setVerticalGroup(
            newPurchaseOrderTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newPurchaseOrderTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("New Purchase Order Details", newPurchaseOrderTab);

        createPurchaseOrderButton.setText("Create Purchase Order");
        createPurchaseOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPurchaseOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 796, Short.MAX_VALUE)
                .addComponent(createPurchaseOrderButton))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createPurchaseOrderButton))
        );

        previewItemsAddedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier", "Description", "MFC", "Part ID", "Quantity", "Unit", "Unit Price", "Totals"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        previewItemsAddedTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(previewItemsAddedTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(itemsAddedToPO)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(itemsAddedToPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(264, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 984, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createPurchaseOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPurchaseOrderButtonActionPerformed
        //Preparing insert statements will need to insert into table when ready.
        if (JobCombo.getSelectedItem().equals("Job List") || ShipToCombo.getSelectedItem().equals("Ship To") || 
                selectSupplierCombo.getSelectedItem().equals("Supplier") || deliveryContactCombo.getSelectedItem().equals("Delivery Contact")){
            JOptionPane.showMessageDialog(null, "Make a selection for all details to create a Purchase Order.");
        }
        else {
            System.out.println("Job: "+JobCombo.getSelectedItem());
            System.out.println("Ship To: "+ShipToCombo.getSelectedItem());
            System.out.println("Supplier: "+selectSupplierCombo.getSelectedItem());
            System.out.println("Expected Date: "+expectedDatePicker.getDate());
            System.out.println("Delivery Contact: "+deliveryContactCombo.getSelectedItem());
            //Loop through table to check for matching supplier name and supplier in the table
            List<Integer> index = new ArrayList<>();
            for (int i=0;i<previewItemsAddedTable.getRowCount();i++){
                if (previewItemsAddedTable.getValueAt(i,0).equals(selectSupplierCombo.getSelectedItem())){
                    System.out.println("Suppliers matched");
                    index.add(i);
                }
            }
            if (index.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a supplier to create a Purchase Order.");
            }
            else{
                DefaultTableModel model = (DefaultTableModel) previewItemsAddedTable.getModel();
                //Insert information from this row into purchase order details
                //Clean up preview Items list for remaining rows.
                Collections.reverse(index);
                index.forEach((index1) -> { model.removeRow(index1); });
                if (model.getRowCount()==0){
                    this.dispose();
                }
            }
        }        
       
        try {
            /*String poreport = "C:\\Users\\ferrinsp\\Documents\\GitHub\\kbplumbapp\\src\\Reports\\GeneratedPO.jrxml";
            JasperReport jpr = JasperCompileManager.compileReport(poreport);
            JasperPrint jpp = JasperFillManager.fillReport(jpr, null);
            JasperViewer.viewReport(jpp);*/

            String jobText = (String) JobCombo.getSelectedItem();
            String shipToText = (String) ShipToCombo.getSelectedItem();
            String selectSupplierText = (String) selectSupplierCombo.getSelectedItem();
            Date expectedDateText = expectedDatePicker.getDate();
            String deliveryContactText = (String) deliveryContactCombo.getSelectedItem();

            FileInputStream fis = new FileInputStream("C:\\Users\\ferrinsp\\Documents\\GitHub\\kbplumbapp\\src\\Reports\\GeneratedPO.jrxml");            
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");

            //set parameters
            Map map = new HashMap();
            map.put("selectedJob", jobText);
            map.put("selectedShipTo", shipToText);
            map.put("selectedSupplier", selectSupplierText);
            map.put("selectedExpectedDate", expectedDateText);
            map.put("selectedDeliveryContact", deliveryContactText);

            //compile report
            JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);

            //view report to UI
            JasperViewer.viewReport(jasperPrint, false);                   
        } catch (FileNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_createPurchaseOrderButtonActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PreviewPurchaseOrder().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JobCombo;
    private javax.swing.JComboBox<String> ShipToCombo;
    private javax.swing.JButton createPurchaseOrderButton;
    private javax.swing.JComboBox<String> deliveryContactCombo;
    private org.jdesktop.swingx.JXDatePicker expectedDatePicker;
    private javax.swing.JTabbedPane itemsAddedToPO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel newPurchaseOrderTab;
    private javax.swing.JTable previewItemsAddedTable;
    private javax.swing.JComboBox<String> selectSupplierCombo;
    // End of variables declaration//GEN-END:variables
}
