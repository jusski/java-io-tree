import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
	private static BufferedWriter fileWriter;
	
	public static void printEntries(File entry, String prefix) throws IOException
	{
		List<File> files = Arrays.asList(entry.listFiles()).stream().filter(File::isFile).filter(e -> !e.isHidden())
				.sorted().collect(Collectors.toList());

		List<File> directories = Arrays.asList(entry.listFiles()).stream().filter(File::isDirectory)
				.filter(e -> !e.isHidden()).sorted().collect(Collectors.toList());

		if (directories.size() == 0)
		{
			printFiles(files, prefix);
		}
		else
		{
			printFiles(files, prefix + "  │");
		}

		printDirectories(directories, prefix + "  ");

	}

	public static void printFiles(List<File> files, String prefix) throws IOException
	{
		String fileNamePrefix = "    ";
		if (!files.isEmpty())
		{
			for (File file : files)
			{
				System.out.println(prefix + fileNamePrefix + file.getName());
				fileWriter.write(prefix + fileNamePrefix + file.getName());fileWriter.newLine();
			}
			System.out.println(prefix); 
			fileWriter.write(prefix);fileWriter.newLine();
		}

	}

	public static void printDirectories(List<File> directories, String prefix) throws IOException
	{
		String directoryNamePrefix = "├───";
		Iterator<File> iterator = directories.iterator();
		if (iterator.hasNext())
		{
			for (int i = 0; i < directories.size() - 1; ++i)
			{
				File directory = iterator.next();
				System.out.println(prefix + directoryNamePrefix + directory.getName());
				fileWriter.write(prefix + directoryNamePrefix + directory.getName());fileWriter.newLine();
				printEntries(directory, prefix + "│" + " ");
			}
			File directory = iterator.next();
			printLastDirectory(directory, prefix);
		}
	}

	public static void printLastDirectory(File directory, String prefix) throws IOException
	{
		String directoryNamePrefix = "└───";
		System.out.println(prefix + directoryNamePrefix + directory.getName());
		fileWriter.write(prefix + directoryNamePrefix + directory.getName());fileWriter.newLine();
		printEntries(directory, prefix + "  ");
	}

	public static void main(String[] args) throws IOException
	{
		String path = "C:/epam/exceptions".replace('/', File.separatorChar);
		File file = new File(path);
		fileWriter = Files.newBufferedWriter(Paths.get("ky123.txt"), StandardCharsets.UTF_16, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		printEntries(file, "");
		fileWriter.flush();
		fileWriter.close();
	

	}

}
