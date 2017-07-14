$(function(){	
    
	$("body").css({visibility:"visible"});
	$("#courierInfo").datagrid({
		iconCls : 'icon-forward',
		fit : true,
		border : false,
		rownumbers : true,
		striped : true,
		pageList: [2,10,20,30],
		pagination : true,
		idField : 'id',
		toolbar : toolbar,
		columns : columns,
		url : '../../pageQueryCouriers.action'
	});
});

/*定义列*/
var columns = [[{
	field : 'id',
	checkbox : true 
},{
	field : 'courierNum',
	title : '工号',
	width : 100,
	align : 'center'
},{
	field : 'name',
	title : '姓名',
	width : 100,
	align : 'center'
},{
	field : 'telephone',
	title : '电话',
	width : 100,
	align : 'center'
},{
	field : 'pda',
	title : 'PDA编号',
	width : 100,
	align : 'center'
},{
	field : 'deltag',
	title : '作废标志',
	width : 100,
	align : 'center',
	formatter : function(value, rowData, rowIndex){
		if(value == null){
			return '正常使用';
		}else if(value == '1'){
			return '已作废';
		}
	}
},{
	field : 'checkPwd',
	title : '查台密码',
	width : 100,
	align : 'center'
},{
	field : 'type',
	title : '取件类型',
	width : 100,
	align : 'center'
},{
	field : 'company',
	title : '单位',
	width : 100,
	align : 'center'
},{
	field : 'vehicleType',
	title : '车辆类型',
	width : 100,
	align : 'center'
},{
	field : 'vehicleNum',
	title : '车牌号',
	width : 100,
	align : 'center'
},{
	field : 'standard.name',
	title : '取派标准',
	width : 100,
	align : 'center',
	formatter : function(val, row, index){
		if(row.standard != null){
			return row.standard.name;
		}
		return "";
	}
},{
	field : 'takeTime',
	title : '轮岗时间',
	width : 100,
	align : 'center'
},{
	field : 'fixedAreas',
	title : '定区',
	width : 100,
	align : 'center'
}]];
/*定义工具栏*/
var toolbar = [{
	id : 'button-add',
	text : '增加',
	iconCls : 'icon-add',
	handler : function(){
		$("#saveCourier").window('open');
	}
},{
	id : 'button-edit',
	text : '修改',
	iconCls : 'icon-edit',
	handler : function(){
		var selections = $("#courierInfo").datagrid("getSelections");
		if(selections.length == 1){
			// 查询到数据库中查询数据
			$.ajax({
				url : '../../findOneCourier.action',
				data : 'id='+selections[0].id,
				type : 'POST',
				success : function(courierData){
					$("#saveOrUpdateCourier").form('load',courierData);
					$("#saveCourier").window('open');
				}
			});
		}else{
			if(selections.length >1){
				$.messager.alert("一次只能修改一条记录!");
			}else{
				$.messager.alert("没有选中任何记录,请选择:");
			}
			
		}
		
	}
},{
	id : 'button-delete',
	text : '作废',
	iconCls : 'icon-cancel',
	handler : function(){
		// 拿到所有的选中行
		var delBatch = $("#courierInfo").datagrid('getSelections');
		if(delBatch.length == 0){
			$.messager.alert("警告","没有选中数据!","warning");	
		}else{
			var delArray = new Array();
			for(var i=0; i<delBatch.length; i++){
				delArray.push(delBatch[i].id);
			}
			var ids = delArray.join("-");
			// 向服务器发起请求,将所有的id传回去,进行内容的修改
			window.location.href="../../delBatch.action?ids="+ids+"&method=1";
		}
	}
},{
	id : 'button-back',
	text : '恢复',
	iconCls : 'icon-undo',
	handler : function(){
		// 拿到所有的选中行
		var delBatch = $("#courierInfo").datagrid('getSelections');
		if(delBatch.length == 0){
			$.messager.alert("警告","没有选中数据!","warning");	
		}else{
			var delArray = new Array();
			for(var i=0; i<delBatch.length; i++){
				delArray.push(delBatch[i].id);
			}
			var ids = delArray.join("-");
			// 向服务器发起请求,将所有的id传回去,进行内容的修改
			window.location.href="../../delBatch.action?ids="+ids+"&method=0";
		}
	}
},{
	id : 'button-search',
	text : '查询',
	iconCls : 'icon-search',
	handler : function(){
		// 打开窗口
		$("#conditionalSearch").window('open');	
	}
}];


$(function(){
	
	/*将form表单的数据转换成json数据格式的方法*/
	$.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    }; 
    
	/*保存或更新取派员信息*/
	$("#save").on("click",function(){
		if($("#saveOrUpdateCourier").form('validate')){
			$("#saveOrUpdateCourier").submit(); 
		}else{
			$.messager.alert("提示", "warning", "表格中存在错误数据,请检查后在提交!");
		}
	});

	/*派件员的条件查询*/
	$("#searchBtn").on('click', function(){
		// 将表单中的数据接收,转换成json格式
		var conditions = $("#conditionalForm").serializeJson();
		// 将json数据绑定到数据表中datagrid('load',param)
		$("#courierInfo").datagrid('load',conditions);
		// 关闭窗口
		$("#conditionalSearch").window('close');
	});
});