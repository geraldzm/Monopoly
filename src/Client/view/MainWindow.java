package Client.view;

import com.monopoly.Client.controller.ServerCommunication;
import com.monopoly.common.Comunication.IDMessage;

import javax.swing.*;
import java.io.IOException;

public class MainWindow extends JFrame {


    public static void main(String args[]) {

        //---------- Example

        try {
            ServerCommunication serverCommunication = new ServerCommunication("LocalHost", message -> System.out.println(message.getIdMessage() + " " + message.getString()));
            serverCommunication.startReceiving();
            serverCommunication.sendText("Hello server!", IDMessage.ADMIN); // IDMessage.ADMIN por poner alguno

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("It was not possible to create the connection with the server");
        }
        //---------- END Example

      /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
        */

    }
}
