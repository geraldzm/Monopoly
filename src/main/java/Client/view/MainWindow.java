package main.java.Client.view;

import main.java.Client.controller.ServerCommunication;
import main.java.common.Comunication.Listener;
import main.java.common.Comunication.Message;

import javax.swing.*;
import java.util.Random;
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

        Listener playing = mens -> { // listener durante el juego
            switch (mens.getIdMessage()) {
                case DICE -> {
                    System.out.println("Tirando dados.....");
                    System.out.println("resultado: " + mens.getNumbers()[0] + " " +mens.getNumbers()[1] + " "+ mens.getNumbers()[2]);
                    serverCommunication.sendMessage(DONE);
                }

                case END -> {
                    System.out.println("Juego finalizado... ");
                    System.out.println("El server se esta desconectando..");
                    serverCommunication.closeConnection();
                }

                case ID -> {
                    System.out.println("Asignado: "+ mens.getNumber());
                    id = mens.getNumber();
                    serverCommunication.sendMessage(DONE);
                }

                case NAME -> {
                    System.out.println("El server solicita un nombre, digitelo: ");

                    int leftLimit = 97; // letter 'a'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 10;
                    Random random = new Random();

                    String generatedString = random.ints(leftLimit, rightLimit + 1)
                            .limit(targetStringLength)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();
                    System.out.println(generatedString);

                    serverCommunication.sendMessage(new Message(id, generatedString, RESPONSE));
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

    }
}
