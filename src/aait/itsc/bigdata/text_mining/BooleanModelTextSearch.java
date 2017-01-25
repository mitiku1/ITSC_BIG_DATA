import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mehari with love! @1/25/17
 */
public class BooleanModelTextSearch {
    public static void main(String[] args) {

        String[] docs = {
                "Shipment of gold damaged in a fire.",
                "Delivery of silver arrived in a silver truck",
                "Shipment of gold arrived in a truck",
                "goodbye world."
        };

        String[] docNames = {"d1","d2","d3","d4"};
        String query ="silver | world";
        try {
            SearchResult result = new BooleanModelTextSearch().booleanModelSearchMulti(query,docNames ,docs);
            System.out.println(result.getDocumentTermMatrix());
            System.out.println(result.getSearchResult());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Search query from documents using boolean model
     * @param query , search query term
     * @param documentsContent , documents to search from document name : document content dictionary
     * @return set of result document names , empty set if no result found
     */
    private SearchResult booleanModelSearchMulti(String query, String[] documentNames, String[] documentsContent) throws Exception{

        if(documentNames.length!=documentsContent.length){
            throw new Exception("documentNames, documents content size mismatch exception");
        }

        SearchResult searchResult = new SearchResult();
        Set<String> result = new HashSet<>();
        ArrayList<String> terms = parseQuery(query);
        ArrayList<String> operators = parseOperator(query);

        if(operators.size()<terms.size()-1){
            throw new Exception("Invalid query,operators not specified.");
        }
        SimpleMatrix documentTermMatrix = new SimpleMatrix(documentsContent.length,terms.size());



        // build document term matrix
        for (int i = 0; i < documentNames.length; i++) {
            String doc = documentsContent[i];
            for (int j = 0; j < terms.size(); j++) {
                String term = terms.get(j);
                if(doc.contains(term)){
                    documentTermMatrix.set(i,j,1);
                }
            }
        }

        //do the search
        for (int i = 0; i <terms.size(); i++) {

            Set<String> tempSet = new HashSet<>();
            String term = terms.get(i);
            for (int k = 0; k < documentNames.length; k++) {
                String doc = documentsContent[k];
                if(doc.contains(term)){
                    tempSet.add(documentNames[k]);
                }
            }

            if(i>=1){
                String operator = operators.get(i-1);
                if(operator.equals("&")){
                    result.retainAll(tempSet);
                }
                if(operator.equals("|")){
                    result.addAll(tempSet);
                }
            }
            else {
                result.addAll(tempSet);

            }
        }
        searchResult.setSearchResult(result);
        searchResult.setDocumentTermMatrix(documentTermMatrix);
        return searchResult;
    }

    private ArrayList<String> parseOperator(String query) {
        ArrayList<String> result = new ArrayList<>();
        String[] terms = query.split(" ");
        for (String term :terms) {
            if(isOperator(term))
                result.add(term);
        }
        return result;
    }

    private ArrayList<String> parseQuery(String query){

        ArrayList<String> result = new ArrayList<>();
        String[] terms = query.split(" ");
        for (String term :terms) {
            if(!isOperator(term))
                result.add(term);
        }
        return result;
    }

    private boolean isOperator(String item){
        String operators = "&!|";
        return operators.contains(item);
    }
}
