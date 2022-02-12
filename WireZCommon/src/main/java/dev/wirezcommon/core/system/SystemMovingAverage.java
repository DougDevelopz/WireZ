package dev.wirezcommon.core.system;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Queue;

public class SystemMovingAverage {

    private final Queue<BigDecimal> samples;
    private final int period;
    private BigDecimal sum = BigDecimal.ZERO;

    public SystemMovingAverage(int period) {
        assert period > 0 : "Period must be a positive integer";
        this.period = period;
        this.samples = new ArrayDeque<>(this.period + 1);
    }

    public void add(BigDecimal num) {
        sum = sum.add(num);
        samples.add(num);
        if (samples.size() > period) {
            sum = sum.subtract(samples.remove());
        }
    }

    public double getAverage() {
        synchronized (this) {
            if (samples.isEmpty()) return 0.0; // technically the average is undefined
            BigDecimal divisor = BigDecimal.valueOf(samples.size());
            return sum.divide(divisor, 30, RoundingMode.HALF_UP).doubleValue();
        }
    }
}
