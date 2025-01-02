import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class DescendingFloatComparator extends WritableComparator {

    protected DescendingFloatComparator() {
        super(FloatWritable.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FloatWritable f1 = (FloatWritable) a;
        FloatWritable f2 = (FloatWritable) b;
        return -f1.compareTo(f2); // Reverse the comparison for descending order
    }
}
