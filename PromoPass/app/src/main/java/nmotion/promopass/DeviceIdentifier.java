package nmotion.promopass;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by Anna on 10/28/2015.
 */
public class DeviceIdentifier {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) {
                    writeInstallationFile(context, installation);
                }
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    public synchronized static void delete(Context context) {
        File installation = new File(context.getFilesDir(), INSTALLATION);
        try {
            if (installation.exists()) {
                boolean success = installation.delete();
                Toast.makeText(context, success ? "success" : "fail", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        String deviceID = new String(bytes);
        // grab ConsumerID from server
        return deviceID;
    }

    private static void writeInstallationFile(Context context, File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String deviceID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if(deviceID == null) {
            deviceID = UUID.randomUUID().toString().replace("-", "");
            deviceID = deviceID.substring(16, deviceID.length());
        }
        out.write(deviceID.getBytes());
        out.close();

        // generate unique consumer id
        String consumerID = UUID.randomUUID().toString().replace("-", "");
        consumerID = consumerID.substring(16, consumerID.length());

        // while consumerID is not unique, generate new one
        // store consumer

    }
}