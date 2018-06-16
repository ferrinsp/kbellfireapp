
package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

public class VItem extends javax.swing.JFrame {

    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;

    public VItem() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getProduct();
    }
    
    public void filter(){
        String text = searchField.getText();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(((DefaultTableModel) ItemTable.getModel())); 
        if(text.length() >0 ){
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" +searchField.getText()));
            ItemTable.setRowSorter(sorter);
        }
    }
  /*  private int findProduct(String supplier, String descp,Double price){
        int id =-1;
         try{
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("SELECT p.id from product p inner join category c on c.category_ID=p.category_id inner join productdescription pd on p.description=pd.pdescID \n" +
            "inner join supplier s on s.supplierid=p.supplier where p.price = "+price+" and s.companyname like '%"+EscapeCharacter.escape(supplier)+"%' and pd.productDescription like '%"+EscapeCharacter.escape(descp)+"%';"); 
             while (resultObj.next()){
                 id= resultObj.getInt("id");
             }
         } catch (SQLException ex) {
            Logger.getLogger(VItem.class.getName()).log(Level.SEVERE, null, ex);
        }
         return id;
    }*/
    private void getProduct() {
        try{
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            if(showHideStatus.isSelected()) {
                resultObj = stateObj.executeQuery("select c.description, pd.productDescription, s.companyname, p.price,\n" +
                    "p.status as 'status',p.unitMeasure,p.manufacturer,p.part_id,p.lastchange, p.id " +
                    "from product p inner join category c on p.category_id=c.category_ID " +
                    "inner join productdescription pd on pd.pdescID=p.description " +
                    "inner join supplier s on s.supplierid=p.supplier " +
                    "where status not like '%Inactive%';");     
            } else {
                resultObj = stateObj.executeQuery(" select  c.description, pd.productDescription, s.companyname, p.price,"
                    + "p.status,p.unitMeasure,p.manufacturer,p.part_id,p.lastchange, p.id "
                    + "from product p inner join category c on p.category_id=c.category_ID "
                    + "inner join productdescription pd on pd.pdescID=p.description "
                    + "inner join supplier s on s.supplierid=p.supplier;");
            }
            ItemTable.setModel(DbUtils.resultSetToTableModel(resultObj));
            ItemTable.getColumn("description").setHeaderValue("Category");
            ItemTable.getColumn("productDescription").setHeaderValue("Product Description");
            ItemTable.getColumn("companyname").setHeaderValue("Supplier");
            ItemTable.getColumn("price").setHeaderValue("Unit Price");
            ItemTable.getColumn("status").setHeaderValue("Status");
            ItemTable.getColumn("unitMeasure").setHeaderValue("Unit");
            ItemTable.getColumn("manufacturer").setHeaderValue("MFC");
            ItemTable.getColumn("part_id").setHeaderValue("Part Number");
            ItemTable.getColumn("lastchange").setHeaderValue("Last Change");
            ItemTable.getColumn("id").setHeaderValue("Product ID");
            ItemTable.repaint();
            connObj.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(NUItem.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        ItemTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        addItemButton = new javax.swing.JButton();
        updateItemButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        productHistoryButton = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        showHideStatus = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Item");

        ItemTable.setAutoCreateRowSorter(true);
        ItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Category", "Description", "Part Number", "MFC", "Supplier", "Price", "Unit", "Status", "Last Change", "Product ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ItemTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() ==2 ) {
                    int prod= (int) ItemTable.getValueAt(row, 9);
                    NUItem addItem = new NUItem(prod);
                    addItem.setVisible(true);
                }
            }
        });
        jScrollPane1.setViewportView(ItemTable);

        addItemButton.setText("Add Item");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        updateItemButton.setText("Update Item");
        updateItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateItemButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        productHistoryButton.setText("Product History");
        productHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productHistoryButtonActionPerformed(evt);
            }
        });

        refresh.setText("Refresh Page");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addItemButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateItemButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productHistoryButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemButton)
                    .addComponent(updateItemButton)
                    .addComponent(closeButton)
                    .addComponent(productHistoryButton)
                    .addComponent(refresh))
                .addGap(12, 12, 12))
        );

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

        showHideStatus.setSelected(true);
        showHideStatus.setText("Hide Inactive");
        showHideStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHideStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(showHideStatus))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showHideStatus))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        NUItem addItem = new NUItem(-1);
        addItem.setVisible(true);
    }//GEN-LAST:event_addItemButtonActionPerformed

    private void updateItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateItemButtonActionPerformed
        int[] index = ItemTable.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No item selected to update.");
        }
        else if (index.length > 1){
            JOptionPane.showMessageDialog(null, "Select only one item to update.");
        }
        else{
            int prod= (int) ItemTable.getValueAt(index[0], 9);
            NUItem addItem = new NUItem(prod);
            addItem.setVisible(true);
        }
    }//GEN-LAST:event_updateItemButtonActionPerformed

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

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER)
            filter();
    }//GEN-LAST:event_searchFieldKeyPressed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void showHideStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHideStatusActionPerformed
        getProduct();
    }//GEN-LAST:event_showHideStatusActionPerformed

    private void productHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productHistoryButtonActionPerformed
        int[] index = ItemTable.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No item selected to view history.");
        } 
        else if (index.length > 1){
            JOptionPane.showMessageDialog(null, "Select only one item to view history.");
        }
        else {
            ProductHistory history = new ProductHistory(ItemTable.getValueAt(index[0], 1).toString());
            history.setVisible(true);
        }
    }//GEN-LAST:event_productHistoryButtonActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        getProduct();
    }//GEN-LAST:event_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(VItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VItem().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ItemTable;
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton productHistoryButton;
    private javax.swing.JButton refresh;
    private javax.swing.JTextField searchField;
    private javax.swing.JCheckBox showHideStatus;
    private javax.swing.JButton updateItemButton;
    // End of variables declaration//GEN-END:variables
}
