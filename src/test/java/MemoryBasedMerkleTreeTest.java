import com.innovation.merkle.MemoryBasedMerkleTree;
import com.innovation.merkle.MerkleTree;
import com.innovation.model.MerkleTreeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MemoryBasedMerkleTreeTest {

    @Test
    public void createTreeTest(){
        List<String> keyspace = new ArrayList<>();
        keyspace.add("Apple");
        keyspace.add("Banana");
        keyspace.add("Dates");
        keyspace.add("PineApple");
        keyspace.add("Kiwi");
        keyspace.add("Orange");
        keyspace.add("StarFruit");
        keyspace.add("WoodApple");
        keyspace.add("Guava");
        MerkleTree tree = null;
        try {
            tree = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace).build();
            tree.prettyPrintTree();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }
        assertNotNull(tree);
    }

    @Test
    public void identicalTreeTest(){
        List<String> keyspace = new ArrayList<>();
        keyspace.add("Apple");
        keyspace.add("Banana");
        keyspace.add("Dates");
        keyspace.add("PineApple");
        keyspace.add("Kiwi");
        keyspace.add("Orange");
        keyspace.add("StarFruit");
        keyspace.add("WoodApple");
        keyspace.add("Guava");
        MerkleTree tree = null;
        try {
            tree = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace).build();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }

        MerkleTree tree1 = null;
        try {
            tree1 = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace).build();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }
        boolean result = tree.isIdentical(tree1);
        assertEquals(result, true);
    }

    @Test
    public void identicalTreeTestFailure(){
        List<String> keyspace = new ArrayList<>();
        keyspace.add("Apple");
        keyspace.add("Banana");
        keyspace.add("Dates");
        keyspace.add("PineApple");
        keyspace.add("Kiwi");
        keyspace.add("Orange");
        keyspace.add("StarFruit");
        keyspace.add("WoodApple");
        keyspace.add("Guava");
        MerkleTree tree = null;
        try {
            tree = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace).build();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }

        List<String> keyspace1 = new ArrayList<>();
        keyspace1.add("Apple");
        keyspace1.add("Banana");
        keyspace1.add("Dates");
        keyspace1.add("PineApple");
        keyspace1.add("Kiwi");
        keyspace1.add("Orange");
        keyspace1.add("StarFruit");
        keyspace1.add("WoodApple");
        keyspace1.add("RipeGuava");

        MerkleTree tree1 = null;
        try {
            tree1 = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace1).build();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }
        boolean result = tree.isIdentical(tree1);
        assertEquals(result, false);
    }

    @Test
    public void performReconstructionTest(){

        List<String> keyspace = new ArrayList<>();
        keyspace.add("Apple");
        keyspace.add("Banana");
        keyspace.add("Dates");
        keyspace.add("PineApple");
        keyspace.add("Kiwi");
        keyspace.add("Orange");
        keyspace.add("StarFruit");
        keyspace.add("WoodApple");
        keyspace.add("Guava");
        MerkleTree tree = null;
        try {
            tree = MemoryBasedMerkleTree.Builder.create().keySpaces(keyspace).build();
        }catch (MerkleTreeException e){
            System.out.println(e);
        }

        keyspace.add("Peach");
        tree.reconstruct(keyspace);
        tree.prettyPrintTree();
    }
}
