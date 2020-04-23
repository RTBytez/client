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
    private DefaultMutableTreeNode files = new DefaultMutableTreeNode(new CustomTreeNode("Files", AllIcons.Nodes.CopyOfFolder));
    private DefaultMutableTreeNode members = new DefaultMutableTreeNode(new CustomTreeNode("Members", AllIcons.General.User));
    private DefaultTreeModel treeModel;

    public TreeController(JTree tree) {
        this.tree = tree;
    }

    public void setupTree() {
        root.add(members);
        root.add(files);
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
        files.add(new DefaultMutableTreeNode(new CustomTreeNode(filename, AllIcons.Nodes.CopyOfFolder)));
        treeModel.reload();
    }

    public void addMember(String membername) {
        members.add(new DefaultMutableTreeNode(new CustomTreeNode(membername, AllIcons.General.User)));
        treeModel.reload();
    }

    public void deleteMember(String membername) {
        DefaultMutableTreeNode curNode;
        for (int i = 0; i < members.getChildCount(); i++) {
            curNode = (DefaultMutableTreeNode) members.getChildAt(i);
            if (curNode.getUserObject() instanceof CustomTreeNode) {
                if (((CustomTreeNode) curNode.getUserObject()).getName().equals(membername)) {
                    members.remove(curNode);
                    break;
                }
            }
        }
    }

    public void deleteFile(String filename) {
        DefaultMutableTreeNode curNode;
        for (int i = 0; i < files.getChildCount(); i++) {
            curNode = (DefaultMutableTreeNode) files.getChildAt(i);
            if (curNode.getUserObject() instanceof CustomTreeNode) {
                if (((CustomTreeNode) curNode.getUserObject()).getName().equals(filename)) {
                    files.remove(curNode);
                    break;
                }
            }
        }
    }
}