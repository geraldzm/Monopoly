package common.Comunication;


import java.io.Serializable;

public enum IDMessage implements Serializable {
    //Server->client
    DICE,
    MOVE,
    GIVEMONEY,
    TAKEMONEY,
    ADMIN,
    REJECTED,
    ACCEPTED,
    STARTED,
    END,
    //Client -> Server
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
* */
