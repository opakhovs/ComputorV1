public class Polynomial {

    private static String origin;
    private static boolean wellFormatted;
    private static double degreeMax, degreeMin;
    private static double[] numberToXDegree = new double[3];

    public static boolean getWellFormatted() {
        return wellFormatted;
    }

    public static double getDegreeMax() {
        return degreeMax;
    }

    public static double getDegreeMin() {
        return degreeMin;
    }

    public static void setOrigin(String inputData) {
        if ((wellFormatted = checkInputData(inputData)) == true) {
            numberToXDegree[0] = 0;
            numberToXDegree[1] = 0;
            numberToXDegree[2] = 0;
            origin = inputData;
            if (calculateArguments() == true) {
                System.out.println("Your equation is well formatted");
            } else
                System.out.println("An argument : \"" + inputData + "\" is bad formatted");
            System.out.println("Equation degree: " + getDegreeMax());
        } else
            System.out.println("An argument : \"" + inputData + "\" is bad formatted");
    }


    public static void ShowReducedForm() {
        boolean isPrinted = false;
        for (int i = 2; i > -1; i--) {
            if (numberToXDegree[i] != 0.0) {
                if (isPrinted == true && numberToXDegree[i] < 0.0)
                    System.out.print(" - ");
                else if (isPrinted == true && numberToXDegree[i] > 0.0)
                    System.out.print(" + ");
                if (i == 0 && isPrinted == true)
                    System.out.print(Math.abs(numberToXDegree[i]));
                else if (i == 0)
                    System.out.print(numberToXDegree[i]);
                else if (isPrinted == true)
                    System.out.print(numberToXDegree[i] + " * X ^ " + i);
                else
                    System.out.print(Math.abs(numberToXDegree[i]) + " * X ^ " + i);
                isPrinted = true;
            }
        }
        if (numberToXDegree[0] == 0 && numberToXDegree[1] == 0 && numberToXDegree[2] == 0)
            System.out.print("0");
        System.out.print(" = 0");
    }

    public static void Solve() {
        System.out.println("Result:");
        if (numberToXDegree[2] == 0.0 && numberToXDegree[1] == 0.0) {
            if (numberToXDegree[0] == 0.0)
                System.out.println("All numbers are solution");
            else
                System.out.println("No solution");
        } else if (numberToXDegree[0] == 0.0 && numberToXDegree[1] == 0)
            System.out.println("X = 0");
        else if (numberToXDegree[2] == 0.0)
            System.out.println("X = " + ((numberToXDegree[0]) * -1) / numberToXDegree[1]);
        else {
            double dyscryminant = (numberToXDegree[1] * numberToXDegree[1]) - (4 * numberToXDegree[2] * numberToXDegree[0]);
            if (dyscryminant < 0.0)
                System.out.println("No solution");
            else if (dyscryminant == 0.0)
                System.out.println("X = " + ((-1) * numberToXDegree[1]) / (2 * numberToXDegree[2]));
            else {
                System.out.println("X1 = " + ((-1) * numberToXDegree[1] - Math.sqrt(dyscryminant)) / (2.0 * numberToXDegree[2]));
                System.out.println("X2 = " + ((-1) * numberToXDegree[1] + Math.sqrt(dyscryminant)) / (2.0 * numberToXDegree[2]));
            }
        }
    }

    private static double parseNumber(char[] toOperate, int pos) {
        long nb;
        int minus;

        nb = 0;
        minus = 1;
        while (toOperate[pos] == ' ')
            pos++;
        if (toOperate[pos] == '-')
            minus = -1;
        if (toOperate[pos] == '-' || toOperate[pos] == '+')
            pos++;
        while (pos < toOperate.length && toOperate[pos] >= '0' && toOperate[pos] <= '9') {
            nb = (nb * 10) + (toOperate[pos] - '0');
            pos++;
        }
        if (pos < toOperate.length && toOperate[pos] == '.')
            return (minus * (nb + parseAfterPoint(toOperate, pos + 1)));
        return (minus * nb);
    }

    private static double parseAfterPoint(char[] toOperate, int pos) {
        double nb = 0.0;
        long pow = 10;

        while (pos < toOperate.length && (toOperate[pos] >= '0' && toOperate[pos] <= '9')) {
            nb += (toOperate[pos] - 48) / pow;
            pow *= 10;
            pos++;
        }
        return nb;
    }

    private static boolean findMaxDegree(char[] toOperate) {
        int powerCount;
        boolean hasX = false;

        degreeMax = -1;
        degreeMin = -1;
        for (int i = 0; i < toOperate.length; i++) {
            if (toOperate[i] == 'X' || toOperate[i] == 'x') {
                hasX = true;
                powerCount = 0;
                i++;
                while (toOperate[i] == 32 || toOperate[i] == '^') {
                    if (toOperate[i] == '^')
                        powerCount++;
                    i++;
                }
                if ((toOperate[i] == '+' || toOperate[i] == '-' || toOperate[i] == '=' || toOperate[i] == '*'
                        || toOperate[i] == '/') && powerCount == 0) {
                    if (1 < degreeMin || degreeMin == -1)
                        degreeMin = 1;
                    if (1 > degreeMax || degreeMax == -1)
                        degreeMax = 1;
                } else {
                    if (powerCount != 1)
                        return false;
                    if (parseNumber(toOperate, i) < degreeMin || degreeMin == -1)
                        degreeMin = parseNumber(toOperate, i);
                    if (parseNumber(toOperate, i) > degreeMax || degreeMax == -1)
                        degreeMax = parseNumber(toOperate, i);
                }
            }
        }
        if (hasX == false){
            degreeMax = 0;
            degreeMin = 0;
        }
        return true;
    }

    private static boolean checkInputData(String inputData) {
        char[] toOperate = inputData.toCharArray();
        for (char toOperateChar :
                toOperate) {
            if (toOperateChar != 32 && toOperateChar != '+' && toOperateChar != '-' && toOperateChar != '*' &&
                    toOperateChar != '/' && toOperateChar != '=' && !(toOperateChar >= '0' && toOperateChar <= '9') &&
                    toOperateChar != 'X' && toOperateChar != 'x' && toOperateChar != '^' && toOperateChar != '.')
                return false;
        }
        if (findMaxDegree(toOperate) == false)
            return false;
        return true;
    }

    private static boolean calculateArguments() {
        int check;
        boolean afterEqual = false;
        char[] toOperate = origin.toCharArray();

        for (int i = 0; i < toOperate.length; i++) {
            if (toOperate[i] == 32)
                continue;
            if (toOperate[i] == '=' && afterEqual == true)
                return false;
            if (toOperate[i] == '=') {
                afterEqual = true;
                continue;
            }
            if ((toOperate[i] >= '0' && toOperate[i] <= '9') || toOperate[i] == '-' || toOperate[i] == '+' ||
                    toOperate[i] == 'X' || toOperate[i] == 'x')
                check = operateNextArgument(toOperate, i, afterEqual);
            else
                continue;
            if (check == i)
                return false;
            i = check - 1;
        }
        return true;
    }

    private static int operateNextArgument(char[] toOperate, int pos, boolean isAfterEqual) {
        int temp;
        int operation;
        int sign;
        double argumant;
        double currentDegree;
        boolean signed = false;

        operation = -1;
        temp = pos;
        currentDegree = -1;
        argumant = 0.0;
        sign = 1;
        for (; pos < toOperate.length; pos++) {
            if (toOperate[pos] == 32)
                continue;
            else if (toOperate[pos] == '*')
                operation = 0;
            else if (toOperate[pos] == '/')
                operation = 1;
            else if ((toOperate[pos] == '-' && pos != temp && toOperate[pos + 1] == 32) || (toOperate[pos] == '+'
                    && toOperate[pos + 1] == 32 && pos != temp) || toOperate[pos] == '=')
                break;
            else if (toOperate[pos] == '-' && toOperate[pos + 1] == 32)
                sign = -1;
            else if (toOperate[pos] == 'X' || toOperate[pos] == 'x') {
                pos++;
                while (toOperate[pos] == 32 || toOperate[pos] == '^') {
                    if (toOperate[pos] == '^') {
                        pos++;
                        currentDegree = parseNumber(toOperate, pos);
                    }
                    while (pos < toOperate.length && (toOperate[pos] == '.' || (toOperate[pos] >= '0' && toOperate[pos] <= '9')))
                        pos++;
                    pos--;
                    break;
                }
                if (currentDegree == -1)
                    currentDegree = 1;
                if (signed == false)
                    argumant = 1.0;
                signed = true;
            } else if (toOperate[pos] == '-' || (toOperate[pos] >= '0' && toOperate[pos] <= '9')) {
                if (sign == 1 && signed == false)
                    argumant += parseNumber(toOperate, pos);
                else if (sign == -1 && signed == false)
                    argumant -= parseNumber(toOperate, pos);
                else if (operation == 0)
                    argumant *= parseNumber(toOperate, pos);
                else
                    argumant /= parseNumber(toOperate, pos);
                signed = true;
                while (pos < toOperate.length && (toOperate[pos] == '-' || toOperate[pos] == '.' || (toOperate[pos] >= '0' && toOperate[pos] <= '9')))
                    pos++;
            }
        }
        if (currentDegree == -1) {
            currentDegree = 0;
        }
        if ((sign == -1 && isAfterEqual == false) || (sign == 1 && isAfterEqual == true))
            numberToXDegree[(int) currentDegree] -= argumant;
        else
            numberToXDegree[(int) currentDegree] += argumant;
        return pos;
    }

}