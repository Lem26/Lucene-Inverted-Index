import java.io.*;

class StreamTest {
	public static void main(String[] args) throws IOException {
		/*
		 * 文件由ANSI转化为UTF-8 需要用到流InputStreamReader和OutputStreamWriter
		 * 这两个流有charset功能
		 */
		File folder = new File("C:\\Users\\26404\\Desktop\\信息检索\\倒排索引\\data\\");
		File[] filem = folder.listFiles();
		for (File srcFile : filem) {

			// File srcFile = new
			// File("C:\\Users\\26404\\Desktop\\信息检索\\倒排索引\\data\\171.txt");
			File destFile = new File(
					"C:\\Users\\26404\\eclipse-workspace\\lucene倒排索引\\WebContent\\data\\" + srcFile.getName());
			InputStreamReader isr = new InputStreamReader(new FileInputStream(srcFile), "GBK"); // ANSI编码
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(destFile), "UTF-8"); // 存为UTF-8

			int len = isr.read();
			while (-1 != len) {

				osw.write(len);
				len = isr.read();
			}
			// 刷新缓冲区的数据，强制写入目标文件
			osw.flush();
			osw.close();
			isr.close();
		}
	}
}