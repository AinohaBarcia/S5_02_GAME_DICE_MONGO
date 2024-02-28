package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.dto;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDto {

    private String idGame;
    private int dice1;
    private int dice2;
    private boolean win;
    private Player player;



    public GameDto(Player player) {
        this.dice1 = diceRandomNum();
        this.dice2 = diceRandomNum();
        this.win = isGameWin();
        this.player = player;

    }

    public int diceRandomNum() {
        return (int) (Math.random() * 6 + 1);
    }

    public boolean isGameWin() {
        return dice1 + dice2 == 7;
    }
    public boolean isWin() {
        win = isGameWin();
        return win;
    }
}