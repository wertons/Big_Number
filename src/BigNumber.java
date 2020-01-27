class BigNumber {

    String numericalValue = "";


    // Constructor 1
    public BigNumber(String s) {
        this.numericalValue = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
        this.numericalValue = b.numericalValue;
    }

    // Suma
    BigNumber add(BigNumber other) {
        if (other.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, other.numericalValue.length());
        } else {
            other.numericalValue = equalizer(other.numericalValue, this.numericalValue.length());
        }
        this.numericalValue = "0" + this.numericalValue;
        other.numericalValue = "0" + other.numericalValue;

        int length = this.numericalValue.length();
        boolean carryOver = false;
        StringBuilder result = new StringBuilder();
        int currentOp = 0;
        for (int i = 0; i < length; i++) {

            currentOp = ((int) (this.numericalValue.charAt(length - i - 1) - 48) + (int) (other.numericalValue.charAt(length - i - 1) - 48));
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
        this.numericalValue = result.toString();
        return this;
    }

    // Resta
    BigNumber sub(BigNumber other) {

        if (other.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, other.numericalValue.length());
        } else {
            other.numericalValue = equalizer(other.numericalValue, this.numericalValue.length());
        }
        this.numericalValue = "0" + this.numericalValue;
        other.numericalValue = "0" + other.numericalValue;

        int length = this.numericalValue.length();
        boolean carryOver = false;
        StringBuilder result = new StringBuilder();
        int currentOp = 0;
        for (int i = 0; i < length; i++) {

            currentOp = ((int) (this.numericalValue.charAt(length - i - 1) - 48) - (int) (other.numericalValue.charAt(length - i - 1) - 48));
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
        this.numericalValue = result.toString();
        return this;

    }

    // Multiplica
    BigNumber mult(BigNumber other) {
        this.numericalValue = reducer(this.numericalValue);
        other.numericalValue = reducer(other.numericalValue);

        BigNumber tmpThis = new BigNumber(this);
        BigNumber tmpNum = new BigNumber("0");
        int currOp = 0;
        for (int i = 0; i < other.numericalValue.length(); i++) {
            currOp = other.numericalValue.charAt(other.numericalValue.length() - i - 1) - 48;
            for (int j = 0; j < currOp; j++) {
                tmpNum.add(tmpThis);
            }
            tmpThis.numericalValue += "0";
        }
        return tmpNum;
    }

    // Divideix
    BigNumber div(BigNumber other) {

        BigNumber rest = new BigNumber("");
        BigNumber result = new BigNumber("");
        for (int i = 0; i < this.numericalValue.length(); i++) {
            int resCount = 0;
            rest.numericalValue += this.numericalValue.charAt(i);
            if (rest.compareTo(other) != -1) {
                while (rest.compareTo(other) != -1) {
                    resCount++;
                    rest.sub(other);
                }
                rest.numericalValue = reducer(rest.numericalValue);
            }
            result.numericalValue += resCount;
        }
        return result;
    }

    // Arrel quadrada
    BigNumber sqrt() {
        BigNumber result = new BigNumber("");
        BigNumber numTester = new BigNumber("");
        BigNumber rest = new BigNumber("");
        StringBuilder tmpStr = new StringBuilder();

        if (this.numericalValue.length() % 2 == 1) {
            this.numericalValue = "0" + this.numericalValue;
        }

        int loop =  (this.numericalValue.length()) / 2;
        for (int i = 0; i < loop; i++) {

            tmpStr.replace(0, tmpStr.length(), this.numericalValue);
            rest.numericalValue += tmpStr.substring(0, 2);

            tmpStr.replace(0, tmpStr.length(), this.numericalValue);
            tmpStr.replace(0, 2, "");
            this.numericalValue = tmpStr.toString();


            int j = 0;
            for (j = 0; j < 11; j++) {

                numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + j;
                numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;

                if (numTester.compareTo(rest) == 1) {
                    j--;
                    numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + (j);
                    numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;
                    numTester.numericalValue = reducer(numTester.numericalValue);

                    break;
                } else if (numTester.compareTo(rest) == 0) {
                    numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + (j);
                    numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;
                    numTester.numericalValue = reducer(numTester.numericalValue);

                    break;
                }
            }

            rest.numericalValue = rest.sub(numTester).numericalValue;
            result.numericalValue += j;


        }


        return result;

    }

    // Potència
    BigNumber power(int n) {
        BigNumber result = new BigNumber(this.numericalValue);

        for (int i = 0; i < n - 1; i++) {
            result.numericalValue = result.mult(this).numericalValue;
        }
        return result;

    }

    // Factorial
    BigNumber factorial() {
        BigNumber result = new BigNumber(this.numericalValue);
        BigNumber counter = new BigNumber(this.numericalValue);
        BigNumber one = new BigNumber("1");
        while (counter.compareTo(new BigNumber("0")) != 0) {

            counter.sub(one);
            if (counter.compareTo(new BigNumber("0")) == 0) {
                break;
            }
            result.numericalValue = result.mult(counter).numericalValue;
        }

        return result;

    }

    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(BigNumber other) {
        while (this.compareTo(other) != 0) {
            if (this.compareTo(other) == 1) {
                this.numericalValue = this.sub(other).numericalValue;
            } else {
                other.numericalValue = other.sub(this).numericalValue;
            }
        }
        return this;

    }

    // Compara dos BigNumber. Torna 0 si són iguals, -1
// si el primer és menor i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {
        if (other.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, other.numericalValue.length());
        } else {
            other.numericalValue = equalizer(other.numericalValue, this.numericalValue.length());
        }
        if (this.numericalValue.length() == other.numericalValue.length()) {
            if (this.numericalValue.equals(other.numericalValue)) {
                return 0;
            } else {
                for (int i = 0; i < this.numericalValue.length(); i++) {
                    if (this.numericalValue.charAt(i) > other.numericalValue.charAt(i)) {
                        return 1;
                    } else if (this.numericalValue.charAt(i) < other.numericalValue.charAt(i)) {
                        return -1;
                    }
                }
            }
        }
        return 0;

    }

    // Torna un String representant el número
    public String toString() {
        return this.numericalValue;

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
        if (ot.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, ot.numericalValue.length());
        } else {
            ot.numericalValue = equalizer(ot.numericalValue, this.numericalValue.length());

        }
        return this.numericalValue.equals(ot.numericalValue);
    }

    public String equalizer(String currentStr, int targetSize) {
        while (currentStr.length() != targetSize) {
            currentStr = "0" + currentStr;
        }
        return currentStr;
    }


    public String reducer(String str) {

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
        if (reduction.toString().isEmpty()) {
            reduction.insert(0, "0");
        }

        return reduction.toString();
    }

}