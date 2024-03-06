package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.repository;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.Mongo.S05T2MONGO.model.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByPlayer (String id);
}
