/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ferrinsp
 */
public class MainPage extends javax.swing.JFrame {
    
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;

    
    public void getPO()    {
        try {
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
        stateObj = connObj.createStatement();
        resultObj = stateObj.executeQuery("select t1.orderid,t1.status, t4.companyname, a.name, date_format(t1.expectedby, '%d/%m/%Y') as 'expectedby', \n" +
        "t3.name, b.name, t1.total from purchaseorder t1\n" +
        "inner join job a on t1.job = a.jobid inner join job b on t1.shipto =b.jobid\n" +
        "inner join kbell.user t3 on t1.createdby = t3.userid inner join supplier t4 on t1.supplier = t4.supplierid;");
        purchaseOrder.setModel(DbUtils.resultSetToTableModel(resultObj));
        purchaseOrder.getColumn("orderid").setHeaderValue("Purchase Order Number");
        purchaseOrder.getColumn("companyname").setHeaderValue("Company");
        purchaseOrder.getColumn("name").setHeaderValue("Job");
        purchaseOrder.getColumn("status").setHeaderValue("Status");
        purchaseOrder.getColumn("expectedby").setHeaderValue("Expected By");
        purchaseOrder.getColumn("name").setHeaderValue("Issued By");
        purchaseOrder.getColumn("name").setHeaderValue("Ship To");
        purchaseOrder.getColumn("total").setHeaderValue("Invoice Total");
        purchaseOrder.repaint();
        //meta = resultObj.getMetaData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MainPage() {
        
        this.setResizable(false);
        initComponents();
        getPO();
        
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

        Main_Panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TabbedView = new javax.swing.JTabbedPane();
        view_purchase_orders1 = new javax.swing.JScrollPane();
        purchaseOrder = new javax.swing.JTable();
        MenuBar = new javax.swing.JMenuBar();
        File_List = new javax.swing.JMenu();
        Print = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();
        Jobs = new javax.swing.JMenu();
        create_Job = new javax.swing.JMenuItem();
        view_Job = new javax.swing.JMenuItem();
        Purchase_Order = new javax.swing.JMenu();
        Create_Purchase_Order = new javax.swing.JMenuItem();
        View_Purchase_Order = new javax.swing.JMenuItem();
        Credit_Memo = new javax.swing.JMenu();
        Create_Credit_Memo = new javax.swing.JMenuItem();
        View_Credit_Memo = new javax.swing.JMenuItem();
        Product = new javax.swing.JMenu();
        Create_Product = new javax.swing.JMenuItem();
        View_Product = new javax.swing.JMenuItem();
        Supplier = new javax.swing.JMenu();
        Create_Supplier = new javax.swing.JMenuItem();
        View_Supplier = new javax.swing.JMenuItem();
        Reports = new javax.swing.JMenu();
        Create_Report = new javax.swing.JMenuItem();
        About = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("MainFrame"); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/KBellLogo.png"))); // NOI18N

        TabbedView.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        purchaseOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Purchase Order", "Supplier ", "Job", "Expected Date", "Issued By", "Ship To", "Invoice Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_purchase_orders1.setViewportView(purchaseOrder);

        TabbedView.addTab("Purchase Order Status", view_purchase_orders1);

        javax.swing.GroupLayout Main_PanelLayout = new javax.swing.GroupLayout(Main_Panel);
        Main_Panel.setLayout(Main_PanelLayout);
        Main_PanelLayout.setHorizontalGroup(
            Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_PanelLayout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(273, Short.MAX_VALUE))
            .addGroup(Main_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabbedView, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
        );
        Main_PanelLayout.setVerticalGroup(
            Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main_PanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addComponent(TabbedView, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        File_List.setText("File");

        Print.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        Print.setLabel("Print");
        File_List.add(Print);

        Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        Exit.setText("Close");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        File_List.add(Exit);

        MenuBar.add(File_List);

        Jobs.setText("Jobs");

        create_Job.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        create_Job.setText("New Job");
        create_Job.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_JobActionPerformed(evt);
            }
        });
        Jobs.add(create_Job);

        view_Job.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        view_Job.setText("View/Update Job");
        view_Job.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_JobActionPerformed(evt);
            }
        });
        Jobs.add(view_Job);

        MenuBar.add(Jobs);

        Purchase_Order.setText("Purchase Orders");

        Create_Purchase_Order.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        Create_Purchase_Order.setText("New Purchase Order");
        Create_Purchase_Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Create_Purchase_OrderActionPerformed(evt);
            }
        });
        Purchase_Order.add(Create_Purchase_Order);

        View_Purchase_Order.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        View_Purchase_Order.setText("View/Update Purchase Order");
        View_Purchase_Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                View_Purchase_OrderActionPerformed(evt);
            }
        });
        Purchase_Order.add(View_Purchase_Order);

        MenuBar.add(Purchase_Order);

        Credit_Memo.setText("Credit Memo");

        Create_Credit_Memo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        Create_Credit_Memo.setText("New Credit Memo");
        Credit_Memo.add(Create_Credit_Memo);

        View_Credit_Memo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        View_Credit_Memo.setText("View/Update Credit Memo");
        Credit_Memo.add(View_Credit_Memo);

        MenuBar.add(Credit_Memo);

        Product.setText("Product");

        Create_Product.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK));
        Create_Product.setText("New Item");
        Create_Product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Create_ProductActionPerformed(evt);
            }
        });
        Product.add(Create_Product);

        View_Product.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        View_Product.setText("View/Update Item");
        View_Product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                View_ProductActionPerformed(evt);
            }
        });
        Product.add(View_Product);

        MenuBar.add(Product);

        Supplier.setText("Suppliers");

        Create_Supplier.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        Create_Supplier.setText("New Supplier");
        Create_Supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Create_SupplierActionPerformed(evt);
            }
        });
        Supplier.add(Create_Supplier);

        View_Supplier.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK));
        View_Supplier.setText("View/Update Suppliers");
        View_Supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                View_SupplierActionPerformed(evt);
            }
        });
        Supplier.add(View_Supplier);

        MenuBar.add(Supplier);

        Reports.setText("Reports");

        Create_Report.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        Create_Report.setText("Create Report");
        Reports.add(Create_Report);

        MenuBar.add(Reports);

        About.setText("About");
        MenuBar.add(About);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Main_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(Main_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Create_ProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Create_ProductActionPerformed
        N_U_Item newItem = new N_U_Item();
        newItem.setVisible(true);
    }//GEN-LAST:event_Create_ProductActionPerformed

    private void create_JobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_JobActionPerformed
        Job job = new Job();
        job.setVisible(true);
    }//GEN-LAST:event_create_JobActionPerformed

    private void Create_SupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Create_SupplierActionPerformed
        NSupplier supplier = new NSupplier();
        supplier.setVisible(true);
    }//GEN-LAST:event_Create_SupplierActionPerformed

    private void Create_Purchase_OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Create_Purchase_OrderActionPerformed
        NPurchaseOrder newPO = new NPurchaseOrder();
        newPO.setVisible(true);
    }//GEN-LAST:event_Create_Purchase_OrderActionPerformed

    private void View_Purchase_OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_View_Purchase_OrderActionPerformed
        VPurchaseOrder viewPO = new VPurchaseOrder();
        viewPO.setVisible(true);
    }//GEN-LAST:event_View_Purchase_OrderActionPerformed

    private void view_JobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_JobActionPerformed
        VJob viewJob = new VJob();
        viewJob.setVisible(true);
    }//GEN-LAST:event_view_JobActionPerformed

    private void View_SupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_View_SupplierActionPerformed
        VSupplier viewSupplier = new VSupplier();
        viewSupplier.setVisible(true);
    }//GEN-LAST:event_View_SupplierActionPerformed

    private void View_ProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_View_ProductActionPerformed
        N_U_Item newItem = new N_U_Item();
        newItem.setVisible(true);
    }//GEN-LAST:event_View_ProductActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed
   
    
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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu About;
    private javax.swing.JMenuItem Create_Credit_Memo;
    private javax.swing.JMenuItem Create_Product;
    private javax.swing.JMenuItem Create_Purchase_Order;
    private javax.swing.JMenuItem Create_Report;
    private javax.swing.JMenuItem Create_Supplier;
    private javax.swing.JMenu Credit_Memo;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JMenu File_List;
    private javax.swing.JMenu Jobs;
    private javax.swing.JPanel Main_Panel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem Print;
    private javax.swing.JMenu Product;
    private javax.swing.JMenu Purchase_Order;
    private javax.swing.JMenu Reports;
    private javax.swing.JMenu Supplier;
    private javax.swing.JTabbedPane TabbedView;
    private javax.swing.JMenuItem View_Credit_Memo;
    private javax.swing.JMenuItem View_Product;
    private javax.swing.JMenuItem View_Purchase_Order;
    private javax.swing.JMenuItem View_Supplier;
    private javax.swing.JMenuItem create_Job;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable purchaseOrder;
    private javax.swing.JMenuItem view_Job;
    private javax.swing.JScrollPane view_purchase_orders1;
    // End of variables declaration//GEN-END:variables
}
