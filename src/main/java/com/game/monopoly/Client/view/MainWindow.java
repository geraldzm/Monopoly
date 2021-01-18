package com.game.monopoly.Client.view;

import com.game.monopoly.Client.model.CardWindowType;

import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        /*FrameController controller = FrameController.getInstance();
        controller.openWindow(FramesID.LOGIN);*/

        new CardWindow(1, CardWindowType.FRIEND).setVisible(true);
    }
}
