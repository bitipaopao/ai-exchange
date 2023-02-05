package com.lenovo.research.se.aiexchange.core;

import com.lenovo.research.se.aiexchange.common.auth.ClientService;
import com.lenovo.research.se.aiexchange.core.dao.ClientDao;
import com.lenovo.research.se.aiexchange.core.entity.ClientDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public byte[] getCS(String ak) {
        ClientDO client = clientDao.selectByAK(ak);
        return Optional.ofNullable(client)
                .map(ClientDO::getAccessSecret)
                .orElse("").getBytes(StandardCharsets.UTF_8);
    }
}
