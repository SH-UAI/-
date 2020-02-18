 //控制层 
app.controller('userController' ,function($scope,$controller ,userService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.register = function(){
		userService.register($scope.entity).success(
			function(response){
				if (response.success){
					location.href = "shoplogin.html";
				} else{
					alert(response.msg);
				}
			}			
		);
	}    

    
});	