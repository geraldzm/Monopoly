package com.game.monopoly.Client.view;

import com.game.monopoly.Client.controller.ComboBoxPopUpController;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class ComboBoxPopUp {

    public JButton button;
    public JComboBox options;
    public JLabel icon;
    public JPanel panel;
    public int result;
    public JDialog diag;

    public ComboBoxPopUp(int [] tokens) {
        if(tokens.length == 0) return;

        result = -1;
        panel = new JPanel();

        JLabel message = new JLabel("Seleccione su ficha");
        icon = new JLabel();

        Stream<Integer> integerStream = Arrays.stream(tokens).boxed();
        options = new JComboBox(integerStream.toArray());


        button = new JButton("Aceptar");

        panel.add(icon);
        panel.add(message);
        panel.add(options);
        panel.add(button);


        diag = new JDialog();
        diag.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        diag.getContentPane().add(panel);
        diag.setLocationRelativeTo(null);

        new ComboBoxPopUpController(this);

        diag.pack();
        diag.setVisible(true);
    }

}

