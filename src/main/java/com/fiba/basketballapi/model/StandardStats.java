package com.fiba.basketballapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class StandardStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private int freeThrowMade;
    @Getter
    @Setter
    private int freeThrowAttempted;
    @Getter
    @Setter
    private int twoPointsMade;
    @Getter
    @Setter
    private int twoPointsAttempted;
    @Getter
    @Setter
    private int threePointsMade;
    @Getter
    @Setter
    private int threePointsAttempted;
    @Getter
    @Setter
    private int rebounds;
    @Getter
    @Setter
    private int blocks;
    @Getter
    @Setter
    private int assists;
    @Getter
    @Setter
    private int steals;
    @Getter
    @Setter
    private int turnovers;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}
