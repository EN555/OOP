package ex1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;


class GraphTest {
	@BeforeAll
	static void runOnceBeforeClass()
	{
		System.out.println("Graph_Tests");
	}
	@AfterAll
	static void runOnceAfterAll()
	{
		System.out.println("The Test Finished!");
	}
	@BeforeEach
	void runBeforeEach()
	{
		System.out.println("The Next Test");
	}
	@AfterEach
	void runAfterEach()
	{
		System.out.println("The  Current Test Finished!");
	}
	/// first check special cases
	
	WGraph_DS h= new WGraph_DS(); 		//initial empty graph
	@Test
	 void empthyGraphTest()
	{
		assertEquals(null, h.getNode(0));			//get node
	
		assertEquals(false, h.hasEdge(0, 1));		//get edge

		//	assertEquals(null, h.getV());				// בבבדיקה
	
		//	assertEquals(null, h.getV(0));				//בבדיקה

		assertEquals(0, h.edgeSize());
	
		assertEquals(0, h.getMC());
	
		assertEquals(0, h.nodeSize());
	
		assertEquals(null, h.GetNi(1));	
	
		assertEquals(null, h.GetNi_W(1));	
	}
	
	@Test
	 void bigGraph()
	{
		for(int i=0 ; i< 10000 ; i++)			//add 10000 nodes to the graph
		{
		h.addNode(i);	
		}
		for(int i=0 ; i < 1000 ; i++)
		{
		h.connect(i, i+1, i );	
		}
		
		assertEquals(true, h.hasEdge(3, 4));			//get node
	
		assertEquals(3, h.getEdge(3, 4));		//get edge
	
		assertEquals(-1, h.getEdge(10000, 1111111));		//the edge not exist so its return -1
		
		assertEquals(-1, h.getEdge(0,1));					////the edge not exist because that i tried to enter her with 0 weight so its return -1
		
		assertEquals(10999, h.getMC());
	
		assertEquals(10000, h.nodeSize());
	
		assertEquals(0, h.GetNi(0).size());	
	
		assertEquals(1, h.GetNi(1).size());

		//		assertEquals(-1, h.GetNi_W(0));

		//		assertEquals(1, h.GetNi(1).size());

	}

	
	
}
