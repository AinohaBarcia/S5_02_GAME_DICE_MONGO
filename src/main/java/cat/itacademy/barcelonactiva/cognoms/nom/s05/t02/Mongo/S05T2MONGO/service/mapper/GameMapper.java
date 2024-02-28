package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.mapper;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;

public class GameMapper {

    public static GameDto mapToGameDto(Game game) {
        GameDto gameDto = new GameDto(game.getPlayer());
        gameDto.setIdGame(game.getId());
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setWin(game.isWin());
        return gameDto;
    }


    public static Game mapToGame(GameDto gameDto, Player player){
        Game game = new Game(player);
        game.setId(gameDto.getIdGame());
        game.setDice1(gameDto.getDice1());
        game.setDice2(gameDto.getDice2());
        game.setWin(gameDto.isWin());
        return game;
    }
}
