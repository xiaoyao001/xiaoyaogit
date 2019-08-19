package com.boot.org.test;

/**
 * 二叉搜索树
 * @author Administrator
 *
 */
public class BinarySearchTree {

	int data;
	BinarySearchTree left;
	BinarySearchTree right;
	
	public BinarySearchTree(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
	
	public void insert(BinarySearchTree root,int data) {
		if(root.data < data) {
			if(root.right == null) {
				root.right = new BinarySearchTree(data);
			}else {
				insert(root.right, data);
			}
		}else {
			if(root.left == null) {
				root.left = new BinarySearchTree(data);
			}else {
				insert(root.left, data);
			}
		}
	}
	
	
	public void middle(BinarySearchTree root) {
		if(root != null) {
			middle(root.left);
			System.out.println(root.data+"   ");
			middle(root.right);
		}
	}
	
	public static void main(String[] args) {
		int data[] = {5,9,10,3,6,4,8};
		BinarySearchTree tree = new BinarySearchTree(data[0]);
		for(int i =1;i<data.length;i++) {
			tree.insert(tree, data[i]);
		}
		System.out.println("中序遍历：：：：");
		tree.middle(tree);
	}
}
