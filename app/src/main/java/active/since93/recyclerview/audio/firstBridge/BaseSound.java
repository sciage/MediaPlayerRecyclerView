package active.since93.recyclerview.audio.firstBridge;

public abstract class BaseSound {

    protected MusicFile musicPojo;

    protected BaseSound(MusicFile musicPojo)
    {
        this.musicPojo = musicPojo;
    }

    abstract public void manufacture();
}
