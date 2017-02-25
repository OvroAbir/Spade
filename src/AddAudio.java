
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sakib
 */
public class AddAudio {
    static int index;
    static void playUltimateWinningAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\Winning1.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
	clip.stop();
    }
    static void playCardShufflingAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\cardShuffle.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.stop();
    }
    static void playCardTakeOutAudio(int duration)
    {
        if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\cardTakeOutPackage2.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
		clip.stop();
    }
    static void playCardDrawAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\drawNew.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
		clip.stop();
    }
    static void playWinningOneRoundAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\WinningOneRound.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
		clip.stop();
    }
    static void playOpeningAudio(int duration)
    {
        if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\OpeningSound.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.stop();
    }
    static void playEndingAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\EndingSound.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
		clip.stop();
    }
    static void playClappingAudio(int duration)
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\ClappingSound.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
		clip.stop();
    }
    static void playMouseHoverAudio(int duration)           // call by 64
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\Click.wav");
        AudioClip clip = Applet.newAudioClip(url);
        System.out.println("***************hoveraudio**************");
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
	clip.stop();
    }
    static void playWrongCardBiddingAudio(int duration)      // call by 382
    {
        //if(!(index == 1)) return;
        URL url = AddAudio.class.getResource("Project Collection\\SoundFiles\\Error.wav");
        AudioClip clip = Applet.newAudioClip(url);
        System.out.println("***************wrongaudio**************");
        clip.loop();
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
	clip.stop();
    }
}
