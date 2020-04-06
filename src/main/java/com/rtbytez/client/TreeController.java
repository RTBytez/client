package com.rtbytez.client;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
        root.add(files);
        root.add(members);
        treeModel = new DefaultTreeModel(root);
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
}
