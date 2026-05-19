const menu = document.getElementById("header-menu-icon");

/**
 * メニューアイコンを押した時の処理
 */
menu?.addEventListener("click", () => {
  const menuSection = document.getElementById("header-menu-section");
  // メニューセクションの表示切り替え
  menuSection?.classList.toggle("d-none");

  // メニューアイコンの切り替え
  const menuIcons = menu.querySelectorAll("i");
  menuIcons.forEach((icon) => {
    icon.classList.toggle("d-none");
  });

  // メインを非表示にすることでスクロールを禁止する
  const main = document.querySelector("main");
  main?.classList.toggle("d-none");
});
