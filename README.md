Analytical Report – Assignment 3
Name: Tulegenov Arlan
Group: SE-2429


Performance Summary by Graph Size:


Small Graphs (7–25 vertices, 9–39 edges):
Prim’s Algorithm: Average execution time – 0.17 ms; average operations – 24
Kruskal’s Algorithm: Average execution time – 0.22 ms; average operations – 126
Both algorithms performed very efficiently on smaller graphs, though Prim’s algorithm was slightly faster overall.


Medium Graphs (81–277 vertices, 135–460 edges):
Prim’s Algorithm: Average execution time – 0.57 ms; average operations – 281
Kruskal’s Algorithm: Average execution time – 0.41 ms; average operations – 1584
Kruskal’s algorithm began to show faster execution times despite requiring more operations.


Large Graphs (481–953 vertices, 914–1852 edges):
Prim’s Algorithm: Average execution time – 0.85 ms; average operations – 1380
Kruskal’s Algorithm: Average execution time – 1.37 ms; average operations – 7726
Prim’s algorithm maintained better speed and efficiency.


Extra-Large Graphs (1255–1800 vertices, 2013–2724 edges):
Prim’s Algorithm: Average execution time – 3.92 ms; average operations – 2450
Kruskal’s Algorithm: Average execution time – 3.22 ms; average operations – 13,842
At the largest scales, Kruskal’s algorithm demonstrated slightly faster performance.


Key Observation:
For all test cases, both algorithms produced identical MST (Minimum Spanning Tree) costs, confirming their correctness and consistency.



2. Comparison Between Prim’s and Kruskal’s Algorithms


Theoretical Analysis:


Kruskal’s Algorithm:
Time Complexity: O(E log E), dominated by the edge-sorting step
Space Complexity: O(V + E) for the edge list and Union-Find structure
Approach: Global, edge-based – selects the smallest edge that doesn’t form a cycle
Data Structure: Disjoint Set Union (Union-Find) with path compression and union by rank
Best Use Case: Sparse graphs where the number of edges (E) is much smaller than V²


Prim’s Algorithm:
Time Complexity: O(E log V) using a binary heap as the priority queue
Space Complexity: O(V + E) for the adjacency list and priority queue
Approach: Local, vertex-based – grows the MST from a chosen starting vertex
Data Structure: Priority queue that maintains edges crossing the MST frontier
Best Use Case: Dense graphs where adjacency list or matrix representations are efficient
Theoretical Performance Comparison


For Sparse Graphs (E ≈ V):
Kruskal’s and Prim’s have comparable performance, both roughly O(V log V).


For Dense Graphs (E ≈ V²):
Kruskal’s complexity increases to O(V² log V), while Prim’s remains O(V² log V), giving Prim’s a slight theoretical edge.


3. Practical Performance Analysis


Operation Count:
Experimentally, Kruskal’s algorithm performed 5–7 times more operations than Prim’s across all graph sizes.
This difference is due to the frequent Union-Find operations (find and union) in Kruskal’s approach, whereas Prim’s mainly counts priority queue operations.


Example:
In Medium Graph #1 (277 vertices, 431 edges):
Prim’s operations: 420
Kruskal’s operations: 2361


Execution Time:
Despite its higher operation count, Kruskal’s algorithm sometimes achieved comparable or even faster execution times, depending on the graph size.

Small Graphs: Prim’s was slightly faster (0.17 ms vs 0.22 ms) because Kruskal’s sorting introduces minor overhead on small inputs.

Medium Graphs: Kruskal’s performed about 28% faster (0.41 ms vs 0.57 ms), likely due to efficient sorting and lightweight Union-Find operations.

Large Graphs: Prim’s regained the advantage (0.85 ms vs 1.37 ms), as Kruskal’s sorting became more expensive with larger edge counts.

Extra-Large Graphs: Kruskal’s showed better scalability (3.22 ms vs 3.92 ms), benefitting from highly optimized Union-Find operations.


4. Strengths of Each Algorithm


Kruskal’s Algorithm:
Easy to implement and understand
Union-Find with path compression yields near-constant time merges
Naturally parallelizable (edge sorting and disjoint set operations)
Works efficiently with edge-list graph representations


Prim’s Algorithm:
Avoids sorting, making it more memory- and cache-efficient
Expands the MST incrementally from a single vertex
Well-suited for adjacency list or matrix structures
Ideal when the MST must begin from a specific starting point


3. Conclusions: Algorithm Selection Guidelines


Based on theoretical analysis and experimental results, the following guidelines emerge for algorithm selection:


Choose Kruskal's Algorithm When:
1. Graph Representation: The graph is naturally represented as an edge list rather than adjacency list/matrix.
2. Sparse Graphs: For graphs where E << V², Kruskal's O(E log E) complexity provides excellent performance. The experimental data shows strong performance in medium-sized sparse graphs.
3. Distributed/Parallel Computing: The sorting phase and independent Union-Find operations are highly parallelizable, making Kruskal's ideal for distributed systems.
4. Dynamic Edge Addition: When edges arrive over time and you need to maintain MST incrementally, Union-Find structure naturally supports this.
5. Memory Constraints: Edge list representation requires less memory than adjacency structures for sparse graphs.
6. Very Large Graphs: The experimental results demonstrate superior scalability for extra-large graphs (1255+ vertices), with 18% better performance than Prim's.


Choose Prim's Algorithm When:
1. Graph Representation: The graph is stored as adjacency list or adjacency matrix, which is common in many applications.
2. Dense Graphs: For graphs approaching E ≈ V², Prim's O(E log V) complexity theoretically outperforms Kruskal's O(E log E).
3. Specific Starting Vertex: When the MST must be constructed from a particular vertex or when exploring from a known starting point.
4. Memory Access Locality: Applications requiring cache-efficient algorithms benefit from Prim's sequential adjacency exploration.
5. Streaming Edges: When all edges are not initially available, Prim's can process edges as they become known from explored vertices.
6. Large but Not Huge Graphs: The experimental results show strong performance for large graphs (481-953 vertices) with 38% better execution time than Kruskal's.
Practical Decision Framework:
For General Purpose Applications:

<img width="1207" height="730" alt="image" src="https://github.com/user-attachments/assets/5213b16e-ef91-4969-8384-c7eeba08a3a6" />


Graphs with < 500 vertices: Either algorithm performs well; choose based on data structure convenience
Graphs with 500-1000 vertices and sparse connectivity: Prim's algorithm shows consistent advantage


Graphs with > 1000 vertices: Kruskal's algorithm demonstrates better scalability
Dense graphs (E > V log V): Prim's algorithm preferred theoretically


Implementation Complexity Considerations:
Both algorithms have similar implementation complexity in modern languages with standard library support:


Kruskal's requires: sorting capability and Union-Find structure
Prim's requires: priority queue and visited tracking


Bonus:
<img width="642" height="659" alt="image" src="https://github.com/user-attachments/assets/a937e45a-1b14-46e4-a9a1-800cc336e5f0" />



4. References
Course Materials:

Aimukhambetov, O. (2025). Lecture 6 – Disjoint Set Union (Union-Find) and Minimum Spanning Trees (Kruskal/Prim). Astana IT University.

Implementation References:
Google Gson Library Documentation: https://github.com/google/gson (Used for JSON parsing in experimental implementation)

Empirical Data:
Experimental results from 33 test cases ranging from 7 to 1800 vertices, executed on the provided Java implementation with input graphs from assign_3_input.json.
