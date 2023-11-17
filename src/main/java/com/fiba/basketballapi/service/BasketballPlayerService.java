package com.fiba.basketballapi.service;

import com.fiba.basketballapi.model.Player;
import com.fiba.basketballapi.model.StandardStats;
import com.fiba.basketballapi.repository.PlayerRespository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class BasketballPlayerService {

    private PlayerRespository playerRespository;
    private StatsService statsService;
    @Value("${file.path}")
    private String path;

    @Autowired
    public BasketballPlayerService(
            PlayerRespository playerRespository,
            StatsService statsService
    ) {
        this.playerRespository = playerRespository;
        this.statsService = statsService;
    }

    public Player savePlayer(Player playerStats) {
        return playerRespository.save(playerStats);
    }
    public JsonObject calculatePlayerStats(String name) {
        Optional<Player> player = playerRespository.findPlayerByName(name);
        List<StandardStats> standardStatsList = player.get().getStandardStats();
        statsService.setStandardStatsList(standardStatsList);
        return buildJsonResponse(player.get());
    }

    public JsonObject buildJsonResponse(Player player) {
        JsonObjectBuilder freeThrowsBuilder  = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(statsService.avgFreeThrowAttempted()))
                .add("made", roundToOneDecimal(statsService.avgFreeThrowMade()))
                .add("shootingPercentage", roundToOneDecimal(statsService.freeThrowPercentage()));

        JsonObjectBuilder twoPointsBuilder = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(statsService.avgTwoPointsAttempted()))
                .add("made", roundToOneDecimal(statsService.avgTwoPointsMade()))
                .add("shootingPercentage", roundToOneDecimal(statsService.twoPointsPercentage()));

        JsonObjectBuilder threePointsBuilder = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(statsService.avgThreePointsAttempted()))
                .add("made", roundToOneDecimal(statsService.avgThreePointsMade()))
                .add("shootingPercentage", roundToOneDecimal(statsService.threePointsPercentage()));

        JsonObject traditionalStats = Json.createObjectBuilder()
                .add("freeThrows", freeThrowsBuilder)
                .add("twoPoints", twoPointsBuilder)
                .add("threePoints", threePointsBuilder)
                .add("points", roundToOneDecimal(statsService.points()))
                .add("rebounds", roundToOneDecimal(statsService.avgRebounds()))
                .add("blocks", roundToOneDecimal(statsService.avgBlocks()))
                .add("assists", roundToOneDecimal(statsService.avgAssists()))
                .add("steals", roundToOneDecimal(statsService.avgSteals()))
                .add("turnovers", roundToOneDecimal(statsService.avgTurnovers()))
                .build();

        JsonObject advancedStats = Json.createObjectBuilder()
                .add("valorization", roundToOneDecimal(statsService.volarization()))
                .add("effectiveFieldGoalPercentage", roundToOneDecimal(statsService.effectiveFieldGoalPercentage()))
                .add("trueShootingPercentage", roundToOneDecimal(statsService.trueShootingPercentage()))
                .add("hollingerAssistRatio", roundToOneDecimal(statsService.hollingerAssistRatio()))
                .build();

        JsonObject playerStats = Json.createObjectBuilder()
                .add("playerName", player.getName())
                .add("gamesPlayed", statsService.getStandardStatsList().size())
                .add("traditional", traditionalStats)
                .add("advanced", advancedStats)
                .build();

        return playerStats;
    }

    public double roundToOneDecimal(double number) {
        return Math.round(number * 10.0) / 10.0;
    }

    @Transactional
    public void saveAllPlayerStatsFromCsvFile() {
        List<String[]> playerStatsFromCsv = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<String[]> allData = csvReader.readAll();
            for (int i = 1; i < allData.size(); i++) {
                String[] playerStats = allData.get(i);
                Optional<Player> playerExist = playerRespository.findPlayerByName(playerStats[0]);
                Player player = new Player();
                player.setName(playerStats[0]);
                player.setPosition(playerStats[1]);
                player.setStandardStats(new ArrayList<>());

                if (playerExist.isPresent()) {
                    player = playerExist.get();
                }

                StandardStats standardStats = new StandardStats();
                standardStats.setFreeThrowMade(Integer.parseInt(playerStats[2]));
                standardStats.setFreeThrowAttempted(Integer.parseInt(playerStats[3]));
                standardStats.setTwoPointsMade(Integer.parseInt(playerStats[4]));
                standardStats.setTwoPointsAttempted(Integer.parseInt(playerStats[5]));
                standardStats.setThreePointsMade(Integer.parseInt(playerStats[6]));
                standardStats.setThreePointsAttempted(Integer.parseInt(playerStats[7]));
                standardStats.setRebounds(Integer.parseInt(playerStats[8]));
                standardStats.setBlocks(Integer.parseInt(playerStats[9]));
                standardStats.setAssists(Integer.parseInt(playerStats[10]));
                standardStats.setSteals(Integer.parseInt(playerStats[11]));
                standardStats.setTurnovers(Integer.parseInt(playerStats[12]));

                player.getStandardStats().add(standardStats);
                standardStats.setPlayer(player);
                playerRespository.save(player);
            }

        } catch (IOException ioException) {
            System.err.println(ioException);
        } catch (CsvException csvException) {
            System.err.println(csvException);
        }
    }
}
