import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;


public class ReverseWords {

	private static Scanner scanner;
	
	private static BufferedReader br;
	
	private static int numberOfTestCases;
	
	private static long start;
	
	// assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;            
    
    /**
     * Create an input stream from a file.
     */
    public void In(File file) {
        try {
            scanner = new Scanner(file, CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + file);
        }
    }
    
    /**
     * Create an input stream from a filename or web page name.
     */
    public static void getInputStream(String s) {
        try {
            // first try to read file from local file system
            File file = new File(s);
            if (file.exists()) {
                scanner = new Scanner(file, CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }       
           // next try for files included in jar
            URL url = StoreCredit.class.getResource(s);
            // or URL from web
            if (url == null) { url = new URL(s); }
            URLConnection site = url.openConnection();

            // in order to set User-Agent, replace above line with these two
            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
            // site.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + s);
        }
    }    
    
    
    public static void main(String[] args) throws IOException {
    	start = System.currentTimeMillis();	
    	getInputStream("Input.txt");
    	readStringFile("D:\\Workspace\\Temp\\GoogleCodeJamPractice\\src\\Input.txt");    	
    	numberOfTestCases = Integer.valueOf(br.readLine());
    	int testCaseNumber = 0;
    	while(true){
    		String sentence = br.readLine();
    		if(sentence == null){
    			break;
    		}
    		if(!sentence.isEmpty() || sentence.equals("")){
    			testCaseNumber++;
    			System.out.println("Case #" + testCaseNumber + ": " + reverseWordByWord(sentence));		
    		}
    	}    		
    	long now = System.currentTimeMillis();
        System.out.println((now - start) / 1000.0);
	}
    
    public static String reverseWordByWord(String str){
        str = str.trim();
        String[] wordArray = str.split(" ");
        String reverse  ="";
        for(int i=wordArray.length-1; i>=0; i--){
        	reverse = reverse + wordArray[i] + " ";
        }
        return reverse.trim();
    }
    
    public static void readStringFile(String fileName) throws IOException{
    	String sCurrentLine;    	    	
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		while ((sCurrentLine = br.readLine()) != null) {
//			System.out.println(sCurrentLine);
//		}
    }
}
