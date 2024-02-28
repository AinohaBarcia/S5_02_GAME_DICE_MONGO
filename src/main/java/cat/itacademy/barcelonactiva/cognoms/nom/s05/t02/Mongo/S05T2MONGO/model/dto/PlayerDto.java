package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    private String idPlayer;
    private String name;
    private LocalDateTime registrationDate;
    private int gamesWin;
    private int gamesLost;
    private double calculateLostRate;
    private double calculateSuccessRate;
    @JsonIgnore
    private List<Game> gamesListDto;

    public PlayerDto(String name) {
        this.name = name;
        this.registrationDate = LocalDateTime.now();
        gamesListDto =new ArrayList<>();
    }
}
