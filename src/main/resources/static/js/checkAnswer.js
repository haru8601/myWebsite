/** @type {string[]} */
// @ts-ignore
const quizWords = words;
const hunterQuestion = document.getElementById("hunter-q");
const hunterInput = /** @type {HTMLInputElement | null} */ (document.getElementById("hunter-input"));
const answerBtn = document.getElementById('show-answer-btn');
const answerArea = document.getElementById("hunter-a");

/**
 * 正解エリアの非表示とボタン文言のリセット
 */
const resetAnswer = () => {
  answerArea && (answerArea.innerText = '');
  answerBtn && (answerBtn.innerText = '正解を見る');
}

/**
 * @param {string[]} words
 * @returns {string}
 *
 * 単語をランダムに抽出
 */
const selectWord = (words) => {
  const i = Math.random() * words.length;
  return words[Math.floor(i)];
}

/**
 * 問題の単語を変更する
 */
const switchQuestion = () => {
  // 問題の切り替え
  hunterQuestion && (hunterQuestion.innerText = selectWord(quizWords));
  // 正解を非表示にする
  resetAnswer();
};

// 初期値設定
switchQuestion();

hunterInput?.addEventListener("keypress", (e) => {
  // IMEの入力中のEnterは弾きたい
  /* keyCodeは非推奨だが代替案があまりない(isComposing等は挙動が微妙)のでこのまま使用 */
  if (e.keyCode == 13) {
    checkAnswer();
  }
});

/**
 * ひらがなをカタカナに変換
 * @param {string} str 変換する文字列
 * @returns {string} カタカナに変換された文字列
 */
const toKatakana = (str) => {
  return str.replace(/[\u3041-\u3096]/g, (match) => {
    return String.fromCharCode(match.charCodeAt(0) + 0x60);
  });
};

const checkAnswer = () => {
  const ok = document.getElementById("ok");
  const ng = document.getElementById("ng");
  const inputValue = hunterInput?.value || '';
  const answer = hunterQuestion?.innerText || '';

  // ひらがなの場合はカタカナにして比較
  if (toKatakana(inputValue) === toKatakana(answer)) {
    ok?.classList.remove("d-none");
    setTimeout(function () {
      hunterInput && (hunterInput.value = '');
      ok?.classList.add("d-none");
      switchQuestion();
    }, 1000);
  } else {
    ng?.classList.remove("d-none");
    setTimeout(function () {
      hunterInput && (hunterInput.value = '');
      ng?.classList.add("d-none");
    }, 1000);
  }
};

const showAnswerOrNext = () => {
  if (answerArea?.innerText) {
    // 正解が表示されている場合は次の問題へ
    switchQuestion();
    resetAnswer();
  } else {
    // 正解が表示されていない場合は正解を表示
    answerArea && (answerArea.innerText = hunterQuestion && hunterQuestion.innerText || '');
    answerBtn && (answerBtn.innerText = '次の問題へ');
  }
};
