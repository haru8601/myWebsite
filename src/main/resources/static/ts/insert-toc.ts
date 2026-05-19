// コード要素
const mdContentSection = document.querySelector<HTMLElement>(
  "#md-content-section",
);

// コード要素からhタグ(目次となるもの)を抽出
const hTagArr = mdContentSection?.innerHTML.match(/<h[0-9].*?<\/h/g);

const replacedTagArr: { size: number; text: string }[] = [];

// 抽出したhタグからタグの大きさ、テキストを抽出
hTagArr?.forEach((hTagText) => {
  const tagInfoObj = { size: 0, text: "" };
  tagInfoObj.size = parseInt(hTagText.replace(/<h([0-9])/, "$1"));
  tagInfoObj.text = hTagText.replace(/<h[0-9].*?>(.*?)<\/h/, "$1");
  replacedTagArr.push(tagInfoObj);
});

// 抽出したhタグ
const tocListSection = document.querySelector<HTMLElement>(
  "#article-toc-section",
);

// 抽出した各テキストを目次の表示場所に追加
replacedTagArr?.forEach((hTag) => {
  const tocAnchor = document.createElement("a");

  // 目次のリンク内に入れるhタグ
  const tocTextTag = document.createElement("p");
  let text = "";
  // h3以下はインデントをつける
  for (let i = 1; i < hTag.size - 1; i++) {
    text += "&nbsp;&nbsp;&nbsp;";
  }
  // h2以下は罫線をつける
  if (hTag.size > 1) {
    text += "┗&nbsp;";
  }
  text += hTag.text;
  tocTextTag.innerHTML = text;

  tocAnchor.appendChild(tocTextTag);
  // marked-gfm-heading-idではidが小文字なのでリンクもそれに合わせる
  // スペースは-になる
  // (),",\,;,.は除外されてる
  tocAnchor.href = `#${hTag.text
    .toLowerCase()
    .replace(/\(|\)|"|\\|;|\./g, "")
    .replace(/\s/g, "-")}`;
  tocListSection?.appendChild(tocAnchor);
});
