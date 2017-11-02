package com.example.qrcode.decode;

import android.os.Handler;
import android.os.Message;

import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.example.qrcode.camera.CameraManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Collection;

/**
 * Created by yangyu on 17/10/18.
 */

/**
 * This class handles all the messaging which comprises the state machine for capture.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ScannerHandler extends Handler {

    private static final String TAG = ScannerHandler.class.getSimpleName();

    private final ScannerActivity activity;
    private final DecodeThread decodeThread;
    private State state;
    private final CameraManager cameraManager;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public ScannerHandler(ScannerActivity activity,
                          Collection<BarcodeFormat> decodeFormats,
                          String characterSet,
                          CameraManager cameraManager) {
        this.activity = activity;
        decodeThread = new DecodeThread(activity, decodeFormats, characterSet);
        decodeThread.start();
        state = State.SUCCESS;

        // Start ourselves capturing previews and decoding.
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message == null) return;
        switch (message.what) {
            case Constant.MESSAGE_SCANNER_DECODE_SUCCEEDED:
                Result r = (Result) message.obj;
                activity.handDecode(r);
                break;
            case Constant.MESSAGE_SCANNER_DECODE_FAIL:
                state = State.PREVIEW;
                cameraManager.requestPreviewFrame(decodeThread.getHandler(), Constant.MESSAGE_SCANNER_DECODE);
                break;
        }
    }

    public void quitSynchronously() {
        state = State.DONE;
        cameraManager.stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), Constant.MESSAGE_SCANNER_QUIT);
        quit.sendToTarget();
        try {
            // Wait at most half a second; should be enough time, and onPause() will timeout quickly
            decodeThread.join(500L);
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        removeMessages(Constant.MESSAGE_SCANNER_DECODE_SUCCEEDED);
        removeMessages(Constant.MESSAGE_SCANNER_DECODE_FAIL);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(), Constant.MESSAGE_SCANNER_DECODE);
        }
    }

}
