
/**
 * 描画先の canvas エレメント
 * @type {HTMLCanvasElement}
 */
// @ts-ignore
const canvas = document.querySelector('#canvas1');
// canvasサイズはstyleとは別
canvas.width = window.innerWidth * 0.95;
canvas.height = window.innerHeight * 0.95;

// レンダラーを作成 (描画先を指定した)
const renderer = new THREE.WebGLRenderer({ canvas: canvas, alpha: true });
renderer.setSize(canvas.width, canvas.height);
renderer.setPixelRatio(window.devicePixelRatio);
renderer.shadowMap.enabled = true;

const camera = setupCamera(
	new THREE.Vector3(50, 50, 100),
	new THREE.Vector3(0, 0, 0)
);

// シーンを作成
const scene = new THREE.Scene();
scene.background = new THREE.Color('lightskyblue');

setupGrid();
const BOX_COUNT = 3;
const RIGHT_ANGLE = Math.PI / 2;
// オブジェクト生成
const meshArr = setupObjects();
// 光源生成
setupLights();

const start = Date.now();
// 5秒間
const stopTime = 5000;
const stopDiff = 1000;

let count = 0;
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
		fov, canvas.width / canvas.height, 0.1, 10000);
	// カメラ位置
	camera.position.add(position);
	// カメラの視線
	camera.lookAt(direction);
	return camera;
}

function setupGrid() {
	// y=0におけるgrid
	const grid = new THREE.GridHelper(1000, 10, new THREE.Color('black'), new THREE.Color('black'));
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
 * @param {boolean} rotate
 * @returns {THREE.MeshLambertMaterial}
 */
function createLambertMaterial(rotate = false) {
	const loader = new THREE.TextureLoader();
	const IMAGE_PATH_ARR = ['people_white', 'exercise_white', 'wizard_white', 'electricity_white'];
	const imgRandomIndex = Math.floor(Math.random() * IMAGE_PATH_ARR.length);
	const src = `../images/three/${IMAGE_PATH_ARR[imgRandomIndex]}.png`;
	const texture = loader.load(src);
	// 回転が必要な画像
	if (rotate) {
		texture.center = new THREE.Vector2(0.5, 0.5);
		texture.rotation = Math.PI;
	}
	const material = new THREE.MeshLambertMaterial({ map: texture });
	return material;
}

function createTextureMaterialArr() {
	// 6つテクスチャをロードしたmaterial指定することで6面別のテクスチャにできる
	const textureMaterialArr = [
		// +x軸
		createLambertMaterial(),
		// -x軸
		createLambertMaterial(),
		// +y軸
		createLambertMaterial(),
		// -y軸
		createLambertMaterial(),
		// +z軸
		createLambertMaterial(),
		// -z軸 //向きおかしいから修正
		createLambertMaterial(true),
	];
	return textureMaterialArr;
}

function setupObjects() {
	// 床
	const planeGeo = new THREE.PlaneGeometry(canvas.width * 10, canvas.height * 10);
	const redMaterial = new THREE.MeshLambertMaterial({ color: 'dimgray', side: THREE.DoubleSide });
	const ground = new THREE.Mesh(planeGeo, redMaterial);
	// デフォはz=0なのでy=0になるよう回転
	ground.rotation.x = RIGHT_ANGLE;
	ground.receiveShadow = true;
	scene.add(ground);

	const size = 30;
	const defaultDist = 30;
	let meshArr = [];
	for (let i = 0; i < BOX_COUNT; i++) {
		const position = new THREE.Vector3(defaultDist * (i - 1), defaultDist, 0);
		const rotation = new THREE.Vector3(RIGHT_ANGLE / (i + 1), 0, 0);
		const boxMesh = createBoxMesh(size, position, rotation, createTextureMaterialArr());
		meshArr.push(boxMesh);
	}
	// 作成したmeshを返却
	return meshArr;
}

function setupLights() {
	// ポイントライト
	const point = new THREE.SpotLight('white', 1, 0, Math.PI / 4.3, 0.05, 1);
	point.position.add(new THREE.Vector3(-10, 100, 60));
	point.castShadow = true;
	scene.add(point);

	// 環境光
	const ambient = new THREE.AmbientLight('lightyellow', 0.75);
	scene.add(ambient); // シーンに追加
}

function tick() {
	let nowTime = Date.now();
	// 実行時間
	const execTime = nowTime - start;
	// 止まるまでの残り時間
	const leftTime = stopTime - execTime;

	// 残り時間すぎたら終了
	if (leftTime < -1500) {
		finalize();
		return;
	}
	// シーン更新
	updateScene(meshArr, leftTime);

	// レンダリング
	renderer.render(scene, camera);

	// 次フレームの描画を予約
	requestAnimationFrame(tick);
}

/**
 * 各フレームでのシーン更新処理
 * @param {Array<THREE.Mesh>} meshArr 回転させるmeshの配列
 * @param {number} leftTime 回転終了までの残り時間
 */
function updateScene(meshArr, leftTime) {
	count++;
	// ちょうど正面で終われる用のバッファ500ms
	if (leftTime < -500) {
		moveCamera(0.005, new THREE.Vector3(0, 0, 0));
		return;
	}

	// 各mesh
	for (let i = 0; i < BOX_COUNT; i++) {
		// meshごとに時間差で回転を止める
		if (leftTime - stopDiff * (BOX_COUNT - i - 1) <= 0) {
			const degree = meshArr[i].rotation.x;
			// 正面からの角度のズレ(178 degreesなら -2 degrees)
			const diffDegree = degree - Math.round(degree / RIGHT_ANGLE) * RIGHT_ANGLE;
			// 1/100以下のズレなら止める
			if (Math.abs(diffDegree) < RIGHT_ANGLE / 100) {
				continue;
			}
		}
		// 角度の変動幅を小さくしていく(減速)
		// 変動幅の計算は動かしながらそれっぽい動きになる数字で決定
		meshArr[i].rotation.x += RIGHT_ANGLE / (count / ((i + 1) * 7));
	}
}

/**
 *
 * @param {number} thetaDiff
 * @param {THREE.Vector3} direction
 */
function moveCamera(thetaDiff, direction) {
	const x = camera.position.x;
	const z = camera.position.z;
	let theta = Math.atan2(z, x);
	let r = Math.sqrt(x * x + z * z);
	theta += thetaDiff;
	camera.position.x = r * Math.cos(theta);
	camera.position.z = r * Math.sin(theta);
	camera.lookAt(direction);
}

// 終了処理
function finalize() {
	const launchDiv = document.getElementById("launch");
	if (launchDiv != null) {
		launchDiv.style.opacity = "0";
		launchDiv.style.pointerEvents = "none";
	}
}
