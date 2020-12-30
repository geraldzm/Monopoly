package Client.view;

import Client.controller.ServerCommunication;
import common.Comunication.IDMessage;
import common.Comunication.Listener;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static common.Comunication.IDMessage.*;

public class MainWindow extends JFrame {

    private static int admin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite la cantidad de jugadores que quiere: ");
        int n = sc.nextInt();
        sc.close();
        return n;
    }

    private static int doneAction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite un numero porque sí: ");
        int n = sc.nextInt();
        sc.close();
        return n;
    }

    public static void dice(){

    }


    public static void main(String args[]) {

        //---------- Example
        try {

            ServerCommunication serverCommunication = new ServerCommunication("LocalHost", null);
            serverCommunication.removeReceiverFilter();

            Listener playing = m -> { // listener durante el juego
                switch (m.getIdMessage()){
                    case DICE -> {
                        System.out.println("Tirando dados....." );

                        int seconds = (int)(Math.random() * 3000); // simulando un delate
                        new Timer().schedule(new TimerTask() {// simulando un delate
                            @Override
                            public void run() {
                                System.out.println("Resultado: " + m.getNumber());
                                serverCommunication.sendMessage(DONE);
                            }
                        }, seconds);
                    }
                    case MOVE -> {
                        System.out.println("Moviéndose....." );

                        int seconds = (int)(Math.random() * 3000); // simulando un delate
                        new Timer().schedule(new TimerTask() {// simulando un delate
                            @Override
                            public void run() {
                                System.out.println("Me movi a la casilla: " + m.getNumber());
                                serverCommunication.sendMessage(DONE);
                            }
                        }, seconds);
                    }

                    case END -> {
                        System.out.println("Juego finalizado... ");
                        System.out.println("El server se esta desconectando..");
                        serverCommunication.closeConnection();
                    }
                }
            };


            serverCommunication.setListener(message -> { //listener mientras se conecta
                switch (message.getIdMessage()){
                    case ADMIN -> serverCommunication.sendInt(admin(), RESPONSE);
                    case REJECTED -> System.out.println("Another looser rejected!");
                    case ACCEPTED -> System.out.println("Accepted!\nwaiting for game to start");
                    case STARTED -> {
                        System.out.println("El juego ha comenzado!!");
                        serverCommunication.setListener(playing);
                        serverCommunication.sendMessage(DONE);
                    }
                }
            });


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
