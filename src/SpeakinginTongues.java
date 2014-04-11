import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;


public class SpeakinginTongues {

	private static Scanner scanner;
	
	private static BufferedReader br;
	
	private static int numberOfTestCases;
	
	private static long start;
	
	private static HashMap<Character, Character> codeTranslateMap = new HashMap<Character, Character>();	
	
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
    
    public static void readStringFile(String fileName) throws IOException{
    	String sCurrentLine;    	    	
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) throws IOException {
    	start = System.currentTimeMillis();	
    	getInputStream("Input.txt");
    	readStringFile("D:\\Workspace\\Temp\\GoogleCodeJamPractice\\src\\Sample.txt");    	
    	numberOfTestCases = Integer.valueOf(br.readLine());
    	int testCaseNumber = 1;
    	while(testCaseNumber <= numberOfTestCases ){
    		String sentence1 = br.readLine();
    		String sentence2 = br.readLine();
    		if(sentence1 != null && sentence2 != null)
    			parseMapFromInput(sentence1, sentence2);	
    		testCaseNumber++;
    	}    	
    	codeTranslateMap.put('z', 'q');
    	if(codeTranslateMap.size() <26){
    		processRemainingAlphabets();
    	}
    	readStringFile("D:\\Workspace\\Temp\\GoogleCodeJamPractice\\src\\Input.txt");
    	numberOfTestCases = Integer.valueOf(br.readLine());
    	testCaseNumber = 1;
    	while(testCaseNumber <= numberOfTestCases ){
    		String sentence1 = br.readLine();    		
    		if(sentence1 != null && sentence1.length() != 0)
    			translateSentences(sentence1, testCaseNumber);	
    		testCaseNumber++;
    	}        	
    	long now = System.currentTimeMillis();
        System.out.println((now - start) / 1000.0);
	}

	private static void translateSentences(String sentence1, int testCaseNumber) {
		char[] charArray = sentence1.toCharArray();
		String translatedSentence = "";
		for(char c : charArray){
			if(c == ' '){
				translatedSentence = translatedSentence + ' ';
			}else{
				for(char k : codeTranslateMap.keySet()){
					if(codeTranslateMap.get(k).equals(c))
						translatedSentence = translatedSentence + k;	
				}				
			}			
		}
		System.out.println("Case #" + testCaseNumber + ": " + translatedSentence);
	}

	private static void processRemainingAlphabets() {
		// TODO Auto-generated method stub
		String all = "abcdefghijklmnopqrstuvwxyz";
		char[] allCharacters = all.toCharArray();
		String unusedInEnglish = "";
		String unusedInGooglerese = "";
		for(char c : allCharacters){
			if(!codeTranslateMap.containsKey(c)){
				unusedInEnglish = unusedInEnglish + c;
			}
		}
		for(char c : allCharacters){
			if(!codeTranslateMap.values().contains(c)){
				unusedInGooglerese = unusedInGooglerese + c;
			}
		}
		if(unusedInEnglish.length() == 1){
			codeTranslateMap.put(unusedInEnglish.charAt(0), unusedInGooglerese.charAt(0));
		}
	}

	private static void parseMapFromInput(String sentence1, String sentence2) {		
		for(int i=0; i<sentence1.length(); i++){
			if(sentence1.charAt(i) != ' ' && !codeTranslateMap.containsKey(sentence2.charAt(i))){
				codeTranslateMap.put(sentence2.charAt(i), sentence1.charAt(i));
			}
		}
	}               
}
