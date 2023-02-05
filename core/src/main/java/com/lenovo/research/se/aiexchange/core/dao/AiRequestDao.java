package com.lenovo.research.se.aiexchange.core.dao;

import com.lenovo.research.se.aiexchange.core.entity.AiRequestDO;
import org.apache.ibatis.annotations.*;

public interface AiRequestDao {


    @Delete("DELETE FROM dwd_ai_request\n" +
            "WHERE uuid=#{uuid, jdbcType=VARCHAR} ")
    void deleteByUuid(@Param("uuid") String uuid);

    @Delete("DELETE FROM dwd_ai_request\n" +
            "WHERE `create_time` &lt;= CURRENT_TIMESTAMP - INTERVAL 3 MONTH")
    void clearHistory();

    @Delete("DELETE FROM dwd_ai_request\n" +
            "WHERE request_id=#{requestId, jdbcType=BIGINT} ")
    void delete(@Param("requestId") Long requestId);

    @Select("SELECT " +
            "  request_id, " +
            "  uuid, " +
            "  `ai_key`, " +
            "  function_id, " +
            "  argument, " +
            "  argument_md5, " +
            "  response, " +
            "  request_status, " +
            "  done_time, " +
            "  create_time, " +
            "  modified_time " +
            "FROM dwd_ai_request " +
            "WHERE uuid=#{uuid, jdbcType=VARCHAR} ")
    AiRequestDO selectByUuid(@Param("uuid") String uuid);

    @Insert("<script>" +
            "INSERT INTO dwd_ai_request\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"uuid != null\">\n" +
            "                `uuid`,\n" +
            "            </if>\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                ai_key,\n" +
            "            </if>\n" +
            "            <if test=\"functionId != null\">\n" +
            "                `function_id`,\n" +
            "            </if>\n" +
            "            <if test=\"argument != null\">\n" +
            "                `argument`,\n" +
            "            </if>\n" +
            "            <if test=\"argumentMd5 != null\">\n" +
            "                `argument_md5`,\n" +
            "            </if>\n" +
            "            <if test=\"requestStatus != null\">\n" +
            "                `request_status`,\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "        VALUES\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"uuid != null\">\n" +
            "                #{uuid, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                #{aiKey, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"functionId != null\">\n" +
            "                #{functionId, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"argument != null\">\n" +
            "                #{argument, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"argumentMd5 != null\">\n" +
            "                #{argumentMd5, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"requestStatus != null\">\n" +
            "                #{requestStatus, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "</script>")
    void insert(AiRequestDO record);

    @Update("<script>" +
            "UPDATE dwd_ai_request " +
            "SET " +
            "   <trim suffixOverrides=\",\">" +
            "       <if test=\"response != null\">" +
            "           response = #{response}," +
            "       </if>" +
            "       <if test=\"requestStatus != null\">" +
            "           request_status = #{requestStatus}," +
            "       </if>" +
            "       <if test=\"doneTime != null\">" +
            "           `done_time` = #{doneTime}," +
            "       </if>" +
            "       modified_time = now()" +
            "   </trim>" +
            "WHERE uuid=#{uuid, jdbcType=VARCHAR} and request_status&lt;#{requestStatus, jdbcType=INTEGER}" +
            "</script>")
    void updateLimitByStatus(AiRequestDO record);
}
