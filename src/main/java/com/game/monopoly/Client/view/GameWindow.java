package com.game.monopoly.Client.view;


public class GameWindow extends javax.swing.JFrame {

    public GameWindow() {
        initComponents();
        setSize(new java.awt.Dimension(1500, 1000));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        btnDice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnTurn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSend = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCards = new javax.swing.JLabel();
        lbGeneralInfo = new javax.swing.JLabel();
        gameContainer = new javax.swing.JPanel();
        tfChat = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taLog = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 102, 102));
        setMinimumSize(new java.awt.Dimension(1550, 1035));
        setName("GameContainer"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(1550, 1035));
        getContentPane().setLayout(null);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Tirar dados");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(360, 950, 110, 40);

        btnDice.setBackground(new java.awt.Color(0, 0, 0));
        btnDice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDice.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(btnDice);
        btnDice.setBounds(350, 950, 130, 40);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Terminar turno");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(190, 950, 130, 40);

        btnTurn.setBackground(new java.awt.Color(0, 0, 0));
        btnTurn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTurn.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(btnTurn);
        btnTurn.setBounds(190, 950, 130, 40);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Enviar");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(950, 950, 90, 40);

        btnSend.setBackground(new java.awt.Color(0, 0, 0));
        btnSend.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSend.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(btnSend);
        btnSend.setBounds(930, 950, 130, 40);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Propiedades");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 950, 90, 40);

        btnCards.setBackground(new java.awt.Color(0, 0, 0));
        btnCards.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCards.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(btnCards);
        btnCards.setBounds(10, 950, 130, 40);

        lbGeneralInfo.setBackground(new java.awt.Color(255, 255, 255));
        lbGeneralInfo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbGeneralInfo.setForeground(new java.awt.Color(0, 0, 0));
        lbGeneralInfo.setText("Dinero:");
        lbGeneralInfo.setOpaque(true);
        getContentPane().add(lbGeneralInfo);
        lbGeneralInfo.setBounds(10, 910, 900, 30);

        gameContainer.setBackground(new java.awt.Color(255, 255, 255));
        gameContainer.setLayout(new java.awt.BorderLayout());
        getContentPane().add(gameContainer);
        gameContainer.setBounds(10, 10, 900, 900);
        getContentPane().add(tfChat);
        tfChat.setBounds(930, 900, 550, 40);

        taLog.setEditable(false);
        taLog.setBackground(new java.awt.Color(255, 255, 255));
        taLog.setColumns(20);
        taLog.setForeground(new java.awt.Color(0, 0, 0));
        taLog.setRows(5);
        jScrollPane2.setViewportView(taLog);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(930, 60, 550, 390);

        taChat.setEditable(false);
        taChat.setBackground(new java.awt.Color(255, 255, 255));
        taChat.setColumns(20);
        taChat.setForeground(new java.awt.Color(0, 0, 0));
        taChat.setRows(5);
        jScrollPane1.setViewportView(taChat);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(930, 500, 550, 400);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Chat");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(930, 460, 90, 40);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Bitacora");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(930, 10, 90, 40);
        getContentPane().add(background);
        background.setBounds(0, 0, 1550, 1070);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel background;
    public javax.swing.JLabel btnCards;
    public javax.swing.JLabel btnDice;
    public javax.swing.JLabel btnSend;
    public javax.swing.JLabel btnTurn;
    public javax.swing.JPanel gameContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lbGeneralInfo;
    public javax.swing.JTextArea taChat;
    public javax.swing.JTextArea taLog;
    public javax.swing.JTextField tfChat;
    // End of variables declaration//GEN-END:variables
}
