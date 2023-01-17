package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel()) {

            long fileSize = inChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();
            for (int i = 0; i < fileSize; i++) {
                builder.append((char) buffer.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] result = builder.toString().trim().split("\\s+");
        Profile profile = new Profile();
        profile.setName(result[1]);
        profile.setAge(Integer.parseInt(result[3]));
        profile.setEmail(result[5]);
        profile.setPhone(Long.parseLong(result[7]));
        return profile;
    }
}
