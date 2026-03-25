/**
 * @type {NodeListOf<HTMLElement>}
 */
const chooseItems = document.querySelectorAll(".hr-choose-item");
/**
 * @type {HTMLElement | null}
 */
const chooseIcon = document.querySelector(".hr-choose-icon");

/**
 * 要素を別要素内に移動
 * @param target {HTMLElement}
 * @param to {HTMLElement}
 */
const _moveElement = (target, to) => {
  to.appendChild(target);
};

/**
 * 選択セクションホバー時の処理
 */
chooseItems.forEach((item) => {
  item.addEventListener("mouseenter", () => {
    if (chooseIcon) {
      _moveElement(chooseIcon, item);
    }
  });
});
