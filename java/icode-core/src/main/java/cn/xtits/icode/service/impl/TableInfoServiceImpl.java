package cn.xtits.icode.service.impl;

import cn.xtits.icode.entity.TableInfo;
import cn.xtits.icode.entity.TableInfoExample;
import cn.xtits.icode.mapper.TableInfoMapper;
import cn.xtits.icode.service.TableInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dan on 2017-12-19 04:03:55
 */
@Service
public class TableInfoServiceImpl implements TableInfoService {

    @Resource
    private TableInfoMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TableInfo record) {
        return mapper.insert(record);
    }

    @Override
    public List<TableInfo> listByExample(TableInfoExample example) {
        PageHelper.startPage(example.getPageIndex().intValue(), example.getPageSize().intValue());
        Page page = (Page) mapper.selectByExample(example);
        example.setCount((int)page.getTotal());
        return page.toPageInfo().getList();
    }

    @Override
    public TableInfo getByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(TableInfo record) {
        return mapper.updateByPrimaryKey(record);
    }
    
    @Override
    public int updateByPrimaryKeySelective(TableInfo record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}