window.onload = function () {
	//セッション取得
	let likeFlg = sessionStorage.getItem("likeFlg");
	//likeしてれば色変更
	if (likeFlg == "1") {
		let icon = document.getElementsByClassName("fa-thumbs-up")[0];
		icon.style.color = "rgb(220, 74, 71)";
	}
}

function countChange() {
	//セッション取得
	let likeFlg = sessionStorage.getItem("likeFlg");
	let likeCount = document.getElementById("likeCount");
	let count = 0;
	if (likeCount != null) {
		count = Number(likeCount.innerText);
	}
	let color = "";
	let type = "";

	//likeしてたら取り消し
	if (likeFlg == "1") {
		likeFlg = "0";
		color = "rgb(61, 60, 63)";
		count--;
		type = "down";
	} else {
		likeFlg = "1";
		color = "rgb(220, 74, 71)";
		count++;
		type = "up";
	}

	//セッションにlikeを記録
	sessionStorage.setItem("likeFlg", likeFlg);
	//アイコンの色変更
	let icon = document.getElementsByClassName("fa-thumbs-up")[0];
	icon.style.color = color;

	//先に表示上で数値変更
	if (likeCount != null) {
		likeCount.innerText = count.toString();
	}

	//controllerに非同期通信
	const XHR = new XMLHttpRequest();
	//記事IDはurlの最後についてるのを取得
	const pathArr = location.pathname.split("/");
	const id = pathArr[pathArr.length - 1];
	XHR.open("GET", "/articles/countChange/" + id + "/" + type);
	XHR.send();

	return;
}