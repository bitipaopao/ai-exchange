package com.shinner.data.aiexchange.core.manager;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCounter {
    private final ReentrantLock lock;
    private final long expireTime;

    private long[] countTimes;

    public ConcurrentCounter(ReentrantLock lock, long expireTime) {
        this.lock = lock;
        this.expireTime = expireTime;
        countTimes = new long[]{};
    }

    public void count() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            long[] tmpRequestTimeArray = new long[countTimes.length + 1];
            System.arraycopy(countTimes, 0, tmpRequestTimeArray, 0, countTimes.length);
            tmpRequestTimeArray[countTimes.length] = now;
            countTimes = tmpRequestTimeArray;
        } finally {
            lock.unlock();
        }

    }

    public long getLast() {
        lock.lock();
        try {
            clear();
            return countTimes[countTimes.length - 1];
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            clear();
            return countTimes.length;
        } finally {
            lock.unlock();
        }
    }

    private void clear() {
        long now = System.currentTimeMillis();
        int escapeIndex = findEscapeIndex(now, expireTime);
        if (escapeIndex > 0) {
            long[] tmpRequestTimeArray = new long[countTimes.length - escapeIndex];
            System.arraycopy(countTimes, escapeIndex, tmpRequestTimeArray, 0, countTimes.length - escapeIndex);
            countTimes = tmpRequestTimeArray;
        }
    }

    private int findEscapeIndex(long now, long expireTime) {
        for (int index = 0; index < countTimes.length; index++) {
            if (now - countTimes[index] < expireTime) {
                return index;
            }
        }
        return countTimes.length;
    }

}
