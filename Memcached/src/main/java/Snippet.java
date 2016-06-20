
import java.io.IOException;
import java.net.InetSocketAddress;
import net.spy.memcached.MemcachedClient;


public class Snippet {
    
    
    private MemcachedClient cache;
    
    public MemcacheCrud()throws IOException{
        cache=new MemcachedClient(new InetSocketAddress("localhost", 11211));

    }
    
    public void put(final Object key, final Object value) {
        // Store a value  for 24 hour
    cache.set(key.toString(), 24 * 3600, value);
  }
    
    public Object retrieve(final String key){
        return cache.get(key);
    }
    
     public void update(final Object key, final Object value) {
        // replace a value and store for 1 hour
    cache.set(key.toString(), 3600, value);
  }


  public void delete(final Object key) {
    cache.delete(key.toString());
  }

  public void clear(){
      cache.flush();
  }
  
    public static void main(String[] args)throws Exception {
        
        MemcacheCrud mc=new  MemcacheCrud();
        
        mc.put("name", "javaguy");
        
        System.out.println(mc.retrieve("name"));
        
        mc.update("name","expertjjava");
         System.out.println(mc.retrieve("name"));
         
       mc.delete("name");
       
       mc.clear();
        
    }

}
