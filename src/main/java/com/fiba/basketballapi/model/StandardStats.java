package com.fiba.basketballapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    @OneToOne(mappedBy = "standardStats")
    private PlayerStats player;
}
