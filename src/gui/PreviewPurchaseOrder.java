package gui;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String [][] supplier = null;
    String [][] category = null;
    String [][] contact= null;
    String [][] job = null;
    Double tax;
    
    public PreviewPurchaseOrder() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getComboJob();
        getComboContact();
        getComboSupplier();
        getItemsTable();
        setDatePicker();
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
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    private void getComboJob() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select jobid, name from job ORDER BY name;");
            //Dynamically set job list size
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
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    @SuppressWarnings("CallToPrintStackTrace")
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
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
        }    
    }
    private int getProduct(int suppID, String prodDesc){
        int id=-1;
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select p.id from product p inner join productdescription pd on pd.pdescID = p.description where pd.productDescription LIKE '%"+prodDesc+"%' and p.supplier = "+suppID+" ;");
            while (resultObj.next()){
                id =resultObj.getInt("id");
                System.out.println("In the result "+id);
            }
        }
        catch (SQLException e) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
        }  
        return id;
    }

    
    private void setDatePicker() {
        Calendar datePicker = Calendar.getInstance();
        datePicker.add(Calendar.DATE, 7);
        expectedDatePicker.setDate(datePicker.getTime());
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
        int orderid=-1;
        //Preparing insert statements will need to insert into table when ready.
        if (JobCombo.getSelectedItem().equals("Job List") || ShipToCombo.getSelectedItem().equals("Ship To") || 
                selectSupplierCombo.getSelectedItem().equals("Supplier") || deliveryContactCombo.getSelectedItem().equals("Delivery Contact")){
            JOptionPane.showMessageDialog(null, "Make a selection for all details to create a Purchase Order.");
        }
        else {
            //Loop through table to check for matching supplier name and supplier in the table
            List<Integer> index = new ArrayList<>();
            for (int i=0;i<previewItemsAddedTable.getRowCount();i++){
                if (previewItemsAddedTable.getValueAt(i,0).equals(selectSupplierCombo.getSelectedItem())){
                    index.add(i);
                }
            }
            if (index.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a supplier to create a Purchase Order.");
            }
            else{
                try {
                    String query ="Insert into purchaseorder (supplier,job,expectedby, contact, tax,total,createdby,shipto,currentTax)"
                        + "values(?,?,?,?,?,?,?,?,?)"    ;
                    //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
                    connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
                    PreparedStatement preparedStmt =connObj.prepareStatement(query);
                    int supp = -1;
                    int j =-1;
                    int ship =-1;
                    int cont =-1;
                    for (String[] supplier1 : supplier) {
                        if (selectSupplierCombo.getSelectedItem().equals(supplier1[1])) {
                            supp = Integer.parseInt(supplier1[0]);
                        }
                    }
                    for (String[] job1 : job) {
                        if (JobCombo.getSelectedItem().equals(job1[1])) {
                            j = Integer.parseInt(job1[0]);
                        }
                    }
                    for (String[] job1 : job) {
                        if (ShipToCombo.getSelectedItem().equals(job1[1])) {
                            ship = Integer.parseInt(job1[0]);
                        }
                    }
                    for (String[] contact1 : contact) {
                        if (deliveryContactCombo.getSelectedItem().equals(contact1[1]+ " "+contact1[2])) {
                            cont = Integer.parseInt(contact1[0]);
                        }
                    }
                    preparedStmt.setInt (1, supp); //supplier
                    preparedStmt.setInt (2, j);  //job number
                    preparedStmt.setDate(3, new java.sql.Date( expectedDatePicker.getDate().getTime())); //expected date
                    preparedStmt.setInt (4, cont);  //contact person
                    preparedStmt.setDouble (5, 0.0);  //total tax
                    preparedStmt.setDouble(6, 0.0); //invoice total
                    preparedStmt.setInt (7,Login.userid); //Created by
                    preparedStmt.setInt(8,ship); //ShipTo number
                    preparedStmt.setDouble(9,MainPage.tax); //Current sales Tax
                    preparedStmt.execute();
                    stateObj = connObj.createStatement();
                    resultObj = stateObj.executeQuery("select max(orderid) as 'id' from purchaseorder;");
                    while (resultObj.next()){
                        orderid=resultObj.getInt("id");
                    }
                    System.out.println("Last inserted "+orderid);
                    //Loop through index and add as many items as needed
                    double prodSubtotal=0.0;
                    for (int k=0; k<index.size();k++) {
                        @SuppressWarnings("UnusedAssignment")
                        double prodTotal=0.0;
                        query = "INSERT into purchaseorderdetails (orderid, product,cost,orderqty,total)"
                        + "values(?,?,?,?,?)";
                        preparedStmt =connObj.prepareStatement(query);
                        preparedStmt.setInt (1, orderid); //PO ID
                        preparedStmt.setInt (2, getProduct(supp, (String) previewItemsAddedTable.getValueAt(index.get(k), 1)));  //Product Number
                        preparedStmt.setDouble(3,Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 6))); //Unit cost
                        preparedStmt.setDouble (4, Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 4))); //Order Qty
                        prodTotal = Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 6)) * Double.parseDouble((String)previewItemsAddedTable.getValueAt(index.get(k), 4));
                        prodSubtotal += prodTotal;
                        preparedStmt.setDouble(5,prodTotal); //Unit Total
                        preparedStmt.execute();
                        //End of loop
                    }
                    //Collect subtotal for items and times by the tax rate and update purchase order with the totals from the lines
                     preparedStmt =connObj.prepareStatement("UPDATE purchaseorder SET subtotal =?, tax =?, total=? where orderid= "+orderid+";");
                     preparedStmt.setDouble(1,prodSubtotal);
                     preparedStmt.setDouble(2,(prodSubtotal*(tax/100)));
                     prodSubtotal=prodSubtotal + (prodSubtotal*(tax/100));
                     preparedStmt.setDouble(3,prodSubtotal);
                     preparedStmt.executeUpdate();
                     connObj.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Clean up preview Items list for remaining rows.
                DefaultTableModel model = (DefaultTableModel) previewItemsAddedTable.getModel();
                Collections.reverse(index);
                index.forEach((index1) -> { model.removeRow(index1); });
                if (model.getRowCount()==0){
                    this.dispose();
                }
                //Generate PO here
                try { 
                    //FileInputStream fis = new FileInputStream("C:\\Users\\ferrinsp\\Documents\\GitHub\\kbplumbapp\\src\\Reports\\PO.jrxml");            
                    FileInputStream fis = new FileInputStream("C:/Users/tatewtaylor/Documents/NetbeansProjects/KBApp/src/Reports/PO.jrxml");
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
                    connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
                    
                    //set parameters
                    Map map = new HashMap();
                    map.put("orderid", orderid);
                    //compile report
                    JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(bufferedInputStream);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);
                    //view report to UI
                    JasperViewer.viewReport(jasperPrint, false);                   
                } catch (FileNotFoundException | SQLException | JRException ex) {
                    Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
