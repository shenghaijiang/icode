package cn.xtits.icode.service.impl;

import cn.xtits.icode.entity.Project;
import cn.xtits.icode.entity.ProjectExample;
import cn.xtits.icode.mapper.ProjectMapper;
import cn.xtits.icode.service.ProjectService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Dan on 2017-12-19 04:03:55
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Project record) {
        return mapper.insert(record);
    }

    @Override
    public List<Project> listByExample(ProjectExample example) {
        PageHelper.startPage(example.getPageIndex().intValue(), example.getPageSize().intValue());
        Page page = (Page) mapper.selectByExample(example);
        example.setCount((int)page.getTotal());
        return page.toPageInfo().getList();
    }

    @Override
    public Project getByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Project record) {
        return mapper.updateByPrimaryKey(record);
    }
    
    @Override
    public int updateByPrimaryKeySelective(Project record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}