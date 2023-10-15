package com.innovation.model;

public class MerkleNode {
    private byte[] value;
    private MerkleNode leftChild;
    private MerkleNode rightChild;

    public MerkleNode buildNode(byte[] value, MerkleNode leftChild, MerkleNode rightChild){
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        return this;
    }

    public byte[] getValue() {
        return value;
    }

    public MerkleNode getLeftChild() {
        return leftChild;
    }

    public MerkleNode getRightChild() {
        return rightChild;
    }
}
