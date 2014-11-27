package eLuoSiFangKuai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利用io流读写排行榜
 * 
 * @author 闫庚哲
 *
 */
public class ioTop {
	public static String FileName = "src/top.score";// 文件名
	public static int TopCount = 5;// 排行榜前几名
	File file = new File(FileName);// 排名文件
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	List<Map.Entry<String, Integer>> mapIds = new ArrayList<Map.Entry<String, Integer>>(
			map.entrySet());// 序列化

	/**
	 * 构造方法
	 */
	ioTop() {
		try {
			if (!file.exists()) {// 不存在就创建
				file.createNewFile();
				// System.out.println("创建排行榜文件完成");
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 获取排行榜信息
	 * 
	 * @return 排序好的链表，不涉及个数，有多少读多少
	 */
	List<Map.Entry<String, Integer>> get() {
		String name;// 存储名字
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));// 读取流
			map.clear();// 清空map
			while ((name = reader.readLine()) != null)
				// 判断是否读完
				map.put(name, Integer.parseInt(reader.readLine()));			
			reader.close();// 关闭流
			mapIds.clear();// 清空重置
			mapIds.addAll(map.entrySet());
			// 排序
			Collections.sort(mapIds,
					new Comparator<Map.Entry<String, Integer>>() {
						public int compare(Map.Entry<String, Integer> o1,
								Map.Entry<String, Integer> o2) {
							return (o2.getValue() - o1.getValue());
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return mapIds;
	}

	/**
	 * 添加新的分数
	 * 
	 * @param name
	 *            名字
	 * @param score
	 *            分数
	 * @return 1成功 -1失败
	 */
	int add(String name, int score) {
		get();
		if (!map.containsKey(name) || map.get(name) < score)
			map.put(name, score);
		mapIds.clear();// 清空重置
		mapIds.addAll(map.entrySet());

		// 排序
		Collections.sort(mapIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		// 保存
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			int over = mapIds.size() > TopCount ? TopCount : mapIds.size();
			for (int i = 0; i < over; i++) {
				writer.write(mapIds.get(i).getKey());
				writer.newLine();
				writer.write(mapIds.get(i).getValue().toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;// 失败
		}
		return 1;// 成功

	}
	/**
	 * 测试代码
	 */
	// ioTop io = new ioTop();
	// List<Map.Entry<String, Integer>> mapIds = io.get();
	// for(int i = 0 ; i<mapIds.size() ; i++)
	// System.out.println(mapIds.get(i).getKey()+","+mapIds.get(i).getValue().toString());
	//
	// io.add("a", 340);
}
