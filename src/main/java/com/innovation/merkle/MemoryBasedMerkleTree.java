package com.innovation.merkle;

import com.innovation.model.MerkleNode;
import com.innovation.model.MerkleTreeException;
import com.innovation.util.MerkleTreeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MemoryBasedMerkleTree<T> implements MerkleTree {

    List<T> objectList;
    Comparator<T> comparator;
    MerkleNode node = null;

    MemoryBasedMerkleTree(Builder builder) throws MerkleTreeException {
        this.objectList = builder.objectList;
        this.comparator = builder.comparator;
        validate(this);
        this.node = constructMerkleTree();
    }

    @Override
    public boolean isIdentical(MerkleTree tree) {
        return checkEqality(this.node, tree.getNode());
    }

    private boolean checkEqality(MerkleNode current, MerkleNode compare){
        if(current == null && compare == null){
            return true;
        }
        if((current == null && compare != null) || (current != null && compare == null)){
            return false;
        }
        if(!Arrays.equals(current.getValue(), compare.getValue())){
            return false;
        }else{
            return checkEqality(current.getLeftChild(), compare.getLeftChild()) &&
                checkEqality(current.getRightChild(), compare.getRightChild());
        }
    }

    public static class Builder<T> {
        List<T> objectList;
        Comparator<T> comparator;
        private Builder() {};
        public static Builder create(){
            return new Builder();
        }

        public Builder<T> keySpaces(List<T> objectList){
            this.objectList = objectList;
            return this;
        }

        public Builder<T> comparator(Comparator<T> comparator){
            this.comparator = comparator;
            return this;
        }
        public MemoryBasedMerkleTree build() throws MerkleTreeException {
            return new MemoryBasedMerkleTree(this);
        }

    }


    public boolean validate(MemoryBasedMerkleTree obj) throws MerkleTreeException {
        if(obj.objectList.size() < 1){
            throw new MerkleTreeException("Tree can't be constructed without any elements");
        }
        T curObj = objectList.get(0);
        if(!curObj.getClass().isPrimitive() && !MerkleTreeUtil.isWrapperClass(curObj)){
            throw new MerkleTreeException("Please set comparator property for the custom object");
        }
        return true;
    }

    private MerkleNode constructMerkleTree(){
        T obj = this.objectList.get(0);
        if(obj instanceof String){
            Collections.sort((List<String>)this.objectList);
        }else if(obj instanceof Short){
            Collections.sort((List<Short>)this.objectList);
        }else if(obj instanceof Boolean){
            Collections.sort((List<Boolean>)this.objectList);
        }else if(obj instanceof Character) {
            Collections.sort((List<Character>)this.objectList);
        }else if(obj instanceof Integer){
            Collections.sort((List<Integer>)this.objectList);
        }else if(obj instanceof Long){
            Collections.sort((List<Long>)this.objectList);
        }else if(obj instanceof BigDecimal){
            Collections.sort((List<BigDecimal>)this.objectList);
        }else if(obj instanceof Double){
            Collections.sort((List<Double>)this.objectList);
        }else{
            Collections.sort(this.objectList, this.comparator);
        }
        return buildNodes(this.objectList);
    }

    private MerkleNode buildNodes(List<T> objectList){
        if(objectList.size() == 0){
            return null;
        }
        if(objectList.size() == 1){
            return new MerkleNode().buildNode(hashify(serialize(objectList.get(0))), null, null);
        }
        int segment = objectList.size()/2;
        List<T> leftObjList = objectList.subList(0, segment);
        MerkleNode left = buildNodes(leftObjList);
        List<T> rightObjList = objectList.subList(segment, objectList.size());
        MerkleNode right = buildNodes(rightObjList);
        byte[] parentHash = hashify(MerkleTreeUtil.concat(left.getValue(), right.getValue()));
        MerkleNode parent = new MerkleNode().buildNode(parentHash, left, right);
        return parent;
    }



    @Override
    public MerkleNode reconstruct(List objectList) {
        return buildNodes(objectList);
    }

    private static byte[] hashify(byte[] buffer) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(buffer);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> byte[] serialize(T object){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return bytes;
    }

    @Override
    public MerkleNode getNode() {
        return node;
    }

    @Override
    public void prettyPrintTree() {
        StringBuilder stringBuilder =new StringBuilder();
        traversePreOrder(stringBuilder, "", "", this.node);
        System.out.println(stringBuilder);
    }

    public void traversePreOrder(StringBuilder sb, String padding, String pointer, MerkleNode node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.getValue());
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.getRightChild() != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.getLeftChild());
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.getRightChild());
        }
    }
}
