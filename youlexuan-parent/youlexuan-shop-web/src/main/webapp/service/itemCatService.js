//商品类目服务层
app.service('itemCatService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../itemCat/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../itemCat/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../itemCat/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../itemCat/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../itemCat/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../itemCat/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../itemCat/search.do?page='+page+"&rows="+rows, searchEntity);
	}

	// 分类查询
	this.findItemCatByPid = function (pid) {
		return $http.get('../itemCat/findItemCatByPid.do?parentId='+pid);
    }

    // 添加分类 查询模板下拉
    this.selectTypeList=function(){
        return $http.get('../typeTemplate/selectType.do');
    }
    
		// 删除分类
	this.deleteType = function (ids) {
		return $http.get('../itemCat/deleteType.do?ids='+ids);
    }
});