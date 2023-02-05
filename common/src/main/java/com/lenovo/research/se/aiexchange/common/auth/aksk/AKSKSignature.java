package com.lenovo.research.se.aiexchange.common.auth.aksk;

import com.lenovo.research.se.aiexchange.common.auth.ClientService;
import com.lenovo.research.se.aiexchange.common.auth.Signature;
import com.lenovo.research.se.aiexchange.common.utils.MD5Util;
import com.lenovo.research.se.aiexchange.common.utils.SignUtil;
import com.lenovo.research.se.aiexchange.model.exception.InvalidAccessException;
import com.lenovo.research.se.aiexchange.model.exception.InvalidParameterException;
import com.lenovo.research.se.aiexchange.model.exception.UnAuthException;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AKSKSignature implements Signature {

    private final String akParamName;
    private final String signParamName;
    private final String slatParamName;
    private final String timeStampName;
    private final Long expireTime;
    private final TimeUnit timeUnit;

    private final ClientService clientService;

    private AKSKSignature(Builder builder) {
        this.akParamName = builder.getAkParamName();
        this.clientService = builder.getClientService();
        this.signParamName = builder.getSignParamName();
        this.expireTime = builder.getExpireTime();
        this.slatParamName = builder.getSlatParamName();
        this.timeUnit = builder.getTimeUnit();
        this.timeStampName = builder.getTimeStampName();
    }

    public static class Builder {

        private String akParamName;
        private String signParamName;
        private String slatParamName;
        private String timeStampName;
        private Long expireTime;
        private TimeUnit timeUnit;
        private ClientService clientService;

        public String getAkParamName() {
            return akParamName;
        }

        public Builder setAkParamName(String akParamName) {
            this.akParamName = akParamName;
            return this;
        }

        public String getSignParamName() {
            return signParamName;
        }

        public Builder setSignParamName(String signParamName) {
            this.signParamName = signParamName;
            return this;
        }

        public String getSlatParamName() {
            return slatParamName;
        }

        public Builder setSlatParamName(String slatParamName) {
            this.slatParamName = slatParamName;
            return this;
        }

        public String getTimeStampName() {
            return timeStampName;
        }

        public Builder setTimeStampName(String timeStampName) {
            this.timeStampName = timeStampName;
            return this;
        }

        public Long getExpireTime() {
            return expireTime;
        }

        public Builder setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public ClientService getClientService() {
            return clientService;
        }

        public Builder setClientService(ClientService clientService) {
            this.clientService = clientService;
            return this;
        }

        public AKSKSignature build() {
            return new AKSKSignature(this);
        }
    }

    @Override
    public void checkTimestamp(Map<String, Object> params) {
        if (params.get(timeStampName) == null) {
            throw new InvalidParameterException("The request timestamp is invalid");
        }
        Long requestTimestamp = Long.valueOf(params.get(timeStampName).toString());
        Long currentTimestamp = timeUnit == TimeUnit.SECONDS
                ? System.currentTimeMillis() / 1000 : System.currentTimeMillis();
        if (currentTimestamp - requestTimestamp > expireTime) {
            throw new InvalidParameterException("The request timestamp is invalid");
        }

        if (currentTimestamp - requestTimestamp < -expireTime) {
            throw new InvalidParameterException("The request timestamp is invalid");
        }
    }

    public void sign(String accessKey, Map<String, Object> params) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        byte[] toS = clientService.getCS(accessKey);
        sign(accessKey, params, toS);
        Arrays.fill(toS, (byte) 0);
    }

    public void sign(String accessKey, Map<String, Object> params, byte[] sc) {
        if (Objects.isNull(params)) {
            params = new HashMap<>();
        }
        params.put(akParamName, accessKey);
        params.put(slatParamName, new SecureRandom().nextDouble());
        params.put(timeStampName, getTimeStamp());
        String toSign = SignUtil.toSignStr(params);
        String sign = MD5Util.sign(toSign, sc);
        params.put(signParamName, sign);
    }

    public void sign(String accessKey, Object body, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(akParamName, accessKey);
        params.put(slatParamName, new SecureRandom().nextDouble());
        params.put(timeStampName, getTimeStamp());

        Map<String, Object> toSignMap = new HashMap<>(params);
        String toSign = SignUtil.toSignStrWithBody(toSignMap, body);
        byte[] toS = clientService.getCS(accessKey);
        String sign = MD5Util.sign(toSign, toS);
        Arrays.fill(toS, (byte) 0);
        params.put(signParamName, sign);
    }

    public void verify(Map<String, Object> params) {
        String accessKey = (String) params.get(akParamName);
        if (Objects.isNull(accessKey)) {
            throw new InvalidAccessException("The request access key is invalid");
        }

        String expectSign = (String) params.remove(signParamName);
        if (Objects.isNull(expectSign)) {
            throw new UnAuthException("The request sign is null");
        }
        String toSign = SignUtil.toSignStr(params);
        byte[] toS = clientService.getCS(accessKey);
        if (toS == null || toS.length == 0 ){
            throw new InvalidAccessException();
        }
        boolean result = MD5Util.verify(toSign, toS, expectSign);
        Arrays.fill(toS, (byte) 0);
        if (!result) {
            throw new UnAuthException("The request sign is invalid");
        }
    }

    public void verify(Map<String, Object> params, Object body) {
        String accessKey = (String) params.get(akParamName);
        if (Objects.isNull(accessKey)) {
            throw new InvalidAccessException("The request access key is invalid");
        }

        String expectSign = (String) params.remove(signParamName);
        String toSign = SignUtil.toSignStrWithBody(params, body);
        byte[] toS = clientService.getCS(accessKey);
        boolean result = MD5Util.verify(toSign, toS, expectSign);
        Arrays.fill(toS, (byte) 0);
        if (!result) {
            throw new UnAuthException("The request sign is invalid");
        }
    }

    public void verify(HttpServletRequest request) {
        Map<String, Object> requestParams = getRequestParameterMap(request);
        Map<String, Object> headerSignInfo = new HashMap<>();
        if (request.getHeader(signParamName) != null) {
            headerSignInfo.put(signParamName, request.getHeader(signParamName));
        }
        if (request.getHeader(slatParamName) != null) {
            headerSignInfo.put(slatParamName, request.getHeader(slatParamName));
        }
        if (request.getHeader(akParamName) != null) {
            headerSignInfo.put(akParamName, request.getHeader(akParamName));
        }
        if (request.getHeader(timeStampName) != null) {
            headerSignInfo.put(timeStampName, request.getHeader(timeStampName));
        }
        requestParams.putAll(headerSignInfo);
        this.checkTimestamp(requestParams);
        this.verify(requestParams);
    }

    private String getTimeStamp() {
        long timeStamp = timeUnit == TimeUnit.SECONDS
                ? System.currentTimeMillis() / 1000 : System.currentTimeMillis();
        return String.valueOf(timeStamp);
    }

}
