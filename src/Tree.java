import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Tree {
    // We recommend attempting this class last, as it hasn't been scaffolded for your team.
    // Even if your team doesn't have time to implement this class, it is a useful exercise
    // to think about how you might split up the work to get the Tree and TreeMultiSet
    // implemented.
    private Integer root;
    private List<Tree> subtrees;
    private static final Random rand = new Random();

    // Constructors
    public Tree(Integer root, List<Tree> subtrees) {
        this.root = root;
        if (subtrees == null) {
            this.subtrees = new ArrayList<>();
        } else {
            this.subtrees = new ArrayList<>(subtrees);
        }
    }

    public Tree(Integer root) {
        this(root, new ArrayList<>());
    }

    public Tree(List<Tree> subtrees) {
        this(null, new ArrayList<>(subtrees));
    }

    public Tree() {
        this(null, new ArrayList<>());
    }

    public boolean is_empty() {
        return root == null;
    }

    public int size() {
        if (is_empty()) {
            return 0;
        }
        else {
            int size = 1;
            for (Tree subtree : subtrees) {
                size += subtree.size();
            }
            return size;
        }
    }

    public int count(int item) {
        if (is_empty()) {
            return 0;
        }
        else {
            int count = 0;
            if (root == item) {
                count +=1;
            }
            for (Tree subtree : subtrees) {
                count += subtree.count(item);
            }
            return count;
        }
    }

    @Override
    public String toString() {
        return strIndented(0);
    }

    private String strIndented(int depth) {
        if (is_empty()) {
            return "";
        }
        else {
            StringBuilder s = new StringBuilder();
            s.append("  ".repeat(depth)).append(root).append("\n");
            for (Tree subtree : subtrees) {
                s.append(subtree.strIndented(depth + 1));
            }
            return s.toString();
        }
    }

    public double average() {
        if (is_empty()) {
            return 0.0;
        }
        else {
            int[] result = average_Helper();
            return (double) result[0] / result[1];
        }
    }

    private int[] average_Helper() {
        if (is_empty()) {
            return new int[]{0,0};
        }
        else {
            int total = root;
            int size = 1;
            for (Tree subtree : subtrees) {
                int[] sub = subtree.average_Helper();
                total += sub[0];
                size += sub[1];
            }
            return new int[]{total, size};
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tree)) {
            return false;
        }
        Tree other = (Tree) obj;
        if (is_empty() && other.is_empty()) {
            return true;
        }
        else if (is_empty() || other.is_empty()) {
            return false;
        }
        else {
            return (root.equals(other.root) &&
                    subtrees.equals(other.subtrees));
        }
    }

    public boolean contains(int item) {
        if (is_empty()) {
            return false;
        }
        else if (root == item) {
            return true;
        }
        else {
            for (Tree subtree : subtrees) {
                if (subtree.contains(item)) {
                    return true;
                };
            }
            return false;
        }
    }

    public List<Integer> leaves() {
        List<Integer> result = new ArrayList<Integer>();
        if (is_empty()) {
            return result;
        }
        else if (subtrees.isEmpty()) {
            result.add(root);
        }
        else {
            for (Tree subtree : subtrees) {
                result.addAll(subtree.leaves());
            }
        }
        return result;
    }

    public boolean delete_item(int item) {
        if (is_empty()) {
            return false;
        }
        else if (root == item) {
            delete_root();
            return true;
        }
        else {
            for (Tree subtree : subtrees) {
                boolean result = subtree.delete_item(item);
                if (result && subtree.is_empty()) {
                    subtrees.remove(subtree);
                    return true;
                }
                else if (result) {
                    return true;
                }
            }
            return false;
        }
    }

    private void delete_root() {
        if (subtrees.isEmpty()) {
            root = null;
        }
        else {
            Tree chosen =  subtrees.remove(subtrees.size() - 1);
            root = chosen.root;
            subtrees.addAll(chosen.subtrees);
        }
    }

    private int extract_leaf() {
        if (subtrees.isEmpty()) {
            int old_root = root;
            root = null;
            return old_root;
        }
        else {
            int leaf = subtrees.get(0).extract_leaf();
            if (subtrees.get(0).is_empty()) {
                subtrees.remove(0);
            }
            return leaf;
        }
    }

    public void insert(int item) {
        if (is_empty()) {
            root = item;
        }
        else if (subtrees.isEmpty()) {
            subtrees.add(new Tree(item));
        }
        else {
            int choice = 1 + rand.nextInt(3);
            if (choice == 3) {
                subtrees.add(new Tree(item));
            }
            else {
                int subtree_index = rand.nextInt(subtrees.size());
                subtrees.get(subtree_index).insert(item);
            }
        }
    }

    public boolean insert_child(int item, int parent) {
        if (is_empty()) {
            return false;
        }
        else if (root == parent) {
            subtrees.add(new Tree(item));
            return true;
        }
        else {
            for (Tree subtree : subtrees) {
                if (subtree.insert_child(item, parent)) {
                    return true;
                }
            }
            return false;
        }
    }
}

