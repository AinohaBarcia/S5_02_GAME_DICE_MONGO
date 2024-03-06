package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.impl;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.exceptions.EmptyListException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.GameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public GameDto createGame(Player player) {
        GameDto gameDto = new GameDto(player);
        Game game = GameMapper.mapToGame(gameDto,player);
        game.setPlayer(player);
        game.setWin(gameDto.isGameWin());
        gameRepository.save(game);
        player.getGamesList().add(game);
        return gameDto;
    }

    @Override
    public void deleteGames(Player player) {
        if(player.getGamesList().isEmpty()){
            throw new EmptyListException("This player doesn't play.");
        } else {
            List<Game> games = new ArrayList<>(player.getGamesList().stream().toList());
            games.forEach(l -> gameRepository.delete(l));
        }
    }

    @Override
    public List<GameDto> getAllGames(Player player) {
        List<Game>gameList = player.getGamesList();
        return gameList.stream().map(GameMapper::mapToGameDto).collect(Collectors.toList());
    }


}
