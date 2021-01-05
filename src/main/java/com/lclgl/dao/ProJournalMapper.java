package com.lclgl.dao;

import com.lclgl.pojo.ProJournal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cksh
 * @create 2021-01-04 19:20
 */
@Repository
@Mapper
public interface ProJournalMapper {

    public List<ProJournal> getJournals();

    public int addProJournal(ProJournal proJournal);

}
