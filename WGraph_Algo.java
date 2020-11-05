package ex1;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class WGraph_Algo implements weighted_graph_algorithms {
	
	private weighted_graph h;
	
	public WGraph_Algo() 
	{	
		this.h= new WGraph_DS();	
	}
	public WGraph_Algo(weighted_graph g) 
	{	
		this.h = g;	
	}
	@Override
	public void init(weighted_graph g) {
		this.h = g;
	}

	@Override
	public weighted_graph getGraph() {
		
		return this.h;
	}

	@Override
	public weighted_graph copy() {
		
		if(this.h.getV().isEmpty())
			return null;
		
		WGraph_DS g= new WGraph_DS();		//the min graph
			
		for(node_info iter :this.h.getV())		//create new nodes and put them at the graph
			g.addNode(iter.getKey());
			
		WGraph_DS graph= (WGraph_DS)this.h;

		

		for(node_info l1 : graph.getV())
		{
				for(node_info l2 : graph.getV(l1.getKey()))		
			{	
					g.GetNi(l1.getKey()).put(l2.getKey(),g.getNode(l2.getKey()));   		//update the key
					g.GetNi_W(l1.getKey()).put(l2.getKey(), graph.GetNi_W(l1.getKey()).get(l2.getKey()));		//update the weight		
			}
			
		}
		
		return (weighted_graph)g;
	}
	
	
	public void dijkstra(int src, int dest)
	{	
		WGraph_DS graph= (WGraph_DS)this.h;			//downcating the graph
		
		Queue<node_info> pq = new PriorityQueue<node_info>();
		for(node_info all : graph.getV())							//initial the weights to -1
			all.setInfo(Colors.WHITE.toString());
		
		graph.getNode(src).setInfo(Colors.WHITE.toString());
		this.h.getNode(src).setTag(0);								//initial the src to 0		
		pq.add(this.h.getNode(src));
		
		while(!pq.isEmpty()) {
		node_info it= pq.poll();
			for(node_info iter : this.h.getV(it.getKey())) {
				if(iter.getInfo().equals(Colors.WHITE.toString())) {	// first option
					iter.setTag(graph.GetNi_W(it.getKey()).get(iter.getKey()) +it.getTag());
					iter.setInfo(Colors.GREY.toString());
					pq.add(iter);
					}
						if(iter.getInfo().equals(Colors.GREY.toString()))	//second option
					{
							if(iter.getTag()> it.getTag()+ graph.GetNi_W(it.getKey()).get(iter.getKey()))
						{
								iter.setTag(it.getTag()+ graph.GetNi_W(it.getKey()).get(iter.getKey()));	
							}
					}
			}
			it.setInfo(Colors.BLACK.toString());			//we finish with that node
		}
		
		
		
	}

	@Override
	public boolean isConnected() {	
		dijkstra(0, 1);
		for(node_info iter : this.h.getV()) {
			if(iter.getInfo().equals(Colors.WHITE.toString())) 
				return false;
		}
			
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		dijkstra(src, dest);
		return this.h.getNode(dest).getTag();
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {

		return null;
	}

	@Override
	public boolean save(String file) {

		return false;
	}

	@Override
	public boolean load(String file) {

		return false;
	}
	public static void main(String [] args)
	{
		
		WGraph_Algo h= new WGraph_Algo();
		WGraph_DS graph= new WGraph_DS();
		h.init(graph);
		graph.addNode(0);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);
		graph.addNode(6);
		graph.addNode(2);
		graph.addNode(5);
		graph.addNode(6);
		graph.addNode(2);
		graph.connect(0, 1, 5);
		graph.connect(2, 1, 5);
		graph.connect(2, 3, 5);
		graph.connect(2, 5, 10);
		graph.connect(3, 4, 15);
		graph.connect(4, 6, 20);
		graph.connect(4, 1, 3);
		graph.removeEdge(4, 6);
		System.out.println(graph);
		System.out.println("/////////////////////////////////////////////////");
//		System.out.println(h.copy());
		System.out.println("/////////////////////////////////////////////////");
		System.out.println(h.isConnected());
		System.out.println(h.shortestPathDist(1,4));
	}

}
