const BEFORE_LIKE_SRC = "/images/icon/heart.svg";
const AFTER_LIKE_SRC = "/images/icon/heart-red.svg";

// 初期処理
window.addEventListener("DOMContentLoaded", () => {
  const articleId = _getArticleId();
  //セッション取得
  let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);
  //likeしてればアイコン変更
  // FIXME: svg埋め込み用に修正
  if (likeFlg == "1") {
    /** @type {HTMLImageElement} */
    let likeImg = document.querySelector("img#like-icon");
    likeImg.src = AFTER_LIKE_SRC;
  }
});

document.querySelector("#like-btn").addEventListener("click", () => {
  const articleId = _getArticleId();
  //セッション取得
  let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);

  // 既存のいいね数
  /** @type {HTMLElement} */
  let likeCount = document.querySelector("#like-count");
  let count = 0;
  count = Number(likeCount.innerText);
  let src = "";
  let type = "";

  //likeしてたら取り消し
  if (likeFlg == "1") {
    likeFlg = "0";
    src = BEFORE_LIKE_SRC;
    count--;
    type = "down";
  } else {
    likeFlg = "1";
    src = AFTER_LIKE_SRC;
    count++;
    type = "up";
  }
  // NOTE: セッションの不具合で0未満になるのを防ぐ
  count = Math.max(count, 0);

  //セッションにlikeを記録
  sessionStorage.setItem(`haroot-likeFlg_${articleId}`, likeFlg);
  //アイコン変更
  /** @type {HTMLImageElement} */
  let likeImg = document.querySelector("img#like-icon");
  likeImg.src = src;

  //先に表示上で数値変更
  likeCount.innerText = count.toString();

  //controllerに非同期通信
  const XHR = new XMLHttpRequest();
  //記事IDはurlの最後についてるのを取得
  const pathArr = location.pathname.split("/");
  const id = pathArr[pathArr.length - 1];
  XHR.open("GET", "/articles/updateCount/" + id + "/" + type);
  XHR.send();
});

const _getArticleId = () => {
  const path = location.pathname;
  const articleId = path.replace(/^.*?([0-9]+)$/, "$1");
  return articleId;
};
