import hljs from "highlight.js";
import "highlight.js/styles/default.min.css";
import { marked } from "marked";
import { gfmHeadingId } from "marked-gfm-heading-id";
import DOMPurify from "dompurify";

//コードブロックをハイライト
hljs.highlightAll();

/**
 * @type {HTMLElement}
 */
const mdContentSection = document.querySelector("#md-content-section");

// NOTE: innerHTMLだと特殊文字がエスケープされてしまうのでtextContentで取得
const markdownString = mdContentSection?.textContent;

//テキストをマークダウン化して貼り付け
marked.use(gfmHeadingId({ prefix: "" }));
Promise.resolve(marked.parse(markdownString)).then((html) => {
  // DOMPurify でサニタイズ（XSS 対策）
  const sanitizedHtml = DOMPurify.sanitize(html);
  mdContentSection.innerHTML = sanitizedHtml;
});

document.addEventListener("DOMContentLoaded", () => {
  // 画像には全て独自クラスを付与
  const images = mdContentSection.querySelectorAll("img");
  images.forEach((img) => {
    img.classList.add("hr-img");
  });
  // リンクは全て外部リンクとし、アイコンを付与
  const links = mdContentSection.querySelectorAll("a");
  links.forEach((link) => {
    link.setAttribute("target", "_blank");
    link.classList.add("text-decoration-underline");
    const icon = document.createElement("i");
    icon.classList.add("fa-solid", "fa-arrow-up-right-from-square");
    link.prepend(icon);
  });
});
