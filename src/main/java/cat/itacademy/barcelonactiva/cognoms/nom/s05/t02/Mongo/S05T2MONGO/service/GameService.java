package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;

import java.util.List;

public interface GameService {
    GameDto createGame(Player player);
    public void deleteGames(Player player);
    List<GameDto> getAllGames(Player player);

}
