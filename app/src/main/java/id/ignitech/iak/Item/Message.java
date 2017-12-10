package id.ignitech.iak.Item;

/**
 * Created by rizki on 10/12/17.
 */

public interface Message {
    interface Authentication {
        final String FAILURE = "Authentication Failed";
    }
    interface Profile {
        final String UPDATED = "Data sudah terupdate";
    }
    interface Fasilitator {
        final String READ_VALUE_FAILED = "Failed to read value";
    }
}
