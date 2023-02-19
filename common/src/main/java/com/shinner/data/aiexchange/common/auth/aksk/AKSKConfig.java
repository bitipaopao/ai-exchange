package com.shinner.data.aiexchange.common.auth.aksk;

import com.shinner.data.aiexchange.common.auth.ClientService;
import com.shinner.data.aiexchange.common.auth.Signature;
import com.shinner.data.aiexchange.common.spring.ConditionalOnPropertyNotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnPropertyNotEmpty("mw.auth.client.aksk.akParamName")
@EnableConfigurationProperties(AKSKProperties.class)
public class AKSKConfig {

    @Autowired
    private ClientService clientService;

    @Bean
    public Signature akskSignature(AKSKProperties akskProperties) {
        AKSKSignature.Builder builder = new AKSKSignature.Builder()
                .setAkParamName(akskProperties.getAkParamName())
                .setSignParamName(akskProperties.getSignParamName())
                .setClientService(clientService)
                .setExpireTime(akskProperties.getExpireTime())
                .setSlatParamName(akskProperties.getSlatParamName())
                .setTimeStampName(akskProperties.getTimeStampName())
                .setTimeUnit(akskProperties.getSecond() == null ? TimeUnit.SECONDS : TimeUnit.MILLISECONDS);
        return builder.build();
    }

}
