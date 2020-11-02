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
		
		Node_Data l1= this.nodes.get(node1);
		Node_Data l2= this.nodes.get(node2);
		
		if(l1 != null && l2 != null && node1 != node2)										//check if this node1 exist
		{									
			if(l1.GetNi().containsKey(node2))				//check if node2 is neighbor of node1
				return true;
		} 
			return false;
	}

	@Override
	public double getEdge(int node1, int node2) 
	{
		if(this.hasEdge(node1, node2))
			return this.nodes.get(node1).GetNi().get(node2);
		return -1;
	}

	@Override
	public void addNode(int key) {
		if(this.nodes.get(key) == null) 
		{
			this.nodes.put(key, new Node_Data(key));
			this.NumberOfnodes++;
			this.NumberOfmodes++;
		}
		return;	
	}

	@Override
	public void connect(int node1, int node2, double w) 
	{	
		Node_Data l1= this.nodes.get(node1);
		Node_Data l2= this.nodes.get(node2);

		if(!this.hasEdge(node1,node2)) 	 //check if the node not exist and not create edge between the node
		{	
			l1.GetNi().put(node2,w);
			l2.GetNi().put(node1,w);
			NumberOfedges++;										//update the number of edged and the number of modes
			NumberOfmodes++;
		}
	}

	@Override
	public Collection<node_info> getV() {
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
