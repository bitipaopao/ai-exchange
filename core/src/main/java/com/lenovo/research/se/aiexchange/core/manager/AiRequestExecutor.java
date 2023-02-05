package com.lenovo.research.se.aiexchange.core.manager;

import com.lenovo.research.se.aiexchange.core.dao.AiFunctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class AiRequestExecutor {

    @Autowired
    private AiFunctionDao aiFunctionDao;

    @PostConstruct
    public void init() {

    }
}
