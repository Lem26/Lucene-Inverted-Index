package LuceneTest;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Iterator;

//import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
//import org.apache.lucene.util.Version;

public class Indexer {
	private IndexWriter writer; // 写索引实例
	// Version version = Version.LUCENE_6_6_0;

	/**
	 * 构造方法 实例化IndexWriter
	 * 
	 * @param indexDir
	 * @throws Exception
	 */
	public Indexer(String indexDir) throws Exception {

		// RAMDirectory dir = new RAMDirectory();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		// Analyzer analyzer = new StandardAnalyzer(); // 标准分词器
		String[] self_stop_words = { "的", "了", "呢", "，", "0", "：", ",", "是" };
		CharArraySet cas = new CharArraySet(0, true);
		for (int i = 0; i < self_stop_words.length; i++) {
			cas.add(self_stop_words[i]);
		}
		// 加入系统默认停用词
		Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
		while (itor.hasNext()) {
			cas.add(itor.next());
		}

		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(cas);
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(dir, iwc);
	}

	/**
	 * 关闭写索引
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.close();
	}

	/**
	 * 索引指定目录的所有文件
	 * 
	 * @param dataDir
	 * @throws Exception
	 */
	public int index(String dataDir) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File f : files) {
			indexFile(f);
		}
		return writer.numDocs();
	}

	/**
	 * 索引指定文件
	 * 
	 * @param f
	 */
	private void indexFile(File f) throws Exception {
		System.out.println("索引文件：" + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}

	/**
	 * 获取文档，文档里再设置每个字段
	 * 
	 * @param f
	 */
	private Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new Field("content", new FileReader(f), TextField.TYPE_NOT_STORED));
		doc.add(new Field("fileName", f.getName(), TextField.TYPE_STORED));
		doc.add(new TextField("fullPath", f.getCanonicalPath(), Field.Store.YES));
		return doc;
	}

	public static void main(String[] args) {
		String indexDir = "C:\\Users\\26404\\eclipse-workspace\\lucene倒排索引\\WebContent\\Index\\";
		String dataDir = "C:\\Users\\26404\\eclipse-workspace\\lucene倒排索引\\WebContent\\data\\";

		// String indexDir = "E:\\lucene\\index\\";
		// String dataDir = "C:\\Users\\26404\\Desktop\\信息检索\\倒排索引\\data\\";
		// String dataDir = "C:\\s";
		Indexer indexer = null;
		int numIndexed = 0;
		long start = System.currentTimeMillis();
		SearchTest st = new SearchTest();

		try {
			indexer = new Indexer(indexDir);
			// indexer = new Indexer();
			numIndexed = indexer.index(dataDir);
			st.setUp();
			st.testTermQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				indexer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("索引：" + numIndexed + " 个文件 花费了" + (end - start) + " 毫秒");
	}

}
