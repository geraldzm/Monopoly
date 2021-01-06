package Client.view;

import Client.controller.ServerCommunication;
import common.Comunication.Listener;

import javax.swing.*;
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
    }
}
