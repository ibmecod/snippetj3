

import redis.clients.jedis.Jedis;


public class Snippet {

    private Jedis jedis;
    
   public RedisCrud(){
       jedis = new Jedis("localhost",2711);
   }
   
   public void store(String key,String value){
       jedis.set(key, value);
   }
   
   public String retrieve(String key){
      return  jedis.get(key);
   }
   
   public void update(String key,String newvalue){
       jedis.set(key, newvalue);
   }
   
   public void delete(String key){
       jedis.del(key);
   }
   
    public static void main(String[] args) {
        RedisCrud mc=new RedisCrud();
        
         mc.store("name", "javasnippet");
        System.out.println(mc.retrieve("name"));
        
        mc.update("name","expertjjava");
         System.out.println(mc.retrieve("name"));
         
       mc.delete("name");
        
    }
   
}
