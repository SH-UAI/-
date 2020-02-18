//服务层
app.service('userService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.register=function(entity){
		return $http.post('../seller/register.do',entity);
	}

});