(function( $ ) {
	$.extend(Tapestry.Initializer, {
		imageUpload: function(spec) {  
			  var imageCount = 0;        //预览框中的图片数量，初始为0

			  var options = {
			        createOptions:{
			          id:"flashID",
			          url:spec.swfUrl,   
			          width:"520",        
			          height:"190",        
			          errorMessage:"Flash插件初始化错误，请更新您的flashplayer版本！",     
			          vars:{
			              //url:spec.action,        
			        	  url:'/index:upload',    
			              uploadDataFieldName: "picdata",                
			              picDescFieldName: "pictitle",            
			              maxSize:2,            
			              maxNum:5,
			              width:520,            
			              height:190,            
			              gridWidth:150,            
			              gridHeight:150,            
			              picWidth:100,            
			              picHeight:100,            
			              compressSize:1,            
			              compressLength:1280,     
			              ext:'{"X-File-Name":"X-File-Name"}', 
			              fileType:'{"description":"图片", "extension":"*.gif;*.jpeg;*.png;*.jpg"}'      //上传文件格式限制
			          },        
			          container: spec.elementId    
			      },    
			      selectFileCallback: function(){alert('select')},      
			      deleteFileCallback: function(){alert('delete')},    
			      uploadCompleteCallback: function(){alert('complete')},    
			      uploadErrorCallback: function(){alert('error')},    
			      allCompleteCallback: function(){alert('all')}    
			  };
			  
			  var flash = new baidu.flash.imageUploader(options);			
			  $("#sbutton").click(function(){
				  alert("upload");
		           flash.upload();
		      })
		}
	});
}) ( jQuery );