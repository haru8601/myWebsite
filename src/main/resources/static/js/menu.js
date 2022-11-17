let menuFlg = false;
let barLeft = "80%";
let menuBar = document.getElementById("menu-bar");

// 遷移時にバーを初期化
window.onload = () => {
	if (!menuBar) {
		return;
	}
	menuBar.style.left = "100%";
	menuFlg = false;
	setBarLeft();
}

// リサイズ時はbarLeftの更新とbarの移動
window.onresize = () => {
	if (!menuBar) {
		return;
	}
	setBarLeft();
	if (menuFlg) {
		menuBar.style.left = barLeft;
	}
}

// クリック時はバーのON/OFF
const menu = () => {
	if (menuBar == null) {
		return;
	}

	if (!menuFlg) {
		menuBar.style.left = barLeft;
		menuFlg = true;
	} else {
		menuBar.style.left = "100%";
		menuFlg = false;
	}
}

const setBarLeft = () => {
	// 670未満の画面サイズの場合はmenuBar全表示
	if (window.innerWidth < 670) {
		barLeft = "0%";
	} else {
		barLeft = "80%";
	}
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

// 曲変更
const selectSong = () => {
	// 0 <= index < fileCount
	const fileIndex = Math.floor(Math.random() * FILE_COUNT);
	music.src = `../audio/${AUDIO_FILES[fileIndex]}.mp3`;
}

// 初期化
selectSong();
let clickCount = 0;

// SEが流れるがボタンがどっかいく関数(リスクとリターン)
const soundPlay = () => {
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
const soundStop = () => {
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

// ボタンの活性化
const ableSoundBtn = () => {
	soundBtn.disabled = false;
	soundBtn.style.cursor = "pointer";
	if (soundFont) {
		soundFont.style.color = "black";
	}
}
// ボタンの非活性化
const disableSoundBtn = () => {
	soundBtn.disabled = true;
	soundBtn.style.cursor = "not-allowed";
	if (soundFont) {
		soundFont.style.color = "gray";
	}
}
// ボタンの移動
const moveSoundBtn = () => {
	const randomIntX = -1 * Math.round(Math.random() * 100);
	const randomIntY = Math.round(Math.random() * 100);
	if (soundBtn) {
		soundBtn.style.transform = `translate(${randomIntX * clickCount}%, ${randomIntY * clickCount}%)`;
	}
}
