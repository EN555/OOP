package ex1;

import java.util.Collection;
import java.util.HashMap;

import ex0.node_data;

public class WGraph_DS implements weighted_graph {
	
	private HashMap<Integer, Node_Data> nodes;	
	private int NumberOfedges=0;
	private int NumberOfnodes=0;
	private int NumberOfmodes=0;
	
	//constructors
	public WGraph_DS()
	{  											
		this.nodes= new HashMap<Integer, Node_Data>();	
	}
		
	public WGraph_DS(HashMap<Integer, Node_Data> a)
	{  
		this.nodes= a ;	
	}
	
	@Override
	public node_info getNode(int key) {
		return this.nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if(this.nodes.containsKey(node1))										//check if this node1 exist
		{									
			if(this.nodes.get(node1).Getneighbors().containsKey(node2))			//check if node2 is neighbor of node1
				return true;
		} 
			return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		if(this.nodes.get(node1) != null) {
			if(this.nodes.get(node2) != null)
				return this.nodes.get(node1).Getneighbors().get(node2).weight;
		}
		return -1;
	}

	@Override
	public void addNode(int key) {
		if(this.nodes.get(key) != null) 
		{
			this.nodes.put(key, new Node_Data(key));
			this.NumberOfnodes++;
		}
		return;	
	}

	@Override
	public void connect(int node1, int node2, double w) 
	{	
		Node_Data point1= this.nodes.get(node1);
		Node_Data point2= this.nodes.get(node2);

		if(node1 != node2 && point1 !=null && point2 != null && !this.hasEdge(node1, node2)) 	 //check if the node not exist and not create edge between the node
		{
			point1.Getneighbors().put(node2, new edge(point2 ,w));
			point2.Getneighbors().put(node1, new edge(point1 ,w));	
			NumberOfedges++;										//update the number of edged and the number of modes
			NumberOfmodes++;
		}
	}

	@Override
	public Collection<node_info> getV() {
//		HashMap<Integer, node_info> map= this.nodes;
//		return (this.nodes.values())(node_info);
		return null;
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		return null;
	}

	@Override
	public node_info removeNode(int key) {
		return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		
	}

	@Override
	public int nodeSize() {
		return this.NumberOfnodes;
	}

	@Override
	public int edgeSize() {
		return this.NumberOfedges;
	}

	@Override
	public int getMC() {
		return this.NumberOfmodes;
	}

}
