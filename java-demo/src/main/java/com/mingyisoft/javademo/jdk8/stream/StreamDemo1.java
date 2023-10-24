package com.mingyisoft.javademo.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo1 {
	public static void main(String[] args) {
		List<String> a = Arrays.asList("aaa","bb","xxx","xxx","cc","dd","abb");
		Stream<String> stream1 = a.stream();
		Stream<String> stream2 = Stream.of("aaa","bb","xxx","xxx","cc","dd","abb");
		Stream<String> stream3 = Stream.of("aaa","bb","xxx","xxx","cc","dd","abb");
		
		//过滤掉包含a的字符->为所有字符累加e->只返回前4个（skip是不返回前x个）-->排序-->去重-->遍历（最终操作，不可逆）
		stream1.filter(string -> string.indexOf("a")==-1)
		.map(string -> string + "e")
		.limit(4).sorted().distinct().forEach(xxx -> System.out.println(xxx+","));
		
		System.out.println("------");
		
		//第2个最终操作：count()
		System.out.println(stream2.filter(string -> string.indexOf("a")==-1)
				.map(string -> string + "e")
				.limit(4).sorted().distinct().count());
		
		System.out.println("------");
		
		//第3个最终操作：collect()
		List<String> b = stream3.filter(string -> string.indexOf("a")==-1)
		.map(string -> string + "e")
		.limit(4).sorted().distinct().collect(Collectors.toList());
		System.out.println(b);
		System.out.println("------");
		
		//在执行最终操作前，前面的逻辑是不执行的。
		
		
		//第4个最终操作：求最大值最小值
		Stream<Integer> stream4 = Stream.of(3,2,1,5,4);
//		System.out.println(stream4.max(Integer::compareTo).get());//(int1, int2) -> int1.compareTo(int2) 的简写，叫方法引用
		System.out.println(stream4.min(Integer::compareTo).get());
		//parallelStream并行流
		
//		Stream 的并行化也是 Java 8 的一大亮点。数据并行化是指将数据分成块，为每块数据分配单独的处理单元。这样可以充分利用多核 CPU 的优势。
//		并行化操作流只需改变一个方法调用。如果已经有一个 Stream 对象，调用它的 parallel() 方法就能让其拥有并行操作的能力。如果想从一个集合类创建一个流，调用 parallelStream() 就能立即获得一个拥有并行能力的流。
//		如果你去计算这段代码所花的时间，很可能比不加上 parallel() 方法花的时间更长。这是因为数据并行化会先对数据进行分块，然后对每块数据开辟线程进行运算，这些地方会花费额外的时间。并行化操作只有在 数据规模比较大 或者 数据的处理时间比较长 的时候才能体现出有事，所以并不是每个地方都需要让数据并行化，应该具体问题具体分析。

		System.out.println("----------------------------------------");
		IntStream intStream = IntStream.of(1, 3, 2, 5, 4, 6);
		println("of", intStream);

		//从11到16,不包含16
		intStream=IntStream.range(11,16);
		//从11到16,包含16
//        intStream=IntStream.rangeClosed(11,16);
		println("range", intStream);

		//包含指定的元素,add方法底层也是调用accept方法，然后返回this
		//返回的int流中的元素顺序与添加顺序一致
		intStream = IntStream.builder().add(23).add(22).add(21).build();
		println("builder", intStream);
	}

	private static void println(String start, IntStream intStream){
		System.out.println("println ->"+start);
		intStream.forEach(x->{
			System.out.println(x);
		});
	}
}
