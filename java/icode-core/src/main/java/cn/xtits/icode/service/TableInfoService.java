package cn.xtits.icode.service;

import cn.xtits.icode.entity.TableInfo;
import cn.xtits.icode.entity.TableInfoExample;

import java.util.List;

/**
 * Created by 
 */
public interface TableInfoService {

    int deleteByPrimaryKey(Integer id);

    int insert(TableInfo record);

    List<TableInfo> listByExample(TableInfoExample example);

    TableInfo getByPrimaryKey(Integer id);

    int updateByPrimaryKey(TableInfo record);
    
    int updateByPrimaryKeySelective(TableInfo record);
}