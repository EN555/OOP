package ex1;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.*;

import javax.imageio.IIOException;


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
		
		weighted_graph g= new WGraph_DS();		//the min graph
			
		for(node_info iter :this.h.getV())		//create new nodes and put them at the graph
			g.addNode(iter.getKey());
			
		weighted_graph graph= this.h;	

		for(node_info l1 : graph.getV())
		{
				for(node_info l2 : graph.getV(l1.getKey()))		
			{		
					g.connect(l1.getKey(), l2.getKey(), graph.getEdge(l1.getKey(), l2.getKey()));  //update the key and the weight
			}		
		}	
		return g;
	}
		
	public void dijkstra(int src, int dest)
	{	
		if(this.h.getNode(src) == null || this.h.getNode(dest) == null)
				return;
	
		weighted_graph graph= this.h;							//downcating the graph

		Queue<node_info> pq = new PriorityQueue<node_info>();		//create min priority queue
		
		for(node_info all : graph.getV())							//initial the info to WHITE it mean that no one didn't visit there
			all.setInfo(Colors.WHITE.toString());
		
		this.h.getNode(src).setTag(0);								//initial the src to distance 0		
		pq.add(this.h.getNode(src));								//add the src node to the priority queue
		
		while(!pq.isEmpty()) {
		node_info it= pq.poll();
			for(node_info iter : graph.getV(it.getKey())) {		//move on all his neighbors and update their distance
				
				if(iter.getInfo().equals(Colors.WHITE.toString())) {							// first option
					iter.setTag(graph.getEdge(it.getKey(), iter.getKey()) +it.getTag());
					iter.setInfo(Colors.GREY.toString());
					pq.add(iter);
					}
						if(iter.getInfo().equals(Colors.GREY.toString()))	//second option
					{	
							double dis=  it.getTag()+ graph.getEdge(it.getKey(), iter.getKey());	// the weight of the edge of iter and it and the weight of the it
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
		File file1 = new File(file);			//create pointer to the file path
		try {
		FileWriter fl = new FileWriter(file1); 
		PrintWriter pw = new PrintWriter(fl);
		pw.println(this.h.toString());
		pw.close();
		}
		catch (FileNotFoundException it)
		{
			System.out.println("you need to enter file!!");
			it.printStackTrace();
			 return false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		try { 
			FileReader fr = new FileReader(file); 
			BufferedReader br = new BufferedReader(fr);
			String str;
			weighted_graph graph = new WGraph_DS();
			str = br.readLine();
			if(str.isEmpty())		//check if the file is empty
				return false;
			while(!str.isEmpty()) 
			{									
			String[] numbers = str.replaceAll("[^0-9.]+", " ").trim().split(" ");	
			int node = Integer.parseInt(numbers[0]);					//define the loc of the node
			graph.addNode(node);						//create a node
			int loc= 1;
			while(loc< numbers.length)								//load the neighbors of this node
			{
				int neighbor= Integer.parseInt(numbers[loc]);			//define the loc of the neighbor
				graph.addNode(neighbor);	
				Double weight= Double.parseDouble(numbers[loc+1]);		//define the loc of the weight
				graph.connect(node , neighbor , weight);			//create an edge between them
				loc+=2;
			}
			
			str = br.readLine();
					}
			this.h= graph;
			br.close();     
			fr.close();   
			return true;
		}
		catch(IOException ex) {  
		
			System.out.print("Error reading file\n" + ex);
			return false;
		}
		
	}

}
