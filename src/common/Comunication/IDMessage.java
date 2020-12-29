package common.Comunication;


import java.io.Serializable;

public enum IDMessage implements Serializable {
    //Server->client
    DICE, //
    MOVE,
    GIVEMONEY,
    TAKEMONEY,
    ADMIN,
    //Client -> Server

    ;

}
