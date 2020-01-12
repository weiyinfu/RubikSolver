import cube.OperationList;
import junit.framework.TestCase;
import two.TableSolver;

public class TestTable extends TestCase {
public void testTableSolver() {
    var solver = new TableSolver();
    String ans = solver.solve("bgywygyrorybbwwrgobwogro");
    System.out.println(new OperationList(ans).toFormatString());
}
}
