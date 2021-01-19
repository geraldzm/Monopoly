package com.game.monopoly.Client.model;

import com.game.monopoly.Client.controller.PropertyCardController;
import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.CasualCard;
import com.game.monopoly.Client.view.PropertyCard;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class CardFactory {

    private static Hashtable <Integer, Card> cards;

    private CardFactory() {}


    
    private static void initCardFactory() {
        int price = 0;
        cards = new Hashtable<>();
        for (int i = 0; i < 73; i++) {
            if (i < 40){
                if (i == 0 || i == 10 || i == 20 || i == 30) continue;   
                else if(i == 5 || i == 15 || i == 25 || i == 35 || i == 19) price = 200;//Casas y 19 Naranja
                else if(i == 12 || i == 28 ) price = 150 ;//Servicios
                else if(i == 1 || i == 3 ) price = 60 ;//Cafes
                else if(i == 6 || i == 8 ) price = 100;//celeste
                else if(i == 9) price  = 120;
                else if(i == 11 || i == 13 ) price = 140;//Rosadas
                else if(i == 14) price = 160;
                else if(i == 16 || i == 18 ) price = 180;//Naranajas
                else if(i == 21 || i == 23 ) price = 220;//Rojas
                else if(i == 24) price = 240;
                else if(i == 26 || i == 27 ) price = 260;//Amarillas
                else if(i == 29) price = 280;
                else if(i == 31 || i == 32 ) price = 300;//Verdes
                else if(i == 34) price = 320;
                else if(i == 37) price = 350;//Azules
                else if(i == 39) price = 400;
                
                cards.put(i, new PropertyCard(null, i, price, PropertyCard.Type.NONE, PropertyCard.Color.BLUE));

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
            PropertyCard propertyCard = new PropertyCard(c.getImage(), c.getId(), ((PropertyCard) c).getPrice(), type, PropertyCard.Color.BLUE);
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

}