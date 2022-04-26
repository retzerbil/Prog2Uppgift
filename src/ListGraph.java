import java.util.*;

public class ListGraph<Thing>{

    Map<Thing, Set<Edge>> nodes = new HashMap<>();

    void add(Thing node){
        nodes.putIfAbsent(node, new HashSet<>());

    }

    void remove(Thing node) throws Exception{
        var test = nodes.get(node);
        if(test != null){
            nodes.remove(test);
        }
        else{
            throw new NoSuchElementException();
        }
    }

    void connect(){

    }

    void disconnect(){

    }

    void setConnectionWeight(){

    }

    void getNodes(){

    }

    void getEdgesFrom(){

    }

    void getEdgeBetween(){

    }

    //toString

    void pathExists(){

    }

    void getPath() {

    }

}
