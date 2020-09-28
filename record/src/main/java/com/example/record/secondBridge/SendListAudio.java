package com.example.record.secondBridge;

import com.example.record.audio.AudioStatus;

import java.util.List;

public interface SendListAudio {
    void sendAudioList(List<AudioStatus> audio, List<String> contactList);

    void sendAudioUrl(List<AudioStatus> audio);
}
