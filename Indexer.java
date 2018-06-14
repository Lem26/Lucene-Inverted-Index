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
	private IndexWriter writer; // д����ʵ��
	// Version version = Version.LUCENE_6_6_0;

	/**
	 * ���췽�� ʵ����IndexWriter
	 * 
	 * @param indexDir
	 * @throws Exception
	 */
	public Indexer(String indexDir) throws Exception {

		// RAMDirectory dir = new RAMDirectory();
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		// Analyzer analyzer = new StandardAnalyzer(); // ��׼�ִ���
		String[] self_stop_words = { "��", "��", "��", "��", "0", "��", ",", "��" };
		CharArraySet cas = new CharArraySet(0, true);
		for (int i = 0; i < self_stop_words.length; i++) {
			cas.add(self_stop_words[i]);
		}
		// ����ϵͳĬ��ͣ�ô�
		Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
		while (itor.hasNext()) {
			cas.add(itor.next());
		}

		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(cas);
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(dir, iwc);
	}

	/**
	 * �ر�д����
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.close();
	}

	/**
	 * ����ָ��Ŀ¼�������ļ�
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
	 * ����ָ���ļ�
	 * 
	 * @param f
	 */
	private void indexFile(File f) throws Exception {
		System.out.println("�����ļ���" + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}

	/**
	 * ��ȡ�ĵ����ĵ���������ÿ���ֶ�
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
		String indexDir = "C:\\Users\\26404\\eclipse-workspace\\lucene��������\\WebContent\\Index\\";
		String dataDir = "C:\\Users\\26404\\eclipse-workspace\\lucene��������\\WebContent\\data\\";

		// String indexDir = "E:\\lucene\\index\\";
		// String dataDir = "C:\\Users\\26404\\Desktop\\��Ϣ����\\��������\\data\\";
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
		System.out.println("������" + numIndexed + " ���ļ� ������" + (end - start) + " ����");
	}

}
