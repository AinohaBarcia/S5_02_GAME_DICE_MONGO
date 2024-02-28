package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.impl;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.GameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return gameDto;
    }

    @Override
    public void deleteGames(String id) {
       List<Game> gameList= gameRepository.findByPlayer(id);
       if(gameList!=null){
           gameList.forEach(gameRepository::delete);
       }

    }

    @Override
    public List<GameDto> getAllGames(Player player) {
        List<Game>gameList = player.getGamesList();
        return gameList.stream().map(GameMapper::mapToGameDto).collect(Collectors.toList());
    }


}
