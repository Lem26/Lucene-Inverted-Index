import java.io.*;

class StreamTest {
	public static void main(String[] args) throws IOException {
		/*
		 * �ļ���ANSIת��ΪUTF-8 ��Ҫ�õ���InputStreamReader��OutputStreamWriter
		 * ����������charset����
		 */
		File folder = new File("C:\\Users\\26404\\Desktop\\��Ϣ����\\��������\\data\\");
		File[] filem = folder.listFiles();
		for (File srcFile : filem) {

			// File srcFile = new
			// File("C:\\Users\\26404\\Desktop\\��Ϣ����\\��������\\data\\171.txt");
			File destFile = new File(
					"C:\\Users\\26404\\eclipse-workspace\\lucene��������\\WebContent\\data\\" + srcFile.getName());
			InputStreamReader isr = new InputStreamReader(new FileInputStream(srcFile), "GBK"); // ANSI����
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8"); // ��ΪUTF-8

			int len = isr.read();
			while (-1 != len) {

				osw.write(len);
				len = isr.read();
			}
			// ˢ�»����������ݣ�ǿ��д��Ŀ���ļ�
			osw.flush();
			osw.close();
			isr.close();
		}
	}
}