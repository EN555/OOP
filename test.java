package ex1;

public class test {

	public static void main(String[] args) {
		WGraph_DS graph= new WGraph_DS();
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);
		graph.addNode(6);
		graph.addNode(2);
		graph.addNode(5);
		graph.addNode(6);
		graph.addNode(2);
		graph.connect(2, 3, 5);
		graph.connect(2, 5, 10);
		graph.connect(3, 4, 15);
		graph.connect(4, 6, 20);
		System.out.println(graph.hasEdge(6, 2));
		System.out.println(graph.hasEdge(0, 0));
		System.out.println(graph.hasEdge(6, 2));
		System.out.println(graph.hasEdge(-1, 2));
		System.out.println(graph.hasEdge(2, 3));
		System.out.println(graph.hasEdge(5, 2));
		System.out.println(graph.getEdge(2, 3));
		System.out.println(graph.getEdge(-1, 2));
		System.out.println(graph.getEdge(2, 5));
		System.out.println(graph.getEdge(0, 0));
		System.out.println(graph.getEdge(5, 10));
		System.out.println(graph);
		System.out.println();
		System.out.println();
		System.out.println(graph.removeNode(2));
		System.out.println(graph);
		graph.removeEdge(3, 4);
		graph.removeEdge(4, 6);
		graph.removeEdge(5, 2);
		graph.removeEdge(1, 0);
		graph.removeEdge(0, 0);
		System.out.println(graph);

		
	}

}
