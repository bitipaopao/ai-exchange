package com.lenovo.research.se.aiexchange.common.utils;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLimiter {

    private final ReentrantLock lock;
    private final int limit;
    private final long escapeTime;

    private long[] countTimes;

    public ConcurrentLimiter(ReentrantLock lock, int limit, long escapeTime) {
        this.lock = lock;
        this.limit = limit;
        this.escapeTime = escapeTime;
        countTimes = new long[]{};
    }

    public long[] getCountTimes() {
        return countTimes;
    }

    public void setCountTimes(long[] countTimes) {
        this.countTimes = countTimes;
    }

    public boolean countOne() {
        this.lock.lock();
        try {
            long now = System.currentTimeMillis();
            if (countTimes.length < limit) {
                long[] tmpRequestTimeArray = new long[countTimes.length + 1];
                System.arraycopy(countTimes, 0, tmpRequestTimeArray, 0, countTimes.length);
                tmpRequestTimeArray[countTimes.length] = now;
                setCountTimes(tmpRequestTimeArray);
                return true;
            }

            if (countTimes.length == limit) {
                int escapeIndex = findEscapeIndex(now, escapeTime);
                if (escapeIndex > 0) {
                    long[] tmpRequestTimeArray = new long[limit - escapeIndex + 1];
                    System.arraycopy(countTimes, escapeIndex, tmpRequestTimeArray, 0, limit - escapeIndex);
                    tmpRequestTimeArray[limit - escapeIndex] = now;
                    setCountTimes(tmpRequestTimeArray);
                    return true;
                }
            }
        } finally {
            this.lock.unlock();
        }
        return false;
    }

    private int findEscapeIndex(long now, long escapeTime) {
        for (int index = 0; index < countTimes.length; index++) {
            if (now - countTimes[index] < escapeTime) {
                return index;
            }
        }
        return countTimes.length;
    }
}
