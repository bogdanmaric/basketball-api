package com.fiba.basketballapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advance_stats_id", referencedColumnName = "id")
    private StandardStats standardStats;
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_stats_id", referencedColumnName = "id")
    private AdvanceStats advanceStats;
}
