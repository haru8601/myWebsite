/**
 * not-nullな要素に対するラッパー関数
 */
export const _notNullWrapper = (element: Element | null): Element => {
  if (!element) {
    throw new Error("element is NOT found.");
  }
  return element;
};

/**
 * 要素を別要素内に移動
 */
export const _moveElement = (target: HTMLElement, to: HTMLElement): void => {
  to.prepend(target);
};
