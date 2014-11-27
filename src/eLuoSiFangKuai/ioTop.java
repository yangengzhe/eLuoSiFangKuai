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
 * ����io����д���а�
 * 
 * @author �Ƹ���
 *
 */
public class ioTop {
	public static String FileName = "src/top.score";// �ļ���
	public static int TopCount = 5;// ���а�ǰ����
	File file = new File(FileName);// �����ļ�
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	List<Map.Entry<String, Integer>> mapIds = new ArrayList<Map.Entry<String, Integer>>(
			map.entrySet());// ���л�

	/**
	 * ���췽��
	 */
	ioTop() {
		try {
			if (!file.exists()) {// �����ھʹ���
				file.createNewFile();
				// System.out.println("�������а��ļ����");
			}
		} catch (IOException e) {
		}
	}

	/**
	 * ��ȡ���а���Ϣ
	 * 
	 * @return ����õ��������漰�������ж��ٶ�����
	 */
	List<Map.Entry<String, Integer>> get() {
		String name;// �洢����
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));// ��ȡ��
			map.clear();// ���map
			while ((name = reader.readLine()) != null)
				// �ж��Ƿ����
				map.put(name, Integer.parseInt(reader.readLine()));			
			reader.close();// �ر���
			mapIds.clear();// �������
			mapIds.addAll(map.entrySet());
			// ����
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
	 * ����µķ���
	 * 
	 * @param name
	 *            ����
	 * @param score
	 *            ����
	 * @return 1�ɹ� -1ʧ��
	 */
	int add(String name, int score) {
		get();
		if (!map.containsKey(name) || map.get(name) < score)
			map.put(name, score);
		mapIds.clear();// �������
		mapIds.addAll(map.entrySet());

		// ����
		Collections.sort(mapIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		// ����
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
			return -1;// ʧ��
		}
		return 1;// �ɹ�

	}
	/**
	 * ���Դ���
	 */
	// ioTop io = new ioTop();
	// List<Map.Entry<String, Integer>> mapIds = io.get();
	// for(int i = 0 ; i<mapIds.size() ; i++)
	// System.out.println(mapIds.get(i).getKey()+","+mapIds.get(i).getValue().toString());
	//
	// io.add("a", 340);
}
