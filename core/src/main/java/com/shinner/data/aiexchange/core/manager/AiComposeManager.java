package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.dao.AiRequestDao;
import com.shinner.data.aiexchange.core.entity.AiComposeAdapterDO;
import com.shinner.data.aiexchange.core.entity.AiComposeDO;
import org.springframework.beans.factory.annotation.Autowired;

public class AiComposeManager {

    @Autowired
    private AiRequestDao aiRequestDao;
    @Autowired
    private AiServiceProxy aiServiceProxy;
    @Autowired
    private AiComposeDO aiComposeDO;
    @Autowired
    private AiComposeAdapterDO aiComposeAdapterDO;


}
