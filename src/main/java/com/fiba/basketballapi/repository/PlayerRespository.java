package com.fiba.basketballapi.repository;

import com.fiba.basketballapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRespository extends JpaRepository<Player, Long> {
    Optional<Player> findPlayerByName(String name);

    @Query("SELECT AVG(ps.freeThrowMade) FROM StandardStats ps WHERE ps.id = :player_id")
    Double getAverageThreePointsThrowByPlayerId(Long player_id);
}
