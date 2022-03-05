package edu.asu.mars.admin;

import org.apache.commons.cli.*;

import java.util.List;
import java.util.Scanner;

public class Tools {

    public static void main(String[] args) {
        CommandLineParser parser = new PosixParser();
        OptionGroup group = new OptionGroup();
        group.setRequired(true);
        Options options = new Options();
        group.addOption(OptionBuilder.withLongOpt("flock").withDescription("test lock on <FILE>").hasOptionalArg().withArgName("FILE").create('f'));
        group.addOption(OptionBuilder.withLongOpt("mac-dir").withDescription("try to prompt user for access to <Directory>").hasOptionalArg().withArgName("Directory").create('d'));
        group.addOption(OptionBuilder.withLongOpt("jfc-dir").withDescription("try to prompt user for access to <Directory> using JFileChooser").hasOptionalArg().withArgName("Directory").create('j'));
        group.addOption(OptionBuilder.withLongOpt("property").withDescription("print all system properties, or just <PROPERTY>").hasOptionalArg().withArgName("PROPERTY").create("p"));
        group.addOption(OptionBuilder.withLongOpt("libs").withDescription("print all system libraries").create("l"));
        group.addOption(OptionBuilder.withLongOpt("env").withDescription("print entire environment, or just <ENV>").hasOptionalArg().withArgName("ENV").create("e"));
        group.addOption(OptionBuilder.withLongOpt("max").withDescription("test max open files").create("m"));
        group.addOption(OptionBuilder.withLongOpt("sec").withDescription("list java security parameters").create("s"));
        group.addOption(OptionBuilder.withLongOpt("nl").withDescription("list loaded java native libraries").create("n"));
        group.addOption(OptionBuilder.withLongOpt("fonts").withDescription("show all system fonts").create("F"));
        group.addOption(OptionBuilder.withLongOpt("max-mem").withDescription("attempt to allocate as much memory as possible (DANGEROUS!)").create("M"));
        group.addOption(OptionBuilder.withLongOpt("zzmem").withDescription("real max-mem (don't run!!!  it will crash computer!)").create("z"));
        group.addOption(OptionBuilder.withLongOpt("ram").withDescription("test filling ram").create("r"));
        group.addOption(OptionBuilder.withLongOpt("cpu").withDescription("Runs a CPU intensive task for <SEC> seconds").hasArgs().withArgName("SEC").create("c"));
//        group.addOption(OptionBuilder.withLongOpt("date").withDescription("date testing").create("d"));   //Not entirely sure what this was for, maybe leap seconds?
        options.addOptionGroup(group);

        try {
            CommandLine line = parser.parse(options, args, true);
            if (line.getArgs().length > 0) {
                throw new ParseException("Only 1 argument allowed");
            }
            if (line.hasOption("property")) {
                if (line.getOptionValue("property") != null) {
                    new Properties().printProperties(line.getOptionValue("property"));
                } else {
                    new Properties().printProperties();
                }
            }
            if (line.hasOption("libs")) {
                new SystemLibs().printLibs();
                System.out.println("libs have been printed");
            }
            if (line.hasOption("nl")) {
                try {
                    List<String> list = new NativeLibraries().getLoadedLibraries();
                    for (String s : list) {
                        System.out.println(s);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
//                System.out.println("libs have been printed");
            }
            if (line.hasOption("env")) {
                if (line.getOptionValue("env") != null) {
                    new SystemEnv().printEnv(line.getOptionValue("env"));
                } else {
                    new SystemEnv().printEnv();
                }
            } else if (line.hasOption("flock")) {
                if (line.getOptionValue("flock") != null) {
                    new FileLockTest().testLock(line.getOptionValue("flock"));
                } else {
                    new FileLockTest().testLock();
                }
            } else if (line.hasOption("cpu")) {
                new CpuTest().runTest(Integer.valueOf(line.getOptionValue("cpu")));
            } else if (line.hasOption("max")) {
                new OpenFilesLimit().findOpenFilesLimit();
            } else if (line.hasOption("sec")) {
                new SecurityProps().listProviders();
            } else if (line.hasOption("ram")) {
                new MaxMemTest().MaxRamMemTest();
            } else if (line.hasOption("max-mem")) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("This mode is dangerous and could lead to a system crash!  Type 'YES' to continue: ");
                String answer = scanner.nextLine();
                scanner.close();
                if (answer.equals("YES")) {
                    new MaxMemTest().FakeMaxMemTest();
                } else {
                    System.err.println("Program Aborted!");
                    System.exit(0);
                }
            } else if (line.hasOption("zzmem")) {
                new MaxMemTest().RealMaxMemTest();
            } else if (line.hasOption("date")) {
                new DateTest();
            } else if (line.hasOption("fonts")) {
                new FontList().showFonts();
            } else if (line.hasOption("mac-dir")) {
                new MacDirectoryAccess().testDirectory(line.getOptionValue("mac-dir"));
            } else if (line.hasOption("jfc-dir")) {
                new MacDirectoryAccess().testWithJFileChooser(line.getOptionValue("jfc-dir"));
            }
        } catch (ParseException pex) {
//            System.err.println(pex.getMessage());
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(100);
        Options helpOptions = new Options();
        for (Object opt : options.getOptions()) {
            Option thisOpt = (Option) opt;
            if (!thisOpt.getOpt().equals("z")) {
                helpOptions.addOption((Option) opt);
            }
        }
        formatter.printHelp("java -jar java_admin_tools.jar", helpOptions, false);
    }
}
