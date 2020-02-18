 //商品类目控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	

	//批量删除
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
		$scope.entity.typeId = $scope.id ;
        if($scope.entity.id!=null){//如果有ID
            serviceObject=itemCatService.update( $scope.entity ); //修改
        }else{
        	$scope.entity.parentId = $scope.parentId ;
            serviceObject=itemCatService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    //$scope.reloadList();//重新加载
                    $scope.findItemCatByPid = $scope.parentId ;
                }else{
                    alert(response.message);
                }
            }
        );
    }
	// 分类查询
	// 设置级别  默认为一级
	$scope.grade = 1 ;
	$scope.setGrade = function (value) {
		$scope.grade = value ;
    }
	// 获取当前所在的级别,在当前级别下添加数据
    $scope.parentId = 0 ;
    // 获取第一级的数据
	$scope.findItemCatByPid = function(pid){
		//每次点击下一级的时候,将pid赋值给parentId
		$scope.parentId = pid;
		//第一次的parent_id为0  第二次的parent_id就是id
		itemCatService.findItemCatByPid(pid).success(
			function (response) {
				$scope.list = response;
            }
		)
	}

    // 显示分类列表
	$scope.itemCatList = function (p_entity) {
		// 显示顶级列表时  将第二级和第三级置空
		if ($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null ;
		}
		// 显示第二级的时候, 为第二级赋值  将第三级置空
		if ($scope.grade == 2){
			$scope.entity_1 = p_entity;
            $scope.entity_2 = null ;
		}
		// 显示第三级的时候,为第三级赋值
        if ($scope.grade == 3){
            $scope.entity_2 = p_entity;
        }
		// 每次查询下一级都需要调用这个方法
        $scope.findItemCatByPid(p_entity.id);
    }

     //添加分类  选择模板
	//$scope.typelateList = {data :[{"id":1,"text":'测试'}]}

    $scope.selectTypeList = function () {
		itemCatService.selectTypeList().success(
			function (response) {
                $scope.typelateList = {data :response}
            }
		)
    }

    // 删除分类
	$scope.deleteType = function () {
		itemCatService.deleteType($scope.selectIds).success(
			function (response) {
				if (response.success){
                    window.location.reload();
				} else{
					alert(response.msg);
				}

            }
		)
    }
});	