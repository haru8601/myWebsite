const copyLinkBtn = document.querySelector<HTMLElement>("#copy-link-btn");

copyLinkBtn?.addEventListener("click", () => {
  if (!navigator.clipboard) {
    // コピー失敗
    console.warn("this browser does not support copying.");
    return;
  }

  // コピー成功
  navigator.clipboard.writeText(location.href.replace(/#.+/, "")).then(() => {
    const copyLinkIcon = copyLinkBtn.querySelector("i.fa-link");
    if (copyLinkIcon != null) {
      copyLinkIcon.classList.add("text-success");
      copyLinkIcon.classList.remove("text-white");
      setTimeout(() => {
        copyLinkIcon.classList.add("text-white");
        copyLinkIcon.classList.remove("text-success");
      }, 1000);
    }
  });
});
