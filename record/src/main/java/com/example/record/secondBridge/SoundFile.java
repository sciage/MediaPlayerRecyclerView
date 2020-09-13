package com.example.record.secondBridge;

import com.example.record.audio.AudioStatus;

import java.util.List;

public class SoundFile extends BaseSoundFile {
    List<AudioStatus> audio;
    List<String> contactList;

    public SoundFile(SendListAudio sendListAudio, List<AudioStatus> audio, List<String> contactList) {
        super(sendListAudio);
        this.audio = audio;
        this.contactList = contactList;
    }

    public void sendAudioList(){
        sendListAudio.sendAudioList(audio, contactList);
    }


}
