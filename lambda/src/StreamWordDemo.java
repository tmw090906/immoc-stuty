import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamWordDemo {

    public static void main(String[] args) throws IOException {


        // try-resource 关闭资源
        try (BufferedReader reader = new BufferedReader(
                new FileReader("E:\\dev\\workspace\\imooc-study\\lambda\\src\\webflux.txt"))) {
            /**
             * 思路为, 将每行转化为流
             * 将每行化为单词数组
             * 对每行的数组长度进行求和
             * 得到文件的单词数
             */
            long wordCount = reader.lines()
                    // 去掉每行前后空格
                    .map(String::trim)
                    // 过滤掉空行
                    .filter(s -> !s.isEmpty())
                    // 将每行用空格分割, 转化为数组
                    .map(s -> s.split(" "))
                    // 这里使用mapToInt 将 Stream 转为IntStream 是为了最后调用sum方法求和
                    .mapToInt(array -> array.length)
                    // 并行处理(以上操作都是无状态的)
                    .parallel()
                    .sum();

            System.out.println("单词数:" + wordCount);
        }


        // try-resource 关闭资源
        try (BufferedReader reader = new BufferedReader(
                new FileReader("E:\\dev\\workspace\\imooc-study\\lambda\\src\\webflux.txt"))) {

            Map<String, Long> counts = reader.lines()
                    // 去掉每行前后空格
                    .map(String::trim)
                    // 过滤掉空行
                    .filter(s -> !s.isEmpty())
                    // 将每行用空格分割, 转化为数组
                    .map(s -> s.split(" "))
                    // 将流中数组拉平为一个流
                    .flatMap(array -> Arrays.stream(array))
                    // 使用收集器, 将单词分类 ,并用counting()方法统计频率
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            System.out.println("单词出现次数:" + counts);


            LongSummaryStatistics summaryStatistics = counts.entrySet().stream()
                    .mapToLong(entry -> entry.getValue())
                    .summaryStatistics();

            System.out.println("统计信息:" + summaryStatistics);

            LongSummaryStatistics summaryStatistics1 = counts.values().stream()
                    .mapToLong(s -> s)
                    .summaryStatistics();

            System.out.println("统计信息2:" + summaryStatistics1);

        }

    }

}
