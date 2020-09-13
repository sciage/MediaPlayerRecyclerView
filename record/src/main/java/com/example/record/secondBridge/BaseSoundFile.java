package com.example.record.secondBridge;

public abstract class BaseSoundFile {

    protected SendListAudio sendListAudio;

    public BaseSoundFile(SendListAudio sendListAudio) {
        this.sendListAudio = sendListAudio;
    }

    abstract public void sendAudioList();
}
