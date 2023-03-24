import java.lang.reflect.Array;
import java.util.*;


public class AllDistances {
    private final int inf = 1000000;
    private int[] vertices_weights;
    private int[][] edges_weights;
    private String[][] path;
    public AllDistances(int[] vertices_weights, int[][] edges_weights)
    {
        this.vertices_weights = vertices_weights;
        this.edges_weights = edges_weights;
    }

    public int[][] distance_matrix()
    {
        int[][] mat1 = toEdgesWeights(edges_weights,vertices_weights);
        FWAlgorithm1(mat1);
        int[][] mat2 = edge2VertWeights(vertices_weights,mat1);
        return mat2;
    }

    public int distance(int u, int v)
    {
        --u;
        --v;
        if(v==u)
            return vertices_weights[v];

        return biDijkstra(u,v);
    }
//    public int distance1(int u, int v)
//    {
//        --u;
//        --v;
//        int a = dijkstra(u,v);
//        return a;
//    }

    public String path(int u, int v)
    {
        if(v==u)
            return u+"";
        --u;
        --v;
        String ans = dijkstra(u,v);
        if(ans.length() < 1)
            return "";
        return ans+"-"+(v+1);
    }

    private String dijkstra(int u, int v)
    {
        int[] dist = new int[vertices_weights.length];
        String[] ans = new String[vertices_weights.length];
        boolean[] visited = new boolean[vertices_weights.length];
        for (int i = 0; i < vertices_weights.length; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            ans[i] = "";
        }


        // Add source node to the priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>();


        // Distance to the source is 0
        pq.add(new Node(u, vertices_weights[u]));
        dist[u] = 0;
        while(!pq.isEmpty())
        {
            Node n = pq.remove();
            for(int i = 0; i < edges_weights.length; i++)
            {
                if(visited[i])
                    continue;
                if(n.node != i && edges_weights[n.node][i] != inf) {
                    if (edges_weights[n.node][i]+ dist[n.node]+vertices_weights[i] < dist[i]) {
                        dist[i] = dist[n.node] + edges_weights[n.node][i]+vertices_weights[i];
                        if(ans[n.node].length() == 0)
                        {
                            ans[i] = ""+(n.node+1) ;
                        }
                        else
                        {
                            ans[i] = ans[n.node] +"-"+ (n.node+1);
                        }
                        pq.add(new Node(i, dist[i]+vertices_weights[i]));
                    }
                }

            }
            visited[n.node] = true;
        }
        return ans[v];
    }

//    private int dijkstra(int u, int v)
//    {
//        int[] dist = new int[vertices_weights.length];
//        boolean[] visited = new boolean[vertices_weights.length];
//        for (int i = 0; i < vertices_weights.length; i++)
//            dist[i] = inf;
//
//        // Add source node to the priority queue
//        PriorityQueue<Node> pq = new PriorityQueue<>();
//
//
//        // Distance to the source is 0
//        pq.add(new Node(u, vertices_weights[u]));
//        dist[u] = vertices_weights[u];
//        while(!pq.isEmpty())
//        {
//            Node n = pq.remove();
//            if(n.node == 6)
//            {
//                System.out.println();
//            }
//            for(int i = 0; i < edges_weights.length; i++)
//            {
//                if(visited[i])
//                    continue;
//                if(n.node != i && edges_weights[n.node][i] != inf) {
//                    if (edges_weights[n.node][i]+ dist[n.node]+vertices_weights[i] < dist[i]) {
//                        dist[i] = dist[n.node] + edges_weights[n.node][i]+vertices_weights[i];
//                    }
//                    pq.add(new Node(i, dist[i]));
//                }
//
//            }
//            visited[n.node] = true;
//        }
//        return Math.min(dist[v], inf);
//    }

    private int biDijkstra(int u, int v)
    {
        int[] dist = new int[vertices_weights.length];
        int[] dist2 = new int[vertices_weights.length];
        boolean[] visited = new boolean[vertices_weights.length];
        boolean[] visited2 = new boolean[vertices_weights.length];
        for (int i = 0; i < vertices_weights.length; i++)
        {
            dist[i] = inf;
            dist2[i] = inf;
        }


        // Add source node to the priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>();
        PriorityQueue<Node> pq2 = new PriorityQueue<>();
        PriorityQueue<Node> pq3 = new PriorityQueue<>();

        // Distance to the source is 0
        pq.add(new Node(u, vertices_weights[u]));
        pq2.add(new Node(v, vertices_weights[v]));
        dist[u] = vertices_weights[u];
        dist2[v] = vertices_weights[v];
        ArrayList<Integer> list = new ArrayList<>();
        int min = inf;
        Node n2 = new Node();
        while(!pq.isEmpty())
        {

            Node n = pq.remove();
            if(!pq2.isEmpty())
            {
                n2 = pq2.remove();
            }
            if(n.node==n2.node)
                pq3.add(new Node(n.node,dist2[n2.node]+dist[n.node]-vertices_weights[n.node]));
            if(n.node==n2.node)
            {
                pq3.add(new Node(n.node,vertices_weights[u]+vertices_weights[v] + edges_weights[u][v]));
                return pq3.remove().cost;
            }
            for(int i = 0; i < edges_weights.length; i++)
            {
                if(!visited[i])
                {
                    if(n.node != i && edges_weights[n.node][i] != inf) {
                        if (edges_weights[n.node][i]+ dist[n.node]+vertices_weights[i] < dist[i]) {
                            dist[i] = dist[n.node] + edges_weights[n.node][i]+vertices_weights[i];
                        }
                        pq.add(new Node(i, dist[i]));
                    }
                }
                if(!visited2[i])
                {
                    if(n2.node != i && edges_weights[n2.node][i] != inf) {
                        if (edges_weights[n2.node][i]+ dist2[n2.node]+vertices_weights[i] < dist2[i]) {
                            dist2[i] = dist2[n2.node] + edges_weights[n2.node][i]+vertices_weights[i];
                        }
                        pq2.add(new Node(i, dist2[i]));
                    }
                }

            }
            visited[n.node] = true;
            visited2[n2.node] = true;
            if(visited[n2.node]&&visited2[n2.node])
                pq3.add(new Node(n2.node,dist[n2.node]+dist2[n2.node]-vertices_weights[n2.node]));
            if(visited[n.node]&&visited2[n.node])
                pq3.add(new Node(n.node,dist[n.node]+dist2[n.node]-vertices_weights[n.node]));

        }
        return Math.min(dist[v], inf);
    }



    private void FWAlgorithm1(int[][] mat){
        int n = mat.length;
        for (int k = 1; k<=n; k++){
            for (int i = 1; i<=n; i++){
                for (int j = 1; j<=n; j++){
                    if (mat[i-1][k-1]!=inf && mat[k-1][j-1]!=inf){
                        if (mat[i-1][j-1] > mat[i-1][k-1]+mat[k-1][j-1]){
                            mat[i-1][j-1] = mat[i-1][k-1]+mat[k-1][j-1];
                            //mat[i][j] = Math.min(mat[i][j],mat[i][k]+mat[k][j]);
                        }
                    }
                }
            }
        }
    }

//    private String[][] FWAlgorithm2(int[][] mat){
//        int n = mat.length;
//        // path matrix initialization
//        boolean[] visted = new boolean[vertices_weights.length];
//        String [][]pathMat = new String[n][n];
//        for (int i=0;i<n;i++){
//            for (int j=0;j<n;j++){
//
//                    if (mat[i][j] != inf) pathMat[i][j] = ""+(i+1);
//                    else  pathMat[i][j] = "";
//
//
//            }
//        }
//
//
//
//
//        // matrix building
//        for (int k = 0; k<n; k++){
//            for (int i = 0; i<n; i++){
//                for (int j = 0; j<n; j++){
//                    if (mat[i][k]!=inf && mat[k][j]!=inf){
//                        if (mat[i][j] > mat[i][k]+mat[k][j]){
//                            mat[i][j] = mat[i][k]+mat[k][j];
//
//                            pathMat[i][j] = pathMat[i][k]+"-"+pathMat[k][j];
//
//                            //mat[i][j] = Math.min(mat[i][j],mat[i][k]+mat[k][j]);
//                        }
//                    }
//                }
//            }
//        }
//
//        return pathMat;
//    }



    private int[][] toEdgesWeights(int[][] weightsEdges, int[] weightsVertices){
        int[][]ans = new int[weightsEdges.length][weightsEdges[0].length];
        for (int i = 1; i <= ans.length; i++) {
            for (int j = 1; j <= ans[0].length; j++) {
                if (weightsEdges[i-1][j-1] != inf){
                    ans[i-1][j-1] = 2*weightsEdges[i-1][j-1] + weightsVertices[i-1] + weightsVertices[j-1];
                }
                else ans[i-1][j-1] = inf;
            }
        }
        return ans;
    }

    private int[][] edge2VertWeights(int[] vertexWeight, int matWEdges[][]){
        int n = matWEdges.length;
        int[][] matWVert = new int[n][n];
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                if (i!=j && matWEdges[i-1][j-1]!=inf)
                    matWVert[i-1][j-1] = (matWEdges[i-1][j-1] + vertexWeight[i-1] + vertexWeight[j-1])/2;
                else if(i == j)
                {
                    matWVert[i-1][j-1] = vertices_weights[i-1];
                }
                else matWVert[i-1][j-1] = inf;
            }
        }
        return matWVert;
    }



}

class Node implements Comparable<Node> {
    public int node;
    public int cost;

    public Node()
    {
    }

    public Node(int node, int cost)
    {
        this.node = node;
        this.cost = cost;
    }


    @Override
    public int compareTo(Node o) {
        if (this.cost < o.cost)
            return -1;
        if (this.cost > o.cost)
            return 1;
        return 0;
    }
}







