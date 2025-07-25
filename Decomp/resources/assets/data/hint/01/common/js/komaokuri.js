var Komaokuri = (function(){
	var prev = null;
	var next = null;
	var cont = null;
	var cnt  = null;
	var pnlNum = null;
	var pnlH = 252;


	return {
		init: function(){
			cnt = 0;
			pnlNum = Math.floor(story.clientHeight / 252);
			prev  = document.getElementById("prev");
			next  = document.getElementById("next");
			story = document.getElementById("story");

			prev.style.visibility = "hidden";
			
			prev.addEventListener("touchend",Komaokuri.onPrevTouch,false);
			next.addEventListener("touchend",Komaokuri.onNextTouch,false);
		},

		onPrevTouch: function(){
			if(0 < cnt){
				cnt--;
				story.style.marginTop = "-" + (cnt * pnlH) + "px";
				next.style.visibility = "visible";
			}
			if(cnt == 0){
				prev.style.visibility = "hidden";
			}
		},

		onNextTouch: function(){
			if(cnt < pnlNum - 1){
				story.style.marginTop = "-" + ((cnt + 1) * pnlH) + "px";
				prev.style.visibility = "visible";
				cnt++;
			}
			if(cnt == pnlNum - 1){
				next.style.visibility = "hidden";
			}
		}
	}
})();


window.onload = function(){
	Komaokuri.init();
}

