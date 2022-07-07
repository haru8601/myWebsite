//IFrame Player APIの読み込み
let newTag = document.createElement('script');
newTag.src = "https://www.youtube.com/iframe_api";
//youtube.jsの後ろに追加
document.body.appendChild(newTag);

//youtubeの読み込み
function onYouTubeIframeAPIReady() {
    new YT.Player(
        'yt-iframe',
        {
            width: 500,
            height: 300,
            videoId: 'R6V18nhDUPk'
        }
    );
}