package com.example.echocircleandroid.ui.theme.screens.components.Product

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

@Composable
fun GetTextWithCameraScreen(navController: NavController) {
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

    /* TODO : 서버에서 해당 제품 있는지 찾아보기*/
    suspend fun sendTextToServer(text: String) {
        val found = true

        if(found){
            navController.navigate(NavItem.CheckDeviceScreen.screen_route)
        }else{
            navController.navigate(NavItem.NotFoundDeviceScreen.screen_route)
        }
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
                text = "수거하실 폐가전의 바코드 번호를 찍어주세요",
                fontSize = 16.sp,
                color = Color.Black,
            )

            if (hasCameraPermission) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .height(300.dp) // 고정된 높이로 설정
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

            if (capturedText.isEmpty() && !isProcessing) {
                Button(
                    onClick = {
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
                    },
                    enabled = !isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "촬영하기")
                }
            }

            if (capturedText.isNotEmpty() && !isProcessing) {


                val coroutineScope = rememberCoroutineScope()

                capturedText.split("\n").forEach { text ->
                    Text(
                        text = text + "   검색하기",
                        fontSize = 16.sp,
                        color = Color.Blue,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch {
                                    sendTextToServer(text)
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        capturedText = ""
                        startCamera()
                    },
                    enabled = !isProcessing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "다시 촬영하기")
                }

                Spacer(modifier = Modifier.height(16.dp))


            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "바코드가 없으신가요?",
                color = Color.Blue,
                modifier = Modifier.clickable {

                },
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
            if (extractedText.isEmpty()) {
                onTextExtracted("텍스트가 감지되지 않았습니다.")
            } else {
                onTextExtracted(extractedText)
            }
        }
        .addOnFailureListener { e ->
            Log.e("DirectProcessingScreen", "Text recognition failed", e)
            onTextExtracted("텍스트 인식 실패")
        }
}