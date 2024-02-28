package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.exceptions;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException (String message){
        super(message);
    }
}
