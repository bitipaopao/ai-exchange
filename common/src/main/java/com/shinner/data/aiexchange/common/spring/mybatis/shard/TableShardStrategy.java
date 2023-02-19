package com.shinner.data.aiexchange.common.spring.mybatis.shard;

import java.util.List;

public interface TableShardStrategy {

    String tableShard(String tableName, List<Object> params);
}
