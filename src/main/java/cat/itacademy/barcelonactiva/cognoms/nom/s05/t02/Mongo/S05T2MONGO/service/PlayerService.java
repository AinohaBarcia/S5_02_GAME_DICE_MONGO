package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    void createPlayer(PlayerDto playerDto);
    PlayerDto updatePlayer(PlayerDto playerDto, String id);
    PlayerDto deltePlayerById(String id);
    PlayerDto getPlayerById(String id);
    List<Player> getAllPlayers();

    public GameDto play (String id);
    List<GameDto> getAllGames(String idPlayer);
    void deleteAllGames(String id);
    double calculateSuccessRate(int totalGames,int wins);
    List<PlayerDto> getAverageSuccesRate();
    public PlayerDto getBestWinnerPlayer();
    PlayerDto getWorstWinnerPlayer();
    void restartAverage(PlayerDto playerDto);
    void updateGameWin(String idPlayer);
    void updateGameLost (String idPlayer);

}
