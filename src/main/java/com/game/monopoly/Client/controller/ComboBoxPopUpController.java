package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.ComboBoxPopUp;
import com.game.monopoly.common.Comunication.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static com.game.monopoly.common.Comunication.IDMessage.RESPONSE;

public class ComboBoxPopUpController {

    public ComboBoxPopUpController(ComboBoxPopUp comboBoxPopUp) {

        comboBoxPopUp.options.setAction(new AbstractAction() { // when select item
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    comboBoxPopUp.icon.setIcon(Utils.getComponentIcon("token_"+ comboBoxPopUp.options.getSelectedItem() +".png", 50, 50));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        comboBoxPopUp.options.setSelectedIndex(0);

        comboBoxPopUp.button.addActionListener(actionEvent -> {
            System.out.println("Eligio la ficha: " + comboBoxPopUp.options.getSelectedItem());
            try {
                comboBoxPopUp.diag.dispose();
                ServerCommunication.getServerCommunication().sendMessage(new Message((Integer) comboBoxPopUp.options.getSelectedItem(), RESPONSE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
