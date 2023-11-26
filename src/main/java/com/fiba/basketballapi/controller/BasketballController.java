package com.fiba.basketballapi.controller;

import com.fiba.basketballapi.service.BasketballPlayerService;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats/player")
public class BasketballController {
    private BasketballPlayerService basketballPlayerService;
    @Autowired
    public BasketballController(BasketballPlayerService basketballPlayerService) {
        this.basketballPlayerService = basketballPlayerService;
    }
    @GetMapping("/{name}")
    public String getPlayerStatsByName(@PathVariable String name) {
        JsonObject response = basketballPlayerService.calculatePlayerStats(name);
        return response.toString();
    }
}
