function copy() {
	if (!navigator.clipboard) {
		//コピー失敗
		return;
	}

	//コピー成功
	navigator.clipboard.writeText(location.href).then(() => {
		const pTag = document.getElementById("copyText");
		if (pTag != null) {
			pTag.style.display = "block";
			setTimeout(() => {
				pTag.style.display = "none";
			}, 2000)
		}
	});
	return;
}