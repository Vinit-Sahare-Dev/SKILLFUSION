package beans;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
public class SentimentAnalyzer {
    private final StanfordCoreNLP pipeline;
    private int score;
    private static final Map<String, String> NEGATION_MAP = new HashMap<>();

    
    public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public SentimentAnalyzer() {
        // Configure the NLP pipeline with sentiment analysis
        Properties props = PropertiesUtils.asProperties(
                "annotators", "tokenize, ssplit, pos, lemma, parse, sentiment",
                "tokenize.language", "en");
        this.pipeline = new StanfordCoreNLP(props);
    }
	
    static {
        NEGATION_MAP.put("not bad", "good");
        NEGATION_MAP.put("not worst", "decent");
        NEGATION_MAP.put("not terrible", "acceptable");
        NEGATION_MAP.put("not horrible", "tolerable");
        NEGATION_MAP.put("not boring", "interesting");
        NEGATION_MAP.put("not difficult", "easy");
        NEGATION_MAP.put("not expensive", "affordable");
        NEGATION_MAP.put("not slow", "fast");
        NEGATION_MAP.put("not weak", "strong");
        NEGATION_MAP.put("not useless", "somewhat useful");
    }

    public static String handleNegations(String text) {
        for (Map.Entry<String, String> entry : NEGATION_MAP.entrySet()) {
            if (text.toLowerCase().contains(entry.getKey())) {
                text = text.toLowerCase().replace(entry.getKey(), entry.getValue());
            }
        }
        return text;
    }

    public String analyzeSentiment(String text) {
    	text=handleNegations(text);
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        int totalScore = 0;
        int sentenceCount = 0;

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            totalScore += getSentimentScore(sentiment);
            sentenceCount++;
        }

        int avgScore = sentenceCount > 0 ? totalScore / sentenceCount : 0;
        score=avgScore;
        return classifySentiment(avgScore);
    }

    private int getSentimentScore(String sentiment) {
        return switch (sentiment.toLowerCase()) {
            case "very negative" -> -2;
            case "negative" -> -1;
            case "neutral" -> 0;
            case "positive" -> 1;
            case "very positive" -> 2;
            default -> 0;
        };
    }

    private String classifySentiment(int score) {
        return switch (score) {
            case -2, -1 -> "Negative";
            case 1, 2 -> "Positive";
            default -> "Neutral";
        };
    }

    public static void main(String[] args) {
        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        
        String text1 = "I love this amazing product!";
        System.out.println("Sentiment: " + analyzer.analyzeSentiment(text1));

        String text2 = "This is the worst experience ever.";
        System.out.println("Sentiment: " + analyzer.analyzeSentiment(text2));

        String text3 = "It's an okay product, nothing special.";
        System.out.println("Sentiment: " + analyzer.analyzeSentiment(text3));
    }
}

