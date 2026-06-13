const menu = document.getElementById("header-menu-icon");

/**
 * メニューアイコンを押した時の処理
 */
menu?.addEventListener("click", () => {
  const headerHeight = document.querySelector("header")?.offsetHeight || 0;
  const menuSection = document.getElementById("header-menu-section");
  // メニューセクションの表示切り替え
  menuSection?.classList.toggle("d-none");
  if (menuSection) {
    menuSection.style.top = `${headerHeight}px`;
  }

  // メニューアイコンの切り替え
  const menuIcons = menu.querySelectorAll("i");
  menuIcons.forEach((icon) => {
    icon.classList.toggle("d-none");
  });
  // NOTE: ヘッダーが一瞬スクロールできてしまうのを防ぐ対応
  const html = document.querySelector("html");
  html?.classList.toggle("overflow-hidden");
});
