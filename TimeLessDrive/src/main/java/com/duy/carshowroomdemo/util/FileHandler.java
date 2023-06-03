package com.duy.carshowroomdemo.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    public static void main(String[] args) throws Exception {
        String path = "D:\\.UNIVERSITY\\T5_2023_SUMMER\\SWP391\\Car_Showroom_Project\\Web_Scraping\\test.txt";
        extractInfo(path);
    }
    public static void extractInfo(String filePath) {
        File file = new File(filePath);
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            String imageRootDir = "d:/Image_Gallery/";
            String currentDir = "";
            int count = 1;
            while((line = bufferedReader.readLine()) != null){
                if(line.contains("START:")){
                    System.out.println("Downloading folder start!");
                    if(line.contains("101")){
                        break;
                    }
                }
                if(line.contains("Car name:")){
                    String directory = handleDirName(line.replace("Car name:", "").trim());
                    currentDir = imageRootDir + directory;
                    Files.createDirectory(Paths.get(currentDir));
                }
                if(line.contains("link")){
                    String imageName = "carvago" + (count++) + ".jpg";
                    Downloader.downloadImage(handleImageTag(line), currentDir + "/" + imageName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String handleImageTag(String line){
        int start = line.indexOf("src=\"") + "src=\"".length();
        int end = line.length() - 3;
        String substring = line.substring(start, end);
        return substring.replaceAll("amp;", "");
    }

    public static String handleDirName(String dirName){
        if(dirName.contains("/")){
            return dirName.replaceAll("\\W","");
        }
        return dirName;
    }

}
