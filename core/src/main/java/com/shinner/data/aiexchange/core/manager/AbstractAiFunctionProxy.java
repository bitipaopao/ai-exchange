package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.entity.AiFunctionDO;

public abstract class AbstractAiFunctionProxy implements AiFunctionProxy {

    private AiFunctionDO aiFunction;

    public AbstractAiFunctionProxy(AiFunctionDO aiFunction) {
        this.aiFunction = aiFunction;
    }

    @Override
    public AiFunctionDO getAiFunction() {
        return aiFunction;
    }

    public AbstractAiFunctionProxy setAiFunction(AiFunctionDO aiFunction) {
        this.aiFunction = aiFunction;
        return this;
    }

    public abstract long lastExecTime();

    public abstract int total();

    public abstract void resetLimiter(int flowRate, int intervalSecond);
}
