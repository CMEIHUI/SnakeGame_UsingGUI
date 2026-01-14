package hellofx;

public class GameRules {

    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    public static Difficulty difficulty = Difficulty.NORMAL;

    // 是否可以穿墙
    public static boolean canWrap() {
        return difficulty == Difficulty.EASY;
    }

    // 游戏节奏（ms per step）
    public static double speed() {
        switch (difficulty) {
            case EASY: return 220;
            case HARD: return 90;
            default: return 140;
        }
    }

    // 是否启用障碍物
    public static boolean hasObstacles() {
        return difficulty != Difficulty.EASY;
    }

    // 得分倍率
    public static int scoreMultiplier() {
        return difficulty == Difficulty.HARD ? 2 : 1;
    }
}
