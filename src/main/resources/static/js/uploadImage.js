/**
 * @type {HTMLInputElement}
 */
// @ts-ignore
const fileInput = document.getElementById("imageFile");

const uploadImage = () => {
  if (fileInput.files?.length) {
    const articleId = location.pathname.replace(/.*\/([0-9]+)$/, "$1");

    // controllerに非同期通信
    const XHR = new XMLHttpRequest();
    XHR.open("POST", `/articles/uploadImage/${articleId}`);
    const formdata = new FormData();
    formdata.append("imageFile", fileInput.files[0]);
    XHR.send(formdata);
    XHR.addEventListener("readystatechange", () => {
      if (XHR.status != 200) {
        console.log(XHR.status);
        console.log(XHR.response);
      }
    });
  }
};
