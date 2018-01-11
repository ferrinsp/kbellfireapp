/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import kbapp.classes.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author ferrinsp
 */
public class NPurchaseOrder extends javax.swing.JFrame {

    /**
     * Creates new form new_purchase_order
     */
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String [][] category = null;
    String [][] contact= null;
    String [][] job = null;
    
    
    private List<PurchaseOrder> searchList = new ArrayList<>();
    private List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
    private DefaultListModel<PurchaseOrder> purchaseOrderModel = new DefaultListModel<>(); // Blessed be the diamond operator

    
    public int findCategory(String cat){
        int index =-1;
        for (int i=0;i<category.length;i++){
            if(cat.equals(category[i][1]))
                index =i;
        }
        
        return index;
    }
    public int getDescription(String desc){
        int index =-1;
        try{
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select pdescID from productdescription where productDescription LIKE '%"+desc+"%';");
            while (resultObj.next()){
                index =resultObj.getInt("pdescID");
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return index;
    }
    private void getComboCategory() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select category_ID, description from category ORDER BY description;");
            //Dynamically set supplier list size
            resultObj.last();
            category = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                category[i][0] =Integer.toString(resultObj.getInt("category_ID"));
                category[i][1]=resultObj.getString("description");
                i++;
                CategoryList.addItem(resultObj.getString("description"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void getProductDetails (int category, int description){
        try {
    //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select s.companyname, p.unitMeasure,manufacturer, part_id, p.price, 0 as 'Quantity' from product p \n" +
"inner join supplier s on p.supplier =s.supplierid  INNER JOIN productdescription pd on pd.pdescID = p.description \n" +
"inner join category c on c.category_ID=p.category_id where p.description ="+description +" and p.category_id="+category+" and p.price>0 order by p.price;");
            PriceTable.setModel(DbUtils.resultSetToTableModel(resultObj));
            PriceTable.getColumn("companyname").setHeaderValue("Supplier");
            PriceTable.getColumn("unitMeasure").setHeaderValue("Unit");
            PriceTable.getColumn("part_id").setHeaderValue("Part ID");
            PriceTable.getColumn("manufacturer").setHeaderValue("MFC");
            PriceTable.getColumn("price").setHeaderValue("Unit Price");
            PriceTable.repaint();
            connObj.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getProductHeader (){
        try {
    //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select DISTINCT c.description, pd.productDescription as pdescription, pd.productsize from product p inner join category c on p.category_id =c.category_ID inner join productdescription pd on p.description = pd.pdescID ORDER BY p.description;");
            itemsSearchTable.setModel(DbUtils.resultSetToTableModel(resultObj));
            itemsSearchTable.getColumn("description").setHeaderValue("Category");
            itemsSearchTable.getColumn("pdescription").setHeaderValue("Product Description");
            itemsSearchTable.getColumn("productsize").setHeaderValue("Size");
            itemsSearchTable.repaint();
            connObj.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *
     */
    public NPurchaseOrder() {
        this.setResizable(false);
        initComponents();
        getProductHeader();
        getComboJob();
        getComboCategory();
        getComboContact();
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        newPurchaseOrderTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        ShipToCombo = new javax.swing.JComboBox<>();
        JobCombo = new javax.swing.JComboBox<>();
        expectedDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        deliveryContactCombo = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        CategoryList = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemsSearchTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        PriceTable = new javax.swing.JTable();
        n_u_product = new javax.swing.JButton();
        addItemToPO = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        itemsAddedToPO = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ItemsAddedTable = new javax.swing.JTable();
        itemsAddedDelete = new javax.swing.JButton();
        createPurchaseOrderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel2.setText("Ship To:");

        jLabel1.setText("Job:");

        ShipToCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ship To" }));

        JobCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Job List" }));

        jLabel3.setText("Expected Date");

        jLabel4.setText("Delivery Contact");

        deliveryContactCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Delivery Contact" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JobCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ShipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(expectedDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(deliveryContactCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(JobCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(expectedDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ShipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(deliveryContactCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout newPurchaseOrderTabLayout = new javax.swing.GroupLayout(newPurchaseOrderTab);
        newPurchaseOrderTab.setLayout(newPurchaseOrderTabLayout);
        newPurchaseOrderTabLayout.setHorizontalGroup(
            newPurchaseOrderTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newPurchaseOrderTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        newPurchaseOrderTabLayout.setVerticalGroup(
            newPurchaseOrderTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newPurchaseOrderTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("New Purchase Order Details", newPurchaseOrderTab);

        searchField.setText("Search");
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFieldFocusLost(evt);
            }
        });
        searchField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchFieldMouseClicked(evt);
            }
        });

        CategoryList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filter By Category" }));

        itemsSearchTable.setAutoCreateRowSorter(true);
        itemsSearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Category", "Description", "Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemsSearchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemsSearchTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(itemsSearchTable);

        PriceTable.setAutoCreateRowSorter(true);
        PriceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Supplier", "MFC", "Part ID", "Unit", "Qty", "Unit Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(PriceTable);

        n_u_product.setText("New Item");
        n_u_product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                n_u_productActionPerformed(evt);
            }
        });

        addItemToPO.setText("Add Item/s");
        addItemToPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemToPOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(CategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addItemToPO)
                .addGap(18, 18, 18)
                .addComponent(n_u_product)
                .addGap(62, 62, 62))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemToPO)
                    .addComponent(n_u_product))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Item Search", jPanel3);

        jScrollPane2.setViewportView(jTabbedPane2);

        ItemsAddedTable.setAutoCreateRowSorter(true);
        ItemsAddedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier", "Description", "MFC", "Part ID", "Quantity", "Unit", "Unit Price", "Totals"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ItemsAddedTable);

        itemsAddedDelete.setText("Delete Item");
        itemsAddedDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsAddedDeleteActionPerformed(evt);
            }
        });

        createPurchaseOrderButton.setText("Create Purchase Order");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(375, 375, 375)
                .addComponent(createPurchaseOrderButton)
                .addGap(18, 18, 18)
                .addComponent(itemsAddedDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(811, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemsAddedDelete)
                    .addComponent(createPurchaseOrderButton))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        itemsAddedToPO.addTab("Items Added to Purchase Order", jPanel2);

        jScrollPane4.setViewportView(itemsAddedToPO);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addItemToPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemToPOActionPerformed

        TableModel model1 = itemsSearchTable.getModel();
        int[] index = itemsSearchTable.getSelectedRows();
        TableModel model2 = PriceTable.getModel();
        int[] index2 = PriceTable.getSelectedRows();
        Object[] row = new Object[8];
        DefaultTableModel model3 = (DefaultTableModel) ItemsAddedTable.getModel();
        if(index.length > 1 || index2.length >1){
            JOptionPane.showMessageDialog(null, "Please only select one row to add.");
        }
        else{
            
                Double total;
                row[0] = model2.getValueAt(index2[0], 0); //supplier
                row[1] = model1.getValueAt(index[0], 1); //description
                row[2] = model2.getValueAt(index2[0], 2);//MFC
                row[3] = model2.getValueAt(index2[0], 3);//Part ID
                row[4] = model2.getValueAt(index2[0], 5); //qty
                row[5] = model2.getValueAt(index2[0], 1);  //Unit Of Measure
                row[6] = model2.getValueAt(index2[0], 4);  //Unit Price
                total = Double.parseDouble(row[4].toString()) * Double.parseDouble(row[6].toString());
                row[7] = Double.toString(total);  // Total
                model3.addRow(row); 
            
                    }
    }//GEN-LAST:event_addItemToPOActionPerformed

    private void n_u_productActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_n_u_productActionPerformed
        N_U_Item newProduct = new N_U_Item();
        newProduct.setVisible(true);
    }//GEN-LAST:event_n_u_productActionPerformed

    private void itemsAddedDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsAddedDeleteActionPerformed
        
        DefaultTableModel model = (DefaultTableModel) ItemsAddedTable.getModel();
        int[] index = ItemsAddedTable.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No items selected");
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm Deletion", dialogButton);
            if(dialogResult == 0) {
                    for(int i = 0; i <index.length; i++)
                    {
                        //Need to address multiple selections
                        model.removeRow(i);
                    }
            } 
        }
    }//GEN-LAST:event_itemsAddedDeleteActionPerformed

    private void searchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusGained
        searchField.setText("");
    }//GEN-LAST:event_searchFieldFocusGained

    private void searchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusLost
        if(searchField.getText().equals(""))
        searchField.setText("Search");
    }//GEN-LAST:event_searchFieldFocusLost

    private void searchFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchFieldMouseClicked
        if (searchField.getText().equals("Search")) {
            searchField.setText("");
        }
    }//GEN-LAST:event_searchFieldMouseClicked

    private void itemsSearchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsSearchTableMouseClicked
        int row = itemsSearchTable.getSelectedRow();
        getProductDetails(findCategory(itemsSearchTable.getModel().getValueAt(row, 0).toString()),getDescription(itemsSearchTable.getModel().getValueAt(row, 1).toString()));
    }//GEN-LAST:event_itemsSearchTableMouseClicked

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
            java.util.logging.Logger.getLogger(NPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NPurchaseOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CategoryList;
    private javax.swing.JTable ItemsAddedTable;
    private javax.swing.JComboBox<String> JobCombo;
    private javax.swing.JTable PriceTable;
    private javax.swing.JComboBox<String> ShipToCombo;
    private javax.swing.JButton addItemToPO;
    private javax.swing.JButton createPurchaseOrderButton;
    private javax.swing.JComboBox<String> deliveryContactCombo;
    private org.jdesktop.swingx.JXDatePicker expectedDatePicker;
    private javax.swing.JButton itemsAddedDelete;
    private javax.swing.JTabbedPane itemsAddedToPO;
    private javax.swing.JTable itemsSearchTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton n_u_product;
    private javax.swing.JPanel newPurchaseOrderTab;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
