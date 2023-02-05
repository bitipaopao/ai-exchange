package com.lenovo.research.se.aiexchange.web.controller;

import com.lenovo.research.se.aiexchange.model.AiResult;
import com.lenovo.research.se.aiexchange.model.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @RequestMapping(value = "/ai/label", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("AI request label")
    public RestResponse<String> aiLabel(@RequestBody String argument) {
        // TODO Business related logic
        return RestResponse.successWith("success");
    }

    @RequestMapping(value = "/ai/type", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("AI request type")
    public RestResponse<String> aiType(@RequestBody String argument) {
        // TODO Business related logic
        return RestResponse.successWith("success");
    }
}
