import hljs from "highlight.js";
import "highlight.js/styles/default.min.css";
import { marked } from "marked";
import { gfmHeadingId } from "marked-gfm-heading-id";
import DOMPurify from "dompurify";

const mdContentSection = document.querySelector<HTMLElement>(
  "#md-content-section",
);

if (mdContentSection) {
  // 一時的なテキストエリアを生成
  const temp = document.createElement("textarea");
  // textContentだと記事内のXMLタグなどが消えてしまうためinnerHTMLから取得する
  // 一度テキストエリアに入れているのは、<br>などがエスケープされた文字列になるのを防ぐため(復号化している)
  temp.innerHTML = mdContentSection.innerHTML;
  const markdownString = temp.value;

  //テキストをマークダウン化して貼り付け
  marked.use(gfmHeadingId({ prefix: "" }));
  Promise.resolve(marked.parse(markdownString)).then((html) => {
    // DOMPurify でサニタイズ（XSS 対策）
    const sanitizedHtml = DOMPurify.sanitize(html);
    mdContentSection.innerHTML = sanitizedHtml;

    // コードブロックをハイライト
    // NOTE: インラインコードは対象外
    mdContentSection
      .querySelectorAll<HTMLElement>("pre > code")
      .forEach((codeBlock) => {
        hljs.highlightElement(codeBlock);
      });
  });
}

document.addEventListener("DOMContentLoaded", () => {
  if (mdContentSection) {
    // 画像には全て独自クラスを付与
    const images = mdContentSection.querySelectorAll<HTMLImageElement>("img");
    images.forEach((img) => {
      img.classList.add("hr-img", "border", "border-2", "border-secondary");
    });

    // リンクは全て外部リンクとし、アイコンを付与
    const links = mdContentSection.querySelectorAll<HTMLAnchorElement>("a");
    links.forEach((link) => {
      link.setAttribute("target", "_blank");
      link.classList.add("text-decoration-underline");
      const icon = document.createElement("i");
      icon.classList.add("fa-solid", "fa-arrow-up-right-from-square");
      link.prepend(icon);
    });

    // 見出しは全て太字に変更
    const headings = mdContentSection.querySelectorAll<HTMLElement>(
      "h1, h2, h3, h4, h5, h6",
    );
    headings.forEach((heading) => {
      heading.classList.add("fw-bold");
    });
  }
});
