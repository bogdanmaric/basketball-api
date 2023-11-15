package com.fiba.basketballapi.repository;

import com.fiba.basketballapi.model.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRespository extends JpaRepository<PlayerStats, Long> {
    Optional<PlayerStats> findPlayerByName(String name);
}
