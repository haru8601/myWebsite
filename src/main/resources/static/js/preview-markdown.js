import hljs from "highlight.js";
import { marked } from "marked";
import { gfmHeadingId } from "marked-gfm-heading-id";

/**
 * @type {HTMLElement}
 */
const mdContentSection = document.querySelector("#md-content-section");

//コードブロックをハイライト
hljs.highlightAll();

let text = mdContentSection?.innerHTML?.replace(/th:inline="(.*?)"/g, "") || "";

//部分的なコードはmarkedが対応してないから自分でspanにして色付け
text = text.replace(/`(?!`)(.*?)`/gm, "<span>$1</span>");

//テキストをマークダウン化して貼り付け
marked.use(gfmHeadingId({ prefix: "" }));

Promise.resolve(marked.parse(text)).then((html) => {
  mdContentSection.innerHTML = html
    // 画像は独自のクラスを当てる
    .replace(/<img /g, '<img class="hr-img"')
    //リンクは別タブで開くようにする
    .replace(
      /<a .*?href="(.*?)".*?>(.*?)<\/a>/g,
      '<a href="$1" target="_blank" class="text-decoration-underline"><i class="fa-solid fa-arrow-up-right-from-square"></i>$2</a>',
    );
});
