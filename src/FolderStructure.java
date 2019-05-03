import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
    Class to return a tree containing all writable folders in readable folders
    Assumption: Folder names are unique
 */
public class FolderStructure {

    public static void main(String[] args) {
        //provide files as arguments to java program
        List<String> readableFolders = Arrays.asList("/var/lib/jenkins","/usr/home","/opt/config","/usr/home/documents"
                ,"/usr/home/environment","/usr/home/images","/usr/home/audio","/app/install");
        List<String> writableFolders = Arrays.asList("documents", "images", "audio","video","install");
        TreeItem treeItem = constructWriteAccessTree(readableFolders,writableFolders);
    }

    private static TreeItem constructWriteAccessTree(List<String> readableFolders, List<String> writableFolders) {
        TreeItem readTree = new TreeItem("/");
        constructReadTree(readableFolders,readTree);
        constructWriteOnlyTree(readTree, writableFolders);
        return readTree;
    }

    private static void constructWriteOnlyTree(TreeItem readTree, List<String> writableFolders) {
        searchForWritableFolder(readTree, writableFolders);
        removeUnwritableFolders(readTree);
    }
    private static void removeUnwritableFolders(TreeItem treeItem) {
        for(int i = 0; i<treeItem.children.size();i++) {
            TreeItem treeItemChildren = treeItem.children.get(i);
            if(treeItemChildren.isWritePath == false) {
                treeItem.children.remove(i);
                i--;
            }
            else
                removeUnwritableFolders(treeItemChildren);
        }
    }

    private static boolean searchForWritableFolder(TreeItem treeItem, List<String> writableFolders) {
        boolean isOneWritable = false;
        for(TreeItem treeItemChildren: treeItem.children) {
            if(writableFolders.contains(treeItemChildren.name))
            {
                treeItemChildren.isWritePath = true;
                isOneWritable = true;
            }
            isOneWritable = searchForWritableFolder(treeItemChildren, writableFolders) || isOneWritable;
        }
        if(isOneWritable) {
            treeItem.isWritePath = true;
            return true;
        }
        return false;
    }

    public static void constructReadTree(List<String> readableFolders, TreeItem root) {
        for(String path: readableFolders){
            String[] folders = path.split("/");
            TreeItem curItem = root;
            for(int i = 1; i< folders.length;i++){
                final String folderName = folders[i];
                Optional<TreeItem> t = curItem.children.stream().filter(f -> f.name.equals(folderName)).findFirst();
                if(t.isPresent())
                {
                    curItem = t.get();
                }else {
                    TreeItem item  = new TreeItem(folders[i]);
                    curItem.children.add(item);
                    curItem = item;
                }
            }
        }
    }
}
