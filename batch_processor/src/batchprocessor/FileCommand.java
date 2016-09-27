package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/
import java.util.Map;

import org.w3c.dom.Element;

//this class is used to parse and execute the file command
public class FileCommand extends Command 
{
	
	//method describing what the current command is doing
	@Override
	public String describe() 
	{
		System.out.println("");
		String s= "FileCommand execution has started";
		return s;
	}

	//method to execute the respective file command
	@Override
	public void execute(String workingDir, Map<String, Command> Commands ) 
	{
	

		System.out.println("File is being executed");

		System.out.println("ID of the file command being executed is "+comm_id);

		System.out.println("path of the file is set as "+comm_path);
		
		System.out.println("execution of the file command has finished");

	}
	
	//method to parse the respective file command 
	@Override
	public  void parse(Element elem) throws ProcessException 
	{
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in File Command");
		}
		System.out.println("ID: " + id);
		
		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in File Command");
		}
		System.out.println("Path: " + path);
		//setting instance variables of the command class after parsing
		//these instance variables will be used for the execution of the command
		comm_id=id;
		comm_path=path;
	}
}
