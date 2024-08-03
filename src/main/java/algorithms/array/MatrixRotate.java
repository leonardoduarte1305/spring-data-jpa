package algorithms.array;

public class MatrixRotate {

    public static void main(String[] args) {
        Integer lines = 3;
        Long[][] matrix = new Long[lines][lines];

        fulfillTheMatrix(lines, matrix);
        printMatrix(lines, matrix);

        System.out.println("============================");

        Long[][] newMatrix = changeTheMatrix(lines, matrix);
        printMatrix(lines, newMatrix);

        System.out.println("============================");

        Long[][] turnedMatrix = turnedMatrix(lines, newMatrix);
        printMatrix(lines, turnedMatrix);

    }

    private static void fulfillTheMatrix(Integer lines, Long[][] matrix) {
        matrix[0][0] = 1L;
        matrix[0][1] = 1L;
        matrix[0][2] = 1L;
        matrix[1][0] = 2L;
        matrix[1][1] = 2L;
        matrix[1][2] = 2L;
        matrix[2][0] = 3L;
        matrix[2][1] = 3L;
        matrix[2][2] = 3L;
    }

    private static void printMatrix(Integer lines, Long[][] matrix) {
        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < lines; col++) {
                System.out.print(matrix[row][col] + "  ");
            }
            System.out.println();
        }
    }

    private static Long[][] changeTheMatrix(Integer lines, Long[][] matrix) {
        Long[][] newMatrix = new Long[lines][lines];
        for (int row = 0; row < lines; row++) {
            for (int col = 0; col < lines; col++) {
                newMatrix[col][row] = matrix[row][col];
            }
        }

        return newMatrix;
    }

    private static Long[][] turnedMatrix(Integer qtd, Long[][] newMatrix) {
        Long[][] turnedMatrix = new Long[qtd][qtd];

        for (int linha = (qtd - 1); 0 <= linha; linha--) {
            for (int coluna = 0; coluna < (qtd - 1); coluna++) {
                turnedMatrix[coluna][linha] = newMatrix[linha][coluna];
            }
        }
        return turnedMatrix;
    }
}
