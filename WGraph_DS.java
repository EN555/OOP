package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import Exercises_2.Node;


public class WGraph_DS implements weighted_graph {
	
	private HashMap<Integer, node_info> nodes;	
	private int NumberOfedges=0;
	private int NumberOfnodes=0;
	private int NumberOfmodes=0;
	private static int count_nodes=0;
	
	//constructors
	public WGraph_DS()
	{  											
		this.nodes= new HashMap<Integer, node_info>();	
	}

	
	
	public WGraph_DS(HashMap<Integer, node_info> a)
	{  
		this.nodes= a ;	
	}
	
	@Override
	public node_info getNode(int key) {
		return this.nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		
		NodeInfo l1= (NodeInfo)this.nodes.get(node1);
		NodeInfo l2= (NodeInfo)this.nodes.get(node2);
		
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
		if(this.hasEdge(node1, node2)) {
			NodeInfo l1= (NodeInfo)this.nodes.get(node1);
			return l1.GetNi_W().get(node2);
		}
		return -1;
	}

	@Override
	public void addNode(int key) {
		if(this.nodes.get(key) == null) 
		{
			this.nodes.put(key, new NodeInfo(key));
			this.NumberOfnodes++;
			this.NumberOfmodes++;
			this.count_nodes++;
		}
		return;	
	}

	@Override
	public void connect(int node1, int node2, double w) 
	{	
		NodeInfo l1= (NodeInfo)this.nodes.get(node1);
		NodeInfo l2= (NodeInfo)this.nodes.get(node2);


		if(l1 != null && l2 !=null && node1 != node2 && !l1.GetNi().containsKey(node2))	 //check if the node not exist and not create edge between the node
		{	
			l1.GetNi().put(node2, (node_info)l2);
			l1.GetNi_W().put(node2, w);
			l2.GetNi().put(node1,(node_info)l1);
			l2.GetNi_W().put(node1, w);
			NumberOfedges++;										//update the number of edged and the number of modes
			NumberOfmodes++;
		}
	}

	@Override
	public Collection<node_info> getV() 
	{
		return this.nodes.values();
	}
	
//	public HashMap<Integer, NodeInfo> getVmap() 			//////////////////		return NodeInfo collection
//	{
//		HashMap<Integer,NodeInfo> map= new HashMap<Integer,NodeInfo>();
//		Iterator<node_info> iter= this.nodes.values().iterator();
//		while(iter.hasNext())
//		{
//			NodeInfo l1= (NodeInfo)iter.next();
//			map.put(l1.getKey(),l1);
//		}
//		return map;
//	}
//	
	@Override
	public Collection<node_info> getV(int node_id) {
		NodeInfo l1= (NodeInfo)this.nodes.get(node_id);
		
		if(l1==null)
			return null;
		return l1.GetNi().values();
	}

	@Override
	public node_info removeNode(int key) 
	{
		NodeInfo l1= (NodeInfo)this.nodes.get(key);
		if(l1 != null) 
		{
		for(int i: l1.GetNi().keySet()) 
		{
			NodeInfo l2= (NodeInfo)this.nodes.get(i);
			l2.GetNi().remove(key);
		}
		return this.nodes.remove(key);
		}
		return null;
	}

	@Override
	public void removeEdge(int node1, int node2) 
	{	
		NodeInfo l1= (NodeInfo)this.nodes.get(node1);
		NodeInfo l2= (NodeInfo)this.nodes.get(node2);
		
		if(this.hasEdge(node1, node2)) 
		{
			l1.GetNi().remove(node2);
			l2.GetNi().remove(node1);
			
		}
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

	public void nodeSize(int nodes) {
		this.NumberOfnodes=nodes;
	}


	public void edgeSize(int edges) {
		this.NumberOfedges= edges;
	}


	public void getMC(int mc) {
		this.NumberOfmodes=mc;
	}
	
	public HashMap<Integer, node_info> GetNi(int node1) 
	{
		NodeInfo l1= (NodeInfo)this.nodes.get(node1);
		return l1.GetNi();
	}
	public HashMap<Integer, Double> GetNi_W(int node1) 
	{
		NodeInfo l1= (NodeInfo)this.nodes.get(node1);
		return l1.GetNi_W();
	}
	
	public String toString() {
		StringBuffer mText = new StringBuffer();
		for (Integer keys : this.nodes.keySet())  
		{
			NodeInfo l1= (NodeInfo)this.nodes.get(keys);
			mText.append("\n"+ "TheKey= "+ l1.getKey() +" TheInfo= "+l1.getInfo());
			if(l1.GetNi().isEmpty() ==false)
				for(node_info iter: l1.GetNi().values())
				mText.append(" TheNeigbor= "+ iter.getKey() +" The Weight "+ l1.GetNi_W().get(iter.getKey()) +"\n");
		}
		return mText.toString();
	}
	
	
	////       inner class		/////
	
	
	private class NodeInfo implements node_info , Comparable<node_info> {
		int key;
		String info;
		double tag;
		private HashMap<Integer, node_info> neighbor;
		private HashMap<Integer, Double> w_neighbor;
		
		
		// constructors
		public NodeInfo(int key,String info,double tag,HashMap<Integer,node_info> hashMap ,HashMap<Integer, Double> hashMap_weight)		//constructor that get all the parameters 
		{		
			this.neighbor= new HashMap<Integer, node_info>(hashMap);
			this.w_neighbor= new HashMap<Integer, Double>(hashMap_weight);
			this.key =key;
			this.info=info;
			this.tag=tag;
		}
					
		public NodeInfo(int key,String info) 
		{
			this(key,info,0,new HashMap<Integer,node_info>(),new HashMap<Integer,Double>());
		}
			
		public NodeInfo(int key) 
		{
			this(key,"",0,new HashMap<Integer,node_info>(),new HashMap<Integer,Double>());
		}
			

			//copy constructor
		public NodeInfo(NodeInfo copy) 								
		{																		
			this(copy.key,copy.info, copy.tag, copy.neighbor, copy.w_neighbor);
		}

		@Override
		public int getKey() {
			// TODO Auto-generated method stub
			return this.key;
		}

		@Override
		public String getInfo() {
			// TODO Auto-generated method stub
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			// TODO Auto-generated method stub
			this.info=s;
		}

		@Override
		public double getTag() {
			// TODO Auto-generated method stub
			return this.tag;
		}

		@Override
		public double setTag(double t) {					
			// TODO Auto-generated method stub
			this.tag=t;
			return t;
		}
		public HashMap<Integer, node_info> GetNi() 
		{
			return this.neighbor;
		}
		public HashMap<Integer, Double> GetNi_W() 
		{
			return this.w_neighbor;
		}
		
//		public void addNi(node_info Ni, double weight) 
//		{
//			this.neighbor.put(Ni.getKey(), Ni);
//		}
		public String toString() 
		{
			return "key="+ " "+ this.getKey() +" info=" +this.getInfo() ;
		
		}

		@Override
		public int compareTo(node_info l1) {
			if(this.getTag()> l1.getTag())
				return 1;
			if(this.getTag()< l1.getTag())
				return -1;
				return 0;
		}

	}



	

}


