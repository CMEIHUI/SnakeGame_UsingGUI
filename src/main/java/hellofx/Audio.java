package hellofx;

public class Audio {
    private static double volume = 0.6; // 0..1

    public static double getVolume() { return volume; }
    public static void setVolume(double v) { volume = Math.max(0, Math.min(1, v)); }

    public static void playSound(String name) {
        // 简化：只打印。进一步可以改为 AudioClip/MediaPlayer 播放文件
        System.out.println("[SOUND] " + name + " (vol=" + String.format("%.2f", volume) + ")");
    }
}
