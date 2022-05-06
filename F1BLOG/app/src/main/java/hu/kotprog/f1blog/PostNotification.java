package hu.kotprog.f1blog;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class PostNotification {
    private static final String CH_ID="POST NOTI";
    private NotificationManager manager;
    private Context context;

    public PostNotification(Context context) {
        this.context = context;
        this.manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel = new NotificationChannel("POST NOTI","POST NOTI",NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.setDescription("Új poszt");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"POST NOTI").setContentTitle("Új poszt")
                .setSmallIcon(R.drawable.noticon).setContentText(message);
        this.manager.notify(0,builder.build());
    }
}
