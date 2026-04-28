
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
		//GOOD
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
	 	 
		if (targetFromNode == null || targetToNode == null) {
			return false;
		}
	
		return targetFromNode.addNeighbor(targetToNode, weight);
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
		GraphNode targetFrom = getNode(fromNode.getValue());
		GraphNode targetTo = getNode(toNode.getValue());

		if (targetFrom == null || targetTo == null) {
			return false;
		}

		if (targetFrom.getDistanceToNeighbor(targetTo) == null) {
			return false;
		}

		targetFrom.removeNeighbor(targetTo);
		targetFrom.addNeighbor(targetTo, newWeight);

		return true;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFrom = getNode(fromNode.getValue());
		GraphNode targetTo = getNode(toNode.getValue());

		if (targetFrom == null || targetTo == null) {
			return null;
		}

		return targetFrom.getDistanceToNeighbor(targetTo);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		if (node == null) {
			return null;
		}
		GraphNode target = getNode(node.getValue());
		if (target == null)
			return null;
		return target.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFrom = getNode(fromNode.getValue());
		GraphNode targetTo = getNode(toNode.getValue());

		if (targetFrom == null || targetTo == null) {
			return false;
		}

		return targetFrom.getDistanceToNeighbor(targetTo) != null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) {
			return false;
		}

		LinkedList<GraphNode> queue = new LinkedList<>();
		HashSet<GraphNode> visited = new HashSet<>();

		queue.add(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			GraphNode currentNode = queue.poll();

			for (GraphNode neighbor : currentNode.getNeighbors()) {
				if (neighbor.getValue().equals(end.getValue())) {
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
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) {
			return -1;
		}

		if (start.getValue().equals(end.getValue())) {
			return 0;
		}

		Queue<GraphNode> queue = new LinkedList<>();
		Set<GraphNode> visited = new HashSet<>();
		Map<GraphNode, Integer> hops = new HashMap<>();

		queue.add(start);
		visited.add(start);
		hops.put(start, 0);

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					hops.put(neighbor, hops.get(current) + 1);

					if (neighbor.getValue().equals(end.getValue())) {
						return hops.get(neighbor);
					}

					queue.add(neighbor);
				}
			}
		}

		return -1;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) {
			return -1;
		}

		Map<GraphNode, Integer> distances = new HashMap<>();
		Set<GraphNode> visited = new HashSet<>();

		for (GraphNode node : nodeList) {
			distances.put(node, Integer.MAX_VALUE);
		}

		distances.put(start, 0);

		while (visited.size() < nodeList.size()) {
			GraphNode current = null;
			int smallestDistance = Integer.MAX_VALUE;

			for (GraphNode node : nodeList) {
				if (!visited.contains(node) && distances.get(node) < smallestDistance) {
					smallestDistance = distances.get(node);
					current = node;
				}
			}

			if (current == null) {
				break;
			}

			if (current.getValue().equals(end.getValue())) {
				return distances.get(current);
			}

			visited.add(current);

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					int newDistance = distances.get(current) + current.getDistanceToNeighbor(neighbor);

					if (newDistance < distances.get(neighbor)) {
						distances.put(neighbor, newDistance);
					}
				}
			}
		}

		return -1;
	}

}
