package com.example.nutrition_assessment_system_android_app.ui.feature.camera.component

import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import java.util.concurrent.TimeUnit
import androidx.camera.core.FocusMeteringAction
import kotlin.math.abs

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onCameraReady: (Camera) -> Unit = { }
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        factory = { context ->
            val previewView = PreviewView(context)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            var camera: Camera? = null
            var baseZoom = 1f
            var isScaling = false

            val scaleGestureDetector = ScaleGestureDetector(
                context,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                        isScaling = true
                        baseZoom = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: baseZoom
                        return true
                    }
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        val zoomState = camera?.cameraInfo?.zoomState?.value
                        val minZoom = zoomState?.minZoomRatio ?: 1f
                        val maxZoom = zoomState?.maxZoomRatio ?: 10f // fallback higher
                        val currentZoom = zoomState?.zoomRatio ?: baseZoom
                        // Use currentZoom * incremental scale for responsiveness
                        val desired = (currentZoom * detector.scaleFactor).coerceIn(minZoom, maxZoom)
                        // Apply only if significant change
                        if (abs(desired - currentZoom) > 0.005f) {
                            camera?.cameraControl?.setZoomRatio(desired)
                        }
                        return true
                    }
                    override fun onScaleEnd(detector: ScaleGestureDetector) {
                        isScaling = false
                    }
                }
            )

            previewView.setOnTouchListener { _, event ->
                val pinchHandled = scaleGestureDetector.onTouchEvent(event)
                if (!pinchHandled && !isScaling && event.pointerCount == 1) {
                    when (event.actionMasked) {
                        MotionEvent.ACTION_UP -> {
                            val tapDuration = event.eventTime - event.downTime
                            if (tapDuration < 250) { // treat as quick tap
                                camera?.let { cam ->
                                    val factory = previewView.meteringPointFactory
                                    val point = factory.createPoint(event.x, event.y)
                                    val action = FocusMeteringAction.Builder(point)
                                        .setAutoCancelDuration(3, TimeUnit.SECONDS)
                                        .build()
                                    cam.cameraControl.startFocusAndMetering(action)
                                }
                            }
                        }
                    }
                }
                pinchHandled || (!isScaling && event.actionMasked == MotionEvent.ACTION_UP && event.pointerCount == 1)
            }

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview
                    )
                    camera.let { onCameraReady(it) }
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }
            }, context.mainExecutor)

            previewView
        }
    )
}