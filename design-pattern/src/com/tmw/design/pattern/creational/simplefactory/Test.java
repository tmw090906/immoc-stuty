package com.tmw.design.pattern.creational.simplefactory;

public class Test {
    public static void main(String[] args) {
        Video video = VideoFactory.getVideo(PythonVideo.class);
        if (video == null) {
            return;
        }
        video.produce();

        Video videoClass = VideoFactory.getVideo("java");
        if (videoClass == null) {
            return;
        }
        videoClass.produce();
    }
}
