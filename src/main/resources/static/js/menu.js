let menuFlg = false;
let menuBar = document.getElementById("menu-bar");

function menu() {
	if (menuBar == null) {
		return;
	}
	const windowWidth = window.innerWidth;
	let barLeft = "80%";
	// 670未満の画面サイズの場合はmenuBar全表示
	if (windowWidth < 670) {
		barLeft = "0%";
	}

	if (!menuFlg) {
		menuBar.style.left = barLeft;
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

function goDown() {
	const downImg = event.target;
	downImg.animate({
		transform: ["none", "translateY(100vh)", "translateY(-80px)", "none"],
		opacity: [1, 0, 0, 1],
	}, {
		duration: 800,
		easing: "ease-in",
	})
}

function jump() {
	const jumpImg = event.target;
	let changeConf = [
		"none",
		"rotate(360deg)",
	];
	let offsetConf = [0];
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