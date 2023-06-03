package com.duy.carshowroomdemo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {
    public static void main(String[] args) throws Exception {
        String remoteUrl = "https://img.freepik.com/premium-photo/image-colorful-galaxy-sky-generative-ai_791316-9864.jpg?w=900";
        String fileName = "D:\\Image_Gallery\\test_image1.jpg";

        downloadImage(remoteUrl, fileName);

    }

    public static void downloadImage(String remoteUrl, String path){
        try {
            URL url = new URL(remoteUrl);
            InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[2048];
            int length = 0;
            while((length = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
