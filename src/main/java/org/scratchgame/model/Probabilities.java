package org.scratchgame.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Probabilities {
    private List<StandardProbability> standardSymbols;

    private StandardProbability bonusSymbols;

    @JsonProperty("standard_symbols")
    public List<StandardProbability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    @JsonProperty("bonus_symbols")
    public StandardProbability getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(StandardProbability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
