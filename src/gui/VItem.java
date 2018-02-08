
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
    
    public void filter(){
        String text = searchField.getText();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(((DefaultTableModel) ItemTable.getModel())); 
        if(text.length() >0 ){
            sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
            ItemTable.setRowSorter(sorter);
        }
    }
    private int findProduct(String supplier, String descp){
        int id =-1;
         try{
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            System.out.println(supplier + descp);
            resultObj = stateObj.executeQuery("SELECT p.id from product p inner join category c on c.category_ID=p.category_id inner join productdescription pd on p.description=pd.pdescID \n" +
            "inner join supplier s on s.supplierid=p.supplier where s.companyname like '%"+supplier+"%' and pd.productDescription like '%"+descp+"%';"); 
             while (resultObj.next()){
                 
                 id= resultObj.getInt("id");
                 System.out.println("PRODUCT NUBMER IN find"+id);
             }
         } catch (SQLException ex) {
            Logger.getLogger(VItem.class.getName()).log(Level.SEVERE, null, ex);
        }
         return id;
    }
    private void getProduct() {
        try{
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery(" select c.description, pd.productDescription, s.companyname, p.price,p.status,p.unitMeasure,p.manufacturer,p.part_id,p.lastchange "
                    + "from product p inner join category c on p.category_id=c.category_ID inner join productdescription pd on pd.pdescID=p.description inner join supplier s on s.supplierid=p.supplier;"); 
            ItemTable.setModel(DbUtils.resultSetToTableModel(resultObj));
            ItemTable.getColumn("description").setHeaderValue("Category");
            ItemTable.getColumn("productDescription").setHeaderValue("Product Description");
            ItemTable.getColumn("companyname").setHeaderValue("Supplier");
            ItemTable.getColumn("price").setHeaderValue("Unit Price");
            ItemTable.getColumn("status").setHeaderValue("Active");
            ItemTable.getColumn("unitMeasure").setHeaderValue("Unit");
            ItemTable.getColumn("manufacturer").setHeaderValue("MFC");
            ItemTable.getColumn("part_id").setHeaderValue("Part Number");
            ItemTable.getColumn("lastchange").setHeaderValue("Last Change");
            ItemTable.repaint();
            connObj.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(NUItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Creates new form VItem
     */
    public VItem() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getProduct();
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
        searchField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Item");

        ItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Category", "Description", "Part Number", "MFC", "Supplier", "Price", "Unit", "Status", "Last Change"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true, true, true
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
                    int prod= findProduct(ItemTable.getValueAt(row, 2).toString(),ItemTable.getValueAt(row, 1).toString());
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(398, 398, 398)
                .addComponent(addItemButton)
                .addGap(26, 26, 26)
                .addComponent(updateItemButton)
                .addGap(18, 18, 18)
                .addComponent(closeButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemButton)
                    .addComponent(updateItemButton)
                    .addComponent(closeButton))
                .addContainerGap(20, Short.MAX_VALUE))
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
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
        this.dispose();
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
            int prod= findProduct(ItemTable.getValueAt(index[0], 2).toString(),ItemTable.getValueAt(index[0], 1).toString());
            System.out.println("PRODUCT NUMBER "+prod);
            NUItem addItem = new NUItem(prod);
            addItem.setVisible(true);
            //this.dispose();
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
    private javax.swing.JTextField searchField;
    private javax.swing.JButton updateItemButton;
    // End of variables declaration//GEN-END:variables
}
