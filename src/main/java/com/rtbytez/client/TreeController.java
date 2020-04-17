package com.rtbytez.client;

import com.intellij.icons.AllIcons;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class TreeController {
    private JTree tree;
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    private DefaultMutableTreeNode files = new DefaultMutableTreeNode("Files");
    private DefaultMutableTreeNode members = new DefaultMutableTreeNode("Members");
    private DefaultTreeModel treeModel;

    public TreeController(JTree tree) {
        this.tree = tree;
    }

    public void setupTree() {
        root.add(new DefaultMutableTreeNode(new CustomTreeNode("Files", AllIcons.Nodes.CopyOfFolder)));
        root.add(new DefaultMutableTreeNode(new CustomTreeNode("Members", AllIcons.General.User)));
        treeModel = new DefaultTreeModel(root);
        class NodeTreeCellRenderer implements TreeCellRenderer {
            private JLabel label;

            NodeTreeCellRenderer() {
                label = new JLabel();
            }

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Object o = ((DefaultMutableTreeNode) value).getUserObject();
                if (o instanceof CustomTreeNode) {
                    CustomTreeNode customTreeNode = (CustomTreeNode) o;
                    label.setIcon(customTreeNode.getIcon());
                    label.setText(customTreeNode.getName());
                }
                return label;
            }
        }
        tree.setCellRenderer(new NodeTreeCellRenderer());
        tree.setModel(treeModel);
    }

    public void addFile(String filename) {
        files.add(new DefaultMutableTreeNode(filename));
        treeModel.reload();
    }

    public void addMember(String membername) {
        members.add(new DefaultMutableTreeNode(membername));
        treeModel.reload();
    }

    public void deleteMember(String membername) {
        DefaultMutableTreeNode curNode = members.getNextNode();
        while (!curNode.toString().equals(membername)) {
            curNode = members.getNextNode();
        }
        members.remove(curNode);
    }

    public void deleteFile(String filename) {

        DefaultMutableTreeNode curNode = files.getNextNode();
        while (!curNode.toString().equals(filename)) {
            curNode = files.getNextNode();
        }
        files.remove(curNode);
    }
}
