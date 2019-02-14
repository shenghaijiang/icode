package cn.xtits.icode.service;

import cn.xtits.icode.entity.ConnectionInfo;
import cn.xtits.icode.entity.ConnectionInfoExample;

import java.util.List;

/**
 * Created by 
 */
public interface ConnectionInfoService {

    int deleteByPrimaryKey(Integer id);

    int insert(ConnectionInfo record);

    List<ConnectionInfo> listByExample(ConnectionInfoExample example);

    ConnectionInfo getByPrimaryKey(Integer id);

    int updateByPrimaryKey(ConnectionInfo record);
    
    int updateByPrimaryKeySelective(ConnectionInfo record);
}