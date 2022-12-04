// コード要素
const contentsElement = document.getElementById("content");

// コード要素からhタグ(目次となるもの)を抽出
const hTagArr = contentsElement?.innerHTML.match(/<h[0-9].*?<\/h/g);

/**
 * @type {{size:number, text:string}[]}
 */
const replacedTagArr = [];

// 抽出したhタグからタグの大きさ、テキストを抽出
hTagArr?.forEach((hTagText) => {
	const tagInfoObj = { size: 0, text: "" };
	tagInfoObj.size = parseInt(hTagText.replace(/<h([0-9])/, "$1"));
	tagInfoObj.text = hTagText.replace(/<h[0-9].*?>(.*?)<\/h/, "$1");
	replacedTagArr.push(tagInfoObj);
});

// 目次の表示場所
const parentElement = document.getElementById("contents-sticky");

// 抽出した各テキストを目次の表示場所に追加
replacedTagArr?.forEach((hTag) => {
	const linkChild = document.createElement("a");

	// 目次のリンク内に入れるhタグ
	const textChild = ((hTag.size == 1) && document.createElement("h3")) || document.createElement("h4");
	let text = "";
	for (let i = 1; i < hTag.size; i++) {
		// h1でなければスペースを入れる
		text += "&nbsp;";
	}
	text += hTag.text;
	textChild.innerHTML = text;

	linkChild.appendChild(textChild);
	// idではなぜか小文字なのでリンクもそれに合わせる
	// スペースは-になるっぽい
	// ()や"や\は除外されてる
	linkChild.href = `#${hTag.text.toLowerCase().replaceAll(/\(|\)|"|\\/g, "").replaceAll(/\s/g, "-")}`;
	parentElement?.appendChild(linkChild);
})