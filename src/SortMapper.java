import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SortMapper extends Mapper<Object, Text, FloatWritable, Text> {

    private FloatWritable rating = new FloatWritable();
    private Text movieId = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Input format: movieId\taverageRating
        String[] columns = value.toString().split("\t");
        if (columns.length == 2) {
            try {
                movieId.set(columns[0]);
                float avgRating = Float.parseFloat(columns[1]);
                rating.set(-avgRating); // Negate the key for descending sort
                context.write(rating, movieId); // Emit (-rating, movieId)
            } catch (NumberFormatException e) {
                // Handle invalid data gracefully
            }
        }
    }
}
