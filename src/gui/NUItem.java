package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class NUItem extends javax.swing.JFrame {
    
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String [][] supplier = null;
    String [][] category = null;
    String [][] description= null;
    int id =-1;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NUItem(int item) {
        this.setResizable(false);
        initComponents();
        this.id = item;
        getComboCategory();
        getDescriptionCombo();
        getComboSupplier();
        setDatePicker();
        getProductInfo();
    }
    
    private int findCategory(String c){
        int index=-1;
        for (int i=0;i<category.length;i++){
            if(c.equals(category[i][1]))
                index =i;
        }
        return index;
    }
    private int findDescription(String d){
        int index=-1;
        for (int i=0;i<description.length;i++){
            if(d.equals(description[i][1]))
                index =i;
        }
        return index;
    }
    private int findSupplier(String s){
        int index=-1;
        for (int i=0;i<supplier.length;i++){
            if(s.equals(supplier[i][1]))
                index =i;
        }
        return index;
    }
    private void getComboSupplier() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellplumb?useSSL=false", "admin", "1qaz2wsx");
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
                SupplierCombo.addItem(resultObj.getString("companyname"));
                i++;
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void getComboCategory() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellplumb?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select category_ID, description from category;");
            //Dynamically set supplier list size
            resultObj.last();
            category = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                category[i][0] =Integer.toString(resultObj.getInt("category_ID"));
                category[i][1]=resultObj.getString("description");
                i++;
                CategoryCombo.addItem(resultObj.getString("description"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    private void getDescriptionCombo(){
        try{
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellplumb?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select pdescID, productDescription from productdescription order by productDescription ;");
            resultObj.last();
            description = new String[resultObj.getRow()][2];
            resultObj.first();
            int i=0;
            while (resultObj.next()){
                description[i][0] =Integer.toString(resultObj.getInt("pdescID"));
                description[i][1]=resultObj.getString("productDescription");
                i++;
                DescriptionCombo.addItem(resultObj.getString("productDescription"));
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void setDatePicker() {
        lastChanged.setDate(Calendar.getInstance().getTime());
    }
    public void getProductInfo(){
        if (id != -1) {
            try {
                connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellplumb?useSSL=false", "admin", "1qaz2wsx");
                stateObj = connObj.createStatement();
                resultObj = stateObj.executeQuery("SELECT c.description, pd.productDescription, p.manufacturer, p.part_id, p.unitMeasure, s.companyname, p.lastchange, p.price, p.status\n" +
                    "from product p inner join category c on c.category_ID=p.category_id inner join productdescription pd on p.description=pd.pdescID \n" +
                    "inner join supplier s on s.supplierid=p.supplier where p.id ="+id+";");
                while (resultObj.next()){
                    CategoryCombo.setSelectedItem(resultObj.getString("description"));
                    DescriptionCombo.setSelectedItem(resultObj.getString("productDescription"));
                    mfcTextField.setText(resultObj.getString("manufacturer"));
                    partNumTextField.setText(resultObj.getString("part_id"));
                    unitMeasure.setSelectedItem(resultObj.getString("unitMeasure"));
                    SupplierCombo.setSelectedItem(resultObj.getString("companyname"));
                    lastChanged.setDate(resultObj.getDate("lastchange"));
                    priceTextField.setText(resultObj.getString("price"));
                    String contactStatus = resultObj.getString("status");
                    System.out.print(contactStatus);
                    if (contactStatus.equals("Inactive")) {
                        itemInactive.setSelected(true);
                    } else
                        itemActive.setSelected(true);
                } connObj.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public void insertProduct() {
        try {
          String query;  
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellplumb?useSSL=false", "admin", "1qaz2wsx");
        if (id==-1) {
        query= "INSERT into product (category_id,description,part_id,manufacturer,supplier,price,unitMeasure,status,lastchange)"
                + "values(?,?,?,?,?,?,?,?,?);";
        }
        else { 
            query = "Update product set category_id =?,description =?,part_id =?,manufacturer=?,"
                + "supplier=?,price=?,unitMeasure=?,status=?,lastchange=? where id = "+id+";";
        }
        //Needs form checking to ensure default values are not inserted
        if (CategoryCombo.getSelectedItem().equals("Category")){
            JOptionPane.showMessageDialog(null, "Please select a category.");
        }
        else if(DescriptionCombo.getSelectedItem().equals("Description")){
            JOptionPane.showMessageDialog(null, "Please select a description.");
        }
        else if(SupplierCombo.getSelectedItem().equals("Supplier")){
            JOptionPane.showMessageDialog(null, "Please select a supplier.");
        }
        else{
            PreparedStatement preparedStmt =connObj.prepareStatement(query);
            preparedStmt.setInt    (1, Integer.parseInt(category[findCategory((String) CategoryCombo.getSelectedItem())][0]));
            preparedStmt.setInt    (2, Integer.parseInt(description[findDescription((String) DescriptionCombo.getSelectedItem())][0]));
            preparedStmt.setString (3, partNumTextField.getText());
            preparedStmt.setString (4, mfcTextField.getText());
            preparedStmt.setInt    (5, Integer.parseInt(supplier[findSupplier((String) SupplierCombo.getSelectedItem())][0]));
            preparedStmt.setDouble    (6, Double.parseDouble(priceTextField.getText()));
            preparedStmt.setString (7, (String)unitMeasure.getSelectedItem());
            String productStatus= null;
            for (Enumeration<AbstractButton> buttons = statusGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    productStatus = button.getText();
                }
            }
            preparedStmt.setString (8, productStatus);
            java.util.Date lastDate = lastChanged.getDate();
            java.sql.Date sqlDate = new java.sql.Date(lastDate.getTime());
            preparedStmt.setDate (9, sqlDate);
            if (id ==-1) {
                preparedStmt.execute();
                JOptionPane.showMessageDialog(null, "New item was added.");
                connObj.close();
            }
            else {
                preparedStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Item was updated.");
                connObj.close();
                this.dispose();
            }
            connObj.close();
        }
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

        statusGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        supplierLabel = new javax.swing.JLabel();
        mfcLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        mfcTextField = new javax.swing.JTextField();
        priceTextField = new javax.swing.JTextField();
        partLabel = new javax.swing.JLabel();
        partNumTextField = new javax.swing.JTextField();
        SupplierCombo = new javax.swing.JComboBox<>();
        CategoryCombo = new javax.swing.JComboBox<>();
        sizeLabel1 = new javax.swing.JLabel();
        unitMeasure = new javax.swing.JComboBox<>();
        DescriptionCombo = new javax.swing.JComboBox<>();
        status = new javax.swing.JLabel();
        itemActive = new javax.swing.JRadioButton();
        itemInactive = new javax.swing.JRadioButton();
        status1 = new javax.swing.JLabel();
        lastChanged = new org.jdesktop.swingx.JXDatePicker();
        jPanel2 = new javax.swing.JPanel();
        addNewItem = new javax.swing.JButton();
        cancelAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Items");

        descriptionLabel.setText("Description");

        categoryLabel.setText("Category");

        supplierLabel.setText("Supplier");

        mfcLabel.setText("Manufacturer");

        priceLabel.setText("Price");

        mfcTextField.setText("Manufacturer");
        mfcTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mfcTextFieldFocusGained(evt);
            }
        });

        priceTextField.setText("Price");
        priceTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                priceTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                priceTextFieldFocusLost(evt);
            }
        });

        partLabel.setText("Part Number");

        partNumTextField.setText("Part Number");
        partNumTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                partNumTextFieldFocusGained(evt);
            }
        });

        SupplierCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier" }));

        CategoryCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Category" }));

        sizeLabel1.setText("Unit of Measure");

        unitMeasure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BNDL", "BOX", "EA", "FT", "LIFT", "ROLL" }));

        DescriptionCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Description" }));

        status.setText("Status");

        statusGroup.add(itemActive);
        itemActive.setSelected(true);
        itemActive.setText("Active");

        statusGroup.add(itemInactive);
        itemInactive.setText("Inactive");

        status1.setText("Last Changed");

        Date date = new Date();
        lastChanged.setDate(date);

        addNewItem.setText("Save");
        addNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewItemActionPerformed(evt);
            }
        });

        cancelAdd.setText("Cancel");
        cancelAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(addNewItem)
                .addGap(56, 56, 56)
                .addComponent(cancelAdd)
                .addGap(120, 120, 120))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewItem)
                    .addComponent(cancelAdd))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(status1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lastChanged, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(itemActive)
                        .addGap(28, 28, 28)
                        .addComponent(itemInactive))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(sizeLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mfcLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(categoryLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(partLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SupplierCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceTextField)
                            .addComponent(mfcTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(partNumTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(CategoryCombo, 0, 299, Short.MAX_VALUE)
                            .addComponent(unitMeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DescriptionCombo, 0, 299, Short.MAX_VALUE))))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryLabel)
                    .addComponent(CategoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLabel)
                    .addComponent(DescriptionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(partLabel)
                    .addComponent(partNumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mfcLabel)
                    .addComponent(mfcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplierLabel)
                    .addComponent(SupplierCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priceTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeLabel1)
                    .addComponent(unitMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemActive)
                    .addComponent(itemInactive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(status1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastChanged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add New Item", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddActionPerformed
        this.id=1;
        this.dispose();
    }//GEN-LAST:event_cancelAddActionPerformed

    private void addNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewItemActionPerformed
        insertProduct();
    }//GEN-LAST:event_addNewItemActionPerformed

    private void partNumTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partNumTextFieldFocusGained
        if(partNumTextField.getText().equals("Part Number"))
            partNumTextField.setText("");
    }//GEN-LAST:event_partNumTextFieldFocusGained

    private void mfcTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mfcTextFieldFocusGained
        if(mfcTextField.getText().equals("Manufacturer"))
            mfcTextField.setText("");
    }//GEN-LAST:event_mfcTextFieldFocusGained

    private void priceTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_priceTextFieldFocusGained
        if(priceTextField.getText().equals("Price"))
            priceTextField.setText("");
    }//GEN-LAST:event_priceTextFieldFocusGained

    private void priceTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_priceTextFieldFocusLost
        if (priceTextField.getText().equals(""))
            priceTextField.setText("Price");
    }//GEN-LAST:event_priceTextFieldFocusLost

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
            java.util.logging.Logger.getLogger(NUItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NUItem(-1).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CategoryCombo;
    private javax.swing.JComboBox<String> DescriptionCombo;
    private javax.swing.JComboBox<String> SupplierCombo;
    private javax.swing.JButton addNewItem;
    private javax.swing.JButton cancelAdd;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JRadioButton itemActive;
    private javax.swing.JRadioButton itemInactive;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXDatePicker lastChanged;
    private javax.swing.JLabel mfcLabel;
    private javax.swing.JTextField mfcTextField;
    private javax.swing.JLabel partLabel;
    private javax.swing.JTextField partNumTextField;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JLabel sizeLabel1;
    private javax.swing.JLabel status;
    private javax.swing.JLabel status1;
    private javax.swing.ButtonGroup statusGroup;
    private javax.swing.JLabel supplierLabel;
    private javax.swing.JComboBox<String> unitMeasure;
    // End of variables declaration//GEN-END:variables
}
