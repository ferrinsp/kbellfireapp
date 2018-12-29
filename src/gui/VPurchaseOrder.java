package gui;

import java.awt.Color;
import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class VPurchaseOrder extends javax.swing.JFrame {
    
    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;

    private void selectpo()    {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select t1.orderid, t1.status, t4.companyname, a.name, date_format(t1.expectedby, '%m/%d/%Y') as 'expectedby', \n" +
            "t3.name, b.name, t1.total from purchaseorder t1 inner join job a on t1.job = a.jobid inner join job b on t1.shipto =b.jobid\n" +
            "inner join kbellfire.user t3 on t1.createdby = t3.userid inner join supplier t4 on t1.supplier = t4.supplierid where t1.status not Like '%Completed%' and t1.status not like '%Deleted%' order by t1.status,t4.companyname,expectedby;");
            purchaseOrder.setModel(DbUtils.resultSetToTableModel(resultObj));
            purchaseOrder.getColumn("orderid").setHeaderValue("Purchase Order Number");
            purchaseOrder.getColumn("companyname").setHeaderValue("Company");
            purchaseOrder.getColumn("name").setHeaderValue("Job");
            purchaseOrder.getColumn("expectedby").setHeaderValue("Expected By");
            purchaseOrder.getColumn("name").setHeaderValue("Issued By");
            purchaseOrder.getColumn("name").setHeaderValue("Ship To");
            purchaseOrder.getColumn("total").setHeaderValue("Invoice Total");
            purchaseOrder.getColumn("status").setHeaderValue("Status");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public VPurchaseOrder() {
        this.cellRenderer = new AlternatingListCellRenderer();
        this.setResizable(false);
        initComponents();
        selectpo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        updatePOButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        Main_Panel1 = new javax.swing.JPanel();
        view_purchase_orders1 = new javax.swing.JScrollPane();
        purchaseOrder = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View Purchase Order");

        updatePOButton.setText("Update Purchase Order");
        updatePOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePOButtonActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(287, Short.MAX_VALUE)
                .addComponent(updatePOButton)
                .addGap(18, 18, 18)
                .addComponent(closeButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updatePOButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        Main_Panel1.setPreferredSize(new java.awt.Dimension(1026, 560));

        purchaseOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Purchase Order", "Status", "Supplier ", "Job", "Expected Date", "Issued By", "Ship To", "Invoice Total"
            }
        ));
        purchaseOrder.getTableHeader().setReorderingAllowed(false);
        purchaseOrder.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() ==2 ) {
                    UPurchaseOrder updatePO = new UPurchaseOrder((int) purchaseOrder.getValueAt(row,0));
                    updatePO.setVisible(true);
                }
            }
        });
        view_purchase_orders1.setViewportView(purchaseOrder);

        javax.swing.GroupLayout Main_Panel1Layout = new javax.swing.GroupLayout(Main_Panel1);
        Main_Panel1.setLayout(Main_Panel1Layout);
        Main_Panel1Layout.setHorizontalGroup(
            Main_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_Panel1Layout.createSequentialGroup()
                .addComponent(view_purchase_orders1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
                .addContainerGap())
        );
        Main_Panel1Layout.setVerticalGroup(
            Main_Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_Panel1Layout.createSequentialGroup()
                .addComponent(view_purchase_orders1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Main_Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 849, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Main_Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void updatePOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePOButtonActionPerformed
        int[] index = purchaseOrder.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No Purchase Order selected to update.");
        }
        else if (index.length > 1){
            JOptionPane.showMessageDialog(null, "Select only one Purchase Order to update.");
        }
        else{
            UPurchaseOrder updatePO = new UPurchaseOrder((int) purchaseOrder.getValueAt(index[0], 0));
            updatePO.setVisible(true);
            //this.dispose();
        }
    }//GEN-LAST:event_updatePOButtonActionPerformed

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
            java.util.logging.Logger.getLogger(VPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VPurchaseOrder().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Main_Panel1;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable purchaseOrder;
    private javax.swing.JButton updatePOButton;
    private javax.swing.JScrollPane view_purchase_orders1;
    // End of variables declaration//GEN-END:variables
}
