package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "players")
public class Player {
    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("registration_date")
    private LocalDateTime registrationDate;
    @JsonProperty("games")
    @DBRef
    private List<Game> gamesList = new ArrayList<>();

    @JsonProperty("games_win")
    private int gamesWin;
    @JsonProperty("% won")
    private double calculateSuccessRate;
    @JsonProperty("games_lost")
    private int gamesLost;
    @JsonProperty("% lost")
    private double calculateLostRate;

    public Player (){
        this.registrationDate = LocalDateTime.now();
    }
}