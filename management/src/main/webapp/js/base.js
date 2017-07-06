$(function(){
	// 先将body隐藏，再显示，不会出现页面刷新效果
	$("body").css({visibility:"visible"});
	
	// 基础档案信息表格
	$('#archives_grid').datagrid( {
		iconCls : 'icon-forward',
		fit : true,
		border : false,
		rownumbers : true,
		striped : true,
		pageList: [30,50,100],
		pagination : true,
		toolbar : toolbar,
		url : "../../data/archives.json",
		idField : 'id',
		columns : columns
	});
	
	// 子档案信息表格
	$('#sub_archives_grid').datagrid( {
		iconCls : 'icon-forward',
		fit : true,
		border : false,
		rownumbers : true,
		striped : true,
		pageList: [30,50,100],
		pagination : true,
		url : "../../data/sub_archives.json",
		idField : 'id',
		columns : child_columns
	});
});	
	
	//工具栏
	var toolbar = [ {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : function(){
			alert('增加');
		}
	}, {
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : function(){
			alert('修改');
		}
	},{
		id : 'button-save',
		text : '保存',
		iconCls : 'icon-save',
		handler : function(){
			alert('保存');
		}
	} ];
	
	// 定义列
	var columns = [ [ {
		field : 'id',
		title : '基础档案编号',
		width : 120,
		align : 'center'
	},{
		field : 'archiveName',
		title : '基础档案名称',
		width : 120,
		align : 'center'
	}, {
		field : 'hasChild',
		title : '是否分级',
		width : 120,
		align : 'center'
	}, {
		field : 'remark',
		title : '备注',
		width : 300,
		align : 'center'
	} ] ];
	
	var child_columns = [ [ {
		field : 'id',
		title : '档案编码',
		width : 120,
		align : 'center'
	},{
		field : 'subArchiveName',
		title : '档案名称',
		width : 120,
		align : 'center'
	}, {
		field : 'archive.id',
		title : '上级编码',
		width : 120,
		align : 'center'
	}, {
		field : 'mnemonicCode',
		title : '助记码',
		width : 120,
		align : 'center'
	}, {
		field : 'mothballed',
		title : '封存',
		width : 120,
		align : 'center'
	}, {
		field : 'remark',
		title : '备注',
		width : 300,
		align : 'center'
	} ] ];

