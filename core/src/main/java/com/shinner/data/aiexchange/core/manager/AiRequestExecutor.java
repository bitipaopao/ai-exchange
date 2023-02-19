package com.shinner.data.aiexchange.core.manager;

import com.shinner.data.aiexchange.core.dao.AiFunctionDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

//@Component
public class AiRequestExecutor {

    @Autowired
    private AiFunctionDao aiFunctionDao;

    @PostConstruct
    public void init() {

    }
}
