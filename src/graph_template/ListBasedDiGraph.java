
package graph_template;
//Test 2
import java.util.*;

public class ListBasedDiGraph implements DiGraph {
	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		nodeList.add(node);
		return true;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		if (node == null) {
			return false;
		}

		GraphNode targetNode = getNode(node.getValue());

		if (targetNode == null) {
			return false;
		}

		nodeList.remove(targetNode);

		for (GraphNode thisNode : nodeList) {
			thisNode.removeNeighbor(targetNode);
		}

		return true;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (node == null) {
			return false;
		}

		GraphNode targetNode = getNode(node.getValue());

		if (targetNode == null) {
			return false;
		}

		targetNode.setValue(newNodeValue);
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		if (node == null) {
			return null;
		}

		GraphNode targetNode = getNode(node.getValue());

		if (targetNode != null) {
			return targetNode.getValue();
		}

		return null;
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {

		//BAD
		fromNode.addNeighbor(toNode, weight);
		
		//GOOD
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
	 	 
		targetFromNode.addNeighbor(targetToNode, weight);
	
		return true;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFrom = getNode(fromNode.getValue());
		GraphNode targetTo = getNode(toNode.getValue());
		if (targetFrom == null || targetTo == null)
			return false;
		return targetFrom.removeNeighbor(targetTo);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		GraphNode target = getNode(node.getValue());
		if (target == null)
			return null;
		return target.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFrom = getNode(fromNode.getValue());
		GraphNode targetTo = getNode(toNode.getValue());
		return targetFrom.getDistanceToNeighbor(targetTo) != null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		LinkedList<GraphNode> queue = new LinkedList<>();
		HashSet<GraphNode> visited = new HashSet<>();
		queue.add(fromNode);
		visited.add(fromNode);
		while (!queue.isEmpty()) {
			GraphNode currentNode = queue.poll();
			for (GraphNode neighbor : currentNode.getNeighbors()) {
				if (neighbor.equals(toNode)) {
					return true;
				}
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.add(neighbor);
				}
			}
		}
		return false;
	}

	@Override
	public Boolean hasCycles() {
		for (GraphNode start : nodeList) {
			if (nodeIsReachable(start, start))
				return true;
		}
		return false;
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
		for (GraphNode thisNode : nodeList) {
			if (thisNode.getValue().equals(nodeValue))
				return thisNode;
		}
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return 0;
	}

 
	 
	
}
