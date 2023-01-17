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
            boolean isData = false;
            for (int i = 0; i < fileSize; i++) {
                char temp = (char) buffer.get();
                switch (temp){
                    case ':':
                    case '\n':
                        builder.append(" ");
                        isData = !isData;
                        continue;
                    case '\r':
                        continue;
                }
                if(isData) {
                    builder.append(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] result = builder.toString().trim().split("\\s+");
        Profile profile = new Profile();
        profile.setName(result[0]);
        profile.setAge(Integer.parseInt(result[1]));
        profile.setEmail(result[2]);
        profile.setPhone(Long.parseLong(result[3]));
        return profile;
    }
}
