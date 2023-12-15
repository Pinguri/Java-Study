import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogSlice {
	private static final String LOG_FILE_PATH = "C:\\Users\\사용자명\\Desktop\\노원\\catalina.out";
	private static final int LOG_ROWNUM = 1000000;
	
	public static void main(String[] args) throws Exception {
		logFileRead();
	}
	
	private static void logFileRead() throws Exception {
		BufferedReader br = null;
		BufferedWriter bw = null;
		//FileWriter fw = null;
		
		try {
			Reader reader = new InputStreamReader(new FileInputStream(LOG_FILE_PATH), "UTF-8");
			br = new BufferedReader(reader);
			int nFileCnt = 1;
			//String fileName = "C:\\Users\\사용자명\\Desktop\\kosis\\kosis_" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".txt";
			String fileName = "C:\\Users\\사용자명\\Desktop\\노원\\노원" + nFileCnt + ".txt";
			nFileCnt++;
			String s = "";
			int nRowCnt = 0;
			//File file = new File(fileName);
			//fw = new FileWriter(file, true);
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
			
			while((s = br.readLine()) != null) {
				nRowCnt++;
				
				System.out.println(s);
				
				/*fw.write(s);
				fw.write("\n");
				fw.flush();*/
				bw.write(s);
				bw.write("\n");
				bw.flush();
				
				if(nRowCnt == LOG_ROWNUM) {
					//if(fw != null) fw.close();
					if(bw != null) bw.close();
					
					nRowCnt = 0;
					//fileName = "C:\\Users\\사용자명\\Desktop\\kosis\\kosis_" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".txt";
					fileName = "C:\\Users\\사용자명\\Desktop\\노원\\노원" + nFileCnt + ".txt";
					nFileCnt++;
					//System.out.println(nFileCnt);
					//file = new File(fileName);
					//fw = new FileWriter(file, true);
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null) br.close();
			//if(fw != null) fw.close();
			if(bw != null) bw.close();
		}
	}
}
