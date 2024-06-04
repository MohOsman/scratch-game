package org.scratchgame.service.implementation;

import org.scratchgame.model.Config;
import org.scratchgame.model.StandardProbability;
import org.scratchgame.service.MatrixGeneratorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MatrixGeneratorServiceImpl implements MatrixGeneratorService {
    @Override
    public String[][] generateMatrix(Config gameConfig) {
        final Random random = new Random();
        final int rows = gameConfig.getRows();
        final int columns = gameConfig.getColumns();
        final List<StandardProbability> strandedSymbolProbabilities = gameConfig.getProbabilities().getStandardSymbols();
        final String[][] matrix = new String[rows][columns];
        strandedSymbolProbabilities.forEach(sp -> {

            String symbol = getSymbol(sp.getSymbols());
            matrix[sp.getRow()][sp.getColumn()] = symbol;
        });
        final String bonusSymbol = getSymbol(gameConfig.getProbabilities().getBonusSymbols().getSymbols());
        matrix[random.nextInt(gameConfig.getRows())][random.nextInt(gameConfig.getColumns())] = bonusSymbol;

        return matrix;
    }


    private String getSymbol(Map<String, Integer> symbolProbMap) {
        final Random random = new Random();
        final double doubleRandom = random.nextDouble();
        double currentProbability = 0.0;
        Map<String, Double> calculatedProbabilities = calculateProbabilities(symbolProbMap);
        for (Map.Entry<String, Double> calculatedProbability : calculatedProbabilities.entrySet()) {
            currentProbability += calculatedProbability.getValue();
            if (doubleRandom <= currentProbability) {
                return calculatedProbability.getKey();
            }
        }
        return "";
    }

    private Map<String, Double> calculateProbabilities(Map<String, Integer> symbols) {
        final double totalWeight = symbols.values()
                .stream()
                .mapToDouble(integer -> integer).
                sum();
        final Map<String, Double> calculatedProbabilities = new HashMap<>();
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            calculatedProbabilities.put(entry.getKey(), entry.getValue() / totalWeight);
        }
        return calculatedProbabilities;
    }
}

