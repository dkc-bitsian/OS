package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 *Krishna Chaitanya Reddy Dodda
*/

import java.util.List;

import org.w3c.dom.Element;
import java.util.Map;

/**This is the Command class and represents the general structure of the 
* various types of commands-pipe,wd,cmd,file which inherit from this parent class
*/

public abstract class Command {
	protected String comm_id;
	protected String comm_path;
	protected String comm_in;
	protected String comm_out;
	protected List <String> comm_args;

	
	public String getId()
	{
		return comm_id;
	}
	public String getPath()
	{
		return comm_path;
	}
	public String getIN()
	{
		return comm_in;
	}
	public String getOUT()
	{
		return comm_out;
	}
	public List<String> getArgs()
	{
		return comm_args;
	}
	public   String describe()
	{
		return "The command has started execution";
	}

	public  void execute(String workingDir,Map<String, Command> Commands)
	{
		
	}

	public  void parse(Element element)throws ProcessException 
	{
		
	}
}
