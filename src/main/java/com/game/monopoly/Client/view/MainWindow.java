package com.game.monopoly.Client.view;


import com.game.monopoly.Client.controller.FrameController;
import com.game.monopoly.Client.controller.FramesID;

import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        FrameController controller = FrameController.getInstance();
        controller.openWindow(FramesID.LOGIN);
    }
}
