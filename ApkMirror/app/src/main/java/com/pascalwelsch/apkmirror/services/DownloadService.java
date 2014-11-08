package com.pascalwelsch.apkmirror.services;

import com.pascalwelsch.apkmirror.model.AppUpdate;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * Created by jannisveerkamp on 07.11.14.
 */
public class DownloadService {

    public class CompleteDownloadReceiver extends BroadcastReceiver {

        String mPackageName;

        public CompleteDownloadReceiver(String packageName) {
            mPackageName = packageName;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {

            long id = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);

            // Register installed receiver and unregister complete receiver
            mContext.unregisterReceiver(this);
            registerBroadcastReceiverInstalled(id);

            DownloadManager dm = (DownloadManager) mContext.getSystemService(
                    Context.DOWNLOAD_SERVICE);

            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(dm.getUriForDownloadedFile(id), mimeType);

            Log.v(TAG, "Installing application: " + dm.getUriForDownloadedFile(id));
            mContext.startActivity(intent);
        }
    }

    public class CompleteInstallReceiver extends BroadcastReceiver {

        long mId;

        public CompleteInstallReceiver(long id) {
            mId = id;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {
            mContext.unregisterReceiver(this);
            DownloadManager dm = (DownloadManager) mContext.getSystemService(
                    Context.DOWNLOAD_SERVICE);
            int removed = dm.remove(mId);
            Log.v(TAG, "Removed " + removed + " files.");
        }
    }

    private static final String TAG = DownloadService.class.getSimpleName();

    private static final String mimeType = "application/vnd.android.package-archive";

    private Context mContext;

    public DownloadService(Context context) {
        mContext = context;
    }

    public void registerBroadcastReceiverInstalled(long id) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addDataScheme("package");

        mContext.registerReceiver(new CompleteInstallReceiver(id), intentFilter);
    }

    public void startDownload(AppUpdate app) {
        // Create request
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(app.getDownloadUrl()));
        request.setDescription("Downloading " + app.getName());
        request.setTitle("Apk-Mirror");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                app.getFilename());
        request.setMimeType(mimeType);
        //request.setNotificationVisibility(
        //        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);

        // Get download service and enqueue file
        DownloadManager manager = (DownloadManager) mContext.getSystemService(
                Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        // Register BroadcastReceiver
        mContext.registerReceiver(new CompleteDownloadReceiver(app.getPackageName()),
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        Log.v(TAG, "Download started: " + app.getFilename());
    }
}
