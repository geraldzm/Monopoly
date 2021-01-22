package com.game.monopoly.Server;

import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.PropertyCard;
import com.game.monopoly.common.Comunication.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static com.game.monopoly.common.Comunication.IDMessage.*;

public class Bank {
    public int house, hotel;

    private Stack<Integer> throne;
    private Stack<Integer> valar;
    private final Server server;

    public Bank(Server server) {
        house = 32;
        hotel = 12;
        this.server = server;
        throne = new Stack<>();
        valar = new Stack<>();

        // insert random cards
        for (int i = 42; i < 58; i++) {
            throne.push(i);
        }

        for (int i = 58; i < 73; i++) {
            valar.push(i);
        }

    }

    public void actionValar(Player current, ArrayList<Player> allPlayers){
        if(valar.isEmpty()) return; // no hay mas cartas

        Card card = CardFactory.getCard(throne.pop());

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(allPlayers));
        ActionQueue single = new ActionQueue(current);

        server.quickActionQueue(new ArrayList<>(Arrays.asList(current)), new Message(card.getId(), THRONE));

        switch (card.getId()){
            case 58 -> {
                if(current.getPosition() != 23) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 23}, MOVE));
                if(current.getPosition() > 23) single.addAction(new Message(200, "Se le da $200 por pasar GO", GIVEMONEY));
                current.setPosition(23);
            }
            case 59 -> {
                // page $25 por casa y $100 por hotel
                int houses = current.getCards()
                        .stream()
                        .mapToInt(integer -> ((PropertyCard) CardFactory.getCard(integer)).getHouseAmount() )
                        .sum();
                int hotels = current.getCards()
                        .stream()
                        .mapToInt(integer -> ((PropertyCard) CardFactory.getCard(integer)).getHotelAmount())
                        .sum();
                takeMoneyAbstraction(current, houses*25+hotels*100, "Pague $25 para cada casa y $100 para cada hotel de su propiedad.");
            }
            case 60 -> {
                int positionMinusThree = ((current.getId()-3) < 0 ? (40+(current.getId()-3)) : (current.getId()-3) ) % 40;
                actionQueue.addAction(new Message(new int[]{current.getId(), 0, positionMinusThree}, MOVE));
            }
            case 61 -> single.addAction(new Message(OUTOFJAILCARD));
            case 62 ->{
                if(current.getPosition() != 5) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 5}, MOVE));
                if(current.getPosition() > 5) single.addAction(new Message(200, "Se le da $200 por pasar GO", GIVEMONEY));
                current.setPosition(5);
            }
            case 63, 64 -> {
                double position = current.getPosition();

                if(position < 5) position = 5;
                else if(position < 15) position = 15;
                else if(position < 25) position = 25;
                else if(position < 35) position = 35;
                else position = 0;

                Player landLord = allPlayers
                        .stream()
                        .filter(player -> player.getCards().contains(card.getId()))
                        .findFirst()
                        .orElseGet(null);

                if(landLord != null){ // si tiene due;o
                    int amountOfRailway = (int) landLord.getCards()
                            .stream().filter(i -> i == 5 || i == 15 || i == 25 || i == 35) // filtramos por ferrocarril
                            .count();

                    int toPay = ((PropertyCard) CardFactory.getCard((int) position)).getPrices()[amountOfRailway - 1];
                    single.addAction(new Message(toPay*2, TAKEMONEY));
                }else{
                    single.addAction(new Message(((PropertyCard) CardFactory.getCard((int) position)).getPriceToPay(), GIVEMONEY));
                }

            }
            case 65 -> {
                if(current.getPosition() != 39) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 39}, MOVE));
                current.setPosition(39);
            }
            case 66 -> single.addAction(new Message(50, GIVEMONEY));
            case 67 -> {

                current.rollDices();
                single.addAction(new Message(current.getDices(), DICE));

                if(current.getPosition() < 12) {
                    if(current.getPosition() != 12) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 12}, MOVE));
                }else {
                    if(current.getPosition() != 28) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 28}, MOVE));
                }

                Player landLord = allPlayers
                        .stream()
                        .filter(player -> player.getCards().contains(card.getId()))
                        .findFirst()
                        .orElseGet(null);

                int amount = current.getDices()[2] * 10;

                if(landLord != null) { // due;o
                    current.reduceMoney(amount, current.getName()+ " le ha pagado $" + amount + " a " + landLord.getName());
                    landLord.addCash(amount, current.getName()+ " le ha pagado $" + amount + " a " + landLord.getName());
                }else { // era del banco
                    current.addCash(amount, "El banco le ha pagado $" + amount + " a " + current.getName());
                }

            }
            case 68 -> current.addCash(150, "El banco le da $150");
            case 69 -> {
                // para la carcel
                if(current.getPosition() != 10) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 10}, MOVE));
                current.toJail();
                server.quickActionQueue(new ArrayList<>(allPlayers), new Message("El jugador " + current.getName() +" se va a la carcel", TOJAIL));
            }
            case 70 -> {
                ArrayList<Player> playersWithoutCurrent = new ArrayList<>(allPlayers);
                playersWithoutCurrent.remove(current);
                server.quickActionQueue(playersWithoutCurrent, new Message(50, GIVEMONEY));
                takeMoneyAbstraction(current, 50*(allPlayers.size()-1), "Pague $50 a cada uno de los jugadores.");
            }
            case 71 -> {
                current.setPosition(0);
                single.addAction(new Message(new int[]{current.getId(), 1, 0}, MOVE));
                current.addCash(200, "Se le acredita $200 por pasar GO");
            }
            case 72 -> takeMoneyAbstraction(current, 15, "Páguese para los pobres un impuesto de $15.");
            case 73 -> {
                if(current.getPosition() != 11) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 11}, MOVE));
                if(current.getPosition() > 11) current.addCash(200, "Se le da $200 por pasar GO");
                current.setPosition(11);
            }
        }

        actionQueue.executeQueue();
        single.executeQueue();
    }


    public void actionThrone(Player current, ArrayList<Player> allPlayers){

        if(throne.isEmpty()) return; // no hay mas cartas

        int pos = throne.pop();
        System.out.println("Carta: " + pos);

        Card card = CardFactory.getCard(pos);

        ActionQueue actionQueue = new ActionQueue(new ArrayList<>(allPlayers));
        ActionQueue single = new ActionQueue(current);

        server.quickActionQueue(new ArrayList<>(Arrays.asList(current)), new Message(card.getId(), THRONE));

        switch(card.getId()) {
            //Arca Comunal
            case 42 -> takeMoneyAbstraction(current, 150, "Hay que pagar su Contribución de $150 para las Escuelas.");
            case 43 -> current.addCash(20, "Cobrar $20 del banco");
            case 44 -> current.addCash(100, "Usted hereda $100");
            case 45 -> takeMoneyAbstraction(current, 150, "Su hospital le exige un pago de $100.");
            case 46 -> current.addCash(200, "Error del banco en favor de usted. Cobrar $200");
            case 47 -> {
                // 50 de cada jugador
                allPlayers.forEach(p -> takeMoneyAbstraction(p, 50, "Page $50 a " +current.getName()));
                current.addCash(50*(allPlayers.size()-1), "Cada jugador le paga $50");
            }
            case 48 -> {
                // page 40 por casa y 115 por hotel
                int houses = current.getCards()
                        .stream()
                        .mapToInt(integer -> ((PropertyCard) CardFactory.getCard(integer)).getHouseAmount() )
                        .sum();
                int hotels = current.getCards()
                        .stream()
                        .mapToInt(integer -> ((PropertyCard) CardFactory.getCard(integer)).getHotelAmount())
                        .sum();

                System.out.println( current.getName()+"  Usted tiene: " + hotels + " hoteles y " + houses + " casas" + " va a pagar "  + houses*40+hotels*115);
                current.reduceMoney(houses*40+hotels*115, "Pague $40 por cada casa y $115 por cada hotel.");
            }
            case 49 -> current.addCash( 25, "Le toca recibir $25 por servicios prestados.");
            case 50 -> current.addCash( 10, "Usted ha ganado el segundo premio en un certamen de belleza. Puede cobrar $10.");
            case 51 ->  current.addCash(45, "$45 de la venta de sus acciones.");
            case 52 -> takeMoneyAbstraction(current, 50, "Páguense al banco $50 para su médico.");
            case 53 -> current.addCash(100, "Póliza del banco le pagara $100");
            case 54 -> current.addCash(100, "ahorros del banco le pagara $100");
            case 55 -> single.addAction(new Message(OUTOFJAILCARD));
            case 56 -> {
                // para la carcel
                if(current.getPosition() != 10) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 10}, MOVE));
                current.toJail();
                server.quickActionQueue(new ArrayList<>(allPlayers), new Message("El jugador " + current.getName() +" se va a la carcel", TOJAIL));
            }
            case 57 -> {
                if(current.getPosition() != 0) actionQueue.addAction(new Message(new int[]{current.getId(), 1, 0}, MOVE));
                current.addCash(200, "Se le da $200 por pasar GO");
                current.setPosition(0);
            }
         }


        actionQueue.executeQueue();
        single.executeQueue();
    }

    private void takeMoneyAbstraction(Player current, int amount, String message) {
        current.reduceMoney(amount, message);
        server.validateLooser(current);
    }

}
