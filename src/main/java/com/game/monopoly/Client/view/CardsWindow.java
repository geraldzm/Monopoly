package com.game.monopoly.Client.view;


public class CardsWindow extends javax.swing.JFrame {

    public CardsWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbPlayers = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnOpen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(306, 433));
        setMinimumSize(new java.awt.Dimension(306, 433));
        setPreferredSize(new java.awt.Dimension(306, 433));
        setResizable(false);
        setSize(new java.awt.Dimension(306, 433));
        getContentPane().setLayout(null);

        cbPlayers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cbPlayers);
        cbPlayers.setBounds(30, 120, 250, 40);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Consultar propiedades");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 20, 180, 40);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Consultar");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(90, 230, 110, 40);
        getContentPane().add(btnOpen);
        btnOpen.setBounds(70, 230, 150, 40);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Salir");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 300, 110, 40);
        getContentPane().add(btnExit);
        btnExit.setBounds(70, 300, 150, 40);
        getContentPane().add(background);
        background.setBounds(0, 0, 310, 430);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel background;
    public javax.swing.JLabel btnExit;
    public javax.swing.JLabel btnOpen;
    public javax.swing.JComboBox<String> cbPlayers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
