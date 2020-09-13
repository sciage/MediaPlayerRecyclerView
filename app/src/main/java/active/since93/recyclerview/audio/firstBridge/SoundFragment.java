package active.since93.recyclerview.audio.firstBridge;

public class SoundFragment extends BaseSound {

    public SoundFragment(MusicFile workShop1)
    {
        super(workShop1);
    }

    @Override
    public void manufacture()
    {
        musicPojo.sendMusicPojo(new MusicFileBean("Hello harish", "", "", ""), 0);
    }
}
