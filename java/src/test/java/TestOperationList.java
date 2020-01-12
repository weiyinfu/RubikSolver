import cube.Operation;
import cube.OperationList;
import junit.framework.TestCase;

import java.util.List;

public class TestOperationList extends TestCase {
public void test() {
    List<Operation> ops = new OperationList("左2下3 后2后下左");
    System.out.println(ops);
    System.out.println(new OperationList(ops.toString()).toFormatString());
}
}
