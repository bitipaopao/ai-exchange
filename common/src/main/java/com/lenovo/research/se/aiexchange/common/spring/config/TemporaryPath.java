package com.lenovo.research.se.aiexchange.common.spring.config;

import com.lenovo.research.se.aiexchange.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SuppressWarnings("unused")
@Component
@ConditionalOnProperty(prefix = "mw.temporary", name = "enable", havingValue = "true")
public class TemporaryPath {

    @Value("${mw.inward.temporary.path:/home/tmp}")
    private String tmpPath;

    @PostConstruct
    private void configCheck() {
        tmpPathCheck();
    }

    public String getTmpPath() {
        if (this.tmpPath.isEmpty()) {
            throw new RuntimeException("Configuration tmp file path is null, The specified tmp file path is not supported");
        }
        return this.tmpPath;
    }

    private void tmpPathCheck() {
        if (!this.tmpPath.isEmpty() && !FileUtil.checkAndMkdir(tmpPath)) {
            throw new RuntimeException("Configuration tmp file path error. With path str=" + tmpPath);
        }
    }
}
