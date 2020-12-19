package com.lclgl.dao;

import com.lclgl.pojo.AuditInfo;
import com.lclgl.pojo.ProInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-16 22:45
 */
@Repository
@Mapper
public interface AuditMapper {

    public List<AuditInfo> getAuditInfo(int staffId);

    public List<AuditInfo> getUnfinishedAuditInfo(int staffId);

    public int addAuditInfo(AuditInfo auditInfo);

    public List<Integer> getUnfinishedProIds(int staffId);

    public int updateAuditStatus(Map<String, Object> map);

}
