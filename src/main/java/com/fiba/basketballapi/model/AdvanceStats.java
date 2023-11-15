package com.fiba.basketballapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
public class AdvanceStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private double twoThrowPercetage;
    @Getter
    @Setter
    private double twoPointsPercentage;
    @Getter
    @Setter
    @OneToOne(mappedBy = "advanceStats")
    private PlayerStats player;
}
