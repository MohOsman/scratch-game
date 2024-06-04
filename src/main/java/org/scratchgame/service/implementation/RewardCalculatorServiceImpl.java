package org.scratchgame.service.implementation;

import org.scratchgame.model.*;
import org.scratchgame.service.RewardCalculatorService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RewardCalculatorServiceImpl implements RewardCalculatorService {
    @Override
    public Result CalculateReward(String[][] matrix, Config gameConfig, int betAmount) {
        final List<String> matrixList = Stream.of(matrix)
                .flatMap(Stream::of)
                .toList();


        final Map<String, WiningCombination> symbolWiningCombintionCountMap = new HashMap<>();
        final Map<String, List<String>> appliedCombinations = new HashMap<>();

        final Map<String, Long> symbolCount = matrixList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        final Set<Map.Entry<String, Long>> winingSymbolCount = symbolCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= 3)
                .collect(Collectors.toSet());

        for (Map.Entry<String, WiningCombination> combinationEntry : gameConfig.getWinCombinations().entrySet()) {
            for (Map.Entry<String, Long> symbolEntrySet : winingSymbolCount) {
                if (symbolEntrySet.getValue() == combinationEntry.getValue().getCount()) {
                    symbolWiningCombintionCountMap.put(symbolEntrySet.getKey(), combinationEntry.getValue());
                    appliedCombinations.put(symbolEntrySet.getKey(), List.of(combinationEntry.getKey()));
                }
            }
        }

        final Map<String, Symbol> symbolRewardMap = gameConfig.getSymbols();
        int calulations = 0;

        for (Map.Entry<String, WiningCombination> winingCombinationEntry : symbolWiningCombintionCountMap.entrySet()) {
            for (Map.Entry<String, Symbol> symbolReawardEntry : symbolRewardMap.entrySet()) {
                if (winingCombinationEntry.getKey().equals(symbolReawardEntry.getKey())) {
                    calulations += (int) (betAmount * symbolReawardEntry.getValue().getReward_multiplier() *
                            winingCombinationEntry.getValue().getReward_multiplier());
                }
            }

        }
        if (calulations == 0)
            appliedCombinations.clear();

        final Reward totalReward = getTotalReward(symbolCount, symbolRewardMap, calulations, betAmount, gameConfig);
        if (totalReward.reward() == 0) {
            appliedCombinations.clear();
        }
        return new Result(matrix, totalReward.reward(), appliedCombinations, totalReward.bonus());
    }


    private Reward getTotalReward(Map<String, Long> symbolCount,
                                  Map<String, Symbol> symbolRewardMap,
                                  int calulations, int betAmount, Config gameConfig) {

        String appliedBonus = "";
        final Probabilities probabilities = gameConfig.getProbabilities();
        final Optional<String> bonusSymbolOptional = getBonusSymbol(symbolCount, probabilities);
        if (bonusSymbolOptional.isPresent() && calulations > betAmount) {
            appliedBonus = bonusSymbolOptional.get();
            if (bonusSymbolOptional.get().equalsIgnoreCase("MISS")) {
                return new Reward(0, "");
            } else if (bonusSymbolOptional.get().startsWith("+")) {
                calulations += symbolRewardMap.get(appliedBonus).getExtra();
            } else {
                calulations *= symbolRewardMap.get(appliedBonus).getReward_multiplier();

            }
        }
        return new Reward(calulations, appliedBonus);
    }


    private Optional<String> getBonusSymbol(Map<String, Long> symbolCount, Probabilities probabilities) {
        return symbolCount.keySet().stream()
                .filter(probabilities.getBonusSymbols().getSymbols().keySet()::contains)
                .findAny();
    }


}




