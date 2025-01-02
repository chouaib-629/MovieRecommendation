import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AverageRatingMapper extends Mapper<Object, Text, Text, FloatWritable> {

    private Text movieId = new Text();
    private FloatWritable rating = new FloatWritable();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Skip the header line
        if (key.toString().equals("0")) {
            return; // Skip header if this is the first record
        }

        // Assuming the input format is: userId,movieId,rating,timestamp
        String[] columns = value.toString().split(",");
        if (columns.length == 4) {
            try {
                movieId.set(columns[1]); // MovieID is in the second column (index 1)
                rating.set(Float.parseFloat(columns[2])); // Rating is in the third column (index 2)
                context.write(movieId, rating); // Emit (movieId, rating)
            } catch (NumberFormatException e) {
                // Skip rows with invalid data
            }
        }
    }
}
