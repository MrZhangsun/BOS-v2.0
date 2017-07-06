$(function(){
	/*开启ztree的数据支持功能*/
	var setting = {
		data:{
        		simpleData:{
        			enable:true
        		}
		},callback: {
			onClick: zTreeOnClick
		}
	};

	// 基本功能菜单加载
	$.post("data/baseNodes.json",function(data){
		$.fn.zTree.init($("#baseFun"), setting, data);
	},"json");
	
	// 系统管理菜单加载
	$.post("data/sysNodes.json",function(data){
		$.fn.zTree.init($("#systemFun"), setting, data);
	},"json");
				
	/*ztree和tabs的整合*/
	function zTreeOnClick(event, treeId, treeNode) {
		var content = '<div style="width:100%;height:100%;overflow:hidden;">'
					+ '<iframe src="'
					+ treeNode.path
					+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';
		if(treeNode.path){
		    if($("#tabs").tabs("exists",treeNode.name)){
		    	$("#tabs").tabs("select",treeNode.name);
		    }else{
		    	$("#tabs").tabs("add",{
			    	title:treeNode.name,
			    	content:content,
			    	closable:true	
		    	});
			}
		}
	};	
});
	
	/*添加一个全局变量保存当前的右击选项卡的标题*/
	var currentRightTitle;
	/*显示菜单*/
	$(function(){
		/*给选项卡添加右击事件*/
		$("#tabs").tabs({
			onContextMenu:function(even, title, index){
				currentRightTitle = title;
				/*关闭浏览器默认的右击事件*/
				even.preventDefault();
				
				$("#menu").menu("show",{
					left:even.pageX,
					top:even.pageY
				});
			}
		});
		
		/*下拉菜单的点击事件*/
		$('#menu').menu({ 
			onClick:function(item){ 
				/*关闭当前选项卡*/
				if(item.name === "Close") {
					$("#tabs").tabs('close',currentRightTitle);
				/*关闭其它*/
				}else if(item.name === "CloseOther"){
					var tabs = $("#tabs").tabs('tabs');
					$(tabs).each(function(){
						var val = $(this).panel('options').title;
						if( val != currentRightTitle){
							$("#tabs").tabs('close', val);
						}
					});
				/*关闭所有*/
				}else if(item.name === "CloseAll"){
					var tabs = $("#tabs").tabs('tabs');
					$(tabs).each(function(){
						var val = $(this).panel('options').title;
						$("#tabs").tabs('close', val);
					});
				}
			} 
		});
	});