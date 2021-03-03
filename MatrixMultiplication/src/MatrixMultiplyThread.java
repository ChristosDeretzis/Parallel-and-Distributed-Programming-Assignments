public class MatrixMultiplyThread extends Thread {

    private int[] firstTable;
    private int[] secondTable;
    private int xIndex;
    private int yIndex;
    private int[][] multipliedTable;

    public MatrixMultiplyThread(int[] firstTable, int[] secondTable, int xIndex, int yIndex, int[][] multipliedTable) {
        this.firstTable = firstTable;
        this.secondTable = secondTable;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.multipliedTable = multipliedTable;
    }

    public void run() {
        int result = 0;
        for (int i = 0; i < firstTable.length; i++) {
                result += firstTable[i] * secondTable[i];
        }
        multipliedTable[xIndex][yIndex] = result;
    }
}


