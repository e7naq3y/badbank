package com.faceless.fxxkapp;

import android.util.Log;
import com.topjohnwu.superuser.Shell;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AExecuteAsRoot {
    public static String APP_TAG = "fxxkapp";

    protected abstract ArrayList<String> getCommandsToExecute();

    public static boolean canRunRootCommands() {
        boolean z;
        try {
            Process exec = Runtime.getRuntime().exec("su");
            DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(exec.getInputStream());
            dataOutputStream.writeBytes("id\n");
            dataOutputStream.flush();
            String readLine = dataInputStream.readLine();
            String str = APP_TAG;
            Log.d(str, "currUid:" + readLine);
            boolean z2 = true;
            if (readLine == null) {
                Log.d(APP_TAG, "Can't get root access or denied by user");
                z = false;
                z2 = false;
            } else if (readLine.contains("uid=0")) {
                Log.d(APP_TAG, "Root access granted");
                z = true;
            } else {
                String str2 = APP_TAG;
                Log.d(str2, "Root access rejected: " + readLine);
                z = false;
            }
            if (z2) {
                dataOutputStream.writeBytes("exit\n");
                dataOutputStream.flush();
            }
            return z;
        } catch (Exception e) {
            String str3 = APP_TAG;
            Log.d(str3, "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
            return false;
        }
    }

    public static List<String> execSuperSuCmd(String str) {
        Shell.Result exec = Shell.cmd(str).exec();
        List<String> out = exec.getOut();
        exec.getCode();
        exec.isSuccess();
        return out;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005e, code lost:
        if (r1 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0060, code lost:
        r1.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0098, code lost:
        if (r1 != null) goto L24;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008b A[Catch: Exception -> 0x009b, TRY_ENTER, TryCatch #7 {Exception -> 0x009b, blocks: (B:12:0x0055, B:14:0x0060, B:43:0x008b, B:45:0x0090, B:47:0x0095), top: B:72:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0090 A[Catch: Exception -> 0x009b, TryCatch #7 {Exception -> 0x009b, blocks: (B:12:0x0055, B:14:0x0060, B:43:0x008b, B:45:0x0090, B:47:0x0095), top: B:72:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0095 A[Catch: Exception -> 0x009b, TRY_LEAVE, TryCatch #7 {Exception -> 0x009b, blocks: (B:12:0x0055, B:14:0x0060, B:43:0x008b, B:45:0x0090, B:47:0x0095), top: B:72:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a9 A[Catch: Exception -> 0x00b6, TryCatch #4 {Exception -> 0x00b6, blocks: (B:55:0x00a4, B:57:0x00a9, B:59:0x00ae, B:61:0x00b3), top: B:70:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ae A[Catch: Exception -> 0x00b6, TryCatch #4 {Exception -> 0x00b6, blocks: (B:55:0x00a4, B:57:0x00a9, B:59:0x00ae, B:61:0x00b3), top: B:70:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00b3 A[Catch: Exception -> 0x00b6, TRY_LEAVE, TryCatch #4 {Exception -> 0x00b6, blocks: (B:55:0x00a4, B:57:0x00a9, B:59:0x00ae, B:61:0x00b3), top: B:70:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.Reader, java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String execSuCmd(String str) {
        StringBuilder sb;
        Process process;
        ?? r2;
        DataOutputStream dataOutputStream;
        ?? r3;
        StringBuilder sb2;
        ?? r32;
        DataOutputStream dataOutputStream2 = null;
        try {
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (Exception e) {
                e = e;
                process = null;
                r2 = null;
            } catch (Throwable th) {
                th = th;
                process = null;
                r2 = null;
            }
            try {
                r2 = new InputStreamReader(process.getInputStream());
                try {
                    r3 = new BufferedReader(r2);
                    try {
                        dataOutputStream = new DataOutputStream(process.getOutputStream());
                        try {
                            try {
                                dataOutputStream.writeBytes(str + "\n");
                                dataOutputStream.writeBytes("exit\n");
                                dataOutputStream.flush();
                                char[] cArr = new char[4096];
                                sb = new StringBuilder();
                                while (true) {
                                    try {
                                        int read = r3.read(cArr);
                                        if (read <= 0) {
                                            break;
                                        }
                                        sb.append(cArr, 0, read);
                                    } catch (Exception e2) {
                                        e = e2;
                                        dataOutputStream2 = dataOutputStream;
                                        r32 = r3;
                                        e.printStackTrace();
                                        if (dataOutputStream2 != null) {
                                            dataOutputStream2.close();
                                        }
                                        if (r2 != null) {
                                            r2.close();
                                        }
                                        if (r32 != 0) {
                                            r32.close();
                                        }
                                    }
                                }
                                process.waitFor();
                                dataOutputStream.close();
                                r2.close();
                                r3.close();
                            } catch (Exception e3) {
                                e = e3;
                                sb = null;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (dataOutputStream != null) {
                                try {
                                    dataOutputStream.close();
                                } catch (Exception unused) {
                                    throw th;
                                }
                            }
                            if (r2 != null) {
                                r2.close();
                            }
                            if (r3 != 0) {
                                r3.close();
                            }
                            if (process != null) {
                                process.destroy();
                            }
                            throw th;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        sb = null;
                        r32 = r3;
                    }
                } catch (Exception e5) {
                    e = e5;
                    sb2 = null;
                    sb = sb2;
                    r32 = sb2;
                    e.printStackTrace();
                    if (dataOutputStream2 != null) {
                    }
                    if (r2 != null) {
                    }
                    if (r32 != 0) {
                    }
                } catch (Throwable th3) {
                    th = th3;
                    r3 = 0;
                    dataOutputStream = r3;
                    if (dataOutputStream != null) {
                    }
                    if (r2 != null) {
                    }
                    if (r3 != 0) {
                    }
                    if (process != null) {
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                r2 = null;
                sb2 = r2;
                sb = sb2;
                r32 = sb2;
                e.printStackTrace();
                if (dataOutputStream2 != null) {
                }
                if (r2 != null) {
                }
                if (r32 != 0) {
                }
            } catch (Throwable th4) {
                th = th4;
                r2 = null;
                r3 = r2;
                dataOutputStream = r3;
                if (dataOutputStream != null) {
                }
                if (r2 != null) {
                }
                if (r3 != 0) {
                }
                if (process != null) {
                }
                throw th;
            }
            return sb.toString();
        } catch (Throwable th5) {
            th = th5;
            dataOutputStream = null;
        }
    }

    public final boolean execute() {
        try {
            ArrayList<String> commandsToExecute = getCommandsToExecute();
            if (commandsToExecute != null && commandsToExecute.size() > 0) {
                Process exec = Runtime.getRuntime().exec("su");
                DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
                Iterator<String> it = commandsToExecute.iterator();
                while (it.hasNext()) {
                    dataOutputStream.writeBytes(it.next() + "\n");
                    dataOutputStream.flush();
                }
                dataOutputStream.writeBytes("exit\n");
                dataOutputStream.flush();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                char[] cArr = new char[4096];
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    int read = bufferedReader.read(cArr);
                    if (read <= 0) {
                        break;
                    }
                    stringBuffer.append(cArr, 0, read);
                }
                bufferedReader.close();
                try {
                    r2 = 255 != exec.waitFor();
                    PrintStream printStream = System.out;
                    printStream.println("BBBB: " + stringBuffer.toString());
                } catch (Exception unused) {
                }
            }
        } catch (IOException e) {
            Log.w("ROOT", "Can't get root access", e);
        } catch (SecurityException e2) {
            Log.w("ROOT", "Can't get root access", e2);
        } catch (Exception e3) {
            Log.w("ROOT", "Error executing internal operation", e3);
        }
        return r2;
    }
}
