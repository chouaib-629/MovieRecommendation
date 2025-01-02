import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AverageRatingReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {

    private FloatWritable result = new FloatWritable();

    @Override
    protected void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        float sum = 0;
        int count = 0;

        // Calculate the sum of ratings
        for (FloatWritable val : values) {
            sum += val.get();
            count++;
        }

        // Calculate the average rating
        if (count > 0) {
            float average = sum / count;
            result.set(Float.parseFloat(String.format("%.3f", average)));
            context.write(key, result); // Emit (movieId, averageRating)
        }
    }
}
