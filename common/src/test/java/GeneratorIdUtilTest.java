import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import com.mit.common.util.GeneratorIdUtil;

public class GeneratorIdUtilTest {
	//用来测试是否产生重复id
	public static ConcurrentHashMap<String, Map<Long, Integer>> size = new ConcurrentHashMap<>();
	
	//用来测试均匀度
	public static Map<String, Map<Character, Integer>> m = new ConcurrentHashMap<>();
	
	public static CountDownLatch c = new CountDownLatch(10000);
	
	/**
	 * 
	 * 获取现在到2018/08/21的毫秒数 @param @return @return long @throws
	 */
	private static long getMil() {
		LocalDateTime ldt = LocalDateTime.of(2018, 8, 21, 0, 0, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Shanghai"));
		long millis = zdt.toInstant().toEpochMilli();
		long now = System.currentTimeMillis();
		long d = now - millis;
		return d;
	}
	/**
	 * 
	* 性能测试
	* @param 
	* @return void
	* @throws
	 */
//	@Test
	public void testPerformance(){
		GeneratorIdUtil g = new GeneratorIdUtil();
		long a = System.currentTimeMillis();
		for(int i = 0; i < 10000000; i++){
			Long gs = g.generatorId("test");
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}
	/**
	 * 
	* 均匀度和是否产生重复id测试
	* @param 
	* @return void
	* @throws
	 */
//	@Test
	public void testEvenly(){
		long start = System.currentTimeMillis();
		int j = 10;
		for(int i = 0; i < 10000; i++){
			if(i > 99  && i % 100 == 0){
				if(System.currentTimeMillis() - start < 1){
					System.out.println("冲突了" + i);
				};
				start = System.currentTimeMillis();
			}
			Thread t = new Thread(new Tt(c));
			t.start();
		}
		try {
			c.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
//		System.out.println("between:" + (end - start));
//		System.out.println(size.size());
		int value = 0;
		for (Entry<String, Map<Long,Integer>> string : size.entrySet()) {
			value += string.getValue().size();
		}
		System.out.println("总id数：" + value);
		/*for (Entry<String, Map<Character, Integer>> string : m.entrySet()) {
			String table = string.getKey();
			Map<Character, Integer> value2 = string.getValue();
			System.out.println(table + value2);
		}*/
	}
	
	class Tt implements Runnable{
		String[] sa = {"a", "b", "c", "d", "e", "f", "g", "k", "l", "m"};
		CountDownLatch c;
		public Tt(CountDownLatch c){
			this.c = c;
		}
		@Override
		public void run() {
			String table = sa[(int)(Math.random() * 10)];
			Long generatorId = GeneratorIdUtil.generatorId(table);
//			System.out.println(table + ":" + generatorId);
//			Long generator}Id = GeneratorIdUtil.generatorId("user");
			synchronized (size) {
				if(size.get(table) == null){
					Map<Long, Integer> s = new ConcurrentHashMap<>();
					s.put(generatorId, 1);
					size.put(table, s);
				}else{
					Map<Long, Integer> map = size.get(table);
					if(map.get(generatorId) != null){
						System.out.println("冲突" + table + generatorId);
					}
					map.put(generatorId, 1);
				}
			}
			String string = generatorId.toString();
			char last = string.charAt(string.length() - 1);
			synchronized (m) {
				if(null == m.get(table)){
					Map<Character, Integer> m1 = new ConcurrentHashMap<>();
					m1.put(last, 1);
					m.put(table, m1);
				}else{
					Map<Character, Integer> map = m.get(table);
					Integer t = map.get(last);
					if(t == null){
						map.put(last, 1);
					}else{
						map.put(last, ++t);
					}
				}
			}
			c.countDown();
		}
	}
}
