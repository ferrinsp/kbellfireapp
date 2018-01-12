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
import javax.swing.table.*;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.RowFilter;

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
    
    public NPurchaseOrder() {
        this.setResizable(false);
        initComponents();
        getComboCategory();
        getProductHeader();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeWindowButton = new javax.swing.JButton();
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
        itemsAddedToPO = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        itemsAddedDelete = new javax.swing.JButton();
        createPurchaseOrderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ItemsAddedTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create New Purchase Order");
        setAlwaysOnTop(true);

        closeWindowButton.setText("Close Window");
        closeWindowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeWindowButtonActionPerformed(evt);
            }
        });

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
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
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
        itemsSearchTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemsSearchTableKeyPressed(evt);
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(addItemToPO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(n_u_product))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(CategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoryList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemToPO)
                    .addComponent(n_u_product))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Item Search", jPanel3);

        itemsAddedDelete.setText("Delete Item");
        itemsAddedDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsAddedDeleteActionPerformed(evt);
            }
        });

        createPurchaseOrderButton.setText("Preview Purchase Order");
        createPurchaseOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPurchaseOrderButtonActionPerformed(evt);
            }
        });

        ItemsAddedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier", "Description", "MFC", "Part ID", "Quantity", "Unit", "Unit Price", "Totals"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ItemsAddedTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(691, 691, 691)
                .addComponent(createPurchaseOrderButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemsAddedDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemsAddedDelete)
                    .addComponent(createPurchaseOrderButton))
                .addContainerGap())
        );

        itemsAddedToPO.addTab("Items Added to Purchase Order", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itemsAddedToPO)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(closeWindowButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(closeWindowButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemsAddedToPO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void closeWindowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeWindowButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeWindowButtonActionPerformed

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        String text = searchField.getText();
        String filter = (String) CategoryList.getSelectedItem();
        System.out.println(filter);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(((DefaultTableModel) itemsSearchTable.getModel())); 
        if (text.length() ==0){
            sorter.setRowFilter(null);
        }
        else {
            sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
            itemsSearchTable.setRowSorter(sorter);
        }
    }//GEN-LAST:event_searchFieldKeyPressed

    private void createPurchaseOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPurchaseOrderButtonActionPerformed
        TableModel original = ItemsAddedTable.getModel();
        DefaultTableModel model = new DefaultTableModel(ItemsAddedTable.getSelectedRowCount(), original.getColumnCount());
        PreviewPurchaseOrder previewScreen = new PreviewPurchaseOrder(model);
        previewScreen.setVisible(true);
    }//GEN-LAST:event_createPurchaseOrderButtonActionPerformed

    private void itemsSearchTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemsSearchTableKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN ) {
            int row = itemsSearchTable.getSelectedRow();
            getProductDetails(findCategory(itemsSearchTable.getModel().getValueAt(row, 0).toString()),getDescription(itemsSearchTable.getModel().getValueAt(row, 1).toString()));
        }
    }//GEN-LAST:event_itemsSearchTableKeyPressed

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
    private javax.swing.JTable PriceTable;
    private javax.swing.JButton addItemToPO;
    private javax.swing.JButton closeWindowButton;
    private javax.swing.JButton createPurchaseOrderButton;
    private javax.swing.JButton itemsAddedDelete;
    private javax.swing.JTabbedPane itemsAddedToPO;
    private javax.swing.JTable itemsSearchTable;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton n_u_product;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
