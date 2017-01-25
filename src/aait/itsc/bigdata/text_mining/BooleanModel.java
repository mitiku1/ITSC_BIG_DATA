package aait.itsc.bigdata.text_mining;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mtk on 1/25/17.
 */
public class BooleanModel {
    private SimpleMatrix documentsWeightMatrix;
    private String[] indexTerms;
    private String[] documetNames;
    private String[] documents;
    public BooleanModel(String[] documentNames, String[] documents){
        this.documents=documents;
        this.documetNames=documentNames;

        this.buildDocumentWeightMatrix();
    }
    public void buildDocumentWeightMatrix(){
        ArrayList<String> terms=new ArrayList<String>();

        for( String d:documents){
            String[] words= d.split(" ");
            for(String w:words){
                if(!terms.contains(w)){
                    terms.add(w);
                }
            }
        }
        indexTerms=terms.toArray(new String[terms.size()]);
        documentsWeightMatrix =new SimpleMatrix(this.documents.length, indexTerms.length);
        for(int i=0;i<documents.length;i++){
            for(int j=0;j<indexTerms.length;j++){
                if(documents[i].contains(indexTerms[j])){
                    documentsWeightMatrix.set(i, j, 1);
                }else{
                    documentsWeightMatrix.set(i, j, 0);
                }
            }
        }

    }
    private Predicate getFirstOperator(String query){
        for(char c:query.toCharArray()){
            if(c=='&'){
                return Predicate.AND;
            }else if(c=='|'){
                return Predicate.OR;
            }else if(c=='!'){
                return Predicate.NOT;
            }
        }
        return Predicate.NULL;
    }

    public Set<Integer> And(Set<Integer> matching,String query){
        System.out.println(query);
        System.out.println(matching);
        String q;
        Predicate predicate=getFirstOperator(query);
        if(predicate==Predicate.AND){
            q=query.substring(query.indexOf('&'));
        }else if(predicate==Predicate.OR){
            q=query.substring(query.indexOf('|'));
        }else{
            q=query;

        }
        q=q.trim();
        Set<Integer> matching2=new HashSet<Integer>();
        for(int i:matching){
            if(documents[i].contains(q)){
                matching2.add(i);
            }
        }
        if(predicate==Predicate.OR){
            matching2=Or(matching2,query.substring(query.indexOf("|")+1));
        }else if(predicate==Predicate.AND){
            matching2=And(matching2,query.substring(query.indexOf("&")+1));
        }
        return matching2;

    }
    public Set<Integer> Or(Set<Integer> matching,String query){
        String q;
        Predicate predicate=getFirstOperator(query);
        if(predicate==Predicate.AND){
            q=query.substring(query.indexOf('&'));
        }else if(predicate==Predicate.OR){
            q=query.substring(query.indexOf('|'));
        }else{
            q=query;

        }
        q=q.trim();
        Set<Integer> matching2=new HashSet<Integer>();
        for(int i:matching){
            matching2.add(i);
        }
        for(int i=0;i<documents.length;i++){
            if(documents[i].contains(q)){
                matching2.add(i);
            }
        }
        if(predicate==Predicate.OR){
            matching2=Or(matching2,query.substring(query.indexOf("|")+1));
        }else if(predicate==Predicate.AND){
            matching2=And(matching2,query.substring(query.indexOf("&")+1));
        }
        return matching2;

    }

    public String[] searchDocuments(String query){
        String q;
        Predicate predicate=getFirstOperator(query);
        if(predicate==Predicate.AND){
            q=query.substring(0,query.indexOf('&'));
        }else if(predicate==Predicate.OR){
            q=query.substring(0,query.indexOf('|'));
        }else{
            q=query;

        }
        q=q.replaceAll(" ","");
        Set<Integer> matching=new HashSet<Integer>();
        for(int i=0;i<documents.length;i++){
            if(documents[i].contains(q)){
                matching.add(i);
            }
        }

        if(predicate==Predicate.OR){
            matching=Or(matching, query.substring(query.indexOf("|")+1));
        }else if(predicate==Predicate.AND){
            matching=And(matching,query.substring(query.indexOf("&")+1));
        }

        int index=0;

        String[] output=new String[matching.size()];
        for(int m:matching){
            output[index]=documetNames[m];
            index++;
        }
        return output;


    }

    public SimpleMatrix getDocumentsWeightMatrix() {
        return documentsWeightMatrix;
    }

    public void setDocumentsWeightMatrix(SimpleMatrix documentsWeightMatrix) {
        this.documentsWeightMatrix = documentsWeightMatrix;
    }
}
