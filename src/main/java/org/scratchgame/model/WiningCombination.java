package org.scratchgame.model;

import java.util.List;

public class WiningCombination {
        private double reward_multiplier;
        private String when;
        private int count;
        private String group;
        private String[][] covered_areas;


        public double getReward_multiplier() {
                return reward_multiplier;
        }

        public void setReward_multiplier(double reward_multiplier) {
                this.reward_multiplier = reward_multiplier;
        }

        public String getWhen() {
                return when;
        }

        public void setWhen(String when) {
                this.when = when;
        }

        public int getCount() {
                return count;
        }

        public void setCount(int count) {
                this.count = count;
        }

        public String getGroup() {
                return group;
        }

        public void setGroup(String group) {
                this.group = group;
        }

        public String[][] getCovered_areas() {
                return covered_areas;
        }

        public void setCovered_areas(String[][] covered_areas) {
                this.covered_areas = covered_areas;
        }
}
