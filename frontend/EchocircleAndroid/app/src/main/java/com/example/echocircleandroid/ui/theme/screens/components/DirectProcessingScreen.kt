
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun DirectProcessingScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasCameraPermission by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }
    var isCameraLoading by remember { mutableStateOf(true) }
    var isProcessing by remember { mutableStateOf(false) }
    var capturedText by remember { mutableStateOf("") }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    var previewView: PreviewView? by remember { mutableStateOf(null) }
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView!!.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                isCameraLoading = false
            } catch (exc: Exception) {
                Log.e("DirectProcessingScreen", "Camera use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "수거하실 폐가전의\n바코드 번호를 찍어주세요",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (hasCameraPermission) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f), // 고정된 비율로 설정
                    contentAlignment = Alignment.Center
                ) {
                    previewView = remember { PreviewView(context) }

                    AndroidView(
                        factory = { context ->
                            startCamera()
                            previewView!!
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    if (isCameraLoading || isProcessing) {
                        CircularProgressIndicator(
                            color = Color.Blue,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            } else {
                Text(
                    text = "카메라 권한이 필요합니다.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Button(
                onClick = {
                    if (capturedText.isEmpty()) {
                        isProcessing = true
                        imageCapture.takePicture(
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    cameraProvider?.unbindAll() // 카메라 멈추기
                                    val bitmap = image.toBitmap()
                                    extractTextFromImage(bitmap) { extractedText ->
                                        capturedText = extractedText
                                        isProcessing = false
                                    }
                                    image.close()
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Log.e("DirectProcessingScreen", "Image capture failed: ${exception.message}", exception)
                                    isProcessing = false
                                }
                            }
                        )
                    } else {
                        // 다시 촬영하기 버튼 동작
                        capturedText = ""
                        startCamera()
                    }
                },
                enabled = !isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = if (capturedText.isEmpty()) "촬영하기" else "다시 촬영하기")
            }

            if (capturedText.isNotEmpty()) {
                Text(
                    text = "추출된 텍스트:\n$capturedText",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "맞으신가요?",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = { /* 다음 단계로 이동하는 동작 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "다음 단계")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "바코드가 없으신가요?",
                color = Color.Blue,
                modifier = Modifier.clickable { /* 바코드가 없는 경우 클릭 시 동작 */ }
            )
        }
    }
}

private fun ImageProxy.toBitmap(): Bitmap {
    val buffer: ByteBuffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

private fun extractTextFromImage(bitmap: Bitmap, onTextExtracted: (String) -> Unit) {
    val image = InputImage.fromBitmap(bitmap, 0)
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            val extractedText = visionText.text
            onTextExtracted(extractedText)
        }
        .addOnFailureListener { e ->
            Log.e("DirectProcessingScreen", "Text recognition failed", e)
            onTextExtracted("")
        }
}
