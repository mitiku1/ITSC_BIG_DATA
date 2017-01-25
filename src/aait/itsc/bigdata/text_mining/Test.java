package aait.itsc.bigdata.text_mining;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by mtk on 1/25/17.
 */
public class Test {
    public static void main(String[] args) {

        String [] names=new String[]{
                "D1","D2","D3","D4"
        };
        String[] documents=new String[]{
                "computer  information retrieval",
                "computer  retrieval",
                "information",
                "computer  information"
        };
        BooleanModel model=new BooleanModel(names,documents);
        String[] matches=model.searchDocuments(" information | retrieval & computer" );
        for(String m:matches){
            System.out.println(m);
        }
        System.out.println(model.getDocumentsWeightMatrix());
    }

}
