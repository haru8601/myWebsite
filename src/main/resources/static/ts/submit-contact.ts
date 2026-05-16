document.getElementById("contactForm")?.addEventListener("submit", (event) => {
  event.preventDefault();
  grecaptcha.ready(() => {
    grecaptcha
      .execute("reCAPTCHA_site_key", { action: "submit" })
      .then((token) => {
        const tokenInput =
          document.querySelector<HTMLInputElement>("#recaptchaToken");
        if (tokenInput) {
          tokenInput.value = token;
        }
        const form = document.querySelector<HTMLFormElement>("#contactForm");
        if (form) {
          form.submit();
        }
      });
  });
  return false;
});
