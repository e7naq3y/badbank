package com.faceless.fxxkapp;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class OkHttpUtil {
    public static String APP_TAG = "fxxkapp";

    public static String getStringSync(String str) throws InterruptedException {
        try {
            return new OkHttpClient().newCall(new Request.Builder().url(str).build()).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getStringAsync(String str) throws InterruptedException {
        new OkHttpClient().newCall(new Request.Builder().url(str).build()).enqueue(new Callback() { // from class: com.faceless.fxxkapp.OkHttpUtil.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str2 = OkHttpUtil.APP_TAG;
                    Log.d(str2, "getStringAsync：" + response.body().string());
                }
            }
        });
        return null;
    }

    public static String postStringAsync(String str) throws InterruptedException {
        new OkHttpClient().newCall(new Request.Builder().url(str).post(new FormBody.Builder().add("a", "1").add("b", "2").build()).build()).enqueue(new Callback() { // from class: com.faceless.fxxkapp.OkHttpUtil.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str2 = OkHttpUtil.APP_TAG;
                    Log.d(str2, "postStringAsync：" + response.body().string());
                }
            }
        });
        return null;
    }

    public static String postStringSync(String str) throws InterruptedException {
        String str2 = null;
        try {
            str2 = new OkHttpClient().newCall(new Request.Builder().url(str).post(new FormBody.Builder().add("a", "1").add("b", "2").build()).build()).execute().body().string();
            String str3 = APP_TAG;
            Log.d(str3, "postStringSync：" + str2);
            return str2;
        } catch (IOException e) {
            e.printStackTrace();
            return str2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0111 A[Catch: IOException -> 0x010d, JSONException -> 0x0122, TryCatch #1 {JSONException -> 0x0122, blocks: (B:3:0x0028, B:5:0x0040, B:7:0x0054, B:9:0x005c, B:11:0x009e, B:12:0x00a1, B:21:0x00cc, B:24:0x00d2, B:27:0x00dc, B:39:0x00ef, B:43:0x00f7, B:44:0x00fa, B:47:0x0101, B:52:0x0109, B:56:0x0111, B:57:0x0114, B:60:0x011b), top: B:71:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x011a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x011e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String postDownloadFileAndSave(String str, String str2, String str3, String str4) {
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        try {
            try {
                Response execute = new OkHttpClient().newCall(new Request.Builder().url(str).post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str2)).build()).execute();
                String mediaType = execute.body().contentType().toString();
                if (mediaType.equals("application/json")) {
                    return new JSONObject(execute.body().string()).getString("message");
                }
                if (!mediaType.equals("application/zip")) {
                    return "未知错误，可能无在线脚本";
                }
                Log.d(APP_TAG, "开始下载文件");
                Log.d(APP_TAG, "文件zip保存的路径：" + str3);
                Log.d(APP_TAG, "文件名：" + str4);
                byte[] bArr = new byte[2048];
                File file = new File(str3);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, str4);
                FileOutputStream fileOutputStream2 = null;
                fileOutputStream2 = null;
                InputStream inputStream2 = null;
                try {
                    inputStream = execute.body().byteStream();
                    try {
                        execute.body().contentLength();
                        fileOutputStream = new FileOutputStream(file2);
                        while (true) {
                            try {
                                int read = inputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            } catch (Exception e) {
                                e = e;
                                inputStream2 = inputStream;
                                try {
                                    e.printStackTrace();
                                    if (inputStream2 != null) {
                                        try {
                                            inputStream2.close();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    return !file2.exists() ? "未知错误，文件下载不成功" : "下载文件成功";
                                } catch (Throwable unused) {
                                    inputStream = inputStream2;
                                    fileOutputStream2 = fileOutputStream;
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    if (fileOutputStream2 != null) {
                                        fileOutputStream2.close();
                                    }
                                    return !file2.exists() ? "未知错误，文件下载不成功" : "下载文件成功";
                                }
                            } catch (Throwable unused2) {
                                fileOutputStream2 = fileOutputStream;
                                if (inputStream != null) {
                                }
                                if (fileOutputStream2 != null) {
                                }
                                if (!file2.exists()) {
                                }
                            }
                        }
                        fileOutputStream.flush();
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        fileOutputStream.close();
                        return !file2.exists() ? "未知错误，文件下载不成功" : "下载文件成功";
                    } catch (Exception e5) {
                        e = e5;
                        fileOutputStream = null;
                    } catch (Throwable unused3) {
                        if (inputStream != null) {
                        }
                        if (fileOutputStream2 != null) {
                        }
                        if (!file2.exists()) {
                        }
                    }
                } catch (Exception e6) {
                    e = e6;
                    fileOutputStream = null;
                } catch (Throwable unused4) {
                    inputStream = null;
                }
            } catch (IOException e7) {
                e = e7;
                e.printStackTrace();
                return "未知错误，可能网络原因";
            }
        } catch (JSONException e8) {
            e = e8;
            e.printStackTrace();
            return "未知错误，可能网络原因";
        }
    }
}
