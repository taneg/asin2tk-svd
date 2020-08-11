package com.kalpana.asin2tk.svd.utils;

import java.io.IOException;

import org.bytedeco.javacpp.Loader;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/8/10 17:08
 **/
public class VideoTransort {

    public static void translate() throws IOException, InterruptedException {
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        ProcessBuilder pb = new ProcessBuilder(ffmpeg, "-i", "/path/to/input.mp4", "-vcodec", "h264", "/path/to/output.mp4");
        pb.inheritIO().start().waitFor();
//        /usr/bin/ffmpeg -i 1.mp4 -vf "delogo=x=50:y=640:w=180:h=60:show=0" -c:a copy 2.mp4
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        translate();
    }
}
