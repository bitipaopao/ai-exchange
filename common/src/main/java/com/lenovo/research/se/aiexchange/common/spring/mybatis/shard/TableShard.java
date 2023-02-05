package com.lenovo.research.se.aiexchange.common.spring.mybatis.shard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableShard {

    String tablePrefix();

    String[] indexFields();

    Class<? extends TableShardStrategy> shardStrategy();

}
