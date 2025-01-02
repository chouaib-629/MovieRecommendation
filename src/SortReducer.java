import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SortReducer extends Reducer<FloatWritable, Text, Text, FloatWritable> {

    private FloatWritable result = new FloatWritable();

    @Override
    protected void reduce(FloatWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Process movies sorted by descending average rating
        for (Text value : values) {
            result.set(-key.get()); // Reverse the negation to get the original rating
            context.write(value, result); // Emit (movieId, averageRating)
        }
    }
}
