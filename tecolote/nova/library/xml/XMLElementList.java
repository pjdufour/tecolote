package nova.library.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLElementList implements Iterable<Node>
{
	public String tagFilter;
	//public String attrFilter;
	private LinkedList<Node> list;
	
	public static XMLElementList createList(NodeList list)
	{
		return new XMLElementList(list);
	}
	public static XMLElementList createListWithoutTextNodes(Node parent)
	{
		LinkedList<Node> list = new LinkedList<Node>();
		list.addAll(getNonTextNodes(parent));
		return new XMLElementList(list);
	}
	public static XMLElementList createListWithTagSelector(String tagSelector,Node parent)
	{
		String tags[] = tagSelector.split(" ");
		LinkedList<Node> list = new LinkedList<Node>();
		list.add(parent);
		for(String tag: tags)
		{
			LinkedList<Node> new_list = new LinkedList<Node>();
			for(Node parentNode: list)
			{
				new_list.addAll(getMatchingNodes(tag,parentNode));
			}
			list = new_list;
		}
		return new XMLElementList(list);
	}
	private static LinkedList<Node> getMatchingNodes(String tagSelector,Node parent)
	{
		LinkedList<Node> list = new LinkedList<Node>();
		NodeList nodes = parent.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			if(node.getNodeName().equals(tagSelector))
				list.add(node);
		}
		return list;
	}
	private static LinkedList<Node> getNonTextNodes(Node parent)
	{
		LinkedList<Node> list = new LinkedList<Node>();
		NodeList nodes = parent.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			if(!node.getNodeName().equals("#text"))
				list.add(node);
		}
		return list;
	}
	private XMLElementList(LinkedList<Node> list)
	{
		tagFilter = "";
		this.list = list;
	}
	private XMLElementList(NodeList nodes)
	{
		this.tagFilter = "";
		this.list = new LinkedList<Node>();
		for(int i = 0; i < nodes.getLength(); i++)
			list.add(nodes.item(i));
	}
	
	public Node get(int i)
	{
		return i<list.size()?list.get(i):null;
	}
	public Iterator<Node> iterator()
	{
		return list.iterator();
	}

}
