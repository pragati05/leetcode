
/*
There is a Hospital which contain N rooms from 1 to N -1. There is a Queue of Patients outside the Hospital. Each patient will be served in a single room and each patient has a time duration that it will take for the treatment. The rooms are allocated to patients by the lowest index (if the room is free). Initially room1 will be given to the patient, then if the 2nd patient comes and the 1st room is still busy, then the room2 will be given to the 2nd patient. now suppose the 1st patient treatment is completed, and the room1 is free now, and a new patient comes, then room1 will be given to the new patient. Each Patient has a start time also (Consider this as appointment time for him, it needs not to be a time format, use Integer for this).

The patients standing in the queue are in non decreasing order of their start time. means if the first patient start time = 1, then the second patient start time will be >= 1. Now the rooms will be allocated to each patients one by one. we have to calculate that by the end when all the patients are treatment done, Which is the room in which the maximum no of patients have been entered?

Example: if N = 2 rooms, and the patients are = 3, which are below

 patient-1 = {start: 1, duration: 8}
 patient-2 = {start: 1, duration: 2}
 patient-3 = {start: 6, duration: 4}

Now first patient will be assigned to room-1, second patient comes at the same time he will be allocated to room-2, now after time 2, room-2 will become free, but patient 3 will come at time 6 and at that time he will be allocated to room-2 as well. so finally room-2 is the room in which the max number of patients have entered.


Problem Statement
Given 3 input parameters: numRooms: Int, appointmentStartTimes: List[Int], appointmentDurations: List[Int]

Return the usage number of the room that held the maximum appointments.

Notes:

* appointmentStartTimes array is sorted
* the rooms should be assigned in the order. Meaning if room #2 and #3 are vacant, we cannot execute next appointment in room 3, it has to be room #2.

Example:

numRooms: 3 startTimes: [1, 3, 8, 13, 20] durations: [20, 3, 6, 2, 1]

Expected output: 3 Explanation:

* Room 1 is busy for the whole duration. So it is noticable that room 2 would be the next go to for the following appointments.
* Appoitment at idx 1, 3, 4 would be held in room 2.

Solution:

* Two heaps, full stop.
* Keep 2 heaps that keep busy rooms and available rooms. And use them as needed.
* To tackle the scenario where all rooms are busy, we would manually change the appointment start time to whenever the next room will be free and process the same appointment again meaning do not increment i in that scenario in the loop(bad practice but needed for optimal solution).
* So heap 1 key will be the end time of the ongoing appointment. Heap 2 will hold the vailable rooms in order.

write a code for above problem along with test cases
 */


import java.util.*;

public class BusyRooms {
    public static void main(String[] args) {
        int numRooms = 3;
        int[] startTimes = new int[]{1, 3, 8, 13, 20};
        int[] durations = new int[]{20, 3, 6, 2, 1};
        int res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res == 3 : "Expected result was 3";


        // write more testcases
        numRooms = 2;
        startTimes = new int[]{1, 1, 6};
        durations = new int[]{8, 2, 4};
        res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res ==  2;

        numRooms = 3;
        startTimes = new int[]{1, 3, 8, 13, 20};
        durations = new int[]{20, 3, 6, 2, 1};
         res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res == 3;

        // write a case where all rooms are busy
        numRooms = 3;
        startTimes = new int[]{1, 3, 8, 13, 20};
        durations = new int[]{20, 3, 6, 2, 1};
         res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res == 3;

        // write corner case where only 1 room is there
        numRooms = 1;
        startTimes = new int[]{1, 3, 8, 13, 20};
        durations = new int[]{20, 3, 6, 2, 1};
        res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res == 5;

        // write corner case where only 1 room is there and 1 patient
        numRooms = 1;
        startTimes = new int[]{1};
        durations = new int[]{20};
        res = getRoomWithMaxAppointments(numRooms, startTimes, durations);
        System.out.println("For input numRooms: " + numRooms + ", startTimes: " + Arrays.toString(startTimes) + ", and durations: " + Arrays.toString(durations) + ", the result is: " + res);
        assert  res == 1;


    }

    public static int getRoomWithMaxAppointments(int numRooms, int[] startTimes, int[] durations) {
        Map<Integer,Integer> roomUse = new HashMap<>();
        PriorityQueue<Integer> availableRooms = new PriorityQueue<>();

        PriorityQueue<ArrayList<Integer>> busyRooms  = new PriorityQueue<>(
                Comparator.comparing(i -> i.get(0))
        );
        // busyRooms store the end time of the ongoing appointment: the roomNumber
        // availableRooms store the available rooms in order
        for(int i = 1; i<=numRooms; i++){
            availableRooms.add(i);
            roomUse.put(i,0);
        }

        for (int i = 0; i < startTimes.length; i++) {
            while(!busyRooms.isEmpty() && busyRooms.peek().get(0) <= startTimes[i]){
                availableRooms.add(busyRooms.poll().get(1));
            }

            if(availableRooms.isEmpty()){
                startTimes[i] = busyRooms.peek().get(0);
                i--;
                continue;
            }else{
               int roomNum =  availableRooms.poll();
               ArrayList<Integer> tuple = new ArrayList<>();
                tuple.add(startTimes[i] + durations[i]);
                tuple.add(roomNum);

                busyRooms.add(tuple);

                roomUse.put(roomNum, roomUse.get(roomNum) + 1);

            }


        }
        System.out.println(roomUse);

        return roomUse.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();

    }
}
