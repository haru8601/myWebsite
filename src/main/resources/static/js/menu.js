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

/**
 * @type {HTMLAudioElement}
 */
let music;
let clickCount = 0;

// SEが流れるがボタンがどっかいく関数(リスクとリターン)
async function soundPlay() {
	clickCount++;

	// ボタンを非活性化
	soundBtn.disabled = true;
	soundBtn.style.cursor = "not-allowed";
	if (soundFont) {
		soundFont.style.color = "gray";
	}

	// 再生
	if (!music) {
		selectSong();
	}
	await music.play();

	// ボタンの移動
	const randomIntX = -1 * Math.round(Math.random() * 100);
	const randomIntY = Math.round(Math.random() * 100);
	if (soundBtn) {
		soundBtn.style.transform = `translate(${randomIntX * clickCount}%, ${randomIntY * clickCount}%)`;
	}

	// 次の曲を選択しておく(読み込み遅延対策)
	selectSong();
}

// 再生停止(リセット)
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

let pushTime = 0;
let startTime = 0;
// 曲変更
function selectSong() {
	// 0 <= index < fileCount
	const fileIndex = Math.floor(Math.random() * FILE_COUNT);
	music = new Audio(`../audio/${AUDIO_FILES[fileIndex]}.wav`);

	// 再生成したmusicにリスナーを付与
	// 再生ボタン押した時間
	music.addEventListener("play", (event) => {
		pushTime = Date.now();
	});
	// 実際に再生が開始する時間
	music.addEventListener("playing", (event) => {
		startTime = Date.now();
		// 3秒以上遅延がある場合はもう再生しない
		if (startTime - pushTime > 3000) {
			music.pause();
		}
	});
	// 再生が終わった時間
	music.addEventListener("ended", (event) => {
		// ボタンを活性化
		soundBtn.disabled = false;
		soundBtn.style.cursor = "pointer";
		soundFont.style.color = "black";
	});
}

//ページ遷移時にメニューバーを戻す
window.addEventListener("beforeunload", (event) => {
	if (menuFlg) {
		menu();
	}
});
