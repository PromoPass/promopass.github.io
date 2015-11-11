package nmotion.promopass;

import android.content.Context;
import android.provider.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /*
    // used for debugging purposes

    public static void delete(Context context){
        File installation = new File(context.getFilesDir(), INSTALLATION);
        installation.delete();
    }

     */

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        String deviceID = new String(bytes);

        JSONArray array = Reader.getResults("http://fendatr.com/api/v1/device/" + deviceID + "/consumer/consumer-id");

        String consumerID = "";
        try {
            JSONObject jsonTemp = array.getJSONObject(0);
            consumerID = jsonTemp.getString("ConsumerID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumerID;
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

        Reader.insert("http://fendatr.com/api/v1/consumer", "{\"DeviceID\" : \"" + deviceID + "\"}");
    }
}