package main.java.Client.view;

public class LoginWindow extends javax.swing.JFrame {

    public LoginWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        lbMonopoly = new javax.swing.JLabel();
        tfUserName = new javax.swing.JTextField();
        cbTokens = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnPlay = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 600));
        getContentPane().setLayout(null);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Jugar");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(80, 400, 60, 25);
        getContentPane().add(lbMonopoly);
        lbMonopoly.setBounds(50, 40, 400, 80);
        getContentPane().add(tfUserName);
        tfUserName.setBounds(40, 210, 420, 30);

        cbTokens.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cbTokens);
        cbTokens.setBounds(40, 320, 420, 30);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ficha");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 270, 220, 30);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre de usuario");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 160, 230, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Salir");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(80, 480, 60, 25);
        getContentPane().add(btnExit);
        btnExit.setBounds(40, 470, 140, 50);
        getContentPane().add(btnPlay);
        btnPlay.setBounds(40, 390, 140, 50);

        background.setMaximumSize(new java.awt.Dimension(500, 600));
        background.setMinimumSize(new java.awt.Dimension(500, 600));
        background.setPreferredSize(new java.awt.Dimension(500, 600));
        getContentPane().add(background);
        background.setBounds(0, 0, 500, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel background;
    public javax.swing.JLabel btnExit;
    public javax.swing.JLabel btnPlay;
    private javax.swing.JComboBox<String> cbTokens;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel lbMonopoly;
    private javax.swing.JTextField tfUserName;
    // End of variables declaration//GEN-END:variables
}
