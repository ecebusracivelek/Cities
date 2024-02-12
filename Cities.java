import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Cities {
    LinearProbingHash<String> vertexTable;
    String[][]adjacencyList;
    private int vertices;
    private int edges;
    public Cities(int size) {
        vertexTable=new LinearProbingHash<>(size);
        adjacencyList=new String[size][1000];
        this.vertices=size;
        this.edges=0;
    }

    public int getEdges() {
        return edges;
    }

    public int getVertices() {
        return vertices;
    }
     public void readGraphFromFile(String filename) {
        try (BufferedReader br=new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line=br.readLine())!= null) {
                int arrowIndex = line.indexOf("->");
                if (arrowIndex!=-1) {
                    String vertex = line.substring(0, arrowIndex);
                    String edgesStr = line.substring(arrowIndex + 2);
                    String[]edges = findEdges(edgesStr);
                    vertexTable.insert(vertex);
                    this.vertices++;
                    int vertexIndex = vertexTable.hash(vertex);
                    adjacencyList[vertexIndex] = edges;
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
     private String[]findEdges(String edge) {
        int count=1;
        for (int i=0; i<edge.length();i++) {
            if (edge.charAt(i)==',') {
                count++;
            }
        }
        String[]edges = new String[count];
        int start=0;
        int index=0;
        for (int i=0; i<edge.length();i++) {
            if (edge.charAt(i)==',') {
                edges[index++]=edge.substring(start, i);
                start=i + 1;
            }
        }
        edges[index]=edge.substring(start);
        return edges;
    }
     
     
  public boolean IsThereAPath(String v1, String v2) {
        int start=vertexTable.hash(v1);
        int end=vertexTable.hash(v2);
        boolean[]visited = new boolean[vertexTable.M]; //visited vertices
        return IsThereAPathDFS(start, end, visited);
    }

    private boolean IsThereAPathDFS(int current, int end, boolean[] visited) {
        if(current==end) {
            return true;
        }
        visited[current]=true;
        if(adjacencyList[current]!=null){
            for (String neighbor:adjacencyList[current]){
                int neighborIndex = vertexTable.hash(neighbor);
                if(!visited[neighborIndex]){
                    if(IsThereAPathDFS(neighborIndex, end, visited)){
                        return true;
                    }
                }
            }
        }

        return false;
    }
   

     public void BFSfromTo(String v1, String v2) {
        int start=vertexTable.hash(v1);
        int end=vertexTable.hash(v2);

        boolean[]visited = new boolean[vertexTable.M];
        int[]parent = new int[vertexTable.M];
        LinkedList<Integer> path = new LinkedList<>();

        bfs(start, end, visited, parent, path);

        printPath(v1, v2, parent, path);
    }


    private void bfs(int start, int end, boolean[] visited, int[] parent, LinkedList<Integer> path) {
        int[]queue=new int[vertexTable.M];
        int first=0,last=0;
        visited[start]=true;
        queue[last++]=start;
        while (first!=last) {
            int current=queue[first++];
            if(current==end) {
                return; 
            }
            if (adjacencyList[current]!=null) {
                for(String neighbor:adjacencyList[current]) {
                    int neighborIndex = vertexTable.hash(neighbor);
                    if (!visited[neighborIndex]) {
                        queue[last++] = neighborIndex;
                        visited[neighborIndex] = true;
                        parent[neighborIndex] = current;
                    }
                }
            }
        }
    }

    
    public void DFSfromTo(String v1, String v2) {
        int start=vertexTable.hash(v1);
        int end=vertexTable.hash(v2);

        boolean[]visited=new boolean[vertexTable.M];
        int[]parent=new int[vertexTable.M];
        LinkedList<Integer> path=new LinkedList<>();
        dfs(start,end,visited,parent,path);
        printPath(v1,v2,parent,path);
    }

    private void dfs(int current,int end,boolean[] visited,int[] parent,LinkedList<Integer> path){
        visited[current]=true;
        if (current==end) {
           return; 
        }
        if(adjacencyList[current]!=null) {
            for(String neighbor:adjacencyList[current]) {
                int neighborIndex=vertexTable.hash(neighbor);
                if(!visited[neighborIndex]) {
                    parent[neighborIndex]=current;
                    dfs(neighborIndex,end,visited,parent,path);
                    break; 
                }
            }
        }
    }
     
    private void printPath(String v1, String v2, int[] parent, LinkedList<Integer> path) {
        int end=vertexTable.hash(v2);
        System.out.print("Path from "+ v1 + " to " + v2 + ": ");
        if (parent[end]==-1) {
            System.out.println("No path exists.");
            return;
        }
        StringBuilder pathStringBuilder=new StringBuilder();
        int current=end;
        while (current!=-1) {
            current=parent[current];
        }
        System.out.println(pathStringBuilder.toString().trim());
    }


 public int WhatIsShortestPathLength(String v1, String v2) {
        int start=vertexTable.hash(v1);
        int end=vertexTable.hash(v2);
        boolean[]visited=new boolean[vertexTable.M];
        int[]parent=new int[vertexTable.M];
        LinkedList<Integer> path=new LinkedList<>();
        bfs(start,end,visited,parent,path);
        int length=0;
        int current=end;
        while (current!=start) {
            if (parent[current]==-1) {
                System.out.println(v1+ " --x-- " + v2);
                return -1;
            }
            current=parent[current];
            length++;
        }
        return length;
    }
    public int NumberOfSimplePaths(String v1, String v2) {
        int start=vertexTable.hash(v1);
        int end=vertexTable.hash(v2);
        boolean[]visited = new boolean[vertexTable.M];
        int[]pathCount = new int[1];
        dfsCountPaths(start, end, visited, pathCount);
        return pathCount[0];
    }

    private void dfsCountPaths(int current, int end, boolean[] visited, int[] pathCount) {
        visited[current]=true;
        if(current==end) {
            pathCount[0]++;
            visited[current]=false;
            return;
        }
        if(adjacencyList[current]!=null) {
            for(String neighbor:adjacencyList[current]) {
                int neighborIndex=vertexTable.hash(neighbor);
                if (!visited[neighborIndex]) {
                    dfsCountPaths(neighborIndex,end,visited,pathCount);
                }
            }
        }
        visited[current] = false;
    }

    public String[]Neighbors(String v1) {
        int index=vertexTable.hash(v1);
        return adjacencyList[index];
    }

    public String HighestDegree() {
        int maxDegree=-1;
        String vertexWithMaxDegree="";
        for (int i = 0; i < adjacencyList.length; i++) {
    if (adjacencyList[i] != null && adjacencyList[i].length > maxDegree) {
        maxDegree = adjacencyList[i].length;
        vertexWithMaxDegree = vertexTable.getKey(i);
    }
}
        return vertexWithMaxDegree;
    }
    public boolean IsDirected() {
        // if there is one non null adjacency list it is directed
        for (String[] list : adjacencyList) {
            if (list!=null) {
                return true;
            }
        }
        return false;
    }

    public boolean AreTheyAdjacent(String v1, String v2) {
        int index=vertexTable.hash(v1);
        if (adjacencyList[index]!=null) {
            for (String neighbor:adjacencyList[index]) {
                if (neighbor.equals(v2)) {
                    return true;
                }
            }
        }
        return false;
    }
   public boolean IsThereACycle(String v1) {
        int start=vertexTable.hash(v1);
        boolean[]visited=new boolean[vertexTable.M];

        return isThereACycleDFS(start, -1, visited);
    }
    private boolean isThereACycleDFS(int current, int parent, boolean[] visited) {
        visited[current]=true;
        if (adjacencyList[current]!=null) {
            for (String neighbor : adjacencyList[current]) {
                int neighborIndex = vertexTable.hash(neighbor);
                if (!visited[neighborIndex]) {
                    if (isThereACycleDFS(neighborIndex, current, visited)) {
                        return true;
                    }
                } else if (neighborIndex!=parent) {
                    return true; 
                }
            }
        }
        return false;
    }
    public void NumberOfVerticesInComponent(String v1) {
        int start=vertexTable.hash(v1);
        boolean[]visited=new boolean[vertexTable.M];

        int componentSize=dfsCountComponentSize(start, visited);
        System.out.println("Number of vertices in the component containing " + v1 + ": " + componentSize);
    }

    private int dfsCountComponentSize(int current, boolean[] visited) {
        visited[current]=true;
        int size=1;

        if (adjacencyList[current]!=null) {
            for (String neighbor:adjacencyList[current]) {
                int neighborIndex=vertexTable.hash(neighbor);
                if (!visited[neighborIndex]) {
                    size+=dfsCountComponentSize(neighborIndex, visited);
                }
            }
        }

        return size;
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Cities cities=new Cities(1000);  
        cities.readGraphFromFile("graph.txt");
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. IsThereAPath");
            System.out.println("2. NumberOfVerticesInComponent");
            System.out.println("3. WhatIsShortestPathLength");
            System.out.println("4. NumberOfSimplePaths");
            System.out.println("5. Neighbors");
            System.out.println("6. HighestDegree");
            System.out.println("7. IsDirected");
            System.out.println("8. AreTheyAdjacent");
            System.out.println("9. IsThereACycle");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice){
                 case 1:
                   System.out.print("Enter the first vertex: ");
                    String vertex1=scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    String vertex2=scanner.nextLine();
                    boolean path=cities.IsThereAPath(vertex1, vertex2);
                    if(path){
                    System.out.println("There is a path between " + vertex1 + " and " + vertex2);
                    }
                    else{
                        System.out.println("There is no path between " + vertex1 + " and " + vertex2);
                    }
                    break;

                case 2:
                    System.out.print("Enter the vertex to find the component size: ");
                    String componentVertex=scanner.nextLine();
                    cities.NumberOfVerticesInComponent(componentVertex);
                    break;

                case 3:
                    System.out.print("Enter the source vertex: ");
                    String sourceVertex=scanner.nextLine();
                    System.out.print("Enter the destination vertex: ");
                    String destinationVertex=scanner.nextLine();
                    int shortestPathLength=cities.WhatIsShortestPathLength(sourceVertex, destinationVertex);
                    System.out.println("Shortest path length from " + sourceVertex + " to " + destinationVertex + ": " + shortestPathLength);
                    break;

                case 4:
                    System.out.print("Enter the source vertex: ");
                    String simplePathsSource=scanner.nextLine();
                    System.out.print("Enter the destination vertex: ");
                    String simplePathsDest=scanner.nextLine();
                    int numSimplePaths=cities.NumberOfSimplePaths(simplePathsSource, simplePathsDest);
                    System.out.println("Number of simple paths from " + simplePathsSource + " to " + simplePathsDest + ": " + numSimplePaths);
                    break;

                case 5:
                    System.out.print("Enter the vertex to get neighbors: ");
                    String neighborsVertex=scanner.nextLine();
                    String[]neighbors=cities.Neighbors(neighborsVertex);
                    System.out.println("Neighbors of " + neighborsVertex + ": " + String.join(", ", neighbors));
                    break;

                case 6:
                    String highestDegreeVertex=cities.HighestDegree();
                    System.out.println("Vertex with the highest degree: " + highestDegreeVertex);
                    break;

                case 7:
                    boolean isDirected=cities.IsDirected();
                    System.out.println("Is the graph directed? " + isDirected);
                    break;

                case 8:
                    System.out.print("Enter the first vertex: ");
                    String adjacentVertex1=scanner.nextLine();
                    System.out.print("Enter the second vertex: ");
                    String adjacentVertex2=scanner.nextLine();
                    boolean areAdjacent=cities.AreTheyAdjacent(adjacentVertex1, adjacentVertex2);
                    System.out.println("Are " + adjacentVertex1 + " and " + adjacentVertex2 + " adjacent? " + areAdjacent);
                    break;
                case 9:
                    System.out.print("Enter the vertex to check for a cycle: ");
                    String cycleVertex=scanner.nextLine();
                    boolean hasCycle = cities.IsThereACycle(cycleVertex);
                    System.out.println("Has cycle starting from " + cycleVertex + ": " + hasCycle);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;    
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice!=0);

        scanner.close();
    }
}
