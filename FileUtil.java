package com.faceless.fxxkapp;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
public class FileUtil {
    public static String APP_TAG = "fxxkapp";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean copyAssetAndWrite(Context context, String str) {
        try {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File file = new File(cacheDir, str);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            } else if (file.length() > 10) {
                return true;
            }
            InputStream open = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    open.close();
                    fileOutputStream.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void unzipFile(String str, String str2) throws IOException {
        String str3 = APP_TAG;
        Log.d(str3, "开始解压的文件： " + str);
        String str4 = APP_TAG;
        Log.d(str4, "解压的目标路径： " + str2);
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        byte[] bArr = new byte[1048576];
        while (nextEntry != null) {
            String str5 = APP_TAG;
            Log.d(str5, "解压文件入口 1：" + nextEntry);
            if (!nextEntry.isDirectory()) {
                String name = nextEntry.getName();
                String str6 = APP_TAG;
                Log.d(str6, "解压文件原来文件的位置： " + name);
                String substring = name.substring(name.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1);
                String str7 = APP_TAG;
                Log.d(str7, "解压文件的名字： " + substring);
                File file2 = new File(str2 + File.separator + substring);
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
            }
            nextEntry = zipInputStream.getNextEntry();
            String str8 = APP_TAG;
            Log.d(str8, "解压文件入口 2： " + nextEntry);
        }
        zipInputStream.close();
        Log.d(APP_TAG, "解压完成");
    }

    public static void writeconfigToFile(String str, String str2, String str3) {
        makeFilePath(str2, str3);
        String str4 = str + "\r\n";
        try {
            File file = new File(str2 + str3);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(str4.getBytes());
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File makeFilePath(String str, String str2) {
        File file;
        makeRootDirectory(str);
        try {
            file = new File(str + str2);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return file;
            }
        } catch (Exception e2) {
            e = e2;
            file = null;
        }
        return file;
    }

    public static void makeRootDirectory(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return;
            }
            file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UnzipWithPassword(String str, String str2, String str3) {
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            new ZipFile(str, str3.toCharArray()).extractAll(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
