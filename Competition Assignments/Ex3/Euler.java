import java.util.*;


public class Euler {
    private int notEqualDegree;
    private HashMap<Integer, nodeData> myMap;
    private StringBuilder path_euler;
    private int src;
    private String ans_path_euler;


    public Euler(boolean[][] adj_matrix) {
        ans_path_euler = "";
        path_euler = new StringBuilder();
        myMap = new HashMap<>();
        notEqualDegree = 0;


        for (int i = 0; i < adj_matrix.length; i++) {
            myMap.put(i, new nodeData(i));
        }
        for (int i = 0; i < adj_matrix.length; i++) {
            for (int j = i + 1; j < adj_matrix[0].length; j++) {
                if (adj_matrix[i][j]) {
                    myMap.get(i).addNi(myMap.get(j));
                    myMap.get(j).addNi(myMap.get(i));
                }
            }
            if ((myMap.get(i).getNi().size() % 2) != 0 && notEqualDegree < 4 ) {
                notEqualDegree++;
                if(notEqualDegree == 3)
                    break;
                src = i;
            }
            else if(notEqualDegree > 2)
            {
                break;
            }
        }
        ans_path_euler = findPath();


    }

    public boolean has_euler_path() {
        if(ans_path_euler.length() == 0)
            return false;
        return notEqualDegree == 2 || notEqualDegree == 0;
    }
    public String euler_path()
    {
        if(has_euler_path())
        {
            return ans_path_euler;
        }
        else
        {
            return "";
        }
    }

    private String findPath() {

        Stack<Integer> stack = new Stack<>();
        HashSet<Integer> isConnected = new HashSet<>();
        Vector<Integer> vector = new Vector<>();
        stack.push(src);
        isConnected.add(src);
        nodeData nodeCurrent;
        nodeData adj;
        while (!stack.isEmpty() || !myMap.get(src).getNi().isEmpty()) {

            if (myMap.get(src).getNi().size() == 0) {
                vector.add(src);
                src = stack.pop();
            }
            else {
                stack.push(src);
                nodeCurrent = myMap.get(src);
                adj = myMap.get(src).getNi().iterator().next();
                myMap.get(src).getNi().remove(adj);
                myMap.get(adj.getKey()).getNi().remove(nodeCurrent);
                src = adj.getKey();
                isConnected.add(src);
            }
        }



//        for(int i = 1; i < path.size(); i++)
//            path_euler = path.elementAt(i) + "->" + path_euler;
//        path_euler =  path_euler + path.elementAt(0);
        if(isConnected.size() == myMap.size())
        {
            for(int i = vector.size()-1; i > 0; i--)
            {
                path_euler.append(vector.elementAt(i));
                path_euler.append("->");
            }
            path_euler.append(vector.elementAt(0));
            ans_path_euler = path_euler.toString();
        }
        else
        {
            ans_path_euler = "";
        }
        return ans_path_euler;

    }



}



class nodeData
{
    private HashMap<Integer,nodeData> mapNi;
    private int key;
    public nodeData(int key)
    {
        this.mapNi = new HashMap<>();
        this.key = key;
    }

    public int getKey() {
        return this.key; // return this key of this node
    }

    public void addNi(nodeData t) {
        mapNi.put(t.getKey(), t);
    }

    public Collection<nodeData> getNi() {
        return mapNi.values();
    }

}

