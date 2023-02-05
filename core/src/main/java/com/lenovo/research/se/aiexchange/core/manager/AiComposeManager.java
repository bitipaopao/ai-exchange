package com.lenovo.research.se.aiexchange.core.manager;

import com.lenovo.research.se.aiexchange.core.dao.AiRequestDao;
import com.lenovo.research.se.aiexchange.core.entity.AiComposeAdapterDO;
import com.lenovo.research.se.aiexchange.core.entity.AiComposeDO;
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
