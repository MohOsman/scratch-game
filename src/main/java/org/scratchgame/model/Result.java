package org.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


public record Result( String[][] matrix, int reward,Map<String, List<String>> appliedWinningCombinations, String appliedBonus) {

}
