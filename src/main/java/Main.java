import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
	public static void printEntries(File entry, String prefix)
	{
		List<File> files = Arrays.asList(entry.listFiles()).stream().filter(File::isFile).sorted()
				.collect(Collectors.toList());

		List<File> directories = Arrays.asList(entry.listFiles()).stream().filter(File::isDirectory)
				.filter(e -> !e.getName().startsWith(".git")).sorted().collect(Collectors.toList());
		if (directories.size() == 0)
		{
			printFiles(files, prefix);
		}
		else
		{
			printFiles(files, prefix + "  │");
		}

		if (directories.size() > 1)
		{
			printDirectories(directories, prefix + "  ");

		}
		else
		{
			printDirectory(directories, prefix + "  ");
		}
	}

	public static void printFiles(List<File> files, String prefix)
	{
		String fileNamePrefix = "    ";
		Iterator<File> iterator = files.iterator();
		if (files.size() > 0)
		{
			for (int fileIndex = 0; fileIndex < files.size(); ++fileIndex)
			{
				File file = iterator.next();
				System.out.println(prefix + fileNamePrefix + file.getName());

			}
			System.out.println(prefix);
		}

	}

	public static void printDirectories(List<File> directories, String prefix)
	{
		String directoryNamePrefix = "├───";
		Iterator<File> iterator = directories.iterator();
		for (int fileIndex = 0; fileIndex < directories.size() - 1; ++fileIndex)
		{
			File directory = iterator.next();
			System.out.println(prefix + directoryNamePrefix + directory.getName());
			printEntries(directory, prefix + "│" + "  ");
		}
		File directory = iterator.next();
		System.out.println(prefix + "└───" + directory.getName());
		printEntries(directory, prefix + "  ");
	}

	public static void printDirectory(List<File> directories, String prefix)
	{
		String directoryNamePrefix = "└───";
		for (File directory : directories)
		{
			System.out.println(prefix + directoryNamePrefix + directory.getName());
			printEntries(directory, prefix + "  ");
		}
	}

	public static void main(String[] args)
	{
		String path = "C:/epam/calculator".replace('/', File.separatorChar);
		File file = new File(path);

		printEntries(file, "");

	}

}
