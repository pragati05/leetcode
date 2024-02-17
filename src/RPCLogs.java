import java.util.*;

public class RPCLogs {
    public static void main(String[] args) {
        int[][] logs;
        int timeout;
        int[] result;

        // write test case for the above scenario by using assert statements in java
        logs = new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 2, 1}, {2, 6, 0}, {1, 7, 1}};
        timeout = 3;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == 1;
        assert result[1] == 7;

        // write test case for the scenario where there is no timeout
        logs = new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 2, 1}, {2, 6, 0}, {1, 7, 1}};
        timeout = 10;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == -1;
        assert result[1] == -1;

        // write test case for the scenario where the first request times out
        logs = new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 2, 1}, {2, 6, 0}, {1, 7, 1}};
        timeout = 1;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == 0;
        assert result[1] == 2;

        // write test case for the scenario where only start logs of 3 requests are present with timeout 5
        logs = new int[][]{{0, 0, 0}, {1, 1, 0}, {2, 2, 0}};
        timeout = 5;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == -1;
        assert result[1] == -1;

        // write test case for 0-0-1 1-2-0 2-3-0 3-4-0 0-5-1 output should be 0-5 with timeout as 3
        logs = new int[][]{{0, 0, 0}, {1, 2, 0}, {2, 3, 0}, {3, 4, 0}, {0, 5, 1}};
        timeout = 3;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == 0;
        assert result[1] == 5;

        // write test case for 0-0-0 with T=5
        logs = new int[][]{{0, 0, 0}};
        timeout = 5;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == -1;
        assert result[1] == -1;

        // write test case for 0-0-0 1-2-0 3-3-0 4-4-0 0-5-1 3-7-1 4-10-1 with T=6 output should be 1-10
        logs = new int[][]{{0, 0, 0}, {1, 2, 0}, {3, 3, 0}, {4, 4, 0}, {0, 5, 1}, {3, 7, 1}, {4, 10, 1}};
        timeout = 6;
        result = getTimedOutRequest(logs, timeout);
        System.out.println("For input logs: " + Arrays.deepToString(logs) + " and timeout: " + timeout + ", the result is: " + Arrays.toString(result));
        assert result[0] == 1;
        assert result[1] == 10;
    }

    public static int[] getTimedOutRequest(int[][] logs, int timeout) {
        int[] result = new int[]{-1, -1};
        List<Integer> list = new LinkedList<>();
        Map<Integer,Integer> map = new HashMap<>();

        for(int[] i: logs){
            int id = i[0];
            int time = i[1];
            int type = i[2];
            if(type == 0){
                map.put(id,time);
                list.add(id);
            }else if(type == 1) {
                if (map.containsKey(id)) {
                    if (time - map.get(id) >= timeout) {
                        map.remove(id);
                        if (list.get(0) == id) {
                            return new int[]{id, time};
                        } else {
                            if (list.get(0) == id){
                                list.remove(0);
                            }else{
                                return new int[]{list.get(0), time};
                             }
                        }
                    }else{
                        if (list.get(0) == id) {
                            list.remove(0);
                        }
                        map.remove(id);
                    }
                }
            }
        }
        return result;
    }
}
