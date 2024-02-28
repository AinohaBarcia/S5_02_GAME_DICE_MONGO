package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.mapper;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.PlayerDto;

public class PlayerMapper {
    public static PlayerDto mapToPlayerDto (Player player){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setIdPlayer(player.getId());
        playerDto.setName(player.getName());
        playerDto.setRegistrationDate(player.getRegistrationDate());
        playerDto.setGamesWin(player.getGamesWin());
        playerDto.setCalculateSuccessRate(player.getCalculateSuccessRate());
        playerDto.setGamesLost(player.getGamesLost());
        playerDto.setCalculateLostRate(player.getCalculateLostRate());
        playerDto.setGamesListDto(player.getGamesList());

        return playerDto;
    }

    public static Player mapToPlayer (PlayerDto playerDto){
        Player player = new Player();
        player.setId(playerDto.getIdPlayer());
        player.setName(playerDto.getName());
        player.setRegistrationDate(playerDto.getRegistrationDate());
        player.setGamesWin(playerDto.getGamesWin());
        player.setCalculateSuccessRate(playerDto.getCalculateSuccessRate());
        player.setGamesLost(playerDto.getGamesLost());
        player.setCalculateLostRate(playerDto.getCalculateLostRate());
        player.setGamesList(playerDto.getGamesListDto());

        return player;
    }
}
