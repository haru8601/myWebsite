import path from "node:path";
import { fileURLToPath } from "node:url";
import MiniCssExtractPlugin from "mini-css-extract-plugin";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const RESOURCE_DIR = "./static";

export default {
  mode: "none", // NOTE:環境ごとに処理を分けない設定
  resolve: {
    extensions: [".ts", ".js"],
  },
  entry: {
    bundler: `${RESOURCE_DIR}/ts/bundler.ts`,
    top: `${RESOURCE_DIR}/ts/top.ts`,
    "check-answer": `${RESOURCE_DIR}/ts/check-answer.ts`,
    common: `${RESOURCE_DIR}/ts/common.ts`,
    "copy-link": `${RESOURCE_DIR}/ts/copy-link.ts`,
    "insert-toc": `${RESOURCE_DIR}/ts/insert-toc.ts`,
    "like-article": `${RESOURCE_DIR}/ts/like-article.ts`,
    "preview-markdown": `${RESOURCE_DIR}/ts/preview-markdown.ts`,
    "tweet-article": `${RESOURCE_DIR}/ts/tweet-article.ts`,
    "upload-image": `${RESOURCE_DIR}/ts/upload-image.ts`,
    "embed-svg": `${RESOURCE_DIR}/ts/embed-svg.ts`,
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: {
          loader: "ts-loader",
          options: {
            transpileOnly: true,
          },
        },
        exclude: /node_modules/,
      },
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
    new MiniCssExtractPlugin({
      filename: "[name].css",
    }),
  ],
  output: {
    path: path.resolve(__dirname, RESOURCE_DIR, "dist"),
    filename: "[name].bundle.js",
  },
};
