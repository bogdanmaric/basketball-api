package com.fiba.basketballapi.controller;

import com.fiba.basketballapi.model.PlayerStats;
import com.fiba.basketballapi.service.BasketballPlayerService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
public class BasketballController {
    private BasketballPlayerService basketballPlayerService;
    @Autowired
    public BasketballController(BasketballPlayerService basketballPlayerService) {
        this.basketballPlayerService = basketballPlayerService;
    }

    @GetMapping()
    public String getPlayerStatsByName() {
        return "Work1";
    }
    @GetMapping("/{name}")
    public String getPlayerStatsByName(@PathVariable String name) {
        return "Work2";
    }
}
