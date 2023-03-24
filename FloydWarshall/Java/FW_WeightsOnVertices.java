package FloydWarshall.Java;

public class FW_WeightsOnVertices {
    static final int INF = 9999999;

    public static void main(String[] args) {
        int[] values = {1,7,10,5};
        int[][] matrix = {
                {0,1,INF,1},
                {1,0,1,INF},
                {INF,1,0,1},
                {1,INF,1,0}
        };

        System.out.println("GIVEN ORIGINAL MATRIX: (* = infinity): ");
        print(matrix);
        FloydWarshall(matrix,values);
    }// END main()

    public static void FloydWarshall(int[][] matrix, int[] values) {

        System.out.println("SET WEIGHTS ON EDGE NEIGHBORS BY (Vi + Vj): ");
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == 0) {
                    matrix[i][j] = values[i];
                }
                else if(matrix[i][j] == 1) {
                    matrix[i][j] = values[i] + values[j];
                }
            }
        }
        print(matrix);

        System.out.println("FOLLOWING FLOYD WARSHALL ALGO (WITH WRONG WEIGHTS): ");
        for(int k = 0 ; k < matrix.length; k++) {
            for(int i = 0 ; i < matrix.length; i++) {
                for(int j = 0 ; j < matrix.length; j++) {
                    if(i != j) {
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
            }
        }
        print(matrix);

        System.out.println("FIXING WRONG WEIGHTS BY (V[SRC] + MATRIX[SRC][DST] + V[DST])/2 ");
        for(int i = 0 ; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(i != j) {
                    matrix[i][j] = (values[i] + matrix[i][j] + values[j])/2;
                }
            }
        }
        print(matrix);
    } // END OF FloydWarshall() METHOD

/************************************************************************************/
    public static void print(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == INF) {
                    System.out.print("*  ");
                }
                else if(matrix[i][j] < 10) {
                    System.out.print(matrix[i][j] + "  ");
                }
                else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
