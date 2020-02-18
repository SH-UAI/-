package com.offcn.service.impl;
import java.util.List;
import java.util.Map;

import com.offcn.common.Specification;
import com.offcn.dao.TbSpecificationOptionMapper;
import com.offcn.pojo.TbSpecificationOption;
import com.offcn.pojo.TbSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.dao.TbSpecificationMapper;
import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationExample;
import com.offcn.pojo.TbSpecificationExample.Criteria;
import com.offcn.service.SpecificationService;

import com.offcn.common.PageResult;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		// 添加规格
		specificationMapper.insert(specification.getTbSpecification());
		// 添加规格选项
        for (TbSpecificationOption tbSpecificationOption : specification.getSpectionOptionList()) {
                // 获取规格的id
                Long id = specification.getTbSpecification().getId();

                tbSpecificationOption.setSpecId(id);
            specificationOptionMapper.insert(tbSpecificationOption);
        }
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
	    // 通过前台传来的对象修改
		specificationMapper.updateByPrimaryKey(specification.getTbSpecification());
		// 先删除规格选项  再重新添加
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specification.getTbSpecification().getId());
        specificationOptionMapper.deleteByExample(example);
        // 删除之后  通过循环遍历重新添加
        for (TbSpecificationOption tbSpecificationOption : specification.getSpectionOptionList()) {
            // 获取规格的id
            Long id = specification.getTbSpecification().getId();

            tbSpecificationOption.setSpecId(id);
            specificationOptionMapper.insert(tbSpecificationOption);
        }
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
	    // 通过主键查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        // 通过外键,也就是规格的主键来查询规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
        Specification specification = new Specification();
        specification.setTbSpecification(tbSpecification);
        specification.setSpectionOptionList(specificationOptions);
        return specification;
    }

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
	    // 删除规格的同时  要删除规格对应的规格选项
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

    @Override
    public List<Map> selectSpecificationOption() {
        return specificationMapper.selectSpecificationOption();
    }
}
