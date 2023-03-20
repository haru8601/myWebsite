// 初期処理
window.addEventListener("DOMContentLoaded", () => {
  const articleId = getArticleId();
  //セッション取得
  let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);
  //likeしてれば色変更
  if (likeFlg == "1") {
    /** @type {HTMLElement} */
    // @ts-ignore
    let icon = document.getElementsByClassName("fa-thumbs-up")[0];
    icon.style.color = "rgb(220, 74, 71)";
  }
});

const updateCount = () => {
  const articleId = getArticleId();
  //セッション取得
  let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);
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
  sessionStorage.setItem(`haroot-likeFlg_${articleId}`, likeFlg);
  //アイコンの色変更
  /** @type {HTMLElement} */
  // @ts-ignore
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
  XHR.open("GET", "/articles/updateCount/" + id + "/" + type);
  XHR.send();

  return;
};

const getArticleId = () => {
  const path = location.pathname;
  const articleId = path.replace(/^.*?([0-9]+)$/, "$1");
  return articleId;
};
