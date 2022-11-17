// 最初のスクロール時に読み込む
window.addEventListener("scroll", () => {
	const bodyTag = document.getElementsByTagName("body")[0];

	// adsense用
	const adsenseTag = document.createElement("script");
	adsenseTag.type = "text/javascript";
	adsenseTag.async = true;
	adsenseTag.src = "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-4331069619603691";
	bodyTag.appendChild(adsenseTag);

	// gtag用
	const gtagTag = document.createElement("script");
	gtagTag.type = "text/javascript";
	gtagTag.async = true;
	gtagTag.src = "https://www.googletagmanager.com/gtag/js?id=G-KG2Q0JRLTH";
	// 追加のタイミングで読み込まれる
	bodyTag.appendChild(gtagTag);
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'G-KG2Q0JRLTH');

}, { once: true });
