package com.shinner.data.aiexchange.core.dao;

import com.shinner.data.aiexchange.core.entity.AiServiceDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AiServiceDao {

    @Delete("DELETE FROM dwd_ai_service\n" +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR} " +
            "   AND host=#{host, jdbcType=VARCHAR}")
    void delete(String aiKey, String host);

    @Select("SELECT " +
            "  `ai_key`, " +
            "  `vendor`, " +
            "  `protocol`, " +
            "  `host`, " +
            "  `port`, " +
            "  `callback_host`, " +
            "  echo_url, " +
            "  create_time, " +
            "  modified_time " +
            "FROM dwd_ai_service " +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR}")
    List<AiServiceDO> queryByAiKey(String aiKey);

    @Select("SELECT " +
            "  `ai_key`, " +
            "  `vendor`, " +
            "  `protocol`, " +
            "  `host`, " +
            "  `port`, " +
            "  `callback_host`, " +
            "  echo_url, " +
            "  create_time, " +
            "  modified_time " +
            "FROM dwd_ai_service ")
    List<AiServiceDO> all();


    @Insert("<script>" +
            "INSERT INTO dwd_ai_service\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                `ai_key`,\n" +
            "            </if>\n" +
            "            <if test=\"vendor != null\">\n" +
            "                `vendor`,\n" +
            "            </if>\n" +
            "            <if test=\"protocol != null\">\n" +
            "                `protocol`,\n" +
            "            </if>\n" +
            "            <if test=\"host != null\">\n" +
            "                `host`,\n" +
            "            </if>\n" +
            "            <if test=\"port != null\">\n" +
            "                `port`,\n" +
            "            </if>\n" +
            "            <if test=\"callbackHost != null\">\n" +
            "                `callback_host`,\n" +
            "            </if>\n" +
            "            <if test=\"echoUrl != null\">\n" +
            "                `echo_url`,\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "        VALUES\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                #{aiKey, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"vendor != null\">\n" +
            "                #{vendor, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"protocol != null\">\n" +
            "                #{protocol, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"host != null\">\n" +
            "                #{host, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"port != null\">\n" +
            "                #{port, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"callbackHost != null\">\n" +
            "                #{callbackHost, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"echoUrl != null\">\n" +
            "                #{echoUrl, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "ON DUPLICATE KEY UPDATE \n" +
            "  callback_host = VALUES(callback_host),\n" +
            "  `echo_url` = VALUES(`echo_url`),\n" +
            "  modified_time = now()\n" +
            "</script>")
    void insert(AiServiceDO record);
}
