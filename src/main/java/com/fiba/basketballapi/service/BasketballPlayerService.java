package com.fiba.basketballapi.service;

import com.fiba.basketballapi.model.AdvanceStats;
import com.fiba.basketballapi.model.PlayerStats;
import com.fiba.basketballapi.model.StandardStats;
import com.fiba.basketballapi.repository.PlayerRespository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketballPlayerService {

    private PlayerRespository playerRespository;
    @Value("${file.path}")
    private String path;


    @Autowired
    public BasketballPlayerService(PlayerRespository playerRespository) {
        this.playerRespository = playerRespository;
    }

    public PlayerStats savePlayer(PlayerStats playerStats) {
        return playerRespository.save(playerStats);
    }
    public void saveAllPlayerStatsFromCsvFile() {
        List<String[]> playerStatsFromCsv = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<String[]> allData = csvReader.readAll();
            for (int i = 1; i < allData.size(); i++) {
                String[] playerStats = allData.get(i);
                PlayerStats player = new PlayerStats();
                StandardStats standardStats = new StandardStats();
                AdvanceStats advanceStats = new AdvanceStats();

                player.setName(playerStats[0]);
                player.setPosition(playerStats[1]);

                standardStats.setFreeThrowAttempted(Integer.parseInt(playerStats[2]));
                standardStats.setFreeThrowAttempted(Integer.parseInt(playerStats[3]));
                standardStats.setPlayer(player);

                advanceStats.setTwoPointsPercentage(Double.parseDouble(playerStats[4]));
                advanceStats.setTwoPointsPercentage(Double.parseDouble(playerStats[5]));
                advanceStats.setPlayer(player);

                player.setAdvanceStats(advanceStats);
                player.setStandardStats(standardStats);

                playerRespository.save(player);

            }

        } catch (IOException ioException) {
            System.err.println(ioException);
        } catch (CsvException csvException) {
            System.err.println(csvException);
        }
    }
}
