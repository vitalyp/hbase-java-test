/**
 * Hadoop/HBase module. Check HBase performance.
 * 
 * @Requirements:
 * 
 * Java module, that inserts records with unique hash-based key
 * Table fields: f1, f2 .. f50
 * Fields data: random strings with chars length:  1..200 
 * Before each INSERT, search for existing KEY in HBase
 
 * @Test:
 *   1. INSERT speed per second, based on database size 
 *   2. Excel report speed/db size 
 * 
 * HBase records size: 100 millions .. memory ends. 
 *
 *
 * @Prerequirements:
 *  
 * Start HBase
 *   > ./bin/start-hbase.sh
 * Start client
 *   > 
 * Create table 'htest'
 *   > create 'htest', 'f1', 'f2', 'f3', 'f4', 'f5', 'f6', 'f7', 'f8', 'f9', 'f10', 'f11', 'f12', 'f13', 'f14', 'f15', 'f16', 'f17', 'f18', 'f19', 'f20', 'f21', 'f22', 'f23', 'f24', 'f25', 'f26', 'f27', 'f28', 'f29', 'f30', 'f31', 'f32', 'f33', 'f34', 'f35', 'f36', 'f37', 'f38', 'f39', 'f40', 'f41', 'f42', 'f43', 'f44', 'f45', 'f46', 'f47', 'f48', 'f49', 'f50'
 * Truncate table 'htest' if exists:
 *   > truncate 'htest' 
 *
 * @author VitalyP
 * */

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.Time;

import com.utils.Utils;


public class HBaseTest {
	
	public static void main(String[] args) throws IOException {
		
		// HBase configuration
		Configuration config = HBaseConfiguration.create();
		HTable table = new HTable(config, "htest");
				 
		long startLoop = Time.now();
		long prevLoop = Time.now();
		long insertsCount = 100000000;
		long logFloor = 10000;
		
		Utils.log("\n\n\n NEW TEST with " + insertsCount + " inserts!!" + "\n Started at" + Time.now());
				
		for (long i=0; i<insertsCount; i++) {
			String key = Utils.intToHash(i);			
			Get g = new Get(Bytes.toBytes(key));
			table.get(g);			
			Put p = new Put(Bytes.toBytes(key));			
			for (int f=1; f<=50; f++) {
				p.add(Bytes.toBytes("f"+f), Bytes.toBytes("data"), Bytes.toBytes(Utils.generateString()));	
			}			 
			table.put(p);
			if (i%logFloor == 0) {
				
				String result_str = "i[" + i + "] = " + (Time.now() - startLoop) + " milliseconds from beginning, from last: (" + (Time.now() - prevLoop) + ")";
				System.out.println(result_str);
				Utils.log(result_str);
				prevLoop  = Time.now();
			}
		}		
		String result_str = "Loop ends in: " + (Time.now() - startLoop); 
		System.out.print(Utils.log(result_str));
		table.close();
	}
}
