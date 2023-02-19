package com.shinner.data.aiexchange.web.controller;

import com.shinner.data.aiexchange.model.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
public class EchoController {

    @RequestMapping(value = "/healthz", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("AI request get")
    public RestResponse<String> echo() {
        return RestResponse.successWith("OK");
    }
}
