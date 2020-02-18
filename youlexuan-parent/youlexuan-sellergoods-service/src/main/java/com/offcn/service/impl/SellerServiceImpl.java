package com.offcn.service.impl;
import java.util.List;

import com.offcn.common.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.dao.TbSellerMapper;
import com.offcn.pojo.TbSeller;
import com.offcn.pojo.TbSellerExample;
import com.offcn.pojo.TbSellerExample.Criteria;
import com.offcn.service.SellerService;

import com.offcn.common.PageResult;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeller> page=   (Page<TbSeller>) sellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller seller) {
		sellerMapper.insert(seller);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller seller){
		sellerMapper.updateByPrimaryKey(seller);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param sellerId
	 * @return
	 */
	@Override
	public TbSeller findOne(String sellerId){
		return sellerMapper.selectByPrimaryKey(sellerId);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] sellerIds) {
		for(String sellerId:sellerIds){
			sellerMapper.deleteByPrimaryKey(sellerId);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
            // 查询审核商家时  只查询状态为0的商家
            seller.setStatus("0");
		TbSellerExample example=new TbSellerExample();
		Criteria criteria = example.createCriteria();
		
		if(seller!=null){

						if(seller.getName()!=null && seller.getName().length()>0){
				criteria.andNameLike("%"+seller.getName()+"%");
			}			if(seller.getNickName()!=null && seller.getNickName().length()>0){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}			if(seller.getPassword()!=null && seller.getPassword().length()>0){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}			if(seller.getEmail()!=null && seller.getEmail().length()>0){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}			if(seller.getMobile()!=null && seller.getMobile().length()>0){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}			if(seller.getStatus()!=null && seller.getStatus().length()>0){
				criteria.andStatusLike("%"+seller.getStatus()+"%");
			}			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}			if(seller.getBrief()!=null && seller.getBrief().length()>0){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}			if(seller.getBankName()!=null && seller.getBankName().length()>0){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}	
		}
		
		Page<TbSeller> page= (Page<TbSeller>)sellerMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	// 注册商户
	@Override
	public void RegisterUser(TbSeller seller) {
		// 用md5对密码进行加密
        String pwd = Md5Utils.getPwd(seller.getPassword());
        seller.setPassword(pwd);
        // 注册时  默认为其状态赋值为0
        seller.setStatus("0");
        sellerMapper.insert(seller);
	}

    @Override
    public void updateseller(String sellerId, String status) {

        TbSeller seller = sellerMapper.selectByPrimaryKey(sellerId);
        seller.setStatus(status);
        sellerMapper.updateByPrimaryKey(seller);
    }
}