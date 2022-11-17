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

//イベントを無効化
function disabledEvent(event) {
	event.preventDefault();
}

const AUDIO_FILES = ["アクセサリーゲット", "きのみゲット", "たいせつなどうぐゲット", "どうぐゲット", "バッジゲット", "ポケッチアプリゲット", "レベルアップ", "わざマシンゲット", "わざ忘れ", "一緒に行こう！", "回復", "進化おめでとう"];
const FILE_COUNT = AUDIO_FILES.length;

/**
 * @type {HTMLButtonElement}
 */
// @ts-ignore
const soundBtn = document.getElementById("sound-play");
/**
 * @type {HTMLElement}
 */
// @ts-ignore
const soundFont = document.getElementsByClassName("fa-play")[0];

let music = new Audio();
let canPlayTime = 0;

// リスナーを付与
// srcが更新され再生可能になる時間
music.addEventListener("canplaythrough", (event) => {
	canPlayTime = Date.now();
})
// 再生が終わった時間
music.addEventListener("ended", (event) => {
	// 曲変更、ボタン活性化
	selectSong();
	ableSoundBtn();
});

// 初期化
selectSong();
let clickCount = 0;

// SEが流れるがボタンがどっかいく関数(リスクとリターン)
function soundPlay() {
	clickCount++;

	// ボタンの非活性化
	disableSoundBtn();

	// 再生
	const pushTime = Date.now();
	if (pushTime >= canPlayTime) {
		// 再生可能なら再生
		music.play();
	} else {
		// 不可能ならボタンを戻す(活性化)
		ableSoundBtn();
	}

	// ボタンの移動
	moveSoundBtn();
}

// 再生停止(リセット)
function soundStop() {
	clickCount = 0;

	// 再生停止、曲変更、ボタン活性化
	music.pause();
	selectSong();
	ableSoundBtn();

	// 位置を戻す
	if (soundBtn) {
		soundBtn.style.transform = "none";
	}
}

// 曲変更
function selectSong() {
	// 0 <= index < fileCount
	const fileIndex = Math.floor(Math.random() * FILE_COUNT);
	music.src = `../audio/${AUDIO_FILES[fileIndex]}.mp3`;
}

// ボタンの活性化
function ableSoundBtn() {
	soundBtn.disabled = false;
	soundBtn.style.cursor = "pointer";
	if (soundFont) {
		soundFont.style.color = "black";
	}
}
// ボタンの非活性化
function disableSoundBtn() {
	soundBtn.disabled = true;
	soundBtn.style.cursor = "not-allowed";
	if (soundFont) {
		soundFont.style.color = "gray";
	}
}
// ボタンの移動
function moveSoundBtn() {
	const randomIntX = -1 * Math.round(Math.random() * 100);
	const randomIntY = Math.round(Math.random() * 100);
	if (soundBtn) {
		soundBtn.style.transform = `translate(${randomIntX * clickCount}%, ${randomIntY * clickCount}%)`;
	}
}

//ページ遷移時にメニューバーを戻す
window.addEventListener("beforeunload", (event) => {
	if (menuFlg) {
		menu();
	}
});
