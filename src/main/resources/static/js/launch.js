window.onload = async function () {
	let slotImgs = document.getElementsByClassName("slotImg");
	const imgNum = 3;
	for (let i = 0; i < imgNum; i++) {
		await new Promise((resolve) => {
			//500msおきにanimation実行
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
					duration: 1000
				});
				//Promiseに結果(空文字)を渡す
				resolve("");
			}, 500);
		});
	};

	let launchDiv = document.getElementById("launch");
	let bingoP = document.getElementsByClassName("bingo-text")[0];
	//cssの画像が落ちるtransition 0.5sに合わせて表示
	await new Promise((resolve) => {
		setTimeout(() => {
			if (bingoP) {
				bingoP.style.display = "block";
			}
			resolve("");
		}, 500);
	})
	//launch終了
	setTimeout(() => {
		if (launchDiv != null) {
			launchDiv.style.opacity = "0";
			launchDiv.style.pointerEvents = "none";
		}
	}, 1000);
}