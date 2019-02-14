package cn.xtits.icode.service.impl;

import cn.xtits.icode.entity.Schemata;
import cn.xtits.icode.entity.SchemataExample;
import cn.xtits.icode.mapper.SchemataMapper;
import cn.xtits.icode.service.SchemataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dan on 2017-12-19 04:03:55
 */
@Service
public class SchemataServiceImpl implements SchemataService {

    @Resource
    private SchemataMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Schemata record) {
        return mapper.insert(record);
    }

    @Override
    public List<Schemata> listByExample(SchemataExample example) {
        PageHelper.startPage(example.getPageIndex().intValue(), example.getPageSize().intValue());
        Page page = (Page) mapper.selectByExample(example);
        example.setCount((int)page.getTotal());
        return page.toPageInfo().getList();
    }

    @Override
    public Schemata getByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Schemata record) {
        return mapper.updateByPrimaryKey(record);
    }
    
    @Override
    public int updateByPrimaryKeySelective(Schemata record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}