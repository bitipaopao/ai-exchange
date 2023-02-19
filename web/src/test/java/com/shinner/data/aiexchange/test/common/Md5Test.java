package com.shinner.data.aiexchange.test.common;

import com.shinner.data.aiexchange.common.utils.MD5Util;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class Md5Test {

    String toMd5 = "{\"subjectId\":2,\"questiontext\":\"<img alt=\\\"aft_question\\\" src=\\\"https://qimg.afanti100.com/data/image/question_image/111/97ce3659de8035beb4e9e01115de3671.png\\\" style=\\\"vertical-align:middle;\\\"/><br/>小华的后面是（&nbsp;&nbsp;&nbsp;）。 \",\"phaseId\":1,\"questions\":[],\"questionIdx\":0,\"questiontype\":2,\"source\":1}";

    @Test
    public void md5Test( ) {
        String md5Code = MD5Util.md5(toMd5,  StandardCharsets.UTF_8);
        md5Code.replace("\n", "");
        System.out.println(md5Code);
    }


}
