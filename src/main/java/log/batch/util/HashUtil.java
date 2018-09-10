
package log.batch.util;

public final class HashUtil {

    /**
     * a quite simple FNV hash
     *
     * @param bytes
     * @return
     */
    public static int quickHash(byte[] bytes,int shard) {
        if (bytes.length == 0) {
            return 0;
        }

        int h = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            h = (31 * h) ^ bytes[i];
        }

        return h & shard;
    }

}