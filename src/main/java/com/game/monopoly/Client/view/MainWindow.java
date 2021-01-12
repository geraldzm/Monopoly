package com.game.monopoly.Client.view;

import java.io.IOException;

import javax.swing.*;
import com.game.monopoly.Client.controller.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        FrameController controller = FrameController.getInstance();
        
        controller.openWindow(FramesID.LOGIN);
    }
}
