package gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select contactid, name,phone from contact where status = 'Active' ORDER BY name;");
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
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
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
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select supplierid, companyname from supplier ORDER BY companyname;");
            //Dynamically set supplier list size
            resultObj.last();
            supplier = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                supplier[i][0] =Integer.toString(resultObj.getInt("supplierid"));
                supplier[i][1]=resultObj.getString("companyname");
                i++;
                supplierCombo.addItem(resultObj.getString("companyname"));
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
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select p.id from product p inner join productdescription pd on pd.pdescID = p.description where pd.productDescription LIKE '%"+EscapeCharacter.escape(prodDesc)+"%' and p.supplier = "+suppID+" ;");
            while (resultObj.next()){
                id =resultObj.getInt("id");
            }
        }
        catch (SQLException e) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
        }  
        return id;
    }

    
    private void setDatePicker() {
        Calendar datePicker = Calendar.getInstance();
        datePicker.add(Calendar.DATE,0);
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
        supplierCombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        quoteNumber = new javax.swing.JTextField();
        quoteDate = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        bldgTextField = new javax.swing.JTextField();
        itemsAddedToPO = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        previewItemsAddedTable = new javax.swing.JTable();
        createPurchaseOrderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preview Purchase Order");

        jLabel2.setText("Ship To:");

        jLabel1.setText("Job:");

        ShipToCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ship To" }));
        ShipToCombo.setNextFocusableComponent(supplierCombo);

        JobCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Job List" }));
        JobCombo.setNextFocusableComponent(ShipToCombo);

        expectedDatePicker.setDate(new Date());
        expectedDatePicker.setNextFocusableComponent(deliveryContactCombo);

        jLabel3.setText("Expected Date");

        jLabel4.setText("Delivery Contact");

        deliveryContactCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Delivery Contact" }));
        deliveryContactCombo.setNextFocusableComponent(quoteNumber);

        jLabel5.setText("Supplier:");

        supplierCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier" }));
        supplierCombo.setNextFocusableComponent(bldgTextField);

        jLabel6.setText("Quote #:");

        jLabel7.setText("Quote Date:");

        quoteNumber.setText("Quote #");
        quoteNumber.setNextFocusableComponent(quoteDate);
        quoteNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quoteNumberFocusGained(evt);
            }
        });

        quoteDate.setNextFocusableComponent(previewItemsAddedTable);

        jLabel8.setText("Bldg:");

        bldgTextField.setText(" ");
        bldgTextField.setNextFocusableComponent(expectedDatePicker);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JobCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShipToCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supplierCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(quoteNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(quoteDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bldgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(expectedDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deliveryContactCombo, 0, 171, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(expectedDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(deliveryContactCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(quoteNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(quoteDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JobCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(bldgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ShipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(supplierCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 939, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        previewItemsAddedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier", "Quantity", "Unit", "MFC", "Part ID", "Description", "Unit Price", "Totals"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        previewItemsAddedTable.setNextFocusableComponent(createPurchaseOrderButton);
        previewItemsAddedTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(previewItemsAddedTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(itemsAddedToPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(263, Short.MAX_VALUE)))
        );

        createPurchaseOrderButton.setText("Create Purchase Order");
        createPurchaseOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPurchaseOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(816, Short.MAX_VALUE)
                .addComponent(createPurchaseOrderButton)
                .addGap(20, 20, 20))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(539, Short.MAX_VALUE)
                .addComponent(createPurchaseOrderButton)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createPurchaseOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPurchaseOrderButtonActionPerformed
        int orderid=-1;
        //Preparing insert statements will need to insert into table when ready.
        if (JobCombo.getSelectedItem().equals("Job List") || ShipToCombo.getSelectedItem().equals("Ship To") || 
                supplierCombo.getSelectedItem().equals("Supplier") || deliveryContactCombo.getSelectedItem().equals("Delivery Contact")){
            JOptionPane.showMessageDialog(null, "Make a selection for all details to create a Purchase Order.");
        }
        else {
            //Loop through table to check for matching supplier name and supplier in the table
            List<Integer> index = new ArrayList<>();
            for (int i=0;i<previewItemsAddedTable.getRowCount();i++){
                if (previewItemsAddedTable.getValueAt(i,0).equals(supplierCombo.getSelectedItem())){
                    index.add(i);
                }
            }
            if (index.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a supplier to create a Purchase Order.");
            }
            else{
                try {
                    String query;
                    boolean all =false;
                    if (quoteNumber.getText().equals("Quote #") && quoteDate.getDate()==null){
                        query ="Insert into purchaseorder (supplier,job,expectedby, contact, tax,total,createdby,shipto,currentTax,bldg)"
                        + "values(?,?,?,?,?,?,?,?,?,?)"    ;
                    }
                    else {
                        query ="Insert into purchaseorder (supplier,job,expectedby, contact, tax,total,createdby,shipto,currentTax,bldg,quote,quotedate)"
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?)"    ;
                        all=true;
                    }
                    //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
                    connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
                    PreparedStatement preparedStmt =connObj.prepareStatement(query);
                    int supp = -1;
                    int j =-1;
                    int ship =-1;
                    int cont =-1;
                    for (String[] supplier1 : supplier) {
                        if (supplierCombo.getSelectedItem().equals(supplier1[1])) {
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
                    preparedStmt.setString(10,bldgTextField.getText());
                    if (all){
                        int quote = 0;
                        try {
                            if (!quoteNumber.getText().equals("Quote #"))
                                quote=Integer.parseInt(quoteNumber.getText());
                        }catch(NumberFormatException fe){
                        };
                        preparedStmt.setInt(11,quote);
                        preparedStmt.setDate(12,new java.sql.Date( quoteDate.getDate().getTime()));
                    }
                    preparedStmt.execute();
                    stateObj = connObj.createStatement();
                    resultObj = stateObj.executeQuery("select max(orderid) as 'id' from purchaseorder;");
                    while (resultObj.next()){
                        orderid=resultObj.getInt("id");
                    }
                    //Loop through index and add as many items as needed
                    double prodSubtotal=0.0;
                    for (int k=0; k<index.size();k++) {
                        @SuppressWarnings("UnusedAssignment")
                        double prodTotal=0.0;
                        query = "INSERT into purchaseorderdetails (orderid, product,cost,orderqty,total)"
                        + "values(?,?,?,?,?)";
                        preparedStmt =connObj.prepareStatement(query);
                        preparedStmt.setInt (1, orderid); //PO ID
                        preparedStmt.setInt (2, getProduct(supp, (String) previewItemsAddedTable.getValueAt(index.get(k), 5)));  //Product Number
                        preparedStmt.setDouble(3,Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 6))); //Unit cost
                        preparedStmt.setDouble (4, Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 1))); //Order Qty
                        prodTotal = Double.parseDouble((String) previewItemsAddedTable.getValueAt(index.get(k), 6)) * Double.parseDouble((String)previewItemsAddedTable.getValueAt(index.get(k), 1));
                        prodSubtotal += prodTotal;
                        preparedStmt.setDouble(5,prodTotal); //Unit Total
                        preparedStmt.execute();
                        //End of loop
                    }
                    //Collect subtotal for items and times by the tax rate and update purchase order with the totals from the lines
                     preparedStmt =connObj.prepareStatement("UPDATE purchaseorder SET subtotal =?, tax =?, total=? where orderid= "+orderid+";");
                     preparedStmt.setDouble(1,prodSubtotal);
                     preparedStmt.setDouble(2,(prodSubtotal*(MainPage.tax/100)));
                     prodSubtotal=prodSubtotal + (prodSubtotal*(MainPage.tax/100));
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
                    InputStream is = getClass().getResourceAsStream("/Reports/PO.jrxml");
                    JasperDesign jd= JRXmlLoader.load(is);
                    connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
                    
                    //set parameters
                    Map map = new HashMap();
                    map.put("orderid", orderid);
                    
                    //compile report
                    JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(jd);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);
                    //view report to UI
                    JasperViewer.viewReport(jasperPrint, false);                   
                } catch (SQLException | JRException ex) {
                    Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_createPurchaseOrderButtonActionPerformed

    private void quoteNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quoteNumberFocusGained
        quoteNumber.setText("");
    }//GEN-LAST:event_quoteNumberFocusGained

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
    private javax.swing.JTextField bldgTextField;
    private javax.swing.JButton createPurchaseOrderButton;
    private javax.swing.JComboBox<String> deliveryContactCombo;
    private org.jdesktop.swingx.JXDatePicker expectedDatePicker;
    private javax.swing.JTabbedPane itemsAddedToPO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel newPurchaseOrderTab;
    private javax.swing.JTable previewItemsAddedTable;
    private org.jdesktop.swingx.JXDatePicker quoteDate;
    private javax.swing.JTextField quoteNumber;
    private javax.swing.JComboBox<String> supplierCombo;
    // End of variables declaration//GEN-END:variables
}
