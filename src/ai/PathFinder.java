package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gamepanel;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<Node>();
    public ArrayList<Node> pathList = new ArrayList<Node>();
    Node startNode, endNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp)
    {
        this.gamepanel = gp;
        instantiateNode();
    }
    public void instantiateNode()
    {
        node = new Node[gamepanel.maxWorldCol][gamepanel.maxWorldRow];

        int col = 0, row = 0;

        while(col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow)
        {
            node[col][row] = new Node(col, row);

            col++;
            if(col == gamepanel.maxWorldRow)
            {
                row++;
                col = 0;
            }
        }
    }
    public void resetNodes()
    {
        int col = 0, row = 0;
        while(col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow)
        {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].parent = null;
            col++;
            if(col == gamepanel.maxWorldRow)
            {
                row++;
                col = 0;
            }
        }
        // Reset other variables
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int endCol, int endRow, Entity entity)
    {
        resetNodes();
        startNode = node[startCol][startRow];
        endNode = node[endCol][endRow];
        startNode.open = true;
        openList.add(startNode);
        currentNode = startNode;

        int col = 0, row = 0;
        while(col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow)
        {
            // set solid nodes
            // check tiles
            int tileNum = gamepanel.tileM.mapTileNum[gamepanel.currentMap][col][row];
            if(gamepanel.tileM.tile[tileNum].collision)
            {
                node[col][row].solid = true;
            }
            // check interactive tiles
            for(int i = 0; i < gamepanel.interactiveTile[1].length; i++)
            {
                if(gamepanel.interactiveTile[gamepanel.currentMap][i] != null && gamepanel.interactiveTile[gamepanel.currentMap][i].destructible)
                {
                    int interactiveTileCol = gamepanel.interactiveTile[gamepanel.currentMap][i].worldX / gamepanel.tileSize;
                    int interactiveTileRow = gamepanel.interactiveTile[gamepanel.currentMap][i].worldY / gamepanel.tileSize;
                    node[interactiveTileCol][interactiveTileRow].solid = true;
                }
            }

            // Set cost
            getCost(node[col][row]);

            col++;
            if(col == gamepanel.maxWorldRow)
            {
                row++;
                col = 0;
            }
        }
    }
    public void getCost(Node node)
    {
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - endNode.col);
        yDistance = Math.abs(node.row - endNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search()
    {
        while(goalReached == false && step < 500) // 5000 is limit
        {
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if(row - 1 >= 0)
            {
                openNode(node[col][row - 1]);
            }
            // Open the Down node
            if(row + 1 < gamepanel.maxWorldRow)
            {
                openNode(node[col][row + 1]);
            }
            // Open the Left node
            if(col - 1 >= 0)
            {
                openNode(node[col - 1][row]);
            }
            // Open the Right node
            if(col + 1 < gamepanel.maxWorldCol)
            {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 0;

            for(int i = 0; i < openList.size(); i++) {

                // Check if this node's F cost is less than the best node's F cost
                if(openList.get(i).fCost < bestNodefCost)
                {
                    bestNodefCost = openList.get(i).fCost;
                    bestNodeIndex = i;
                }
                // if the F cost is the same, check the G cost
                else if(openList.get(i).fCost == bestNodefCost)
                {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                    {
                        bestNodeIndex = i;
                    }
                }
            }
            // If there is no node to open
            if(openList.isEmpty())
            {
                break;
            }

            // After the loop, openList[bestNodeIndex] is the next step node
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == endNode)
            {
                goalReached = true;
                tracePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node)
    {
        if(!node.open && !node.checked && !node.solid)
        {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void tracePath()
    {
        Node current = endNode;
        while(current != startNode)
        {
            pathList.add(0, current);
            current = current.parent;
        }
    }

}
