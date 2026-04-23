// 初期処理
window.addEventListener("DOMContentLoaded", () => {
  const articleId = _getArticleId();
  //セッション取得
  let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);
  //likeしてればアイコン変更
  if (likeFlg == "1") {
    /** @type {HTMLImageElement} */
    let icon = document.querySelector("#like-icon");
    // FIXME: 正しい画像パスに変更
    icon.src = "/images/likedImage.png";
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
    // FIXME: 正しい画像パスに変更
    src = "/images/noImage.png";
    count--;
    type = "down";
  } else {
    likeFlg = "1";
    // FIXME: 正しい画像パスに変更
    src = "/images/likedImage.png";
    count++;
    type = "up";
  }
  // NOTE: セッションの不具合で0未満になるのを防ぐ
  count = Math.max(count, 0);

  //セッションにlikeを記録
  sessionStorage.setItem(`haroot-likeFlg_${articleId}`, likeFlg);
  //アイコン変更
  /** @type {HTMLImageElement} */
  let icon = document.querySelector("#like-icon");
  icon.src = src;

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
