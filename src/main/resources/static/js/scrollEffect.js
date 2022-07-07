
let targets = document.getElementsByClassName("animation");
const targetLen = targets.length;
let showFlgArr = new Array(targetLen).fill(false);

//1つ目は常に表示
window.addEventListener("load", () => {
	let first = targets[0];
	if (first) {
		first.classList.add("showing");
		first.style.pointerEvents = "auto";
	}
})

window.addEventListener("scroll", () => {
	//現在の画面の一番上のY座標(一番上が0,下向きに正)
	const scrollY = window.pageYOffset;
	const windowHeight = window.innerHeight;
	//画面上部からの差が3/4まで来たら表示
	const showDiff = windowHeight / 4 * 3;
	//2つ目以降で表示非表示切り替え処理
	for (let i = 1; i < targetLen; i++) {
		let target = targets[i];
		//各トピックの絶対座標=offset+各トピックの相対座標(一番上)
		const topicYTop = scrollY + target.getBoundingClientRect().top;
		//hiddenなら近づいたら表示
		if (!showFlgArr[i]) {
			if (topicYTop - scrollY < showDiff) {
				target.classList.add("showing");
				target.style.pointerEvents = "auto";
				showFlgArr[i] = true;
			}
			//非表示
		} else {
			if (topicYTop - scrollY >= showDiff) {
				target.classList.remove("showing");
				target.style.pointerEvents = "none";
				showFlgArr[i] = false;
			}
		}
	}
})