package common.Comunication;


import java.io.Serializable;

public enum IDMessage implements Serializable {
    //Server->client
    DICE(true),
    MOVE(true),
    GIVEMONEY(true),
    TAKEMONEY(true),
    ADMIN,
    REJECTED,
    ACCEPTED,
    STARTED(true),
    END,
    MESSAGE,
    LOGBOOK,
    NAME,
    NAMES,
    //######Client -> Server######
    RESPONSE,
    DONE
    ;

    private boolean done; // if Server->Client ID has to return DONE

    IDMessage() {
        done = false;
    }

    IDMessage(boolean done) {
        this.done = done;
    }

    /**
     * <h3>When the communication is S->C</h3>
     * <p>True if the ID has to return done, false if not</p>
     * */
    public boolean returnDone(){
        return done;
    }
}

/*
* NOTAS de qué debe hacer cada ID
*
* abreviaciones de estas notas: Server=S, cliente=C
*
* -------------------Server->client------------------------
*
* ADMIN: le indica al C que fue le primero en conectarse, el C debe responder con un número, el número indica la cantidad de jugadores
* que va a permitir en la partida, el número debe estar en [2,7[
*
* REJECTED: Si el C intentó conectarse y fue rechazado
*
* ACCEPTED: indica que el C fue acceptado en la partida
*
* STARTED: Dice que el juego ya comenzó
*
* END: Indica que el server se va a desconectar
*
* MESSAGE: Un mensaje del chat con la forma en el String="Nombre: mensaje"
*
* LOGBOOK: Mensaje a ser cargado en la bitácora
*
*
* NAME: El S le esta solicitando un nombre
* solo se permiten letras normales [Aa-Zz]
*
* NAMES: El S esta enviando los nombres de TODOS los jugadores incluyendo el de el mismo cliente
* e.g. "Carlos,Maria,Mario,valeria"   para el cliente llamado Mario
*
* #######################################################################
* --------------------client->Server------------------------
*
* RESPONSE: cada vez que el S le solicite algo al C el id de la respuesta debe ser este.
* e.g. el S envia ADMIN, el C debe responder con un número, el ID del message es RESPONSE
*
* DONE: el cliente responde esto cuando se le solicito hacer algo para lo que hay que "esperar al C"
* e.g. el S envia DICE; el cliente muestra la animación de los dados y tiene que responder con DONE
* cuando termina. El S va a esperar el DONE eternamente si es necesario.
*
* MESSAGE: Un mensaje para el chat
* */
