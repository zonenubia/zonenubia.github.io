
import com.google.api.apikeys.v2.AndroidApplication;
import com.google.api.apikeys.v2.AndroidKeyRestrictions;
import com.google.api.apikeys.v2.ApiKeysClient;
import com.google.api.apikeys.v2.Key;
import com.google.api.apikeys.v2.Restrictions;
import com.google.api.apikeys.v2.UpdateKeyRequest;
import com.google.protobuf.FieldMask;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RestrictApiKeyAndroid {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException, TimeoutException {
    // TODO(Developer): Before running this sample,
    //  1. Replace the variable(s) below.
    String projectId = "awdev-my-id";

    // ID of the key to restrict. This ID is auto-created during key creation.
    // This is different from the key string. To obtain the key_id,
    // you can also use the lookup api: client.lookupKey()
    String keyId = "AIzaSyDAXoY0ZSOZ1TNpCpXcEKG4p_x2N2h3h3g";

    restrictApiKeyAndroid(projectId, keyId);
  }

  // Restricts an API key based on android applications.
  // Specifies the Android application that can use the key.
  public static void restrictApiKeyAndroid(String projectId, String keyId)
      throws IOException, ExecutionException, InterruptedException, TimeoutException {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the `apiKeysClient.close()` method on the client to safely
    // clean up any remaining background resources.
    try (ApiKeysClient apiKeysClient = ApiKeysClient.create()) {

      // Restrict the API key usage by specifying the allowed android applications.
      Restrictions restrictions = Restrictions.newBuilder()
          .setAndroidKeyRestrictions(AndroidKeyRestrictions.newBuilder()
              .addAllowedApplications(AndroidApplication.newBuilder()
                  // Specify the android application's package name and SHA1 fingerprint.
                  .setPackageName("my.id.awdev.app")
                  .setSha1Fingerprint("9A:E5:97:2A:48:F5:B2:CB:73:88:C3:72:E2:2D:EB:2F:19:92:D5:11")
                  .build())
              .build())
          .build();

      Key key = Key.newBuilder()
          .setName(String.format("projects/%s/locations/global/keys/%s", projectId, keyId))
          // Set the restriction(s).
          // For more information on API key restriction, see:
          // https://cloud.google.com/docs/authentication/api-keys
          .setRestrictions(restrictions)
          .build();

      // Initialize request and set arguments.
      UpdateKeyRequest updateKeyRequest = UpdateKeyRequest.newBuilder()
          .setKey(key)
          .setUpdateMask(FieldMask.newBuilder().addPaths("restrictions").build())
          .build();

      // Make the request and wait for the operation to complete.
      Key result = apiKeysClient.updateKeyAsync(updateKeyRequest).get(3, TimeUnit.MINUTES);

      // For authenticating with the API key, use the value in "result.getKeyString()".
      System.out.printf("Successfully updated the API key: %s", result.getName());
    }
  }
}

