package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.controller;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.service.PlayerService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private PlayerService playerService;


    @PostMapping("/createPlayer")
    public ResponseEntity<String> createPlayer(@RequestBody PlayerDto playerDto) {
        if(playerDto.getName()==null|| playerDto.getName().isEmpty()){
            playerDto.setName(null);
        }
        playerService.createPlayer(playerDto);
        return new ResponseEntity<>("Player is created",HttpStatus.CREATED);
    }
    @GetMapping("/getAllPlayers")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players=playerService.getAllPlayers();
        if (players.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(players,HttpStatus.OK);
    }


    @PutMapping("/updatePlayerById/{idPlayer}") //TODO cazar exceptcion si no pones body
    public ResponseEntity<String> updatePlayerById(@PathVariable(value = "idPlayer") String id,
                                                   @RequestBody PlayerDto newPlayerDto) {
        PlayerDto playerDto = playerService.updatePlayer(newPlayerDto, id);
        if (playerDto != null) {
            return new ResponseEntity<>("Player was updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not fourn", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{idGame}/games")
    public ResponseEntity<GameDto> play(@PathVariable("idGame") String id) {
        GameDto newGame = playerService.play(id);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }
    @DeleteMapping("/deleteAllGames/{idPlayer}")
    public ResponseEntity<String> deleteAllGames(@PathVariable("idPlayer") String id) {
        playerService.deleteAllGames(id);
        return new ResponseEntity<>("Games deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/getGames/{idGame}")
    public ResponseEntity<List<Game>> getAllGames(@PathVariable("idGame") String id) {
        List<Game> games = playerService.getAllGames(id);
        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(games, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletePlayerById/{idPlayer}")
    public ResponseEntity<String> deletePlayerById(@PathVariable ("idPlayer")String id) {
        playerService.deletePlayerById(id);
        return new ResponseEntity<>("Player was deleted", HttpStatus.OK);
    }

    @GetMapping("/getRanking")
    public ResponseEntity<List<PlayerDto>>getAverageSuccesRate() {
        List<PlayerDto> playersRanking = playerService.getAverageSuccesRate();
        if(playersRanking.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(playersRanking,HttpStatus.OK) ;
        }
    }

    @GetMapping("/getBestWinnerPlayer")
    public ResponseEntity<PlayerDto> getBestWinnerPlayer() {
        PlayerDto bestPlayer = playerService.getBestWinnerPlayer();
        if (bestPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bestPlayer, HttpStatus.OK);
        }
    }
    @GetMapping("/getWorstWinnerPlayer")
    public ResponseEntity<PlayerDto> getWorstWinnerPlayer() {
        PlayerDto worstPlayer = playerService.getWorstWinnerPlayer();
        if (worstPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(worstPlayer, HttpStatus.OK);
        }
    }

}
