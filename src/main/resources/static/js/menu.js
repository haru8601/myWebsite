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

const AUDIO_FILES = ["アクセサリーゲット", "きのみゲット", "たいせつなどうぐゲット", "どうぐゲット", "バッジゲット", "ポケッチアプリゲット", "わざマシンゲット", "わざ忘れ", "一緒に行こう！", "回復", "進化おめでとう"];
const FILE_COUNT = AUDIO_FILES.length;
const soundBtn = document.getElementById("sound-play");
let music;
let clickCount = 0;
// SEが流れるがボタンがどっかいく(リスクとリターン)
function soundPlay() {
	clickCount++;
	const fileIndex = Math.round(Math.random() * FILE_COUNT);
	music = new Audio(`../audio/${AUDIO_FILES[fileIndex]}.wav`);
	music.play();
	const randomIntX = -1 * Math.round(Math.random() * 100);
	const randomIntY = Math.round(Math.random() * 100);
	if (soundBtn) {
		soundBtn.style.transform = `translate(${randomIntX * clickCount}%, ${randomIntY * clickCount}%)`;
	}
}

function soundStop() {
	clickCount = 0;
	if (music) {
		music.pause();
	}
	if (soundBtn) {
		soundBtn.style.transform = "none";
	}
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