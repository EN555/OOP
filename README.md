# OOP
### main goal
The main goal of the program is to create udirected weighted graph.
The program composed from two classes and inner class that inplements three interfaces.
### WGraph_DS
The create of nodes did after the creation of the graph:
```java
public void addNode(int key) 
```
At the base class you can create graph, and you can crate nodes only from the graph, the create of the node executed by the inner class.

for built wighted graph i used at two hashmap, the first hashmap uses for to enter the neighbros according to their key and
the second hashmap uses for enter the same keys the weights of the edges.
at this class have else function to add. remove node, remove edge and etc.
### WGraph_DS
```java
public weighted_graph copy() 
```

