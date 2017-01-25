import org.ejml.simple.SimpleMatrix;

import java.util.Set;

/**
 * Created by mehari with love! @1/25/17
 */
public class SearchResult {
    private SimpleMatrix documentTermMatrix;
    private Set<String> searchResult;

    public SearchResult(){

    }
    public SearchResult(SimpleMatrix documentTermMatrix, Set<String> searchResult){

        this.documentTermMatrix = documentTermMatrix;
        this.searchResult = searchResult;
    }

    public SimpleMatrix getDocumentTermMatrix() {
        return documentTermMatrix;
    }

    public void setDocumentTermMatrix(SimpleMatrix documentTermMatrix) {
        this.documentTermMatrix = documentTermMatrix;
    }

    public Set<String> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Set<String> searchResult) {
        this.searchResult = searchResult;
    }
}
