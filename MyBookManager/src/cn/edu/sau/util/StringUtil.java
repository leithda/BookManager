package cn.edu.sau.util;

/**
 * �ַ����жϹ�����
 * */
public class StringUtil {
	public static boolean isEmpty(String str) {
		if ("".equals(str) || str == null)
			return true;
		else
			return false;
	}

	public static boolean isNotEmpty(String str) {
		if ("".equals(str) || str == null)
			return false;
		else
			return true;
	}
}