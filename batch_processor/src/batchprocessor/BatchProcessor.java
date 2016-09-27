package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/


import java.io.File;
import java.util.Map;


/**This is the main class of the program. It performs two key operations. 
* The first is the building of the batch for execution of the input xml file.
* This is done my creating an object of the BatchParser class
* The second operation is to execute this batch which is created.
* This is done with the help of the execute batch method.
*/
public class BatchProcessor 
{

	public static void main(String[] args)
	{
		
		try
		{
			String fileName =  args[0];
			
			//building the batch
			BatchParser batchParser = new BatchParser();
			Batch batch;
			File f = new File(fileName);
			batch=batchParser.buildBatch(f);
			
			System.out.println("##########################################");
			System.out.println("Batch execution has started");
			
			//executing the batch
			executeBatch(batch);
			
			
			System.out.println("Batch execution has finished");

		}
		catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("BatchProcessor File Not Found");
			e.printStackTrace();   
        }
		
	} 
	//method to execute the created batch. Batch is passed as an argument.
	public static void executeBatch(Batch batch)
	{
		
		Map<String, Command> commandlist;
		commandlist=batch.getCommands();
		
		System.out.println("List of Commands in the Batch are");
       
        for(String key : commandlist.keySet())
        {
            System.out.println(key.toString());
        }
		for(String key : commandlist.keySet())
		{
			System.out.println(commandlist.get(key).describe());
			commandlist.get(key).execute(batch.getWorkingDir(),batch.getCommands());
			System.out.println("##########################################");


		}
		
	}
}
