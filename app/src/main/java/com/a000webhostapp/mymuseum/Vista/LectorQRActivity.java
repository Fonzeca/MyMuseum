package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.a000webhostapp.mymuseum.R;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by Alexis on 21/10/2017.
 */

public class LectorQRActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
	private ZBarScannerView scannerView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scannerView = new ZBarScannerView(this);
		setContentView(scannerView);
		
	}
	protected void onResume() {
		super.onResume();
		scannerView.setResultHandler(this);
		scannerView.startCamera();
	}
	protected void onPause() {
		super.onPause();
		scannerView.stopCamera();
	}
	public void handleResult(Result result) {
		String respuestaEntera = "";
		try {
			byte[] b = result.getContents().getBytes("Shift_JIS");
			respuestaEntera = new String(b, StandardCharsets.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.v("RespuestaQR", result.getContents());
		Log.v("RespuestaQR Truncada", respuestaEntera);
		Log.v("Tipo de lectura", result.getBarcodeFormat().getName());
		Intent intent = new Intent();
		intent.setData(Uri.parse(respuestaEntera));
		setResult(RESULT_OK,intent);
		finish();
		
		//scannerView.resumeCameraPreview(this);
	}
	
}
