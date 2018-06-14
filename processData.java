package processFile;

import java.util.*;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class processData {

	public static void main(String[] args) {
		try {
			File f = new File("C:\\Users\\26404\\Desktop\\ÐÅÏ¢¼ìË÷\\µ¹ÅÅË÷Òý\\data.txt");

			InputStreamReader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			int count = 0;
			line = br.readLine();
			while (line != null) {
				line = br.readLine();
				System.out.println("line" + count + line);
				if (line.length() == 1)
					count++;
				// File writename = new
				// File("C:\\Users\\26404\\eclipse-workspace\\luceneµ¹ÅÅË÷Òý\\data\\"
				// + count + ".txt");
				File writename = new File("C:\\Users\\26404\\Desktop\\ÐÅÏ¢¼ìË÷\\µ¹ÅÅË÷Òý\\data\\" + count + ".txt");
				if (!writename.exists())
					writename.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
				/*
				 * PrintWriter out = new PrintWriter( new BufferedWriter(new
				 * OutputStreamWriter(new FileOutputStream(writename,true),
				 * "UTF-8")));
				 */
				line = line.replace("/", "");
				line = line.replace("E", "");
				line = line.replace("M", "");
				line = line.replace(" ", "");
				line = line.replace("\r\n", "");
				line = line.replace("#", "");
				if (line.length() >= 1)
					out.write(line + "\r\n");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}
