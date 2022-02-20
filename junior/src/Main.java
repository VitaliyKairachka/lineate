import java.util.Arrays;

public class Main {
    private final static Integer INPUTS[][] = {{-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20},
            {-6, -3, -2, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20},
            {-4, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20},
            {-6, -5, -4, -3, -2, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20, 100, 101, 102, 103, 105},
            {1, 3, 4, 6, 7, 9, 20, 21}
    };

    private final static String[] RESULTS = {"-6,-3-1,3-5,7-11,14,15,17-20",
            "-6,-3,-2,0,1,3-5,7-11,14,15,17-20",
            "-4-1,3-5,7-11,14,15,17-20",
            "-6--2,0,1,3-5,7-11,14,15,17-20,100-103,105",
            "1,3,4,6,7,9,20,21"
    };

    public static void main(String[] args) {

        for (int i = 0; i < INPUTS.length; i++) {
            var result = convertToIntervals(INPUTS[i]);
            var expectedResult = RESULTS[i];
            boolean correct = result.equalsIgnoreCase(expectedResult);
            System.out.println("The result is " + (correct ? "correct. " : "incorrect. ") + "Result:" + result + " ExpectedResult:" + expectedResult);
        }
    }

    private static String convertToIntervals(Integer[] input) {
        Object[] sortedArray = Arrays.stream(input).sorted().toArray();     //сортируем массив
        int[] result = new int[sortedArray.length];     //создаем массив чисел для переноса из массива Object
        for (int i = 0; i < result.length; i++) {       //переносим в массив чисел
            result[i] = (int) sortedArray[i];
        }
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < result.length; i++) {       //перебор всех элементов
            if (i == result.length - 2) {       //если это 2 последних элемента - записываем их и заканчиваем цикл(нет смысла проверять по условию)
                resultString.append(",").append(result[i]).append(",").append(result[i + 1]);
                break;
            } else if (i == result.length - 1) {        //если это 1 послдний элемент - ситуация как и с 2
                resultString.append(",").append(result[i]);
                break;
            } else if (result[i] + 2 == result[i + 2]) {        //если элемент через 2 позиции = значение + 2 данного элемента - подходит по условию
                if (i == 0) {       //если это первый элемент - записываем без запятой
                    resultString.append(result[i]);
                } else {        //иначе ставим запятую перед элементом
                    resultString.append(",").append(result[i]);
                }
                int second = result[i + 1];     //создаем 2 число после тире и присваиваем значение следующего элемента
                for (int j = i; j < result.length; j++) {       //перебор элементов с позиции где нашелся нужный диапозон
                    if (result[j] - second == 1) {      //если элемент - следующий элемент == 1
                        second = result[j];     //записываем этот элемент
                        i = j;      //i присваиваем позицию j, чтобы дальше не проверять элементы которые попали в диапозон
                    }
                }
                resultString.append("-").append(second);        //записываем второй элемент через тире
            } else if (result[i] + 2 != result[i + 2]) {        //если (значение элемента + 2) != (элемент + 2 позиции), то нет нужного диапозона(по условию)
                if (i == 0) {       //если это первая позиция - записываем без запятой
                    resultString.append(result[i]);
                    continue;
                }
                resultString.append(",").append(result[i]);     //записываем элемент с запятой
            }
        }
        return String.valueOf(resultString);
    }
}
