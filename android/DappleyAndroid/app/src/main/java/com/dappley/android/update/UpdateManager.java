package com.dappley.android.update;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dappley.android.R;
import com.dappley.android.dialog.ConfirmDialog;
import com.dappley.android.dialog.LoadingDialog;
import com.dappley.android.network.FileRequest;
import com.dappley.android.network.RetrofitRequest;
import com.dappley.android.util.Constant;
import com.dappley.android.util.StorageUtil;
import com.dappley.android.util.VersionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateManager {
    private static final int DOWNLOAD_ING = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private static final int DOWNLOAD_ERROR = -1;

    public static final boolean CHECK_AUTO = true;
    public static final boolean CHECK_USER = false;


    private Context context;

    ProgressBar proDownload;
    TextView tvPercent;
    TextView tvKbNow;
    TextView tvKbAll;

    private Dialog downloadDialog;

    private String savePath;
    private int progress;
    private int totalByte;
    private int downByte;

    private boolean isAutoCheck;
    private boolean cancelUpdate = false;
    private int newVersionCode;
    private String newFileName;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ING:
                    showProgress();
                    break;
                case DOWNLOAD_FINISH:
                    installApk();
                    break;
                case DOWNLOAD_ERROR:
                    onDownloadError();
                    break;
            }
        }
    };

    /**
     * @param context
     * @param isAutoCheck
     */
    public UpdateManager(Context context, boolean isAutoCheck) {
        this.context = context;
        this.isAutoCheck = isAutoCheck;
    }


    public void checkUpdate() {
        if (!isAutoCheck) {
            LoadingDialog.show(context, R.string.layout_version_checking);
        }
        getNewVersion();
    }

    private void getNewVersion() {
        RetrofitRequest.sendGetRequest(Constant.URL_APP_VERSION, new RetrofitRequest.ResultHandler(context) {
            @Override
            public void onBeforeResult() {
                LoadingDialog.close();
            }

            @Override
            public void onResult(String response) {
                if (response == null || response.trim().length() == 0) {
                    Toast.makeText(context, R.string.layout_version_no_new, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.has("versionCode") || !jsonObject.has("fileName")) {
                        Toast.makeText(context, R.string.layout_version_no_new, Toast.LENGTH_SHORT).show();
                        LoadingDialog.close();
                        return;
                    }
                    newVersionCode = jsonObject.getInt("versionCode");
                    newFileName = jsonObject.getString("fileName");
                    int versionCode = VersionUtil.getVerionCode(context);
                    LoadingDialog.close();
                    if (newVersionCode > versionCode) {
                        showUpdateDialog(newFileName);
                    } else {
                        if (!isAutoCheck) {
                            Toast.makeText(context, R.string.layout_version_no_new, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, R.string.layout_version_no_new, Toast.LENGTH_SHORT).show();
                    LoadingDialog.close();
                }
            }

            @Override
            public void onAfterFailure() {
                LoadingDialog.close();
//                if (!isAutoCheck) {
//                    Toast.makeText(context, R.string.layout_version_error, Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void showUpdateDialog(final String fileName) {
        ConfirmDialog dialog = new ConfirmDialog(context, new ConfirmDialog.OnClickListener() {
            @Override
            public void onConfirm() {
                showDownloadDialog(fileName);
            }
        });
        dialog.setTitle(R.string.note_confirm_title);
        dialog.setContent(R.string.layout_version_new);
        dialog.setConfirmText(R.string.layout_yes);
        dialog.setCancelText(R.string.layout_no);
        dialog.show();
    }

    private void showDownloadDialog(String fileName) {
        Builder builder = new Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null);
        proDownload = (ProgressBar) view.findViewById(R.id.pro_download);
        tvPercent = (TextView) view.findViewById(R.id.txt_percent);
        tvKbNow = (TextView) view.findViewById(R.id.txt_kb_now);
        tvKbAll = (TextView) view.findViewById(R.id.txt_kb_all);

        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadDialog != null) {
                    downloadDialog.dismiss();
                }
                cancelUpdate = true;
            }
        });

        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();
        downloadDialog.getWindow().setContentView(view);

        downloadApk(fileName);
    }

    private void downloadApk(String fileName) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_CONTRACT_BASE)
                .callbackExecutor(executorService)
                .build();

        String url = String.format(Constant.URL_APP_DOWNLOAD, fileName);
        FileRequest fileRequest = retrofit.create(FileRequest.class);
        Call<ResponseBody> call = fileRequest.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (writeResponseBodyToDisk(response.body())) {
                        downloadDialog.dismiss();
                    } else {
                        mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
                    }
                } else {
                    mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        savePath = StorageUtil.getDownloadPath(context);
        File apkFile = new File(savePath, newFileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(apkFile);

            BigDecimal bd1024 = new BigDecimal(1024);
            totalByte = new BigDecimal(fileSize).divide(bd1024, BigDecimal.ROUND_HALF_UP).setScale(0).intValue();

            while (!cancelUpdate) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                progress = (int) (((float) (fileSizeDownloaded * 100.0 / fileSize)));
                downByte = new BigDecimal(fileSizeDownloaded).divide(bd1024, BigDecimal.ROUND_HALF_UP).setScale(0).intValue();
                mHandler.sendEmptyMessage(DOWNLOAD_ING);
            }
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showProgress() {
        proDownload.setProgress(progress);
        tvPercent.setText(progress + "%");
        tvKbAll.setText(totalByte + "Kb");
        tvKbNow.setText(downByte + "Kb");
    }

    private void onDownloadError() {
        Toast.makeText(context, R.string.layout_download_error, Toast.LENGTH_SHORT).show();
        downloadDialog.dismiss();
    }

    private void installApk() {
        File apkFile = new File(savePath, newFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}