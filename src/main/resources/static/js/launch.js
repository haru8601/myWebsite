window.onload = async function () {
	let slotImgs = document.getElementsByClassName("slotImg");
	// 画像が落ちてくる時間間隔(ms)
	const fallDuration = 1000;
	for (let i = 0; i < slotImgs.length; i++) {
		await new Promise((resolve) => {
			//一定間隔でanimation実行
			setTimeout(() => {
				slotImgs[i].style.transform = "none";
				slotImgs[i].animate({
					transform: [
						"translateY(-100vh)",
						"none",
						"translateY(-3%) scale(0.96,1.04)",
						"translateY(-20%) scale(1)",
						"translateY(-13%)",
						"none"
					],
					offset: [0, 0.5, 0.55, 0.7, 0.75],
				}, {
					duration: fallDuration
				});
				//Promiseに結果(空文字)を渡す
				resolve("");
			}, fallDuration / 2);
		});
	};

	let launchDiv = document.getElementById("launch");
	let bingoP = document.getElementsByClassName("bingo-text")[0];
	// 最後のanimateが始まった直後にここに来てしまうので
	// animateが終わるまで待ってから実行
	await new Promise((resolve) => {
		setTimeout(() => {
			if (bingoP) {
				bingoP.style.display = "block";
			}
			resolve("");
		}, fallDuration / 2);
	})

	// 少し時間を置いてlaunch終了
	setTimeout(() => {
		if (launchDiv != null) {
			launchDiv.style.opacity = "0";
			launchDiv.style.pointerEvents = "none";
		}
	}, fallDuration);
};

