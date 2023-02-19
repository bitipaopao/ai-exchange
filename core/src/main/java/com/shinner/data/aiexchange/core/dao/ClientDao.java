package com.shinner.data.aiexchange.core.dao;

import com.shinner.data.aiexchange.core.entity.ClientDO;
import org.apache.ibatis.annotations.*;

public interface ClientDao {

    @Delete("DELETE FROM sys_client_info\n" +
            "WHERE client_id=#{clientId, jdbcType=VARCHAR} \n" +
            "   AND env=#{env, jdbcType=VARCHAR} ")
    void delete(ClientDO record);

    @Select("SELECT " +
            "  client_id, " +
            "  env, " +
            "  `client_access_key`, " +
            "  access_secret, " +
            "  `app_id`, " +
            "  project_id, " +
            "  expire_time, " +
            "  create_time, " +
            "  modified_time " +
            "FROM sys_client_info " +
            "WHERE client_access_key=#{accessKey, jdbcType=VARCHAR} " +
            "LIMIT 1")
    ClientDO selectByAK(@Param("accessKey") String accessKey);

    @Select("SELECT " +
            "  client_id, " +
            "  `env`, " +
            "  `client_access_key`, " +
            "  access_secret, " +
            "  `app_id`, " +
            "  project_id, " +
            "  expire_time, " +
            "  create_time, " +
            "  modified_time " +
            "FROM sys_client_info " +
            "WHERE client_id=#{clientId, jdbcType=VARCHAR} AND `env`=#{env, jdbcType=VARCHAR}")
    ClientDO selectByKey(@Param("clientId") String clientId, @Param("env") String env);

    @Insert("<script>" +
            "INSERT INTO sys_client_info\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"clientId != null\">\n" +
            "                `client_id`,\n" +
            "            </if>\n" +
            "            <if test=\"env != null\">\n" +
            "                env,\n" +
            "            </if>\n" +
            "            <if test=\"clientAccessKey != null\">\n" +
            "                `client_access_key`,\n" +
            "            </if>\n" +
            "            <if test=\"accessSecret != null\">\n" +
            "                `access_secret`,\n" +
            "            </if>\n" +
            "            <if test=\"appId != null\">\n" +
            "                `app_id`,\n" +
            "            </if>\n" +
            "            <if test=\"projectId != null\">\n" +
            "                `project_id`,\n" +
            "            </if>\n" +
            "            <if test=\"expireTime != null\">\n" +
            "                `expire_time`,\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "        VALUES\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"clientId != null\">\n" +
            "                #{clientId, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"env != null\">\n" +
            "                #{env, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"clientAccessKey != null\">\n" +
            "                #{clientAccessKey, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"accessSecret != null\">\n" +
            "                #{accessSecret, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"appId != null\">\n" +
            "                #{appId, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"projectId != null\">\n" +
            "                #{projectId, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "            <if test=\"expireTime != null\">\n" +
            "                #{expireTime, jdbcType=TIMESTAMP},\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "ON DUPLICATE KEY UPDATE \n" +
            "  client_access_key = VALUES(client_access_key),\n" +
            "  `access_secret` = VALUES(`access_secret`),\n" +
            "  `expire_time` = VALUES(`expire_time`),\n" +
            "  modified_time = now()\n" +
            "</script>")
    void insertOnDup(ClientDO clientInfo);

    @Update("<script>" +
            "UPDATE sys_client_info " +
            "SET " +
            "   <trim suffixOverrides=\",\">" +
            "       <if test=\"clientAccessKey != null\">" +
            "           client_access_key = #{clientAccessKey}," +
            "       </if>\n" +
            "       <if test=\"accessSecret != null\">" +
            "           `access_secret` = #{accessSecret}," +
            "       </if>\n" +
            "       <if test=\"appId != null\">" +
            "           app_id = #{appId}," +
            "       </if>" +
            "       <if test=\"projectId != null\">" +
            "           project_id = #{projectId}," +
            "       </if>" +
            "       <if test=\"expireTime != null\">" +
            "           `expire_time` = #{expireTime}," +
            "       </if>" +
            "       modified_time = now()" +
            "   </trim>" +
            "WHERE client_id=#{clientId, jdbcType=VARCHAR} AND `env`=#{env, jdbcType=VARCHAR}" +
            "</script>")
    void updateSelective(ClientDO clientInfo);
}
