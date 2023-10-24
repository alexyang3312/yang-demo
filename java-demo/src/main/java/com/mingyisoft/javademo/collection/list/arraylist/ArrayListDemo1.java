package com.mingyisoft.javademo.collection.list.arraylist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo1 {
	public static void main(String[] args) {
		// 定义并创建一个ArrayList对象,list虽然允许存放不同类型的数据，但通常不建议这么做。
		List<Integer> list1 = new ArrayList<Integer>();
		// 判断list是否有元素
		System.out.println(list1.isEmpty());
		// 添加list元素
		// 允许有重复元素
		list1.add(5);
		for (int i = 0; i <= 5; i++) {
			list1.add(i);
		}
		// 按照插入顺序排序
		list1.add(2);
		// 允许多个null
		list1.add(null);
		list1.add(null);
		list1.add(null);

		System.out.println(list1);
		// 从list获取下标为x的元素
		System.out.println(list1.get(1));

		// 如果list中有此元素则返回该元素的下标(从0开始)，否则返回-1
		System.out.println("index==>" + list1.indexOf(2));
		// 如果list中有此元素则返回最后一次出现的该元素的下标(从0开始)，否则返回-1
		System.out.println("index==>" + list1.lastIndexOf(2));

		// list中元素个数
		System.out.println("~~~" + list1.size() + "~~~");

		// 删除list元素，允许按下标删和按元素删两种方式
		list1.remove(new Integer(3));

		// 指定位置添加元素，效率低，因为会把大家都往后移动。。。。
		list1.add(0, 9);
		System.out.println(list1);
		// 指定位置添加元素，直接覆盖
		list1.set(0, 8);
		System.out.println(list1);

		// list转数组
		Integer[] xxx = list1.toArray(new Integer[] {});
		for (Integer a : xxx) {
			System.out.println("array===>" + a);
		}

		// list添加另外一个list
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(10);
		list2.add(11);
		list2.add(12);
		list1.addAll(list2);
		System.out.println(list1);

		// 切分出一个子list，类似于String的substring()
		List<Integer> subList1 = list1.subList(0, 3);
		System.out.println("subList1=" + subList1);

		// list中是否包含某个元素
		if (list1.contains(2)) {
			System.out.println("2 yes");
		} else {
			System.out.println("2 no");
		}

		// 注意，此处我删掉了list里的一个元素，这个元素原本是属于list2的，然后containsAll就失败了。
		list1.remove(new Integer(5));
		if (list1.containsAll(list2)) {
			System.out.println("list2 yes");
		} else {
			System.out.println("list2 no");
		}
		
		List tempList1 = new ArrayList();
		tempList1.add(12);
		tempList1.add(10);
		tempList1.add(11);
		tempList1.add(2);
		tempList1.add(2);
		tempList1.add(4);
		tempList1.add(1);
		tempList1.add(0);
		tempList1.add(8);
		//顺序打乱是否能命中contains？能命中
		if (list1.containsAll(tempList1)) {
			System.out.println("tempList1 yes");
		} else {
			System.out.println("tempList1 no");
		}
		
		//超出了是否能命中contains？无法命中
		List tempList2 = new ArrayList();
		tempList2.add(12);
		tempList2.add(10);
		tempList2.add(11);
		tempList2.add(999);
		if (list1.containsAll(tempList2)) {
			System.out.println("tempList2 yes");
		} else {
			System.out.println("tempList2 no");
		}

		// 其实在list1中的list2已经不完整了，但是也能把剩余的全部干掉。
		list1.removeAll(list2);
		System.out.println(list1);
		list1.add(1);

		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(1);

		// 只保留参数与list1的交集，即1；list是3，2，1，参数是1，则保留1。如果list是1，参数是3，2，1则返回false，没有变化。
		System.out.println(list1.retainAll(list3));
		System.out.println(list1);

		// 清空list
		list1.clear();
		System.out.println(list1);

		// list的四种遍历方式
		List<String> list4 = new ArrayList<String>();
		list4.add("A");
		list4.add("B");
		list4.add("C");

		// 第1种使用foreach遍历List
		for (String str : list4) { //
			System.out.println(str);
		}

		// 第2种使用原始for
		for (int i = 0; i < list4.size(); i++) {
			System.out.println(list4.get(i));
		}

		// 第3种使用迭代器进行相关遍历
		Iterator<String> ite = list4.iterator();
		while (ite.hasNext()) {
			System.out.println(ite.next());
		}

		// 第4种使用JDK8的Lambda
		list4.forEach(item->System.out.println(item));
	}
}
