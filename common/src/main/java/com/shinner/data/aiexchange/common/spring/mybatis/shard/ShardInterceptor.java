package com.shinner.data.aiexchange.common.spring.mybatis.shard;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@ConditionalOnBean(SqlSessionFactoryBean.class)
public class ShardInterceptor implements Interceptor {

    private static final ReflectorFactory defaultReflectorFactory = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                defaultReflectorFactory
        );

        TableShard tableShard = getTableShardAnnotation(metaObject);
        if (tableShard == null) {
            return invocation.proceed();
        }

        String tableName = tableShard.tablePrefix();
        String newTableName = covertTableName(tableShard, metaObject);
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        metaObject.setValue("delegate.boundSql.sql", sql.replaceAll(tableName, newTableName));

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private TableShard getTableShardAnnotation(MetaObject metaObject) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)
                metaObject.getValue("delegate.mappedStatement");

        String id = mappedStatement.getId();
        id = id.substring(0, id.lastIndexOf('.'));
        Class<?> clazz = Class.forName(id);
        return clazz.getAnnotation(TableShard.class);
    }

    private String covertTableName(TableShard tableShard, MetaObject metaObject) {
        List<Object> params = getSqlParameters(tableShard.indexFields(), metaObject);
        try {
            Class<? extends TableShardStrategy> shardStrategyClazz = tableShard.shardStrategy();
            TableShardStrategy shardStrategy = shardStrategyClazz.newInstance();
            return shardStrategy.tableShard(tableShard.tablePrefix(), params);
        } catch (Throwable t) {
            throw new RuntimeException("Table shard error at covert table name.", t);
        }
    }

    private List<Object> getSqlParameters(String[] fields, MetaObject metaObject) {
        return Arrays.stream(fields)
                .map(field -> {
                    String metaKey = "delegate.parameterHandler.parameterObject." + field;
                    Object obj = metaObject.getValue(metaKey);
                    if (Objects.isNull(obj)) {
                        throw new RuntimeException("Table shard need the parameter : " + field);
                    }
                    return obj;
                })
                .collect(Collectors.toList());
    }
}
