import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: Driver <input path> <intermediate output path> <final output path>");
            System.exit(-1);
        }

        int exitCode = run(args[0], args[1], args[2]);
        System.exit(exitCode);
    }

    public static int run(String inputPath, String intermediatePath, String outputPath) throws Exception {
        Configuration conf = new Configuration();

        // First Job: Calculate average ratings
        Job avgJob = Job.getInstance(conf, "Average Rating");
        avgJob.setJarByClass(Driver.class);
        avgJob.setMapperClass(AverageRatingMapper.class);
        avgJob.setReducerClass(AverageRatingReducer.class);
        avgJob.setOutputKeyClass(Text.class);
        avgJob.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(avgJob, new Path(inputPath)); // Input file
        FileOutputFormat.setOutputPath(avgJob, new Path(intermediatePath)); // Intermediate output

        if (!avgJob.waitForCompletion(true)) {
            return 1; // Exit with failure if the first job fails
        }

        // Second Job: Sort by average rating (descending)
        Job sortJob = Job.getInstance(conf, "Sort Ratings");
        sortJob.setJarByClass(Driver.class);
        sortJob.setMapperClass(SortMapper.class);
        sortJob.setReducerClass(SortReducer.class);
        sortJob.setMapOutputKeyClass(FloatWritable.class);
        sortJob.setMapOutputValueClass(Text.class);
        sortJob.setOutputKeyClass(Text.class);
        sortJob.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(sortJob, new Path(intermediatePath)); // Intermediate output as input
        FileOutputFormat.setOutputPath(sortJob, new Path(outputPath)); // Final output

        // Wait for the sort job to complete
        if (!sortJob.waitForCompletion(true)) {
            return 1; // Exit with failure if the second job fails
        }

        return 0; // Successful completion
    }
}
