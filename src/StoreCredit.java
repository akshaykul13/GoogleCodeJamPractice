import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;


public class StoreCredit {
	private static Scanner scanner;
	
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
    
    public static void main(String[] args) {
    	start = System.currentTimeMillis();	
    	getInputStream("Input.txt");
    	numberOfTestCases = Integer.valueOf(scanner.nextInt());
    	for(int i=0; i<numberOfTestCases; i++){
    		findSuitableItems(i+1);
    	}
    	long now = System.currentTimeMillis();
        System.out.println((now - start) / 1000.0);
	}

	private static void findSuitableItems(int testCaseNumber) {		
		int creditAvailable = Integer.valueOf(scanner.nextInt());
		int numberOfItems = Integer.valueOf(scanner.nextInt());
		int[] itemPrices = new int[numberOfItems];
		for(int i=0; i<numberOfItems; i++){
			itemPrices[i] = Integer.valueOf(scanner.nextInt());
		}
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); 
		for(int k=0; k<itemPrices.length; k++){
			if(map.containsKey(creditAvailable - itemPrices[k])){
				if(k < map.get(creditAvailable - itemPrices[k])){
					System.out.println("Case #" + testCaseNumber + ": " + (k+1) + " " + (map.get(creditAvailable - itemPrices[k])+1));	
					return;
				}									
				else{
					System.out.println("Case #" + testCaseNumber + ": " + (map.get(creditAvailable - itemPrices[k])+1) + " " + (k+1));
					return;
				}					
			}else{
				map.put(itemPrices[k], k);
			}
		}
	}
}
