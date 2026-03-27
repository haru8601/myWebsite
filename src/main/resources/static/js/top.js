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
const CHOOSE_ITEM_MAX_INDEX = 999;

window.addEventListener("keydown", (e) => {
  if (e.key === "ArrowDown") {
    const chooseIndex = parseInt(
      chooseIcon.parentElement?.dataset.hrChooseIndex ??
        CHOOSE_ITEM_MAX_INDEX.toString(),
    );
    // アイコンの親要素が最後のindexでなければ
    if (chooseIndex < chooseItems.length - 1) {
      _moveElement(chooseIcon, chooseItems[chooseIndex + 1]);
    }
  } else if (e.key == "ArrowUp") {
    const chooseIndex = parseInt(
      chooseIcon.parentElement?.dataset.hrChooseIndex ??
        CHOOSE_ITEM_MIN_INDEX.toString(),
    );
    // アイコンの親要素が最初のindexでなければ
    if (chooseIndex > CHOOSE_ITEM_MIN_INDEX) {
      _moveElement(chooseIcon, chooseItems[chooseIndex - 1]);
    }
  } else {
    // TODO: ヒントを表示
  }
});
