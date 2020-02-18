//服务层
app.service('loginService',function($http){
	    	
	//获取登录用户
	this.getLoginName=function(){
		return $http.get('../login/loginName.do');
	}

});