package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//////////////////////////////////////////////////////////
/////////////////// Interfaces //////////////////////////
////////////////////////////////////////////////////////
interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

interface Block {
    String getColor();

    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}



//////////////////////////////////////////////////////////
/////////////////// Wall class //////////////////////////
////////////////////////////////////////////////////////
@NoArgsConstructor
@AllArgsConstructor
public class Wall implements Structure {
    private List<Block> blocks;

    public void assignTestValues(){
        blocks = new ArrayList<>();

        blocks.add(new BlockImpl("Red", "Glass"));
        blocks.add(new BlockImpl("Blue", "Concrete"));

        blocks.add(new CompositeBlockImpl("Green", "Paper", 5));
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream().flatMap(b->blockUtils.toFlatMap(b)).filter(b->b.getColor().equals(color)).findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return getBlocksFlatStream().filter(b->b.getMaterial().equals(material)).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return getBlocksFlatStream().collect(Collectors.toList()).size();
    }

    private Stream<Block> getBlocksFlatStream(){
        return blocks.stream().flatMap(b->blockUtils.toFlatMap(b));
    }



    //////////////////////////////////////////////////////////
    /////////////////// private classes /////////////////////
    ////////////////////////////////////////////////////////
    private static class blockUtils{
        static Stream<Block> toFlatMap(Block b){
            if(b instanceof CompositeBlockImpl) return ((CompositeBlockImpl) b).getBlocks().stream();
            return Stream.of(b);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private class BlockImpl implements Block{
        protected String color;
        protected String material;
    }


    @Getter
    private class CompositeBlockImpl extends BlockImpl implements CompositeBlock {
        private List<Block> blocks;

        CompositeBlockImpl(String color, String material, int size){
            this.color = color;
            this.material= material;

            List<Block> blocks = new ArrayList<>();
            for(int i=0; i<size; i++){
                blocks.add(new BlockImpl(color,material));
            }
            this.blocks=blocks;
        }

    }
}

