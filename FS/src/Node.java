import java.util.Arrays;

class Node {
    private final String name;
    private final long id;
    private final String color;
    private final Node children[];
    private final long parent_id;
    private int childSize;

    Node(String name, long id, String color, long parent_id) {
        this.name = name;
        this.id = id;
        this.color = color;
        this.parent_id = parent_id;
        children = new Node[255];
        childSize = 0;
    }

    void addNode(Node node) {
        if (node.getParent_id() == 0) {
            addNodeToParent(this, node);
        } else {
            // find parent
            Node allNodes[] = getAllNodes();
            for (Node aNode : allNodes) {
                if (aNode.getId() == node.getParent_id()) {
                    addNodeToParent(aNode, node);
                    break;
                }
            }
        }
    }

    private static void addNodeToParent(Node parent, Node node) {
        parent.children[parent.getChildSize()] = node;
        parent.setChildSize(parent.getChildSize() + 1);
    }

    void deleteNode(long id) {
        boolean startDelete = false;
        for (int i = 0; i < childSize - 1; ++i) {
            if (!startDelete) {
                if (children[i].getId() == id) {
                    startDelete = true;
                }
            }
            if (startDelete) {
                children[i] = children[i + 1];
            }
        }
        childSize--;
    }

    Node[] getAllNodes() {
        Node nodes[] = new Node[255];
        int pointer = 0;
        for (int i = 0; i < getChildSize(); ++i) {
            nodes[pointer++] = children[i];
            if (children[i].getChildSize() > 0) {
                Node receivedNodes[] = children[i].getAllNodes();
                for (Node node : receivedNodes) {
                    if (node == null)
                        break;
                    nodes[pointer++] = node;
                }
            }
        }
        return nodes;
    }

    String getName() {
        return name;
    }

    long getId() {
        return id;
    }

    String getColor() {
        return color;
    }

    Node[] getChilds() {
        return children;
    }

    long getParent_id() {
        return parent_id;
    }

    private int getChildSize() {
        return childSize;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", color='" + color + '\'' +
                ", parent_id=" + parent_id +
                ", childSize=" + childSize +
                '}';
    }

    private void setChildSize(int childSize) {
        this.childSize = childSize;
    }
}
