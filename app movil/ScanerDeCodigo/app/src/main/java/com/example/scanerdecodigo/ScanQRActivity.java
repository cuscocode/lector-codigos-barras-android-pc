package com.example.scanerdecodigo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ScanQRActivity extends Fragment {

   /* private static final String TAG = "ScanQRActivity";
    private DecoratedBarcodeView barcodeView;
    private boolean isFlasgON;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_layout_venta, container, false);

        isFlasgON = false;
        barcodeView = vista.findViewById(R.id.scanner_codigo);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.decodeContinuous(callback);

        Button btn_flash = vista.findViewById(R.id.btn_flas);
        if (!hasFlash()) {
            btn_flash.setVisibility(View.GONE);
        }

        btn_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFlashLight();
            }
        });

        return vista;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            Log.e(TAG, result.getText());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };


    private boolean hasFlash() {
        return getContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashLight() {
        if (isFlasgON) {
            isFlasgON = false;
            barcodeView.setTorchOff();
        } else {
            isFlasgON = true;
            barcodeView.setTorchOn();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }*/
}
