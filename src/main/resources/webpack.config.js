import path from "node:path";
import { fileURLToPath } from "node:url";
import MiniCssExtractPlugin from "mini-css-extract-plugin";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const RESOURCE_DIR = "./static";

export default {
  mode: "none", // NOTE:環境ごとに処理を分けない設定
  entry: {
    bundler: `${RESOURCE_DIR}/js/bundler.js`,
    top: `${RESOURCE_DIR}/js/top.js`,
  },
  module: {
    rules: [
      {
        test: /\.(scss|sass|css)$/i,
        use: [
          // NOTE: scss→css の順に読まれるようにする必要がある(配列の下から順に読まれる)
          MiniCssExtractPlugin.loader, // 生成されたjsからcssを切り離す
          "css-loader", // `@import`や`url()`を変換
          "sass-loader", // sassをcssに変換
        ],
      },
      {
        // webpack5から file-loader ではなく Asset Modules を使用する
        // 参考) https://webpack.js.org/guides/asset-modules/#resource-assets
        test: /\.(png|svg|jpg|gif)$/,
        type: "asset/resource",
        generator: {
          filename: "[hash][ext][query]",
          publicPath: "/dist/",
        },
      },
    ],
  },
  plugins: [
    // NOTE: 2026/03/27現在、cssは全て1つのファイルに統合している
    new MiniCssExtractPlugin({
      filename: "style.css",
    }),
  ],
  output: {
    path: path.resolve(__dirname, RESOURCE_DIR, "dist"),
    filename: "[name].bundle.js",
  },
};
