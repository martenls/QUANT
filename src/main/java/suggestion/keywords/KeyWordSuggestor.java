package suggestion.keywords;

import java.io.*;
import java.util.*;

public class KeyWordSuggestor {
    private Map<String, Set<String>>stopWordMap;
    public KeyWordSuggestor(){
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/main/resources/stopwords/stopwords.properties"));
            stopWordMap = new HashMap<>();
            for(String lang:prop.stringPropertyNames()){
                loadStopwords(prop.getProperty(lang)).ifPresent(set -> stopWordMap.put(lang,(Set)set));
            }

        }
        catch (IOException ex) {
            System.out.println("QUANT properties not found use default settings");
        }
    }
    private Optional<Set<String>> loadStopwords(String filename){
        FileReader fileReader;
        try {
            Set<String>stopwords=new HashSet<>();
            fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String element;
            while ((element = bufferedReader.readLine()) != null)
                stopwords.add(element);
            bufferedReader.close();
            return Optional.of(stopwords);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: "+filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }
    public List<String> suggestKeywords(String question, String lang){
        Set<String> stopwords = stopWordMap.get(lang);
        String[] words = question.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        List<String>keywords = new ArrayList<>();
        for (String word : words)
            if (!stopwords.contains(word))
                keywords.add(word);
        return keywords;
    }
}
