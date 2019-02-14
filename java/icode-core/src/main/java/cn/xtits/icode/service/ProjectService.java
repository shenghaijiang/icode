package cn.xtits.icode.service;

import cn.xtits.icode.entity.Project;
import cn.xtits.icode.entity.ProjectExample;

import java.util.List;

/**
 * Created by 
 */
public interface ProjectService {

    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    List<Project> listByExample(ProjectExample example);

    Project getByPrimaryKey(Integer id);

    int updateByPrimaryKey(Project record);
    
    int updateByPrimaryKeySelective(Project record);
}