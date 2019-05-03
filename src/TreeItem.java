import java.util.ArrayList;
import java.util.List;

public class TreeItem {
    public String name;
    public List<TreeItem> children;
    public boolean isWritePath;

    public TreeItem(String s) {
        this.name = s;
        children = new ArrayList<>();
    }
/*
    public void addChildren(String s){
        TreeItem ch = new TreeItem(s);
        this.children.add(ch);
    }
    */
}
