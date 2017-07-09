package com.ue.someutils;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by xin on 4/7/17.
 */

public class SoundPlayerHelper {
    private Activity context;
    private SoundPool soundPool;
    private int soundId = -1;

    public SoundPlayerHelper(Activity context) {
        this.context = context;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        } else {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    soundPool.play(sampleId, 1, 1, 0, 0, 1);
                    soundId = sampleId;
                }
            }
        });
    }

    public void play(int soundRawRes) {
        if (soundPool == null) {
            return;
        }
        if (soundId == -1) {
            //加载音频
            soundPool.load(context, soundRawRes, 1);
        } else {
            //已经加载过，直接播放
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    public void release() {
        if (soundPool == null) {
            return;
        }
        soundPool.release();
    }
}
