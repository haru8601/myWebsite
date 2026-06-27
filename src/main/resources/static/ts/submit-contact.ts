const form = document.querySelector<HTMLFormElement>("#contactForm");

const recaptchaSiteKey = form?.dataset.recaptchaSiteKey;

form?.addEventListener("submit", (event) => {
  event.preventDefault();
  grecaptcha.ready(() => {
    if (!recaptchaSiteKey) {
      console.error("reCAPTCHA site key is not set.");
      return;
    }
    grecaptcha
      .execute(recaptchaSiteKey, { action: "homepage" })
      .then((token) => {
        const tokenInput =
          document.querySelector<HTMLInputElement>("#recaptchaToken");
        if (tokenInput) {
          tokenInput.value = token;
        } else {
          console.error("reCAPTCHA token input field is not found.");
        }
        const form = document.querySelector<HTMLFormElement>("#contactForm");
        if (form) {
          form.submit();
        } else {
          console.error("form is not found.");
        }
      });
  });
});
