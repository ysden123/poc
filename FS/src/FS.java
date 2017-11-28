public class FS {
    private static final String COMMAND_ADD_DIR = "addDir";
    private static final String COMMAND_DELETE_DIR = "deleteDir";
    private static final String COMMAND_ADD_FILE = "addFile";
    private static final String COMMAND_DELETE_FILE = "deleteFile";
    private static final String COMMAND_SHOW_DIRSAND_FILE = "show";

    private Node tree = new Node("root", 0L, null, 0);

    void AddDir(String name, String color, long id, long parent_id) {
        Node newDir = new Node(name, id, color, parent_id);
        tree.addNode(newDir);
    }

    void DeleteDir(long id) {
        makeDeleteNode(id);
    }

    void AddFile(String name, long id, String color, long parent_id) {
        Node newFile = new Node(name, id, color, parent_id);
        tree.addNode(newFile);
    }

    void DeleteFile(long id) {
        makeDeleteNode(id);
    }

    private void makeDeleteNode(long id) {
        Node nodes[] = tree.getAllNodes();
        Node nodeToDelete = findNodeById(nodes, id);
        Node parentNode = (nodeToDelete.getParent_id() == 0) ? tree : findNodeById(nodes, nodeToDelete.getParent_id());
        parentNode.deleteNode(id);
    }

    void ShowDirsandFile() {
        Node nodes[] = tree.getAllNodes();
        for(Node node:nodes){
            if (node == null)
                break;
            System.out.println(node);
        }
    }

    private static Node findNodeById(Node allNodes[], long id) {
        for (Node node : allNodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        // todo handle case when no one node was found

        throw new RuntimeException("handle case when no one node was found");
    }


    /**
     * Command: command, id,color,parent_id
     *
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(args[0]);
        FS fs = new FS();
/*
        switch (args[0]) {
            case COMMAND_ADD_DIR:
                fs.AddDir(args[1], args[2], Long.parseLong(args[3]), Long.parseLong(args[4]));
                break;
            case COMMAND_DELETE_DIR:
                fs.DeleteDir(Long.parseLong(args[1]));
                break;
            case COMMAND_ADD_FILE:
                fs.AddFile(args[1], Long.parseLong(args[2]), args[3], Long.parseLong(args[4]));
                break;
            case COMMAND_DELETE_FILE:
                fs.DeleteFile(Long.parseLong(args[1]));
                break;
            case COMMAND_SHOW_DIRSAND_FILE:
                fs.ShowDirsandFile();
        }
*/
    }
}
