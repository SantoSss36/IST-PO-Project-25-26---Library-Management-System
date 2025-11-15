package bci;

import java.util.ArrayList;
import java.util.List;

public class DVD extends Work {

    private Creator _director;
    private String _igac;

    public DVD (int workId, String title, List<Creator> director, int price, Category category , String igac, int copies) {
        super(workId, title, price, category, copies);

        _director = director.get(0);
        _igac = igac;
    }

    public String getIgac() {
        return _igac;
    }

    @Override
    public String getCreatorName() {
        return _director.getName();
    }

    @Override
    public List<Creator> getCreators() {
        List<Creator> list = new ArrayList<>();
        list.add(_director);

        return list;
    }

    @Override
    public String toString() {
        return "" + getWorkId() + " - " + getAvailableCopies() + " de " + getCopies() + " - DVD - " + getTitle() + " - " + getPrice() 
        + " - " + getCategoryString() + " - " + getCreatorName() + " - " + getIgac();
    }
}
