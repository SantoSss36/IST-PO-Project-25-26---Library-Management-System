package bci;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Creator implements Serializable {

    private List<Work> _works = new ArrayList<>();
    private String _name;

    public Creator(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void addToList(Work work) {
        _works.add(work);
        if (_works.size() > 1) {
            Collections.sort(_works);
        }
    }

    public List<Work> getWorkList() {
        return _works;
    }

    public void removeWorkFromCreator(Work work) {
        _works.remove(work);
    }
}
