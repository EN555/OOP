package ex1;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
		if(this.h.getNode(src) == null || this.h.getNode(dest) == null)
				return;
	
		WGraph_DS graph= (WGraph_DS)this.h;							//downcating the graph

		Queue<node_info> pq = new PriorityQueue<node_info>();		//create min priority queue
		
		for(node_info all : graph.getV())							//initial the info to WHITE it mean that no one didn't visit there
			all.setInfo(Colors.WHITE.toString());
		
		this.h.getNode(src).setTag(0);								//initial the src to distance 0		
		pq.add(this.h.getNode(src));								//add the src node to the priority queue
		
		while(!pq.isEmpty()) {
		node_info it= pq.poll();
			for(node_info iter : graph.getV(it.getKey())) {		//move on all his neighbors and update their distance
				
				if(iter.getInfo().equals(Colors.WHITE.toString())) {							// first option
					iter.setTag(graph.GetNi_W(it.getKey()).get(iter.getKey()) +it.getTag());
					iter.setInfo(Colors.GREY.toString());
					pq.add(iter);
					}
						if(iter.getInfo().equals(Colors.GREY.toString()))	//second option
					{	
							double dis=  it.getTag()+ graph.GetNi_W(it.getKey()).get(iter.getKey()); // the weight of the edge of iter and it and the weight of the it
							
							if(iter.getTag()> dis)
						{
								iter.setTag(dis);	
							}
					}
			}
			it.setInfo(Colors.BLACK.toString());			//we finish with that node
		}		
	}

	@Override
	public boolean isConnected() {	
		if(this.h.getV().size() < 2)
			return true;
		Iterator<node_info> it = this.h.getV().iterator();
		node_info l1 = it.next();
		node_info l2 = it.next();		
		
		dijkstra(l1.getKey(), l2.getKey());					//call to dijkstra from random src and dest
		
		for(node_info iter : this.h.getV()) {				//check if dijkstra didn't succeed to reach to all the nodes
			if(iter.getInfo().equals(Colors.WHITE.toString()))  		//i initial all the info to WHITE and if someone stay with white it says that he isn't connect 
				return false;
		}
			
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		dijkstra(src, dest);
		if(this.h.getNode(dest).getInfo().equals(Colors.BLACK.toString()))		//check if the fuction reach to the src
		{	
			return this.h.getNode(dest).getTag();
		}
			return -1;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) 
	{
		dijkstra(src, dest);
		if(this.h.getNode(dest).getInfo().equals(Colors.WHITE.toString()))
				return null;
		
		LinkedList<node_info> ls= new LinkedList<node_info>();
		node_info temp =this.h.getNode(dest);
		double smallest=0;
		ls.push(temp);	
		while(temp.getKey() != src) {
			Iterator<node_info> iter = this.h.getV(temp.getKey()).iterator();		//define the node that we check is neighbors			
			smallest = iter.next().getTag();
			for(node_info l1 : this.h.getV(temp.getKey())) 			//move on all the neighbor of dest and look for the smallest 
			{		
					if(l1.getTag()<= smallest)				//check if the current smallest than iter.next 
					{
						smallest = l1.getTag();
						temp = l1;
					}			
				}
			ls.push(temp);									//push to the head of the list
		}
		return ls;
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
		graph.addNode(0);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
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
		h.init(graph);
		System.out.println(graph);
		System.out.println("/////////////////////////////////////////////////");
//		System.out.println(h.copy());
		System.out.println("/////////////////////////////////////////////////");
		System.out.println(h.isConnected());
		System.out.println(h.shortestPathDist(1,6));
		System.err.println("////////////////////////////");
		System.out.println(h.shortestPath(0, 6));
	}

}
