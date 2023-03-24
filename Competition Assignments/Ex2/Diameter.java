import java.lang.reflect.Array;
import java.util.*;


public class Diameter {
    private HashMap<Integer, nodeData> myMap;
    private Queue<Integer> queue;
    private int diameterTree;
    public Diameter(boolean[][] adj_matrix)
    {
        myMap = new HashMap<>();
        this.queue = new LinkedList<>();
        for(int i = 0; i < adj_matrix.length; i++)
        {
            myMap.put(i,new nodeData(i));
        }
        for(int i = 0; i < adj_matrix.length; i++)
        {
            for(int j = i+1; j < adj_matrix[0].length; j++)
            {
                if(adj_matrix[i][j]) {
                    myMap.get(i).addNi(myMap.get(j));
                    myMap.get(j).addNi(myMap.get(i));
                }
            }
            if(myMap.get(i).getNi().size() == 1)
                queue.add(i);
        }
        diameterTree = fire();
    }


    public int get_diam()
    {
        return this.diameterTree;
    }




    private  int fire(){
        int counterOldLeaves = queue.size();
        int counterNewLeaves = 0;
        int counterFire = 0;

        while(!queue.isEmpty()) {
            int leafNode = queue.remove();
            int neighborNode = myMap.get(leafNode).getNi().iterator().next().getKey();
            --counterOldLeaves;
            myMap.get(leafNode).removeNi(myMap.get(neighborNode));
            myMap.get(neighborNode).removeNi(myMap.get(leafNode));
            myMap.remove(leafNode);
            if (myMap.get(neighborNode).getNi().size() == 1) {
                queue.add(neighborNode);
                ++counterNewLeaves;
            }
            if (counterOldLeaves == 0) {
                ++counterFire;
                if(myMap.size() <= 2)
                    break;
                counterOldLeaves = counterNewLeaves;
                counterNewLeaves = 0;
            }
        }
        if(myMap.size() == 2)
            return (counterFire+1)*2-1;
        else
            return counterFire*2;
    }





//    public static void main(String[] args)
//    {
//
//        boolean[][] mat = {
//                            {false,true,false,false,false,false},
//                            {true,false,true,true,false,false},
//                            {false,true,false,false,false,false},
//                            {false,true,false,false,true,true},
//                            {false,false,false,true,false,false},
//                            {false,false,false,true,false,false}
//        };
//        boolean b[][] = new boolean[40000-2][40000-2];
//        for(int i = 0; i < b.length; i++) {
//            for (int j = i; j < b.length; j++) {
//                if(i != j) {
//                        if(i+1 < b.length)
//                        {
//                            b[i][i+1] = true;
//                            b[i+1][i] = true;
//                        }
//
//                        if(i-1 >= 0)
//                        {
//                            b[i][i-1] = true;
//                            b[i-1][i] = true;
//                        }
//
//                }
//            }
//        }
////        for(int i = 0; i < b.length; i++)
////        {
////            System.out.println(Arrays.toString(b[i]));
////        }
//
//
//        double start_i = 0;
//        double end_i = 0;
//        double max = 0;
//        for(int i = 0; i < 1; i++)
//        {
//            start_i = new Date().getTime();
//            Diameter diameter = new Diameter(b);
//            System.out.println(diameter.get_diam());
//            System.out.println(diameter.get_diam());
//            System.out.println(diameter.get_diam());
//            end_i = new Date().getTime();
//
//        }
//        System.out.println("get_diam takes " +((end_i-start_i)/1000) +" sec\n");
//        start_i = 0;
//        end_i = 0;
//
//
//
//
//
//
//    }



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

    public void addNi(nodeData t)
    {
        mapNi.put(t.getKey(),t);
    }
    public void removeNi(nodeData t)
    {
        mapNi.remove(t.getKey());
    }


    public Collection<nodeData> getNi() {
        return mapNi.values();
    }

}
