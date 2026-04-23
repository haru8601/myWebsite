/**
 * 記事URLをツイート
 */
document.querySelector("#tweet-article-btn").addEventListener("click", () => {
  const title = encodeURIComponent(
    document.querySelector("#title").textContent,
  );
  const currentUrl = encodeURIComponent(location.href);
  const tweetUrl = `https://twitter.com/share?text=${title}&url=${currentUrl}`;
  window.open(tweetUrl, "_blank");
});
