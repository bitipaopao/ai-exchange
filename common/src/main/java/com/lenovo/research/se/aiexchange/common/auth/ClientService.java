package com.lenovo.research.se.aiexchange.common.auth;

@FunctionalInterface
public interface ClientService {

    byte[] getCS(String ak);
}
