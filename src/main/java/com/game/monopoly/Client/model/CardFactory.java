package com.game.monopoly.Client.model;

import com.game.monopoly.Client.controller.PropertyCardController;
import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.CasualCard;
import com.game.monopoly.Client.view.PropertyCard;
import com.game.monopoly.Client.view.PropertyCard.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class CardFactory {

    private static Hashtable <Integer, Card> cards;

    private CardFactory() {}

    private static void initCardFactory() {
        int cost = 0;
        Colors color = null;
        cards = new Hashtable<>();
        for (int i = 0; i < 73; i++) {
            int[] prices = new int[6];
            if (i < 40){
                switch(i){
                    case 0,10,20,30 : continue;
                    case 5,15,25,35 : { cost = 200; prices[0] = 50; prices[1] = 100;prices[2] = 150; prices[3] = 200;}//Casas
                    case 12,28 : cost = 150;//Servicios
                    case 4 : prices[0] = 200; // impuesto
                    case 38 : prices[0] = 100; // impuesto
                    case 1 : {cost = 60;color = Colors.BROWN; prices[0] = 2;prices[1] = 10; prices[2] = 30; prices[3] = 90; prices[4] = 160; prices[5] = 250;}//Cafes
                    case 3 : {cost = 60; color = Colors.BROWN;prices[0] = 4;prices[1] = 20;prices[2] = 60;prices[3] = 180; prices[4] = 320; prices[5] = 450;}
                    case 6, 8 : {cost = 100;color = Colors.LIGHTBLUE;prices[0] = 6;prices[1] = 30;prices[2] = 90;prices[3] = 270;prices[4] = 400;prices[5] = 550;}//celeste
                    case 9 :{cost  = 120; color = Colors.LIGHTBLUE;prices[0] = 8;prices[1] = 40;prices[2] = 100;prices[3] = 300;prices[4] = 450;prices[5] = 600;}
                    case 11 , 13 : {cost = 140; color = Colors.PINK;prices[0] = 10;prices[1] = 50;prices[2] = 150;prices[3] = 450;prices[4] = 625;prices[5] = 750;}//Rosadas
                    case 14 :{cost = 160; color = Colors.PINK;prices[0] = 12;prices[1] = 60;prices[2] = 80;prices[3] = 500;prices[4] = 700;prices[5] = 900;}
                    case 16 , 18 :{cost = 180; color = Colors.ORANGE;prices[0] = 14;prices[1] = 70;prices[2] = 200;prices[3] = 550;prices[4] = 750;prices[5] = 950;}//Naranajas
                    case 19 : {cost = 200; color = Colors.ORANGE;prices[0] = 16;prices[1] = 80;prices[2] = 220;prices[3] = 600;prices[4] = 800;prices[5] = 1000;}
                    case  21,23: {cost = 220; color = Colors.RED;prices[0] = 18;prices[1] = 90;prices[2] = 250;prices[3] = 700;prices[4] = 875;prices[5] = 1050;}//Rojas
                    case 24: {cost = 240;color = Colors.RED;prices[0] = 20;prices[1] = 100;prices[2] = 300;prices[3] = 750;prices[4] = 825;prices[5] = 1100;}
                    case 26,27 : {cost = 260;color = Colors.YELLOW;prices[0] = 22;prices[1] = 110;prices[2] = 330;prices[3] = 800;prices[4] = 975;prices[5] = 1150;}//Amarillas
                    case 29: {cost = 280;color = Colors.YELLOW;prices[0] = 24;prices[1] = 120;prices[2] = 360;prices[3] = 850;prices[4] = 1025;prices[5] = 1200;}
                    case 31 , 32 : {cost = 300;color = Colors.GREEN;prices[0] = 26;prices[1] = 130;prices[2] = 390;prices[3] = 900;prices[4] = 1100;prices[5] = 1275;}//Verdes
                    case 34: {cost = 320;color = Colors.GREEN;prices[0] = 28;prices[1] = 150;prices[2] = 450;prices[3] = 1000;prices[4] = 1200;prices[5] = 1400;}
                    case 37 : {cost = 350;color = Colors.BLUE;prices[0] = 35;prices[1] = 175;prices[2] = 500;prices[3] = 1100;prices[4] = 1300;prices[5] = 1500;}//Azules
                    case 39 : {cost = 400;color = Colors.BLUE;prices[0] = 50;prices[1] = 200;prices[2] = 600;prices[3] = 1400;prices[4] = 1700;prices[5] = 2000;}
                }
                cards.put(i, new PropertyCard(null, i, cost, PropertyCard.Type.NONE, color, prices));
            } else {
                if (i == 40 || i == 41) continue;
                String text = "";
                switch(i){
                    //Arca Comunal
                    case 42 -> text = "Hay que pagar su Contribución de $150\n para las Escuelas";
                    case 43 -> text = "Le toca un Reembolso a cuenta de su \ncontribución sobre sus ingresos.\n Cobrar $20 del banco";
                    case 44 -> text = "Usted hereda $100";
                    case 45 -> text = "Su hospital le exige un pago de $100. ";
                    case 46 -> text = "Error del banco en favor de usted. Cobrar $200";
                    case 47 -> text = "Cobre $50 de cada jugador. ";
                    case 48 -> text = "Pague $40 por cada casa y $115 por cada hotel. ";
                    case 49 -> text = "Le toca recibir $25 por servicios prestados. ";
                    case 50 -> text = "Usted ha ganado el segundo premio\n en un certamen de belleza.\n Puede cobrar $10. ";
                    case 51 -> text = "El banco le pagara $45 procedente\n de la venta de sus acciones.";
                    case 52 -> text = "Páguense al banco $50 para su médico. ";
                    case 53 -> text = "Cumplíose el plazo de los ahorros\n para la navidad.\n Pase al banco para cobrar $100";
                    case 54 -> text = "Se vende la póliza dotal de sus seguros\n El banco le pagara $100";
                    case 55 -> text = "Salga de la cárcel gratis. ";
                    case 56 -> text = " ¡Al calabozo! ";
                    case 57 -> text = "Avance hasta ¨GO¨. Cobrar $200 ";
                    //Casualidad - Fortuna
                    case 58 -> text = "Avance hasta DREADFORT. ";
                    case 59 -> text = " Pague $25 para cada casa y $100 para cada\n hotel de su propiedad. ";
                    case 60 -> text = "¡Atrás! Retroceda su señal 3 espacios. ";
                    case 61 -> text = "Salir libre de la Cárcel. ";
                    case 62 -> text = "Tómese un pase o por el F.C Reading.\n Si pasa sobre ¨GO¨\n Cóbrense $200 al banco. ";
                    case 63 -> text = "Avance su señal hasta la casa más cercana\n y pague a su dueño  el doble del alquiler \nque le corresponde.\n - Si la casa carece de dueño, lo puede cobrar\n al banco. ";
                    case 64 -> text = "Avance su señal hasta la casa más cercana\n y pague a su dueño  el doble del alquiler \nque le corresponde.\n - Si la casa carece de dueño, lo puede cobrar\n al banco. ";
                    case 65 -> text = "Adelante su señal hasta KING’S LANDING. ";
                    case 66 -> text = "El banco le paga un dividendo de $50. ";
                    case 67 -> text = "Mueva su señal hasta la utilidad más cercana.\n - Si no tiene dueño, la puede comprar al banco.\n - Si tiene propietario, lance los dados y pague\n al propietario diez veces la suma lanzada. ";
                    case 68 -> text = "Cóbrense $150 al banco. ";
                    case 69 -> text = "¡Váyase directamente a la cárcel!\n No se pasa por GO ni se cobran los $200. ";
                    case 70 -> text = "Pague $50 a cada uno de los jugadores. ";
                    case 71 -> text = "Adelante hasta ¨GO¨. Cóbrense $200. ";
                    case 72 -> text = "Páguese para los pobres un impuesto de $15. ";
                    case 73 -> text = "Adelántese hasta VAES DOTHRAK. Si pasa sobre ¨GO¨, cóbrense $200. ";
                }
                int idCard = 40;
                if (i > 57) idCard = 41;
                cards.put(i, new CasualCard(null, i, text));
            }
        }
    }

    public static Card getCard(int value, PropertyCard.Type type){
        if(cards == null) {
            initCardFactory();
        }
        Card c = cards.get(value);

        if (c instanceof PropertyCard){

            c.setImage(new ImageIcon(Utils.getIcon.apply(c.getId()+".png").getScaledInstance(280, 392, Image.SCALE_SMOOTH)));
            PropertyCard propertyCard = new PropertyCard(c.getImage(), c.getId(), ((PropertyCard) c).getPrice(), type, ((PropertyCard) c).getColor(),((PropertyCard) c).getPrices());
            new PropertyCardController(propertyCard).init();

            return propertyCard;
        }

        c.setImage(new ImageIcon(Utils.getIcon.apply(c.getId()+".png").getScaledInstance(400, 300, Image.SCALE_SMOOTH)));
        return new CasualCard(c.getImage(), c.getId(),((CasualCard)c).getText());
    }

    public static Card getCard(int value){
        if(cards == null) {
            initCardFactory();
        }
        return cards.get(value);
    }

    public static PropertyCard getPropertyCard(int value) {
        if(cards == null) {
            initCardFactory();
        }
        if(cards.get(value) instanceof PropertyCard)
            return (PropertyCard) cards.get(value);
        return null;
    }

}