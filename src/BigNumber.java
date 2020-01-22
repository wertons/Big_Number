import java.util.Stack;

class BigNumber {

    String value = "";


    // Constructor 1
    public BigNumber(String s) {
        this.value = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
        this.value = b.value;
    }

    // Suma
    BigNumber add(BigNumber other) {
        if (other.value.length() > this.value.length()) {
            this.value = equalizer(this.value, other.value.length());
        } else {
            other.value = equalizer(other.value, this.value.length());
        }
        this.value = "0" + this.value;
        other.value = "0" + other.value;

        int length = this.value.length();
        boolean carryOver = false;
        StringBuilder result = new StringBuilder();
        int currentOp = 0;
        for (int i = 0; i < length; i++) {

            currentOp = ((int) (this.value.charAt(length - i - 1) - 48) + (int) (other.value.charAt(length - i - 1) - 48));
            if (carryOver) {
                currentOp++;
            }
            carryOver = false;

            if (currentOp > 9) {
                carryOver = true;
                currentOp = currentOp - 10;
            }
            result.insert(0, Integer.toString(currentOp));

        }
        this.value = result.toString();
        return this;
    }

    // Resta
    BigNumber sub(BigNumber other) {

        if (other.value.length() > this.value.length()) {
            this.value = equalizer(this.value, other.value.length());
        } else {
            other.value = equalizer(other.value, this.value.length());
        }
        this.value = "0" + this.value;
        other.value = "0" + other.value;

        int length = this.value.length();
        boolean carryOver = false;
        StringBuilder result = new StringBuilder();
        int currentOp = 0;
        for (int i = 0; i < length; i++) {

            currentOp = ((int) (this.value.charAt(length - i - 1) - 48) - (int) (other.value.charAt(length - i - 1) - 48));
            if (carryOver) {
                currentOp--;
            }
            carryOver = false;

            if (currentOp < 0) {
                carryOver = true;
                currentOp = currentOp + 10;
            }
            result.insert(0, Integer.toString(currentOp));

        }
        this.value = result.toString();
        return this;

    }

    // Multiplica
    BigNumber mult(BigNumber other) {
        this.value = reductor(this.value);
        other.value = reductor(other.value);

        BigNumber tmpThis = new BigNumber(this);
        BigNumber tmpNum = new BigNumber("0");
        int currOp = 0;
        for (int i = 0; i < other.value.length(); i++) {
            currOp = other.value.charAt(other.value.length()-i-1)-48;

            for (int j = 0; j < currOp; j++) {

                tmpNum.add(tmpThis);
            }
            tmpThis.value += "0";

        }

        return tmpNum;
    }

    // Divideix
    BigNumber div(BigNumber other) {
        return this;

    }

    // Arrel quadrada
    BigNumber sqrt() {
        return this;

    }

    // Potència
    BigNumber power(int n) {
        return this;

    }

    // Factorial
    BigNumber factorial() {
        return this;

    }

    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(BigNumber other) {
        return this;

    }

    // Compara dos BigNumber. Torna 0 si són iguals, -1
// si el primer és menor i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {
        if (other.value.length() > this.value.length()) {
            this.value = equalizer(this.value, other.value.length());
        } else {
            other.value = equalizer(other.value, this.value.length());
        }
        if (this.value.length() == other.value.length()) {
            if (this.value.equals(other.value)) {
                return 0;
            } else {
                for (int i = 0; i < this.value.length(); i++) {
                    if (this.value.charAt(i) > other.value.charAt(i)) {
                        return 1;
                    } else if (this.value.charAt(i) < other.value.charAt(i)) {
                        return -1;
                    }
                }
            }
        }
        return 0;

    }

    // Torna un String representant el número
    public String toString() {
        return "";

    }

    // Mira si dos objectes BigNumber són iguals

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BigNumber)) {
            return false;
        }
        BigNumber ot = (BigNumber) other;
        if (ot.value.length() > this.value.length()) {
            this.value = equalizer(this.value, ot.value.length());
        } else {
            ot.value = equalizer(ot.value, this.value.length());

        }
        return this.value.equals(ot.value);

    }

    public String equalizer(String currentStr, int targetSize) {
        while (currentStr.length() != targetSize) {
            currentStr = "0" + currentStr;
        }
        return currentStr;
    }

    public String reductor(String str) {
        boolean firstFound = false;
        StringBuilder reduction = new StringBuilder();
        reduction.append(str);
        for (int i = 0; i < str.length(); i++) {
            if (reduction.charAt(0) == 48) {
                reduction.replace(0, 1, "");
            } else {
                break;
            }
        }

        return reduction.toString();
    }

}