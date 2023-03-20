import * as THREE from "three";

/**
 * 描画先の canvas エレメント
 * @type {HTMLCanvasElement}
 */
// @ts-ignore
const canvas = document.querySelector("#canvas1");
// canvasサイズはstyleとは別
canvas.width = window.innerWidth * 0.95;
canvas.height = window.innerHeight * 0.95;

const RIGHT_ANGLE = Math.PI / 2;

// レンダラーを作成 (描画先を指定した)
const renderer = new THREE.WebGLRenderer({ canvas: canvas, alpha: true });
renderer.setSize(canvas.width, canvas.height);
renderer.setPixelRatio(window.devicePixelRatio);
renderer.shadowMap.enabled = true;

// カメラ作成
const camera = setupCamera(
  new THREE.Vector3(20, 50, 90),
  new THREE.Vector3(0, 0, 0)
);

// シーンを作成
const scene = new THREE.Scene();
scene.background = new THREE.Color("lightskyblue");

// グリッド追加
setupGrid();

const BOX_COUNT = 3;
let textureIndex = 0;
let imageIndex = 0;
const TEXTURE_DIRECTION = ["+x", "-x", "+y", "-y", "+z", "-z"];
// オブジェクト生成
const meshArr = setupObjects();

// 光源生成
setupLights();

let tickCount = 0;

const boxStopFlg = [false, false, false];
const stopTickCount = [0, 0, 0];
const finalDegreeArr = [0, 0, 0];

const MAX_SPEED = RIGHT_ANGLE / 8; // (degree / 1 tick)
const ENOUGH_MIN_SPEED = RIGHT_ANGLE / 45;
// 正面からのずれの角度は10 degreeまで
const ENOUGH_MIN_DIFF_DEGREE = 15 * (Math.PI / 180);

let bingoFlg = false;
let stopFlg = false;
// 最後BINGO表示の際の回転角
const BINGO_DIFF_DEGREE = RIGHT_ANGLE / 170;

// 止める対象boxのindex
// 最初は何も止めないので存在しないindex
let stopTargetIndex = 1000;
// 各boxの速度
let v = [MAX_SPEED, MAX_SPEED, MAX_SPEED];
// boxを止め始めるtickカウント
const REDUCE_SPEED_COUNT = 50;

const startTime = performance.now(); // milli sec
const TIME_LIMIT = 5 * 1000;
// 毎フレームの処理
tick();

/**
 * カメラを作成する
 * @param {THREE.Vector3} position カメラの位置
 * @param {THREE.Vector3} direction カメラの視線の場所
 * @returns {THREE.Camera}
 */
function setupCamera(position, direction) {
  // field of view(画角, 見える範囲の角度)
  const fov = 100;
  // カメラを作成
  const camera = new THREE.PerspectiveCamera(
    fov,
    canvas.width / canvas.height,
    0.1,
    10000
  );
  // カメラ位置
  camera.position.add(position);
  // カメラの視線
  camera.lookAt(direction);
  return camera;
}

function setupGrid() {
  // y=0におけるgrid
  const grid = new THREE.GridHelper(
    1000,
    10,
    new THREE.Color("black"),
    new THREE.Color("black")
  );
  scene.add(grid);
  // 3D軸(x:赤, y:緑, z:青)
  const axes = new THREE.AxesHelper(500);
  scene.add(axes);
}

/**
 * LambertMaterialを付けたMeshを作成して返却する
 * @param {number} size 箱のサイズ
 * @param {THREE.Vector3} position 箱の座標
 * @param {THREE.Vector3} rotation 箱の角度
 * @param {Array<THREE.MeshLambertMaterial>} textureMaterialArr 貼り付けるMaterial
 * @returns {THREE.Mesh}
 */
function createBoxMesh(size, position, rotation, textureMaterialArr) {
  // 箱を作成
  const boxGeo = new THREE.BoxGeometry(size, size, size);
  const boxMesh = new THREE.Mesh(boxGeo, textureMaterialArr);
  boxMesh.position.add(position);
  boxMesh.rotation.setFromVector3(rotation);
  boxMesh.castShadow = true;
  scene.add(boxMesh);
  return boxMesh;
}

/**
 * 画像を付与したLambertMaterialを作成して返却する
 * @param {number} index boxのindex
 * @returns {THREE.MeshLambertMaterial}
 */
function createLambertMaterial(index) {
  const loader = new THREE.TextureLoader();
  const direction = TEXTURE_DIRECTION[textureIndex];
  const IMAGE_PATH_ARR = [
    "people_white",
    "exercise_white",
    "wizard_white",
    "electricity_white",
  ];
  const BINGO_PATH_ARR = ["bi", "n", "go"];
  let src = "";
  // 側面以外に貼る
  if (!direction.includes("x")) {
    src = `../images/three/${IMAGE_PATH_ARR[imageIndex]}.png`;
    imageIndex = (imageIndex + 1) % IMAGE_PATH_ARR.length;
  } else {
    src = `../images/three/${BINGO_PATH_ARR[index]}.png`;
  }
  const texture = loader.load(src);
  // 回転が必要な画像
  if (TEXTURE_DIRECTION[textureIndex] == "-z") {
    texture.center = new THREE.Vector2(0.5, 0.5);
    texture.rotation = Math.PI;
  }
  const material = new THREE.MeshLambertMaterial({ map: texture });
  textureIndex = (textureIndex + 1) % 6;
  return material;
}

/**
 *
 * @param {number} index boxのindex
 * @returns
 */
function createTextureMaterialArr(index) {
  // 6つテクスチャをロードしたmaterial指定することで6面別のテクスチャにできる
  const textureMaterialArr = [];
  for (let i = 0; i < 6; i++) {
    textureMaterialArr.push(createLambertMaterial(index));
  }
  return textureMaterialArr;
}

function setupObjects() {
  // 床
  const planeGeo = new THREE.PlaneGeometry(
    canvas.width * 10,
    canvas.height * 10
  );
  const redMaterial = new THREE.MeshLambertMaterial({
    color: "dimgray",
    side: THREE.DoubleSide,
  });
  const ground = new THREE.Mesh(planeGeo, redMaterial);
  // デフォはz=0なのでy=0になるよう回転
  ground.rotation.x = RIGHT_ANGLE;
  ground.receiveShadow = true;
  scene.add(ground);

  const boxSize = 30;
  const defaultDist = 30;
  let meshArr = [];
  for (let i = 0; i < BOX_COUNT; i++) {
    const position = new THREE.Vector3(defaultDist * (i - 1), defaultDist, 0);
    // 0-360
    const initDegree = Math.PI * 2 * Math.random();
    const rotation = new THREE.Vector3(initDegree, 0, 0);
    // const rotation = new THREE.Vector3(0, 0, 0);
    const boxMesh = createBoxMesh(
      boxSize,
      position,
      rotation,
      createTextureMaterialArr(i)
    );
    meshArr.push(boxMesh);
  }
  // 作成したmeshを返却
  return meshArr;
}

function setupLights() {
  // ポイントライト
  const point = new THREE.SpotLight("white", 1, 0, Math.PI / 4.3, 0.05, 1);
  point.position.add(new THREE.Vector3(-10, 100, 60));
  point.castShadow = true;
  scene.add(point);

  // 環境光
  const ambient = new THREE.AmbientLight("lightyellow", 0.75);
  scene.add(ambient); // シーンに追加
}

function tick() {
  // 終了条件
  if (stopFlg || (!bingoFlg && performance.now() - startTime > TIME_LIMIT)) {
    finalize();
    return;
  } else if (bingoFlg) {
    // 止まってはないけどビンゴ
    for (let i = 0; i < BOX_COUNT; i++) {
      if (meshArr[i].rotation.y <= -RIGHT_ANGLE) {
        finalize();
        return;
      }
    }
  }

  // シーン更新
  updateScene(meshArr);

  // レンダリング
  renderer.render(scene, camera);

  // 次フレームの描画を予約
  requestAnimationFrame(tick);
}

/**
 * 各フレームでのシーン更新処理
 * @param {Array<THREE.Mesh>} meshArr 回転させるmeshの配列
 */
function updateScene(meshArr) {
  tickCount++;
  // 最後のboxが止まったら
  if (v[BOX_COUNT - 1] == 0) {
    bingoFlg = checkBingo();
    // bingoなら横回転
    if (bingoFlg) {
      rotateY();
    } else {
      stopFlg = true;
    }
    return;
  }

  // 各meshを回転
  for (let i = 0; i < BOX_COUNT; i++) {
    meshArr[i].rotation.x = (meshArr[i].rotation.x + v[i]) % (Math.PI * 2);
  }

  // 0番目のboxを止め始める判定
  if (tickCount < REDUCE_SPEED_COUNT) {
    // 最初は止める判定を行わない
    return;
  } else if (tickCount == REDUCE_SPEED_COUNT) {
    // 途中から0番目を止め始める
    stopTargetIndex = 0;
    stopTickCount[stopTargetIndex] = REDUCE_SPEED_COUNT;
  }

  // boxの速度変化
  if (
    v[stopTargetIndex] < ENOUGH_MIN_SPEED &&
    // 92 degree -> 2 degree
    // 183 degree -> 3 degree
    meshArr[stopTargetIndex].rotation.x % RIGHT_ANGLE < ENOUGH_MIN_DIFF_DEGREE
  ) {
    // 速度が遅く角度が十分小さい時、対象を止める
    stopTarget();
  } else {
    // それ以外はstop対象の速度を変更
    v[stopTargetIndex] = calcSpeed(tickCount - stopTickCount[stopTargetIndex]);
  }
}

/**
 * ビンゴか否か確認
 * @returns true: ビンゴ, false: ビンゴじゃない
 */
function checkBingo() {
  let resFlg = true;
  // 全て角度一致ならbingo
  finalDegreeArr.forEach((boxDegree) => {
    resFlg &&= finalDegreeArr[0] == boxDegree;
  });
  return resFlg;
}

// 終了処理
function finalize() {
  const launchDiv = document.getElementById("launch");
  if (launchDiv != null) {
    launchDiv.style.opacity = "0";
    launchDiv.style.pointerEvents = "none";
  }
}

function stopTarget() {
  // 角度を綺麗な値に強制変更(178 degree->180 degree)
  meshArr[stopTargetIndex].rotation.x =
    Math.round(meshArr[stopTargetIndex].rotation.x / RIGHT_ANGLE) * RIGHT_ANGLE;
  finalDegreeArr[stopTargetIndex] = Math.round(
    (meshArr[stopTargetIndex].rotation.x / Math.PI) * 180
  );
  // 止めたカウント記憶
  stopTickCount[stopTargetIndex + 1] = tickCount;
  boxStopFlg[stopTargetIndex] = true;

  // 速度0
  v[stopTargetIndex] = 0;
  // 止める対象を移動
  stopTargetIndex++;
}

function calcSpeed(t) {
  const a = -RIGHT_ANGLE / MAX_SPEED;
  return RIGHT_ANGLE / (t - a);
}

function rotateY() {
  for (let i = 0; i < 3; i++) {
    const degree = finalDegreeArr[i];
    meshArr[i].rotation.y -= BINGO_DIFF_DEGREE;
    if (degree == 90) {
      meshArr[i].rotation.x -= BINGO_DIFF_DEGREE;
    } else if (degree == 180) {
      meshArr[i].rotation.x -= BINGO_DIFF_DEGREE * 2;
    } else if (degree == 270) {
      meshArr[i].rotation.x += BINGO_DIFF_DEGREE;
    }
  }
}
