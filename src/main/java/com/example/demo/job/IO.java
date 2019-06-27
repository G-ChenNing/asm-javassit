package com.example.demo.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class IO {
    public static void copyFileByChannel(File source, File des) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel(); FileChannel targetChannel = new FileOutputStream(des).getChannel();) {
            for (long count = sourceChannel.size(); count > 0; ) {
                long transferred = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }
}
