package com.lenovo.research.se.aiexchange.common.spring.mybatis.shard;

import java.util.List;

public interface TableShardStrategy {

    String tableShard(String tableName, List<Object> params);
}
