import { _notNullWrapper, _moveElement } from "./common.js";

/**
 * @type {NodeListOf<HTMLElement>}
 */
const chooseItems = document.querySelectorAll(".hr-choose-item");

/**
 * @type {HTMLElement}
 */
const chooseIcon = _notNullWrapper(document.querySelector(".hr-choose-icon"));

/**
 * 選択セクションホバー時の処理
 */
chooseItems.forEach((item) => {
  item.addEventListener("mouseenter", () => {
    _moveElement(chooseIcon, item);
  });
});

const CHOOSE_ITEM_MIN_INDEX = 0;

window.addEventListener("keydown", (e) => {
  console.log(e.key);
  if (e.key === "ArrowDown") {
    const choosenIndex = _getChoosenIndex();
    // アイコンの親要素が最後のindexでなければ
    if (choosenIndex < chooseItems.length - 1) {
      _moveElement(chooseIcon, chooseItems[choosenIndex + 1]);
    }
  } else if (e.key == "ArrowUp") {
    const choosenIndex = _getChoosenIndex();
    // アイコンの親要素が最初のindexでなければ
    if (choosenIndex > CHOOSE_ITEM_MIN_INDEX) {
      _moveElement(chooseIcon, chooseItems[choosenIndex - 1]);
    }
  } else if (e.key === "Enter") {
    const choosenIndex = _getChoosenIndex();
    const choosenItem = chooseItems[choosenIndex];
    const choosenLink = choosenItem.closest("a");
    console.log(choosenIndex, choosenItem, choosenLink);
    if (choosenLink) {
      choosenLink.click();
    }
  } else {
    // TODO: ヒントを表示
  }
});

/**
 * 現在選択中のトピックのindexを取得
 */
const _getChoosenIndex = () => {
  return parseInt(
    chooseIcon.parentElement?.dataset.hrChooseIndex ??
      CHOOSE_ITEM_MIN_INDEX.toString(),
  );
};
