package main.java.Client.view;

import main.java.Client.controller.ServerCommunication;
import main.java.common.Comunication.Listener;
import main.java.common.Comunication.Message;

import javax.swing.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static main.java.common.Comunication.IDMessage.*;

public class MainWindow extends JFrame {

    static int id;
    static String name;
    static Scanner sc = new Scanner(System.in);
    static boolean admin = false;


    private static int admin(){
        System.out.println("Digite la cantidad de jugadores que quiere: ");
        int n = sc.nextInt();
        admin = true;
        return n;
    }

    private static int doneAction(){
        System.out.println("Digite un numero porque sí: ");
        return sc.nextInt();
    }

    public static void dice(){

    }


    public static void main(String args[]) {

        //---------- Example

        ServerCommunication serverCommunication = ServerCommunication.getServerCommunication();
        serverCommunication.removeReceiverFilter();

        Listener chat = m -> System.out.println(m.getString());
        serverCommunication.setChatListener(chat);

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
                            serverCommunication.sendMessageChat("Hola a todos " + seconds);
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

                case ID -> {
                    System.out.println("Asignado: "+ m.getNumber());
                    id = m.getNumber();
                    serverCommunication.sendMessage(DONE);
                }

                case NAME -> {
                    System.out.println("El server solicita un nombre, digitelo: ");
                    String name = sc.next();
                    serverCommunication.sendMessage(new Message(id, name, RESPONSE));
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
