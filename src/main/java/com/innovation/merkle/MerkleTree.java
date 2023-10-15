package com.innovation.merkle;

import com.innovation.model.MerkleNode;

import java.util.List;

public interface  MerkleTree<T> {
    boolean isIdentical(MerkleTree tree);
    MerkleNode reconstruct(List<T> objectList);
    MerkleNode getNode();
    void prettyPrintTree();
}
