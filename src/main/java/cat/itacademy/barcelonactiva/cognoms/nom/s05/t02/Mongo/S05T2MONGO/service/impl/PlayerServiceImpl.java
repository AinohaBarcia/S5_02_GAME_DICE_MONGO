package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.impl;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.exceptions.EmptyListException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.exceptions.RepeatedValueException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.GameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.PlayerService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameService gameService;

    @Override
    public void createPlayer(PlayerDto playerDto) {

        if (playerDto.getName() == null || playerDto.getName().isEmpty()) {
            playerDto.setName("Anonymous");
        }
        playerDto.setRegistrationDate(LocalDateTime.now());
        Player player = PlayerMapper.mapToPlayer(playerDto);
        Optional<Player> playerName = playerRepository.findByName(player.getName());
        if (playerName.isPresent()) {
            throw new RepeatedValueException("This player is already created");
        }
        playerRepository.save(player);
    }
    @Override
    public PlayerDto updatePlayer(PlayerDto newplayerDto, String id) {
        Player newPlayer = PlayerMapper.mapToPlayer(newplayerDto);
        Optional<Player> playerOptional = playerRepository.findById(id);
        Player player = playerOptional.orElseThrow(() -> new PlayerNotFoundException("The player doesn't exist"));

        if (!newplayerDto.getName().isEmpty() || !newplayerDto.getName().isBlank()) {
            Optional<Player> playerName = playerRepository.findByName(newPlayer.getName());
            playerName.ifPresent(existingPlayer ->{
                throw new RepeatedValueException("This name is already created");
            });
            player.setName(newPlayer.getName());
        } else {
            player.setName("ANONYMOUS");
        }
        playerRepository.save(player);

        return PlayerMapper.mapToPlayerDto(player);
    }
    @Override
    public PlayerDto deletePlayerById(String id) throws PlayerNotFoundException {
        return playerRepository.findById(id).map(player -> {
            playerRepository.deleteById(id);
            System.out.println("Player with id: " + id + " is deleted");
            return PlayerMapper.mapToPlayerDto(player);
        }).orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found"));

    }

    @Override
    public PlayerDto getPlayerById(String id) throws PlayerNotFoundException {
        return PlayerMapper.mapToPlayerDto(playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found")));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    @Override
    public void deleteAllGames(String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            gameService.deleteGames(player);
            PlayerDto playerDto = restartAverage(PlayerMapper.mapToPlayerDto(player));
        }else {
            throw new PlayerNotFoundException( "This player doesn't exisy");
        }
    }
    @Override
    public PlayerDto restartAverage(PlayerDto playerDto) {
            playerDto.setGamesLost(0);
            playerDto.setCalculateSuccessRate(0);
            playerDto.setCalculateLostRate(0);
            playerDto.setGamesWin(0);

        return playerDto;
    }
    @Override
    public double calculateSuccessRate(int totalGames, int wins) {
        if (totalGames == 0) {
            return 0;
        }
        return ((double)wins / totalGames) * 100;
    }
    public double calculatePlayerSuccessRate(String id) {
        PlayerDto playerDto = getPlayerById(id);
        List<Game> playerGames = getAllGames(id);

        int totalGames = playerGames.size();
        int wins = 0;
        for (Game game : playerGames) {
            if (game.isWin()) {
                wins++;
            }
        }

        return calculateSuccessRate(totalGames, wins);
    }
    @Override
    public GameDto play(String id) {
        Optional<Player> playerSerch = playerRepository.findById(id);

        if(playerSerch.isPresent()){
            Player player = playerSerch.get();
            GameDto gameDto = gameService.createGame(player);

            if (gameDto.isWin()) {
                updateGameWin(player.getId());
            } else {
                updateGameLost(player.getId());
            }
            playerRepository.save(player);

            return gameDto;
        } else {
            throw new PlayerNotFoundException("This player doesn't exist");
        }
    }
    @Override
    public List<Game> getAllGames(String id) {
        Optional<Player> playerSearch = playerRepository.findById(id);
        if (playerSearch.isPresent()) {
            Player player = playerSearch.get();

            List<Game> allGames = player.getGamesList();
            int totalGames = allGames.size();
            int wins = (int) allGames.stream().filter(Game::isWin).count();
            int losses = totalGames - wins;

            double winPercentage = calculateSuccessRate(totalGames, wins);
            double lossPercentage = calculateSuccessRate(totalGames, losses);

            player.setGamesWin(wins);
            player.setCalculateSuccessRate(winPercentage);
            player.setGamesLost(losses);
            player.setCalculateLostRate(lossPercentage);

            playerRepository.save(player);

            return allGames;
        } else {
            throw new PlayerNotFoundException("Player with id " + id + " not found");
        }
    }

    @Override
    public List<PlayerDto> getAverageSuccesRate() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDto> playersRanking = new ArrayList<>();
        players.stream().toList().forEach(l -> playersRanking.add(PlayerMapper.mapToPlayerDto(l)));
        playersRanking.sort(Comparator.comparing(PlayerDto::getGamesWin));
        if(playersRanking.isEmpty()){
            throw new PlayerNotFoundException("No games played.");
        }
        return playersRanking;
    }
    @Override
    public PlayerDto getWorstWinnerPlayer() {
        return getAverageSuccesRate().stream().toList().get(getAverageSuccesRate().size()-1);
    }
    @Override
    public PlayerDto getBestWinnerPlayer() {
        return getAverageSuccesRate().stream().toList().get(0);
    }
    @Override
    public void updateGameWin(String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setGamesWin(player.getGamesWin() + 1);
            double successRate= calculatePlayerSuccessRate(id);
            player.setCalculateSuccessRate(successRate);

            playerRepository.save(player);
        } else {
            throw new PlayerNotFoundException("Player doesn't exist");
        }
    }

    @Override
    public void updateGameLost(String id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if(playerOptional.isPresent()){
            Player player = playerOptional.get();
            player.setGamesLost(player.getGamesLost() + 1);
            double successRate = calculatePlayerSuccessRate(id);
            player.setCalculateSuccessRate(successRate);
            playerRepository.save(player);
        } else {
            throw new PlayerNotFoundException("Player doesn't exist");
        }
    }
   //lateLostRate(lostRate);
}



