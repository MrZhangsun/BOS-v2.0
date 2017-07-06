/*消息框的显示*/
$(function(){
	$.messager.show({  	
	  title:'欢迎访问!',  	
	  msg:'你好,今天是'+ new Date(),  	
	  timeout:5000,  	
	  showType:'slide'  
	}); 
	
	/*警告框*/
	$.messager.alert('警告','非法输入!','warning',function(){
		alert("close");
	});
	/*确认框*/
	$.messager.confirm("hello","确认关闭?",function(result){
		if (result) {
			alert("close");
		} else {
			alert("no");
		}
	});
	
	/*输入框*/
	$.messager.prompt("温馨提示","请输入用户名:",function(val){
		alert(val);
	});
	
	/*进度条*/
	$.messager.progress({
		title:"hess",
		msg:"正在下载",
		text:"finish",
		interval:1000
	});
});