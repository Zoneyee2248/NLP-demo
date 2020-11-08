package test;

import java.util.*;

import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class Test {

	private final static String NON_COMPLIANT_REGEX = "[^\u4e00-\u9fa5a-zA-Z0-9]";
	private final static String SPACE_REGEX = "[\\s]+";
	private final static String EMPTY = " ";

	public static String[] strTostrArray(String str) {
		str = str.toLowerCase();
		str = str.replaceAll(NON_COMPLIANT_REGEX, EMPTY).replaceAll(SPACE_REGEX, EMPTY);
		str = NLPTokenizer.analyze(str).toStringWithoutLabels();
		String[] strs = str.split(EMPTY);
		return strs;
	}

	public static void countWord(String[] strs) {
		Set<String> maxStrHash = new HashSet<String>();
		HashMap<String, Integer> strHash = new HashMap<String, Integer>();
		countWordValue(strs, strHash);
		Set<java.util.Map.Entry<String, Integer>> entrySet = strHash.entrySet();
		String maxStr = null;
		int maxValue = 0;
		for (java.util.Map.Entry<String, Integer> e : entrySet) {
			maxValue = countMaxValue(e, maxStr, maxValue, maxStrHash);
		}
		System.out.println("出現最多的單詞是：" + maxStrHash + "，出現了" + maxValue + "次");
	}
	
	public static void countWordValue(String[] strs, HashMap<String, Integer> strHash) {
		Integer in = null;
		for (String s : strs) {
			in = strHash.put(s, 1);
			if (in != null) {
				strHash.put(s, in + 1);
			}
		}
	}

	public static int countMaxValue(java.util.Map.Entry<String, Integer> e, String maxStr, int maxValue,
			Set<String> maxStrHash) {
		String key = e.getKey();
		Integer value = e.getValue();
		if (value > maxValue) {
			// 第1次 先加到集合裡
			if (maxValue == 0) {
				maxStrHash.add(key);
			}
			// 刪掉出現次數較小的單詞 並加入新單詞
			if (!key.equals(maxStr)) {
				maxStrHash.clear();
				maxStrHash.add(key);
			}
			maxValue = value;
			maxStr = key;
		} else if (value == maxValue) {
			// 次數相同就加入
			maxStrHash.add(key);
		}
		System.out.println("單詞：" + key + "，出現了" + value + "次");
		return maxValue;
	}

	public static void main(String[] args) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
			System.out.println("本程式為分析單詞工具\n請輸入一行有含中英文的句子：");
			String str = scan.nextLine();
			System.out.println("輸入的句子為：" + str);
			String[] strs = strTostrArray(str);
			countWord(strs);
		} catch (Exception e) {

		} finally {
			if (scan != null) {
				scan.close();
			}
		}
	}
}