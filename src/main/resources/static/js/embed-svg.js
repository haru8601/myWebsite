/**
 * SVGをインライン表示で埋め込む
 */
const embedSvg = async () => {
  /**
   * @type {NodeListOf<HTMLElement>}
   */
  const svgContainers = document.querySelectorAll(".embedded-svg-container");

  for (let index = 0; index < svgContainers.length; index++) {
    const container = svgContainers[index];
    const svgPath = container.dataset.svgPath;
    const width = container.dataset.svgWidth;

    if (!svgPath) {
      continue;
    }

    try {
      const response = await fetch(svgPath);
      let svgText = await response.text();

      // NOTE: 他のsvgとクラス名が被ってしまうので、
      // prefixを追加している
      const classPrefix = `hr-${index}`;
      // 付与されたクラス名の修正
      svgText = svgText.replace(
        /class="cls-(\d+)"/g,
        `class="${classPrefix}-cls-$1"`,
      );
      // 定義の修正
      svgText = svgText.replace(/\.cls-(\d+)/g, `.${classPrefix}-cls-$1`);

      container.innerHTML = svgText;
      const svg = container.querySelector("svg");
      if (width) {
        svg.style.width = width;
      }
    } catch (error) {
      console.error(`Failed to load SVG from ${svgPath}:`);
      console.error(error);
    }
  }
};

document.addEventListener("DOMContentLoaded", () => {
  embedSvg();
});
