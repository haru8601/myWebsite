const dataContainer = document.getElementById("data-container");
const quizWords: string[] = dataContainer?.dataset.words?.split(",") ?? [];

console.log("quizWords:", quizWords);
const hunterQuestion = document.getElementById("hunter-q");
const hunterInput = document.getElementById(
  "hunter-input",
) as HTMLInputElement | null;
const answerBtn = document.getElementById("show-answer-btn");
const nextQuestionBtn = document.getElementById("next-question-btn");
const answerArea = document.getElementById("hunter-a");

// キーボード操作時のリスナー
hunterInput?.addEventListener("keypress", (e) => {
  // IMEの入力中のEnterは弾きたい
  /* keyCodeは非推奨だが代替案があまりない(isComposing等は挙動が微妙)のでこのまま使用 */
  if (e.keyCode == 13) {
    _checkAnswer();
  }
});

// 回答ボタン押下時のリスナー
document
  .querySelector<HTMLElement>("#judge-btn")
  ?.addEventListener("click", () => {
    _checkAnswer();
  });

// 正解表示ボタン押下時のリスナー
answerBtn?.addEventListener("click", () => {
  if (answerArea) {
    answerArea.innerText = hunterQuestion?.innerText ?? "";
  }
  answerBtn?.classList.add("d-none");
  nextQuestionBtn?.classList.remove("d-none");
});

// 次の問題ボタン押下時のリスナー
nextQuestionBtn?.addEventListener("click", () => {
  _switchQuestion();
  _resetAnswer();
});

/**
 * 正解エリアの非表示とボタン文言のリセット
 */
const _resetAnswer = (): void => {
  if (answerArea) {
    answerArea.innerText = "";
  }
  answerBtn?.classList.remove("d-none");
  nextQuestionBtn?.classList.add("d-none");
};

/**
 * 単語をランダムに抽出
 */
const _selectWord = (words: string[]): string => {
  const i = Math.random() * words.length;
  return words[Math.floor(i)];
};

/**
 * 問題の単語を変更する
 */
const _switchQuestion = (): void => {
  if (hunterQuestion) {
    // 問題の切り替え
    hunterQuestion.innerText = _selectWord(quizWords);
  }
  // 正解を非表示にする
  _resetAnswer();
};

// 初期値設定
_switchQuestion();

/**
 * ひらがなをカタカナに変換
 */
const _toKatakana = (str: string): string => {
  return str.replace(/[ぁ-ゖ]/g, (match) => {
    return String.fromCharCode(match.charCodeAt(0) + 0x60);
  });
};

/**
 * 正誤判定をし、ox記号を表示
 */
const _checkAnswer = (): void => {
  const ok = document.getElementById("quizOk");
  const ng = document.getElementById("quizNg");
  const inputValue = hunterInput?.value ?? "";
  const answer = hunterQuestion?.innerText ?? "";

  // ひらがなの場合はカタカナにして比較
  if (_toKatakana(inputValue) === _toKatakana(answer)) {
    ok?.classList.remove("d-none");
    setTimeout(function () {
      if (hunterInput) {
        hunterInput.value = "";
      }
      ok?.classList.add("d-none");
      _switchQuestion();
    }, 1000);
  } else {
    ng?.classList.remove("d-none");
    setTimeout(function () {
      if (hunterInput) {
        hunterInput.value = "";
      }
      ng?.classList.add("d-none");
    }, 1000);
  }
};
