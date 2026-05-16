public class test {

    /**
     * 计算 n 个人中没有任何两人生日相同的概率
     * @param n 人数
     * @return 生日全不相同的概率（0~1之间的小数）
     */
    public static double calculateNoSameBirthdayProbability(int n) {
        // 总天数，不考虑闰年
        final int DAYS = 365;
        // 初始概率：第一个人生日任意，概率为1
        double probability = 1.0;

        // 循环计算每个人的概率并相乘
        for (int i = 1; i < n; i++) {
            // 第i+1个人与前面所有人不同的概率
            probability *= (DAYS - i) / (double) DAYS;
        }

        return probability;
    }

    public static void main(String[] args) {
        // 计算50个人的情况
        int peopleCount = 23;
        double result = calculateNoSameBirthdayProbability(peopleCount);

        // 输出结果
        System.out.println(peopleCount + "个人中没有任何一对生日相同的概率：");
        System.out.printf("小数形式：%.4f%n", result);
        System.out.printf("百分比形式：%.5f%%", result * 100);
    }
}
