package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class MainPage extends javax.swing.JFrame {
    
    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    public static double tax = 0.0;
    
    private void getDashboard() {
        try {
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
        stateObj = connObj.createStatement();
        resultObj = stateObj.executeQuery("select Count(*) from purchaseorder where status Like '%Issued%';");
        while (resultObj.next()){
            issuedCount.setText(resultObj.getString("Count(*)"));
        }
        resultObj = stateObj.executeQuery("select Count(*) from purchaseorder where status Like '%Pending%';");
        while (resultObj.next()){
            pendingCount.setText(resultObj.getString("Count(*)"));
        }
        resultObj = stateObj.executeQuery("select Count(*) from purchaseorder where status Like '%Back%';");
        while (resultObj.next()){
            boCount.setText(resultObj.getString("Count(*)"));
        }
        connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getPOStatus()    {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select t1.orderid, t1.status, t4.companyname, a.name, date_format(t1.expectedby, '%m/%d/%Y') as 'expectedby', \n" +
            "t3.name, b.name, t1.total from purchaseorder t1 inner join job a on t1.job = a.jobid inner join job b on t1.shipto =b.jobid\n" +
            "inner join kbell.user t3 on t1.createdby = t3.userid inner join supplier t4 on t1.supplier = t4.supplierid where t1.status not Like '%Completed%' order by t1.status,t4.companyname;");
            purchaseOrder.setModel(DbUtils.resultSetToTableModel(resultObj));
            purchaseOrder.getColumn("orderid").setHeaderValue("Purchase Order Number");
            purchaseOrder.getColumn("companyname").setHeaderValue("Company");
            purchaseOrder.getColumn("name").setHeaderValue("Job");
            purchaseOrder.getColumn("expectedby").setHeaderValue("Expected By");
            purchaseOrder.getColumn("name").setHeaderValue("Issued By");
            purchaseOrder.getColumn("name").setHeaderValue("Ship To");
            purchaseOrder.getColumn("total").setHeaderValue("Invoice Total");
            purchaseOrder.getColumn("status").setHeaderValue("Status");
            purchaseOrder.repaint();
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getTax() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select tax from tax;");
            while (resultObj.next()){
                taxValue.setText(Double.toString(resultObj.getDouble("tax")));
                tax = resultObj.getDouble("tax");
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MainPage() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getDashboard();
        getPOStatus();
        getTax();
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xsize = (int) tk.getScreenSize().getWidth();
        int ysize = (int) tk.getScreenSize().getHeight();
        this.setSize(xsize, ysize-10);
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
        TabbedView = new javax.swing.JTabbedPane();
        view_purchase_orders = new javax.swing.JScrollPane();
        purchaseOrder = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        boCount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        issuedCount = new javax.swing.JTextField();
        pendingCount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        taxValue = new javax.swing.JTextField();
        refresh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        updatePOMainButton = new javax.swing.JButton();
        MenuBar = new javax.swing.JMenuBar();
        File_List = new javax.swing.JMenu();
        logOffFileMenu = new javax.swing.JMenuItem();
        Jobs = new javax.swing.JMenu();
        create_Job = new javax.swing.JMenuItem();
        view_Job = new javax.swing.JMenuItem();
        Purchase_Order = new javax.swing.JMenu();
        completedPOs = new javax.swing.JMenuItem();
        Create_Purchase_Order = new javax.swing.JMenuItem();
        View_Purchase_Order = new javax.swing.JMenuItem();
        Credit_Memo = new javax.swing.JMenu();
        completedCM = new javax.swing.JMenuItem();
        newCreditMemo = new javax.swing.JMenuItem();
        viewCreditMemo = new javax.swing.JMenuItem();
        Product = new javax.swing.JMenu();
        miscFunction = new javax.swing.JMenuItem();
        newProduct = new javax.swing.JMenuItem();
        quoteMenu = new javax.swing.JMenuItem();
        productHistory = new javax.swing.JMenuItem();
        View_Product = new javax.swing.JMenuItem();
        Reports = new javax.swing.JMenu();
        jobListMenuItem = new javax.swing.JMenuItem();
        productListMenuItem = new javax.swing.JMenuItem();
        supplierListMenuItem = new javax.swing.JMenuItem();
        Supplier = new javax.swing.JMenu();
        Create_Supplier = new javax.swing.JMenuItem();
        View_Supplier = new javax.swing.JMenuItem();
        About = new javax.swing.JMenu();
        Software = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("KBell Plumbing");
        setName("MainFrame"); // NOI18N
        setResizable(false);

        TabbedView.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N

        purchaseOrder.setAutoCreateRowSorter(true);
        purchaseOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Purchase Order", "Supplier ", "Job", "Expected Date", "Issued By", "Ship To", "Invoice Total", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        view_purchase_orders.setViewportView(purchaseOrder);

        TabbedView.addTab("Purchase Order Status", view_purchase_orders);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/KBellLogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel1)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Back Order:");

        boCount.setEditable(false);

        jLabel3.setText("Issued:");

        issuedCount.setEditable(false);

        pendingCount.setEditable(false);

        jLabel4.setText("Pending:");

        jLabel5.setText("Tax:");

        taxValue.setEditable(false);

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(taxValue)
                            .addComponent(boCount)
                            .addComponent(issuedCount)
                            .addComponent(pendingCount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(boCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(issuedCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(pendingCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(taxValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refresh)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout Main_PanelLayout = new javax.swing.GroupLayout(Main_Panel);
        Main_Panel.setLayout(Main_PanelLayout);
        Main_PanelLayout.setHorizontalGroup(
            Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main_PanelLayout.createSequentialGroup()
                        .addComponent(TabbedView)
                        .addContainerGap())
                    .addGroup(Main_PanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        Main_PanelLayout.setVerticalGroup(
            Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Main_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(TabbedView, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        updatePOMainButton.setText("Update Purchase Order");
        updatePOMainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePOMainButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updatePOMainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updatePOMainButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        File_List.setText("File");

        logOffFileMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        logOffFileMenu.setText("Logoff");
        logOffFileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOffFileMenuActionPerformed(evt);
            }
        });
        File_List.add(logOffFileMenu);

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

        completedPOs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK));
        completedPOs.setText("Completed Purchase Order's");
        completedPOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedPOsActionPerformed(evt);
            }
        });
        Purchase_Order.add(completedPOs);

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

        completedCM.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK));
        completedCM.setText("Completed Credit Memo's");
        completedCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completedCMActionPerformed(evt);
            }
        });
        Credit_Memo.add(completedCM);

        newCreditMemo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        newCreditMemo.setText("New Credit Memo");
        newCreditMemo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCreditMemoActionPerformed(evt);
            }
        });
        Credit_Memo.add(newCreditMemo);

        viewCreditMemo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        viewCreditMemo.setText("View Credit Memo");
        viewCreditMemo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewCreditMemoActionPerformed(evt);
            }
        });
        Credit_Memo.add(viewCreditMemo);

        MenuBar.add(Credit_Memo);

        Product.setText("Product");

        miscFunction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.SHIFT_MASK));
        miscFunction.setText("Misc Functions");
        miscFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miscFunctionActionPerformed(evt);
            }
        });
        Product.add(miscFunction);

        newProduct.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK));
        newProduct.setText("New Item");
        newProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProductActionPerformed(evt);
            }
        });
        Product.add(newProduct);

        quoteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.SHIFT_MASK));
        quoteMenu.setText("New Quote");
        quoteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quoteMenuActionPerformed(evt);
            }
        });
        Product.add(quoteMenu);

        productHistory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        productHistory.setText("Product History");
        productHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productHistoryActionPerformed(evt);
            }
        });
        Product.add(productHistory);

        View_Product.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        View_Product.setText("View/Update Item");
        View_Product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                View_ProductActionPerformed(evt);
            }
        });
        Product.add(View_Product);

        MenuBar.add(Product);

        Reports.setText("Reports");

        jobListMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jobListMenuItem.setText("Job List");
        jobListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jobListMenuItemActionPerformed(evt);
            }
        });
        Reports.add(jobListMenuItem);

        productListMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        productListMenuItem.setText("Product List");
        productListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productListMenuItemActionPerformed(evt);
            }
        });
        Reports.add(productListMenuItem);

        supplierListMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        supplierListMenuItem.setText("Supplier List");
        supplierListMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierListMenuItemActionPerformed(evt);
            }
        });
        Reports.add(supplierListMenuItem);

        MenuBar.add(Reports);

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

        About.setText("About");

        Software.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        Software.setText("Software Information");
        Software.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SoftwareActionPerformed(evt);
            }
        });
        About.add(Software);

        MenuBar.add(About);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Main_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Main_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProductActionPerformed
        NUItem newItem = new NUItem(-1);
        newItem.setVisible(true);
    }//GEN-LAST:event_newProductActionPerformed

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
        VItem vItem = new VItem();
        vItem.setVisible(true);
    }//GEN-LAST:event_View_ProductActionPerformed

    private void logOffFileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOffFileMenuActionPerformed
        this.dispose();
        
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_logOffFileMenuActionPerformed

    private void SoftwareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SoftwareActionPerformed
        AboutTheSoftware about = new AboutTheSoftware();
        about.setVisible(true);
    }//GEN-LAST:event_SoftwareActionPerformed

    private void newCreditMemoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCreditMemoActionPerformed
        NCreditMemo creditMemo = new NCreditMemo();
        creditMemo.setVisible(true);
    }//GEN-LAST:event_newCreditMemoActionPerformed

    private void viewCreditMemoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewCreditMemoActionPerformed
        VCreditMemo viewMemo = new VCreditMemo();
        viewMemo.setVisible(true);
    }//GEN-LAST:event_viewCreditMemoActionPerformed

    private void miscFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miscFunctionActionPerformed
        NDescription nDesc = new NDescription();
        nDesc.setVisible(true);
    }//GEN-LAST:event_miscFunctionActionPerformed

    private void jobListMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jobListMenuItemActionPerformed
        try {
            InputStream is = getClass().getResourceAsStream("/Reports/Jobs.jrxml");     
            JasperDesign jd= JRXmlLoader.load(is);
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");

            //set parameters
            Map map = new HashMap();
            
            //compile report
            JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);

            //view report to UI
            JasperViewer.viewReport(jasperPrint, false);                   
        } catch (SQLException | JRException ex) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jobListMenuItemActionPerformed

    private void productListMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productListMenuItemActionPerformed
        ProductReportDates prd = new ProductReportDates();
        prd.setVisible(true);
    }//GEN-LAST:event_productListMenuItemActionPerformed

    private void supplierListMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierListMenuItemActionPerformed
        try {
            InputStream is = getClass().getResourceAsStream("/Reports/Suppliers.jrxml");            
            JasperDesign jd= JRXmlLoader.load(is);
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");

            //set parameters
            Map map = new HashMap();
            
            //compile report
            JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);

            //view report to UI
            JasperViewer.viewReport(jasperPrint, false);                   
        } catch (SQLException | JRException ex) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_supplierListMenuItemActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        getDashboard();
        getPOStatus();
        getTax();
    }//GEN-LAST:event_refreshActionPerformed

    private void updatePOMainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePOMainButtonActionPerformed
        int[] index = purchaseOrder.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No item selected to update.");
        }
        else if (index.length > 1){
            JOptionPane.showMessageDialog(null, "Select only one item to update.");
        }
        else{
            UPurchaseOrder updatePO = new UPurchaseOrder((int) purchaseOrder.getValueAt(index[0],0));
            updatePO.setVisible(true);
        }
    }//GEN-LAST:event_updatePOMainButtonActionPerformed

    private void quoteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quoteMenuActionPerformed
        NQuote quote = new NQuote();
        quote.setVisible(true);
    }//GEN-LAST:event_quoteMenuActionPerformed

    private void productHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productHistoryActionPerformed
        ProductHistory ph = new ProductHistory();
        ph.setVisible(true);
    }//GEN-LAST:event_productHistoryActionPerformed

    private void completedPOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedPOsActionPerformed
        VCompletePOs cPO = new VCompletePOs();
        cPO.setVisible(true);
    }//GEN-LAST:event_completedPOsActionPerformed

    private void completedCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completedCMActionPerformed
        VCompleteCMs cCM = new VCompleteCMs();
        cCM.setVisible(true);
    }//GEN-LAST:event_completedCMActionPerformed
   
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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new MainPage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu About;
    private javax.swing.JMenuItem Create_Purchase_Order;
    private javax.swing.JMenuItem Create_Supplier;
    private javax.swing.JMenu Credit_Memo;
    private javax.swing.JMenu File_List;
    private javax.swing.JMenu Jobs;
    private javax.swing.JPanel Main_Panel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu Product;
    private javax.swing.JMenu Purchase_Order;
    private javax.swing.JMenu Reports;
    private javax.swing.JMenuItem Software;
    private javax.swing.JMenu Supplier;
    private javax.swing.JTabbedPane TabbedView;
    private javax.swing.JMenuItem View_Product;
    private javax.swing.JMenuItem View_Purchase_Order;
    private javax.swing.JMenuItem View_Supplier;
    private javax.swing.JTextField boCount;
    private javax.swing.JMenuItem completedCM;
    private javax.swing.JMenuItem completedPOs;
    private javax.swing.JMenuItem create_Job;
    private javax.swing.JTextField issuedCount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JMenuItem jobListMenuItem;
    private javax.swing.JMenuItem logOffFileMenu;
    private javax.swing.JMenuItem miscFunction;
    private javax.swing.JMenuItem newCreditMemo;
    private javax.swing.JMenuItem newProduct;
    private javax.swing.JTextField pendingCount;
    private javax.swing.JMenuItem productHistory;
    private javax.swing.JMenuItem productListMenuItem;
    private javax.swing.JTable purchaseOrder;
    private javax.swing.JMenuItem quoteMenu;
    private javax.swing.JButton refresh;
    private javax.swing.JMenuItem supplierListMenuItem;
    private javax.swing.JTextField taxValue;
    private javax.swing.JButton updatePOMainButton;
    private javax.swing.JMenuItem viewCreditMemo;
    private javax.swing.JMenuItem view_Job;
    private javax.swing.JScrollPane view_purchase_orders;
    // End of variables declaration//GEN-END:variables
}
