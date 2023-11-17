package com.fiba.basketballapi.service;

import com.fiba.basketballapi.model.StandardStats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsService {

    private List<StandardStats> standardStatsList = new ArrayList<>();


    public List<StandardStats> getStandardStatsList() {
        return standardStatsList;
    }

    public void setStandardStatsList(List<StandardStats> standardStatsList) {
        this.standardStatsList = standardStatsList;
    }

    public double avgFreeThrowMade() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getFreeThrowMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgFreeThrowAttempted() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getFreeThrowAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTwoPointsMade() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTwoPointsMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTwoPointsAttempted() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTwoPointsAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgThreePointsMade() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getThreePointsMade();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgThreePointsAttempted() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getThreePointsAttempted();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgRebounds() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getRebounds();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgBlocks() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getBlocks();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgAssists() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getAssists();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgSteals() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getSteals();
        }
        return (double) value/standardStatsList.size();
    }

    public double avgTurnovers() {
        int value = 0;
        for (StandardStats s : standardStatsList) {
            value += s.getTurnovers();
        }
        return (double) value/standardStatsList.size();
    }

    public double freeThrowPercentage() {
        return avgFreeThrowMade()/avgFreeThrowAttempted()*100;
    }

    public double twoPointsPercentage() {
        return avgTwoPointsMade()/avgTwoPointsAttempted()*100;
    }

    public double threePointsPercentage() {
        return avgThreePointsMade()/avgThreePointsAttempted()*100;
    }

    public double points() {
        return avgFreeThrowMade()
                + 2 * avgTwoPointsMade()
                + 3 * avgThreePointsMade();
    }

    public double volarization() {
        return (avgFreeThrowMade()
                + 2 * avgTwoPointsMade()
                + 3 * avgThreePointsMade()
                + avgRebounds()
                + avgBlocks()
                + avgAssists()
                + avgSteals())
                -
                (avgFreeThrowAttempted()
                - avgFreeThrowMade()
                + avgTwoPointsAttempted()
                - avgTwoPointsMade()
                + avgThreePointsAttempted()
                - avgThreePointsMade()
                + avgTurnovers());
    }

    public double effectiveFieldGoalPercentage() {
        return ((avgTwoPointsMade()
                + avgThreePointsMade()
                + 0.5
                * avgThreePointsMade())
                / (avgTwoPointsAttempted() + avgThreePointsAttempted()) * 100);
    }

    public double trueShootingPercentage() {
        return points() / (2 * ((avgTwoPointsAttempted() + avgThreePointsAttempted())
                + 0.475 * avgFreeThrowAttempted())) * 100;
    }

    public double hollingerAssistRatio() {
        return avgAssists() / (avgTwoPointsAttempted()
                + avgThreePointsAttempted()
                + 0.475
                * avgFreeThrowAttempted()
                + avgAssists()
                + avgTurnovers()) * 100;
    }

}
