package com.MuhammadNurmajiid.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;


    private Map<String, Sound> soundMap;
    private Map<String, Music> musicMap;
    private Music currentMusic;

    private AudioManager() {
        soundMap = new HashMap<>();
        musicMap = new HashMap<>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void registerSound(String key, String filename) {
        if (soundMap.containsKey(key)) {
            return;
        }
        if (Gdx.audio != null && Gdx.files != null && Gdx.files.internal(filename).exists()) {
            soundMap.put(key, Gdx.audio.newSound(Gdx.files.internal(filename)));
        } else {
            System.out.println("No SFX" + filename);
        }
    }

    public void registerMusic(String key, String filename) {
        if (musicMap.containsKey(key)) {
            return;
        }
        if (Gdx.audio != null && Gdx.files != null && Gdx.files.internal(filename).exists()) {
            musicMap.put(key, Gdx.audio.newMusic(Gdx.files.internal(filename)));
        } else {
            System.out.println("No BGM" + filename);
        }
    }

    public Sound getSound(String key) {
        return soundMap.get(key);
    }

    public Music getMusic(String key) {
        return musicMap.get(key);
    }

    public void playSound(String key) {
        Sound sound = getSound(key);
        if (sound != null) {
            sound.play(0.15f);
        }
    }



    public void play(String introKey, String loopKey) {
        Music intro = getMusic(introKey);
        Music loop = getMusic(loopKey);
        if (intro == null || loop == null) {
            return;
        }
        stopMusic();
        currentMusic = intro;
        intro.setLooping(false);
        intro.setVolume(0.5f);
        intro.setOnCompletionListener(music -> {
            currentMusic = loop;
            loop.setLooping(true);
            loop.setVolume(0.5f);
            loop.play();
        });
        intro.play();
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    public void init() {
        //BGM is cover from Everything will Freeze, a cover of Sakuya's Theme
        registerMusic("bgm_intro", "intro.ogg");
        registerMusic("bgm_loop", "loop.ogg");

        //SFX is ripped from the Original touhou game (i larped touhou so idk which i search online)
        registerSound("sfx_shoot", "shoot.wav");
        registerSound("sfx_enemy_die", "dead.wav");
        registerSound("sfx_item", "item.wav");
    }

    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        for (Music music : musicMap.values()) {
            music.dispose();
        }
        soundMap.clear();
        musicMap.clear();
        currentMusic = null;
    }
}
