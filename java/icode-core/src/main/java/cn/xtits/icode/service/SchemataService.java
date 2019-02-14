package cn.xtits.icode.service;

import cn.xtits.icode.entity.Schemata;
import cn.xtits.icode.entity.SchemataExample;

import java.util.List;

/**
 * Created by 
 */
public interface SchemataService {

    int deleteByPrimaryKey(Integer id);

    int insert(Schemata record);

    List<Schemata> listByExample(SchemataExample example);

    Schemata getByPrimaryKey(Integer id);

    int updateByPrimaryKey(Schemata record);
    
    int updateByPrimaryKeySelective(Schemata record);
}