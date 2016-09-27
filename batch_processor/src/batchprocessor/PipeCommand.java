package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//pipeCommand class has two behaviour methods 
//1. parse the pipe command from xml
//2. execute the parsed pipe command 

public class PipeCommand extends Command 
{
	//every pipe command has only two cmd commands. Hence objects of each cmd is created
	PipeCmdCommand pipeCmd1= new PipeCmdCommand();
	
	PipeCmdCommand pipeCmd2= new PipeCmdCommand();
	

	//method describes the pipe command
	@Override
	public String describe() 
	{
		System.out.println("");
		String s= "PipeCommand execution has started";
		return s;
	}
	//executing the pipe command
	@Override
	public void execute(String workingDir, Map<String, Command> batch_CommandList ) 

	{
		System.out.println("Pipe command is being executed");
		System.out.println("ID of the Pipe command being executed is "+ comm_id);
		


		try
		{
			List<String> command = new ArrayList<String>();
			
			command.add(pipeCmd1.comm_path);

			
			//adding commands (comm_args) to a list which would be fed to the processbuilder 
			for(int i=0;i<pipeCmd1.comm_args.size();i++)
			{
			command.add(pipeCmd1.comm_args.get(i));
			}
			
			//building the first process builder . 

			//describe pipecmd
			System.out.println("Executing the first cmd of pipe command with ID-"+pipeCmd1.comm_id);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.directory(new File(workingDir));
			File wd = builder.directory();
			final Process process1 = builder.start();

			OutputStream os = process1.getOutputStream();
			
			try
            {
                if(!(pipeCmd1.comm_in.isEmpty()||pipeCmd1.comm_in==null))
                {
                	
                	FileInputStream fis = new FileInputStream(new File(wd,batch_CommandList.get(pipeCmd1.comm_in).comm_path));
        	
                	command.add(batch_CommandList.get(pipeCmd1.comm_in).comm_path);
        	    	
        			copyStreams(fis, os);

                }
            }
            catch(NullPointerException e)
            {
                System.out.println("ERROR PROCESSING-While executing the first cmd command of pipe ,Unable to find Input file with ID " + pipeCmd1.comm_in);
                e.printStackTrace();
            }
			
			
			InputStream is = process1.getInputStream();
			System.out.println("Executing the second cmd of pipe command with ID-"+pipeCmd2.comm_id);
			List<String> command2 = new ArrayList<String>();
			
			command2.add(pipeCmd2.comm_path);

			//adding commands (comm_args) to a list which would be fed to the processbuilder 
			for(int i=0;i<pipeCmd2.comm_args.size();i++)
			{
			command2.add(pipeCmd2.comm_args.get(i));
			}
			

			//building the second process builder for executing the second cmd command of the pipe
			ProcessBuilder builder2 = new ProcessBuilder(command2);
			builder2.directory(new File(workingDir));
			File wd2 = builder2.directory();
			
			final Process process2 = builder2.start();
			
			OutputStream os2 = process2.getOutputStream();
			
			// copying the inputstream  of the first process to the outputstream of second process
			// The second process now gets its input from the output of the first process
			copyStreams(is, os2);  
			
			InputStream is2 = process2.getInputStream();
			try
            {
                if(!(pipeCmd2.comm_out.isEmpty()||pipeCmd2.comm_out==null))
                {

        			File outfile = new File(wd2,batch_CommandList.get(pipeCmd2.comm_out).comm_path);
        			
        	
        			FileOutputStream fos = new FileOutputStream(outfile);
        			copyStreams(is2, fos);


                }
            }
			catch(NullPointerException e)
            {
                System.out.println("ERROR PROCESSING-While executing the second cmd command of pipe ,Unable to find output file with ID " + pipeCmd2.comm_out);
                e.printStackTrace();
            }
			
			
		}
		catch(IOException e)
        {
            System.out.println("PipeCommand IOException");

        }
		System.out.println("Pipe Command has completed execution");

	}

	//method to parse the pipe command
	@Override
	public void parse(Element element) throws ProcessException 
	{
		
		String id = element.getAttribute("id");
		if (id == null || id.isEmpty()) 
		{
			throw new ProcessException("Missing ID in Pipe Command");
		}
		System.out.println("ID: " + id);
		comm_id=id;
		
		NodeList nodes = element.getElementsByTagName("cmd");

		Node node1 = nodes.item(0);
		if (node1.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node1;
			pipeCmd1.parse(elem);
		}
		Node node2 = nodes.item(1);
		if (node2.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) node2;
			pipeCmd2.parse(elem);
		}
	}
	/**
	 * Copying the contents of the input stream to the output stream in
	 * separate thread. The thread ends when an EOF is read from the 
	 * input stream.   
	 */
	static void copyStreams(final InputStream is, final OutputStream os) {
		Runnable copyThread = (new Runnable() {
			@Override
			public void run()
			{
				try {
					int achar;
					while ((achar = is.read()) != -1) {
						os.write(achar);
					}
					os.close();
				}
				catch (IOException ex) {
					throw new RuntimeException(ex.getMessage(), ex);
				}
			}
		});
		new Thread(copyThread).start();
	}

}
