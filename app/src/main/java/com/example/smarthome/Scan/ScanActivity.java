//package com.example.smarthome.Scan;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.smarthome.R;
//import com.google.zxing.Result;
//import com.google.zxing.ResultPoint;
//import com.journeyapps.barcodescanner.BarcodeCallback;
//import com.journeyapps.barcodescanner.BarcodeResult;
//import com.journeyapps.barcodescanner.CompoundBarcodeView;
//import java.util.List;
//public class ScanActivity extends AppCompatActivity {
//    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
//    private CompoundBarcodeView barcodeView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan);
//        barcodeView = findViewById(R.id.barcode_scanner);
//        barcodeView.decodeContinuous(callback);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA},
//                    CAMERA_PERMISSION_REQUEST_CODE);
//        } else {
//            barcodeView.resume();
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        barcodeView.pause();
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                barcodeView.resume();
//            } else {
//                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//    private BarcodeCallback callback = new BarcodeCallback() {
//        @Override
//        public void barcodeResult(BarcodeResult result) {
//            if (result.getText() != null) {
//                Log.d("ScanActivity", "Scan result: " + result.getText());
//                // 处理扫描结果
//            }
//        }
//        @Override
//        public void possibleResultPoints(List<ResultPoint> resultPoints) {
//        }
//    };
//}
