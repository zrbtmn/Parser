package Parser;

import Parser.UI.ParameterPair;

import java.util.List;

public class Section {

    private String sectionName;
    private List<ParameterPair> parameters;

    Section(String sectionName, List<ParameterPair> parameters){
        this.sectionName = sectionName;
        this.parameters = parameters;
    }

    public List<ParameterPair> getParameters(){
        return parameters;
    }

    public String getSectionName(){
        return sectionName;
    }
}