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
    @Value("${file.path}")
    private String path;

    @Autowired
    public BasketballPlayerService(PlayerRespository playerRespository) {
        this.playerRespository = playerRespository;
    }

    public Player savePlayer(Player playerStats) {
        return playerRespository.save(playerStats);
    }
    public JsonObject calculatePlayerStats(String name) {
        Optional<Player> player = playerRespository.findPlayerByName(name);
        List<StandardStats> standardStatsList = player.get().getStandardStats();
        return buildJsonResponse(standardStatsList, player.get());
    }

    public JsonObject buildJsonResponse(List<StandardStats> standardStatsList, Player player) {
        JsonObjectBuilder freeThrowsBuilder  = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(avgFreeThrowAttempted(standardStatsList)))
                .add("made", roundToOneDecimal(avgFreeThrowMade(standardStatsList)))
                .add("shootingPercentage", roundToOneDecimal(freeThrowPercentage(standardStatsList)));

        JsonObjectBuilder twoPointsBuilder = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(avgTwoPointsAttempted(standardStatsList)))
                .add("made", roundToOneDecimal(avgTwoPointsMade(standardStatsList)))
                .add("shootingPercentage", roundToOneDecimal(twoPointsPercentage(standardStatsList)));

        JsonObjectBuilder threePointsBuilder = Json.createObjectBuilder()
                .add("attempts", roundToOneDecimal(avgThreePointsAttempted(standardStatsList)))
                .add("made", roundToOneDecimal(avgThreePointsMade(standardStatsList)))
                .add("shootingPercentage", roundToOneDecimal(threePointsPercentage(standardStatsList)));

        JsonObject traditionalStats = Json.createObjectBuilder()
                .add("freeThrows", freeThrowsBuilder)
                .add("twoPoints", twoPointsBuilder)
                .add("threePoints", threePointsBuilder)
                .add("points", roundToOneDecimal(points(standardStatsList)))
                .add("rebounds", roundToOneDecimal(avgRebounds(standardStatsList)))
                .add("blocks", roundToOneDecimal(avgBlocks(standardStatsList)))
                .add("assists", roundToOneDecimal(avgAssists(standardStatsList)))
                .add("steals", roundToOneDecimal(avgSteals(standardStatsList)))
                .add("turnovers", roundToOneDecimal(avgTurnovers(standardStatsList)))
                .build();

        JsonObject advancedStats = Json.createObjectBuilder()
                .add("valorization", roundToOneDecimal(volarization(standardStatsList)))
                .add("effectiveFieldGoalPercentage", roundToOneDecimal(effectiveFieldGoalPercentage(standardStatsList)))
                .add("trueShootingPercentage", roundToOneDecimal(trueShootingPercentage(standardStatsList)))
                .add("hollingerAssistRatio", roundToOneDecimal(hollingerAssistRatio(standardStatsList)))
                .build();

        JsonObject playerStats = Json.createObjectBuilder()
                .add("playerName", player.getName())
                .add("gamesPlayed", standardStatsList.size())
                .add("traditional", traditionalStats)
                .add("advanced", advancedStats)
                .build();

        return playerStats;
    }

    public double roundToOneDecimal(double number) {
        return Math.round(number * 10.0) / 10.0;
    }

    public double avgFreeThrowMade(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getFreeThrowMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgFreeThrowAttempted(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getFreeThrowAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTwoPointsMade(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTwoPointsMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTwoPointsAttempted(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTwoPointsAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgThreePointsMade(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getThreePointsMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgThreePointsAttempted(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getThreePointsAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgRebounds(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getRebounds();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgBlocks(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getBlocks();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgAssists(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getAssists();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgSteals(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getSteals();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTurnovers(List<StandardStats> standardStatsList) {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTurnovers();
        }
        return (double) value/standardStatsList.size();
    }

    public double freeThrowPercentage(List<StandardStats> standardStatsList) {
        return avgFreeThrowMade(standardStatsList)/avgFreeThrowAttempted(standardStatsList)*100;
    }

    public double twoPointsPercentage(List<StandardStats> standardStatsList) {
        return avgTwoPointsMade(standardStatsList)/avgTwoPointsAttempted(standardStatsList)*100;
    }

    public double threePointsPercentage(List<StandardStats> standardStatsList) {
        return avgThreePointsMade(standardStatsList)/avgThreePointsAttempted(standardStatsList)*100;
    }

    public double points(List<StandardStats> standardStatsList) {
        return avgFreeThrowMade(standardStatsList)
                + 2 * avgTwoPointsMade(standardStatsList)
                + 3 * avgThreePointsMade(standardStatsList);
    }

    public double volarization(List<StandardStats> standardStatsList) {
        return (avgFreeThrowMade(standardStatsList)
                + 2 * avgTwoPointsMade(standardStatsList)
                + 3 * avgThreePointsMade(standardStatsList)
                + avgRebounds(standardStatsList)
                + avgBlocks(standardStatsList)
                + avgAssists(standardStatsList)
                + avgSteals(standardStatsList))
                -
                (
                    avgFreeThrowAttempted(standardStatsList)
                        - avgFreeThrowMade(standardStatsList)
                        + avgTwoPointsAttempted(standardStatsList)
                        - avgTwoPointsMade(standardStatsList)
                        + avgThreePointsAttempted(standardStatsList)
                        - avgThreePointsMade(standardStatsList)
                        + avgTurnovers(standardStatsList)

                );
    }

    public double effectiveFieldGoalPercentage(List<StandardStats> standardStatsList) {
        return ((avgTwoPointsMade(standardStatsList)
                + avgThreePointsMade(standardStatsList)
                + 0.5
                * avgThreePointsMade(standardStatsList))
                / (avgTwoPointsAttempted(standardStatsList) + avgThreePointsAttempted(standardStatsList)) * 100);
    }

    public double trueShootingPercentage(List<StandardStats> standardStatsList) {
        return points(standardStatsList) / (2 * ((avgTwoPointsAttempted(standardStatsList) + avgThreePointsAttempted(standardStatsList))
                + 0.475 * avgFreeThrowAttempted(standardStatsList))) * 100;
    }

    public double hollingerAssistRatio(List<StandardStats> standardStatsList) {
        return avgAssists(standardStatsList) / (avgTwoPointsAttempted(standardStatsList)
                + avgThreePointsAttempted(standardStatsList)
                + 0.475
                * avgFreeThrowAttempted(standardStatsList)
                + avgAssists(standardStatsList)
                + avgTurnovers(standardStatsList)) * 100;
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
