package com.cnvmxm.aggregatorservice.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static File createTempFile(String prefix, String suffix, byte[] fileBytes) throws IOException {
        File tempFile = File.createTempFile(prefix, suffix);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
        }
        return tempFile;
    }
}
