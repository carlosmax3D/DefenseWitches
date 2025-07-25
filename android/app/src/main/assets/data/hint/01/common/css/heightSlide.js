$(function(){

	//����ݒ�
	$("#billboard ul").css("height",252*$("#billboard ul li").size()+"px");
	$("#billboard ul li:last").prependTo("#billboard ul");
	$("#billboard ul").css("margin-top","-252px");

	//�{�^���O��
	$("#prev").click(function(){

		$("#billboard ul").animate({ marginTop : parseInt($("#billboard ul").css("margin-top"))+252+"px"},"","swing" , function(){
			$("#billboard ul").css("margin-top","-252px")
			$("#billboard ul li:last").prependTo("#billboard ul");
			$("#next,#prev").show();
		});
	});


	//�{�^������
	$("#next").click(function(){
		$("#billboard ul").animate({ marginTop : parseInt($("#billboard ul").css("margin-top"))-252+"px"},"","swing" , function(){
			$("#billboard ul").css("margin-top","-252px");
			$("#billboard ul li:first").appendTo("#billboard ul");
			$("#next,#prev").show();
		});

	});

	/*
	var ua = navigator.userAgent;
	if(ua.indexOf('iPad') > -1){
		//iPad�̏���
	}
	*/

	//�T�C�Y�ύX
	var changeSize = function(){
		var zm =  screen.width / parseInt($("#wrap").css("width")) * 1.0;
		//var zm =  window.innerWidth / parseInt($("#wrap").css("width")) * 0.8;
		$("#wrap").css("zoom",zm);
	};


	changeSize();
	setTimeout(scrollTo, 100, 0, 1);


	window.onorientationchange = function(){
		changeSize();
	}


	/*timer

	var timerID = setInterval(function(){

		$("#next").click();

	},5000);

	//reset

	$("#prev img,#next img").click(function(){

		clearInterval(timerID);

	});
	*/

});
