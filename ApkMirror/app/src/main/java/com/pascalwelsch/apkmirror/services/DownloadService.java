package com.pascalwelsch.apkmirror.services;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by jannisveerkamp on 07.11.14.
 */
public class DownloadService {

    Context mContext;

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            long id = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
            DownloadManager dm = (DownloadManager) mContext.getSystemService(
                    Context.DOWNLOAD_SERVICE);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(dm.getUriForDownloadedFile(id),
                    "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        }
    };

    public DownloadService(Context context) {
        mContext = context;
    }

    public void startDownload(String url, String apkName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Downloading " + apkName);
        request.setTitle("Downloading " + apkName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apkName);
        request.setMimeType("application/vnd.android.package-archive");

        // Get download service and enqueue file
        DownloadManager manager = (DownloadManager) mContext.getSystemService(
                Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        mContext.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

}
