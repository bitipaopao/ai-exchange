package com.shinner.data.aiexchange.common.auth;

@FunctionalInterface
public interface ClientService {

    byte[] getCS(String ak);
}
