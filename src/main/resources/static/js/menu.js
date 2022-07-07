let menuFlg = false;
let menuBar = document.getElementById("menu-bar");

function menu() {
	if (menuBar == null) {
		return;
	}
	if (!menuFlg) {
		menuBar.style.left = "0";
		document.addEventListener("touchmove", disabledEvent, { passive: false });
		document.addEventListener("mousewheel", disabledEvent, { passive: false });
		menuFlg = true;
	} else {
		menuBar.style.left = "100%";
		document.removeEventListener("touchmove", disabledEvent);
		document.removeEventListener("mousewheel", disabledEvent);
		menuFlg = false;
	}
}

function jump() {
	const jumpImg = event.target;
	let changeConf = [
		"none",
		"rotate(360deg)",
	];
	let offsetConf = [0];
	if (jumpImg.id == "bingo") {
		changeConf = [
			"none",
			"translateY(-20px)",
			"none"
		];
		offsetConf = [0, 0.3];
	}
	jumpImg.animate({
		transform: changeConf,
		offset: offsetConf,
	}, {
		duration: 300
	});
}

//イベントを無効化
function disabledEvent(event) {
	event.preventDefault();
}

//ページ遷移時にメニューバーを戻す
window.addEventListener("beforeunload", (event) => {
	if (menuFlg) {
		menu();
	}
})