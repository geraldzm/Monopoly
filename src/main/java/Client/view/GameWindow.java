package main.java.Client.view;


public class GameWindow extends javax.swing.JFrame {

    public GameWindow() {
        initComponents();
        setSize(new java.awt.Dimension(1500, 1000));
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameContainer = new javax.swing.JPanel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 102, 102));
        setMaximumSize(new java.awt.Dimension(1500, 1000));
        setMinimumSize(new java.awt.Dimension(1500, 1000));
        setName("GameContainer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1500, 1000));
        setResizable(false);
        setSize(new java.awt.Dimension(1500, 1000));
        getContentPane().setLayout(null);

        gameContainer.setBackground(new java.awt.Color(255, 255, 255));
        gameContainer.setLayout(new java.awt.BorderLayout());
        getContentPane().add(gameContainer);
        gameContainer.setBounds(6, 6, 900, 900);
        getContentPane().add(background);
        background.setBounds(0, 0, 1500, 1000);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel background;
    public javax.swing.JPanel gameContainer;
    // End of variables declaration//GEN-END:variables
}
