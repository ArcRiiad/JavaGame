package gameproject.jiwon.game.utils;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music extends Thread {

    private Clip player;
    private boolean isLoop;
    private File file;
    private AudioInputStream ais;

    public Music(String name, boolean isLoop) {
        try {
            this.isLoop = isLoop;
            file = new File(name);

            ais = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            player = AudioSystem.getClip();

            player.open(ais);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //음악 종료
    public void close() {
        isLoop = false;
        player.stop();
        this.interrupt(); // 해당 스레드 중지 상태로
    }

    //음악 실행
    @Override
    public void run() {
        try {
            player.start();
            if (isLoop) {
                player.loop(100); // 100번 실행
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playOnce (){
        try {
            player.start();
            if (isLoop) {
                player.loop(1); // 100번 실행
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}