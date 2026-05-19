const btnTweet = document.querySelector<HTMLElement>("#tweet-article-btn");
const titleEl = document.querySelector<HTMLElement>("#title");

/**
 * 記事URLをツイート
 */
btnTweet?.addEventListener("click", () => {
  const title = encodeURIComponent(titleEl?.textContent ?? "");
  const currentUrl = encodeURIComponent(location.href);
  const tweetUrl = `https://twitter.com/share?text=${title}&url=${currentUrl}`;
  window.open(tweetUrl, "_blank");
});
