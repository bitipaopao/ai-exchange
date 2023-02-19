package com.shinner.data.aiexchange.signature.test;

import com.shinner.data.aiexchange.signature.SignatureHelper;
import org.junit.Test;

public class SignatureDemo {

    @Test
    public void signatureTest() {
        String urlParaStr = "salt=0.234153348&argMd5=6f9fe6f61b7e4681b32f881996754c11&access-key=moxi-oa-ak-suishenxing";
        String sk = "046A752BE25BCE2D";
        String newUrlParaStr = SignatureHelper.generatorSignUrl(urlParaStr, sk);
        System.out.println(newUrlParaStr);
    }

}
