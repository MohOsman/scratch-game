package org.scratchgame.model;public class Symbol {
    private double reward_multiplier;
    private String type;
    private String impact;
    private int extra;

    public double getReward_multiplier() {
        return reward_multiplier;
    }

    public void setReward_multiplier(double reward_multiplier) {
        this.reward_multiplier = reward_multiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
}


