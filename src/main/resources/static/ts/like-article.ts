const notLikeIcon = document.querySelector<HTMLElement>("#not-like-icon");
const likedIcon = document.querySelector<HTMLElement>("#liked-icon");

// 初期処理
window.addEventListener("DOMContentLoaded", () => {
  const articleId = _getArticleId();
  //セッション取得
  const likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);
  //likeしてればアイコン変更
  if (likeFlg === "1") {
    notLikeIcon?.classList.add("d-none");
    likedIcon?.classList.remove("d-none");
  }
});

document
  .querySelector<HTMLElement>("#like-btn")
  ?.addEventListener("click", () => {
    const articleId = _getArticleId();
    //セッション取得
    let likeFlg = sessionStorage.getItem(`haroot-likeFlg_${articleId}`);

    // 既存のいいね数
    const likeCount = document.querySelector<HTMLElement>("#like-count");
    let count = 0;
    count = Number(likeCount?.innerText ?? "0");
    let type = "";

    //likeしてたら取り消し
    if (likeFlg === "1") {
      likeFlg = "0";
      count--;
      type = "down";
      // アイコン変更
      notLikeIcon?.classList.remove("d-none");
      likedIcon?.classList.add("d-none");
    } else {
      likeFlg = "1";
      count++;
      type = "up";
      // アイコン変更
      notLikeIcon?.classList.add("d-none");
      likedIcon?.classList.remove("d-none");
    }
    // NOTE: セッションの不具合で0未満になるのを防ぐ
    count = Math.max(count, 0);

    //セッションにlikeを記録
    sessionStorage.setItem(`haroot-likeFlg_${articleId}`, likeFlg);

    //先に表示上で数値変更
    if (likeCount) {
      likeCount.innerText = count.toString();
    }

    //controllerに非同期通信
    const XHR = new XMLHttpRequest();
    //記事IDはurlの最後についてるのを取得
    const pathArr = location.pathname.split("/");
    const id = pathArr[pathArr.length - 1];
    XHR.open("GET", "/articles/updateCount/" + id + "/" + type);
    XHR.send();
  });

const _getArticleId = (): string => {
  const path = location.pathname;
  const articleId = path.replace(/^.*?([0-9]+)$/, "$1");
  return articleId;
};
