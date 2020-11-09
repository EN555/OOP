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

import filesSerialization.Point;


public class WGraph_Algo implements weighted_graph_algorithms {
	
	private weighted_graph h;
	
	//constructors
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
	
		if(this.h ==null ) {			//if the graph initial to null return null
			 return null;
		}
		weighted_graph g= new WGraph_DS();		//initial weighted graph
			
		for(node_info iter :this.h.getV())		//create new nodes and put them at the graph
			g.addNode(iter.getKey());
			weighted_graph graph= this.h;	
		for(node_info node : graph.getV()){
			for(node_info neighbor : graph.getV(node.getKey())){
					int v1 = node.getKey();
					int v2 = neighbor.getKey();
					g.connect(v1, v2, graph.getEdge(v1, v2));  //update the key and the weight
			}		
		}	
		return g;
	}
		
	public void dijkstra(int src, int dest)
	{	
		if(this.h == null || this.h.getNode(src) == null || this.h.getNode(dest) == null)
				return;
	
		weighted_graph graph= this.h;							//downcating the graph

		Queue<node_info> pq = new PriorityQueue<node_info>();		//create min priority queue
		
		for(node_info all : graph.getV())							//initial the info to WHITE it mean that no one didn't visit there
			all.setInfo(Colors.WHITE.toString());
		
		graph.getNode(src).setTag(0);								//initial the src to distance 0		
		pq.add(graph.getNode(src));								//add the src node to the priority queue
		
		while(!pq.isEmpty()) {
		node_info it= pq.poll();
		
			for(node_info iter : graph.getV(it.getKey())) {		//move on all his neighbors and update their distance
				
				if(iter.getInfo().equals(Colors.WHITE.toString())) 
				{							// first option
					iter.setTag(graph.getEdge(it.getKey(), iter.getKey()) +it.getTag());
					iter.setInfo(Colors.GREY.toString());
					pq.add(iter);
					}
				if(iter.getInfo().equals(Colors.GREY.toString()))								
					{						//second option
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
		if(this.h == null)					//check if the graph is null
			return false;
		if(this.h.getV().size() < 2)		
			return true;
		Iterator<node_info> it = this.h.getV().iterator();
		node_info l1 = it.next();				//search two node to put in dijkstra
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
		if(this.h == null || this.h.getNode(src) == null || this.h.getNode(dest) == null)
			return -1;
		dijkstra(src, dest);
		if(this.h.getNode(dest).getInfo().equals(Colors.BLACK.toString()))		//check if the fuction reach to the src
		{	
			return this.h.getNode(dest).getTag();			//return the weight that dijkstra calculate
		}
			return -1;
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) 
	{
		if(this.h == null || this.h.getNode(src)==null || this.h.getNode(dest) == null)		 
			return  new LinkedList<node_info>();
			
		dijkstra(src, dest);							//call to dijkstra
		if(this.h.getNode(dest).getInfo().equals(Colors.WHITE.toString()))
				return new LinkedList<node_info>();
		
		LinkedList<node_info> ls= new LinkedList<node_info>();				//initial linked list to put in the nodes
		node_info temp =this.h.getNode(dest);
		ls.push(temp);	
		while(temp.getKey() != src) {
			
			for(node_info l1 : this.h.getV(temp.getKey())){ 			//move on all the neighbor of dest and look for where he came from
					if(l1.getTag() == temp.getTag()-this.h.getEdge(temp.getKey(), l1.getKey()))	{			
						temp = l1;
						break;
					}			
				}
			ls.push(temp);									//push to the head of the list
		}
		return ls;
	}

	@Override
	public boolean save(String file) {
		
		if(this.h ==null)					//check if have any graph
			return false;
		try{
			FileOutputStream fo = new FileOutputStream(file);			//use streams to save and loaf files
			ObjectOutputStream output = new ObjectOutputStream(fo);
			output.writeObject(this.h);							//convert the object to strean 
			output.close();
			fo.close();
		}
		catch(FileNotFoundException it)					//check if haven't any file to output
		{
			System.out.println("you need to enter file!!");
			it.printStackTrace();
			 return false;
		}
		catch(IOException e)			//if this error cause of input output the program will throw this error
		{
			e.printStackTrace();
			return false;	
		}
		catch(Exception e){				//every another error the program will throw that
			e.printStackTrace();
			return false;
		}		
		return true;					//if the prog didn't catch by any catch error it'w will return true
	}

	@Override
	public boolean load(String file) {
		
		try{
			FileInputStream myFile = new FileInputStream(file);				//read the only stream file
			ObjectInputStream ois = new ObjectInputStream(myFile);
			weighted_graph graph = new WGraph_DS();
			graph = (weighted_graph)ois.readObject();				//convert the the stream to graph object 
			this.h=graph;								//update the pointer of the Hashmap
			ois.close();
			myFile.close();
		}
		catch(FileNotFoundException e)			//if this error cause of input output the program will throw this error
		{
			e.printStackTrace();
			return false;
		}
		catch(IOException ex) {  					//if this error cause of input output the program will throw this error
			System.out.print("Error reading file\n" + ex);
			return false;
			}
		catch (Exception error){		//every another error the program will throw that
			error.printStackTrace();
			return false;
		}
		
		return true;
	}
}
