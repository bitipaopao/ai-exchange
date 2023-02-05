package com.lenovo.research.se.aiexchange.core.dao;

import com.lenovo.research.se.aiexchange.core.entity.AiFunctionDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AiFunctionDao {

    @Select("SELECT " +
            "  `ai_key`, " +
            "  function_id, " +
            "  `function_res_type`, " +
            "  `function_batch`, " +
            "  flow_interval_second, " +
            "  flow_rate, " +
            "  function_protocol, " +
            "  function_method, " +
            "  function_info, " +
            "  path, " +
            "  valid, " +
            "  create_time, " +
            "  modified_time " +
            "FROM dwd_ai_function " +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR}  " +
            "   AND function_id=#{functionId, jdbcType=VARCHAR}")
    AiFunctionDO getAiFunctionInfo(String aiKey, String functionId);


    @Select("SELECT " +
            "  `ai_key`, " +
            "  function_id, " +
            "  `function_res_type`, " +
            "  `function_batch`, " +
            "  flow_interval_second, " +
            "  flow_rate, " +
            "  function_protocol, " +
            "  function_method, " +
            "  function_info, " +
            "  path, " +
            "  valid, " +
            "  create_time, " +
            "  modified_time " +
            "FROM dwd_ai_function " +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR}")
    List<AiFunctionDO> queryAiFunctionByAiKey(String aiKey);

    @Delete("DELETE FROM dwd_ai_function " +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR}  " +
            "   AND function_id=#{functionId, jdbcType=VARCHAR}")
    void delete(String aiKey, String functionId);

    @Insert("<script>" +
            "INSERT INTO dwd_ai_function\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                `ai_key`,\n" +
            "            </if>\n" +
            "            <if test=\"functionId != null\">\n" +
            "                function_id,\n" +
            "            </if>\n" +
            "            <if test=\"functionResType != null\">\n" +
            "                `function_res_type`,\n" +
            "            </if>\n" +
            "            <if test=\"functionBatch != null\">\n" +
            "                `function_batch`,\n" +
            "            </if>\n" +
            "            <if test=\"flowIntervalSecond != null\">\n" +
            "                `flow_interval_second`,\n" +
            "            </if>\n" +
            "            <if test=\"flowRate != null\">\n" +
            "                `flow_rate`,\n" +
            "            </if>\n" +
            "            <if test=\"path != null\">\n" +
            "                `path`,\n" +
            "            </if>\n" +
            "            <if test=\"valid != null\">\n" +
            "                `valid`,\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "        VALUES\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"aiKey != null\">\n" +
            "                #{aiKey, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"functionId != null\">\n" +
            "                #{functionId, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"functionResType != null\">\n" +
            "                #{functionResType, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "            <if test=\"functionBatch != null\">\n" +
            "                #{functionBatch, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "            <if test=\"flowIntervalSecond != null\">\n" +
            "                #{flowIntervalSecond, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "            <if test=\"flowRate != null\">\n" +
            "                #{flowRate, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "            <if test=\"path != null\">\n" +
            "                #{path, jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"valid != null\">\n" +
            "                #{valid, jdbcType=INTEGER},\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "ON DUPLICATE KEY UPDATE \n" +
            "  function_res_type = VALUES(function_res_type),\n" +
            "  `function_batch` = VALUES(`function_batch`),\n" +
            "  `flow_interval_second` = VALUES(`flow_interval_second`),\n" +
            "  `flow_rate` = VALUES(`flow_rate`),\n" +
            "  modified_time = now()\n" +
            "</script>")
    void insertOnDup(AiFunctionDO record);


    @Update("<script>" +
            "UPDATE dwd_ai_function " +
            "SET " +
            "   <trim suffixOverrides=\",\">" +
            "       <if test=\"functionResType != null\">" +
            "           function_res_type = #{functionResType}," +
            "       </if>\n" +
            "       <if test=\"functionBatch != null\">" +
            "           `function_batch` = #{functionBatch}," +
            "       </if>\n" +
            "       <if test=\"flowIntervalSecond != null\">" +
            "           flow_interval_second = #{flowIntervalSecond}," +
            "       </if>" +
            "       <if test=\"flowRate != null\">" +
            "           flow_rate = #{flowRate}," +
            "       </if>" +
            "       <if test=\"path != null\">" +
            "           `path` = #{path}," +
            "       </if>" +
            "       <if test=\"valid != null\">" +
            "           `valid` = #{valid}," +
            "       </if>" +
            "       modified_time = now() " +
            "   </trim>" +
            "WHERE ai_key=#{aiKey, jdbcType=VARCHAR}  " +
            "   AND function_id=#{functionId, jdbcType=VARCHAR}" +
            "</script>")
    void updateSelective(AiFunctionDO record);
}
