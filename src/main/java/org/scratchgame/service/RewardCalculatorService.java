package org.scratchgame.service;

import org.scratchgame.model.Config;
import org.scratchgame.model.Result;

public interface RewardCalculatorService {

    Result CalculateReward(String[][] matrix, Config GameConfig, int betAmout);
}
