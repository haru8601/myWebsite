/**
 * not-nullな要素に対するラッパー関数
 * @param {HTMLElement | null} element
 * @returns {HTMLElement} not-nullな要素
 */
export const _notNullWrapper = (element) => {
  if (!element) {
    throw new Error("element is NOT found.");
  }
  return element;
};

/**
 * 要素を別要素内に移動
 * @param target {HTMLElement}
 * @param to {HTMLElement}
 */
export const _moveElement = (target, to) => {
  to.prepend(target);
};
