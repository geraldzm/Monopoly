package com.game.monopoly.Client.model;

import com.game.monopoly.Client.view.Card;
import com.game.monopoly.Client.view.CasualCard;
import com.game.monopoly.Client.view.PropertyCard;
import javax.swing.*;
import java.util.Hashtable;


public class CardFactory {
    private static Hashtable <Integer, Card> cards;
    private static String imagePath = "src/main/java/com/game/monopoly/Client/res/Image/";
    
    private CardFactory() {}
    
    private static void initCardFactory() {
        cards = new Hashtable<>();
        for (int i = 0; i < 73; i++) {
            if (i < 40){
                if (i == 0 || i == 10 || i == 20 || i == 30) continue;
                cards.put(i, new PropertyCard(new ImageIcon(Utils.getIcon.apply(i+".png").getScaledInstance(300, 400, 300)), i, PropertyCard.Type.NONE));
            }else{
                if (i == 40 || i == 41) continue;
                String text = "";
                switch(i){
                    //Arca Comunal
                    case 42 -> text = "Hay que pagar su Contribución de $150 para las Escuelas";
                    case 43 -> text = "Le toca un Reembolso a cuenta de su Contribución\n sobre sus ingresos. Cobrar $20 del banco";
                    case 44 -> text = "Usted hereda $100";
                    case 45 -> text = "Su hospital le exige un pago de $100. ";
                    case 46 -> text = "Error del banco en favor de usted. Cobrar $200";
                    case 47 -> text = "Cobre $50 de cada jugador. ";
                    case 48 -> text = "Pague $40 por cada casa y $115 por cada hotel. ";
                    case 49 -> text = "Le toca recibir $25 por servicios prestados. ";
                    case 50 -> text = "Usted ha ganado el segundo premio en un certamen de belleza.\n Puede cobrar $10. ";
                    case 51 -> text = "El banco le pagara $45 procedente de la venta de sus acciones.";
                    case 52 -> text = "Páguense al banco $50 para su médico. ";
                    case 53 -> text = "Cumplíose el plazo de los ahorros para la navidad.\n Pase al banco para cobrar $100";
                    case 54 -> text = "Se vende la póliza dotal de sus seguros sobre la vida.\n El banco le pagara $100";
                    case 55 -> text = "Salga de la cárcel gratis. ";
                    case 56 -> text = " ¡Al calabozo! ";
                    case 57 -> text = "Avance hasta ¨GO¨. Cobrar $200 ";
                    //Casualidad - Fortuna
                    case 58 -> text = "Avance hasta DREADFORT. ";
                    case 59 -> text = " Pague $25 para cada casa y $100 para cada hotel\n de su propiedad. ";
                    case 60 -> text = "¡Atrás! Retroceda su señal 3 espacios. ";
                    case 61 -> text = "Salir libre de la Cárcel. ";
                    case 62 -> text = "Tómese un pase o por el F.C Reading. Si pasa sobre ¨GO¨\n Cóbrense $200 al banco. ";
                    case 63 -> text = "Avance su señal hasta la casa más cercana y pague \na su dueño  el doble del alquiler que le corresponde.\n - Si la casa carece de dueño, lo puede cobrar al banco. ";
                    case 64 -> text = "Avance su señal hasta la casa más cercana y pague \na su dueño el doble del alquiler que le corresponde. "
                            + "\n - Si la casa carece de dueño, lo puede cobrar al banco. ";
                    case 65 -> text = "Adelante su señal hasta KING’S LANDING. ";
                    case 66 -> text = "El banco le paga un dividendo de $50. ";
                    case 67 -> text = "Mueva su señal hasta la utilidad más cercana.\n - Si no tiene dueño, la puede comprar al banco.\n - Si tiene propietario, lance los dados y pague al propietario \n diez veces la suma lanzada. ";
                    case 68 -> text = "Cóbrense $150 al banco. ";
                    case 69 -> text = "¡Váyase directamente a la cárcel!\n No se pasa por GO ni se cobran los $200. ";
                    case 70 -> text = "Pague $50 a cada uno de los jugadores. ";
                    case 71 -> text = "Adelante hasta ¨GO¨. Cóbrense $200. ";
                    case 72 -> text = "Páguese para los pobres un impuesto de $15. ";
                    case 73 -> text = "Adelántese hasta VAES DOTHRAK. Si pasa sobre ¨GO¨, cóbrense $200. ";
                }
                int idCard = 40;
                //if (i > 57) idCard = 41; 
                cards.put(i, new CasualCard(new ImageIcon(Utils.getIcon.apply(idCard+".png").getScaledInstance(450, 300, 450)), i, text));
            }
        }

    }

    public static Card getCard(int value, PropertyCard.Type type){
        if(cards == null) initCardFactory();
        Card c = cards.get(value);
        if (c instanceof PropertyCard) return new PropertyCard(c.getImage(), c.getId(), type);
        return new CasualCard(c.getImage(), c.getId(),((CasualCard)c).getText());
    }

}