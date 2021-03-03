package com.ifmo.hatchery.model.system;

import lombok.Getter;

@Getter
public enum Caste {
    ALPHA(1, 1), BETA(2, 4), GAMMA(4, 16), DELTA(16, 32), EPSILON(32, 64);
    private final long minAmount;
    private final long maxAmount;

    Caste(int minAmount, int maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
