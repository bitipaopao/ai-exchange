package com.shinner.data.aiexchange.core;

import com.shinner.data.aiexchange.common.auth.ClientService;
import com.shinner.data.aiexchange.core.dao.ClientDao;
import com.shinner.data.aiexchange.core.entity.ClientDO;
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
