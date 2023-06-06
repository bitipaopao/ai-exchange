package com.shinner.data.aiexchange.core.source;

import com.shinner.data.aiexchange.common.utils.AESUtil;
import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.common.utils.Zlib;
import com.shinner.data.aiexchange.model.RestResponse;
import org.springframework.stereotype.Component;

@Component("jikeTokenWrapper")
public class JikeTokenWrapper implements SourceTokenWrapper{

    private String key = "121006e472cf485dd0ad62c09f8a16dc";
    @Override
    public String assembleRequestBody(String body) {
        byte[] dataEncode = Zlib.compress(body.getBytes());
        return AESUtil.encryptCBC(key, dataEncode);
    }

    public RestResponse assembleResult(String responseStr) {
        GKResponse restResponse = JsonSerializer.deserialize(responseStr, GKResponse.class);
        if (restResponse.getCode() == 0) {
            String resultStr = AESUtil.decryptCBC(key, restResponse.getData());
            return RestResponse.successWith(resultStr);
        }
        return new RestResponse().setCode(restResponse.getCode())
                .setData(restResponse.getData())
                .setMsg(restResponse.getMessage());
    }

    public class GKResponse {
        private Integer code;
        private String message;
        private String data;

        public Integer getCode() {
            return code;
        }

        public GKResponse setCode(Integer code) {
            this.code = code;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public GKResponse setMessage(String message) {
            this.message = message;
            return this;
        }

        public String getData() {
            return data;
        }

        public GKResponse setData(String data) {
            this.data = data;
            return this;
        }
    }
}
