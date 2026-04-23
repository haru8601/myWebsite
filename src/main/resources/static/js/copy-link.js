/**
 * 記事URLをコピー
 */
document.querySelector("#copy-link-btn").addEventListener("click", () => {
  if (!navigator.clipboard) {
    //コピー失敗
    console.warn("this browser does not support copying.");
    return;
  }

  //コピー成功
  navigator.clipboard.writeText(location.href.replace(/#.+/, "")).then(() => {
    const pTag = document.getElementById("copied-text");
    if (pTag != null) {
      pTag.classList.remove("d-none");
      setTimeout(() => {
        pTag.classList.add("d-none");
      }, 2000);
    }
  });
  return;
});
