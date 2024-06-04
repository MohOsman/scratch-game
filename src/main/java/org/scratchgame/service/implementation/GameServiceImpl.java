package org.scratchgame.service.implementation;

import org.scratchgame.model.*;
import org.scratchgame.service.GameService;
import org.scratchgame.service.MatrixGeneratorService;
import org.scratchgame.service.RewardCalculatorService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameServiceImpl implements GameService {

    final Config gameConfig;
    private final MatrixGeneratorService matrixGeneratorService;
    private final RewardCalculatorService rewardCalculatorService;

    public GameServiceImpl(Config gameConfig, MatrixGeneratorService matrixGeneratorService, RewardCalculatorService rewardCalculatorService) {
        this.gameConfig = gameConfig;
        this.matrixGeneratorService = matrixGeneratorService;
        this.rewardCalculatorService = rewardCalculatorService;
    }


    @Override
    public Result start(int betAmount) {
        final String[][] matrix = matrixGeneratorService.generateMatrix(gameConfig);
        return rewardCalculatorService.CalculateReward(matrix, gameConfig, betAmount);
    }
}

