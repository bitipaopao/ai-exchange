package com.lenovo.research.se.aiexchange.common.spring.mybatis;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("unused")
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@ConditionalOnBean(SqlSessionFactoryBean.class)
public class SlowSqlInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        final BoundSql boundSql = statementHandler.getBoundSql();
        boolean isException = false;
        try {
            return invocation.proceed();
        } catch (Exception e) {
            String logMessage = "<==  Execute SQL Error: " +
                    "\n" +
                    getSqlStr(boundSql) +
                    "\n";
            logger.error(logMessage);
            isException = true;
            throw e;
        } finally {
            if (!isException) {
                long execSqlTimeConsuming = System.currentTimeMillis() - startTime;
                long TIME_CONSUMING_THRESHOLD = 100;
                if (execSqlTimeConsuming >= TIME_CONSUMING_THRESHOLD) {
                    String loggerMessage = "<==  Slow SQL: " +
                            "\n" +
                            getSqlStr(boundSql) +
                            "\n" +
                            "执行耗时[" +
                            execSqlTimeConsuming +
                            "ms]";
                    logger.error(loggerMessage);
                }
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String getSqlStr(BoundSql boundSql) {
        Object parameterValue = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterValue == null || parameterMappings.size() == 0) {
            return sql;
        }
        try {
            sql = sql.replaceFirst("\\?", getParameterValueStr(parameterValue));
        } catch (Exception e) {
            logger.error("reorganize SQL statement errors.", e);
        }

        return sql;
    }

    private String getParameterValueStr(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        if (value instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
            return "'" + formatter.format(value) + "'";
        }

        return value.toString();
    }
}
