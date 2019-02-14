package cn.xtits.icode.service.impl;

import cn.xtits.icode.entity.ConnectionInfo;
import cn.xtits.icode.entity.ConnectionInfoExample;
import cn.xtits.icode.mapper.ConnectionInfoMapper;
import cn.xtits.icode.service.ConnectionInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dan on 2017-12-19 04:03:55
 */
@Service
public class ConnectionInfoServiceImpl implements ConnectionInfoService {

    @Resource
    private ConnectionInfoMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConnectionInfo record) {
        return mapper.insert(record);
    }

    @Override
    public List<ConnectionInfo> listByExample(ConnectionInfoExample example) {
        PageHelper.startPage(example.getPageIndex().intValue(), example.getPageSize().intValue());
        Page page = (Page) mapper.selectByExample(example);
        example.setCount((int)page.getTotal());
        return page.toPageInfo().getList();
    }

    @Override
    public ConnectionInfo getByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(ConnectionInfo record) {
        return mapper.updateByPrimaryKey(record);
    }
    
    @Override
    public int updateByPrimaryKeySelective(ConnectionInfo record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}