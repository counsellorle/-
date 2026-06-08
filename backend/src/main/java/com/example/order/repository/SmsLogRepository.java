package com.example.order.repository;

import com.example.order.entity.SmsLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

/**
 * 短信日志仓储接口
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Mapper
public interface SmsLogRepository {

    /**
     * 根据 ID 查询短信日志
     *
     * @param id 日志 ID
     * @return 日志
     */
    @Select("SELECT * FROM sms_log WHERE id = #{id}")
    Optional<SmsLog> findById(@Param("id") Long id);

    /**
     * 保存短信日志
     *
     * @param smsLog 日志
     * @return 是否成功
     */
    int save(SmsLog smsLog);
}
