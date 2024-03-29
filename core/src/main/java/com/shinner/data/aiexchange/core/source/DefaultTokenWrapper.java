package com.shinner.data.aiexchange.core.source;

import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.model.RestResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("defaultTokenWrapper")
public class DefaultTokenWrapper implements SourceTokenWrapper{

    @Override
    public Map<String, Object> assembleRequestToken(Map<String, Object> params) {
        return params;
    }

    @Override
    public RestResponse assembleResult(String responseStr) {
        DefaultResponse restResponse = JsonSerializer.deserialize(responseStr, DefaultResponse.class);
        if (restResponse.getCode() == 0) {
            return RestResponse.successWith(JsonSerializer.serialize(restResponse.getData()));
        }
        return new RestResponse().setCode(restResponse.getCode())
                .setData(restResponse.getData())
                .setMsg(restResponse.getMessage());
    }

    public class DefaultResponse {
        private Integer code;
        private String message;
        private Object data;

        public Integer getCode() {
            return code;
        }

        public DefaultResponse setCode(Integer code) {
            this.code = code;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public DefaultResponse setMessage(String message) {
            this.message = message;
            return this;
        }

        public Object getData() {
            return data;
        }

        public DefaultResponse setData(Object data) {
            this.data = data;
            return this;
        }
    }
}
