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
        this.value = reducer(this.value);
        other.value = reducer(other.value);

        BigNumber tmpThis = new BigNumber(this);
        BigNumber tmpNum = new BigNumber("0");
        int currOp = 0;
        for (int i = 0; i < other.value.length(); i++) {
            currOp = other.value.charAt(other.value.length() - i - 1) - 48;

            for (int j = 0; j < currOp; j++) {

                tmpNum.add(tmpThis);
            }
            tmpThis.value += "0";

        }

        return tmpNum;
    }

    // Divideix
    BigNumber div(BigNumber other) {
        boolean done = false;
        int counter = 0;
        int restCounter = 0;
        BigNumber currOP = new BigNumber("");
        BigNumber rest = new BigNumber("");
        StringBuilder tmpStr = new StringBuilder();
        String tmpStorage = "";
        while (this.compareTo(other) != -1) {


            if (currOP.compareTo(other) == -1) {
                counter++;

                tmpStr.append(this.value.substring(counter - 1, counter));
                currOP.value = tmpStr.toString();

            } else {
                while (currOP.compareTo(other) != -1) {
                    currOP.sub(other);
                    currOP.value = reducer(currOP.value);
                    restCounter++;
                }
                currOP.value = reducer(currOP.value);
                tmpStorage = currOP.value;
                tmpStr.setLength(0);
                tmpStr.append(this.value);
                tmpStr.replace(0, counter, currOP.value);
                counter -= (counter - currOP.value.length());
                this.value = tmpStr.toString();

                tmpStr.setLength(0);
                tmpStr.append(tmpStorage);
                rest.value += restCounter;
                restCounter = 0;


            }
        }
        if (currOP.compareTo(new BigNumber("0")) == 0) {
            rest.value += "0";
        }
        return this;
    }

    // Arrel quadrada
    BigNumber sqrt() {
        return this;

    }

    // Potència
    BigNumber power(int n) {
        BigNumber result = new BigNumber(this.value);

        for (int i = 0; i < n - 1; i++) {
            result.value = result.mult(this).value;
        }
        return result;

    }

    // Factorial
    BigNumber factorial() {
        BigNumber result = new BigNumber(this.value);
        BigNumber counter = new BigNumber(this.value);
        BigNumber one = new BigNumber("1");
        while (counter.compareTo(new BigNumber("0")) != 0) {

            counter.sub(one);
            if (counter.compareTo(new BigNumber("0")) == 0) {
                break;
            }
            result.value = result.mult(counter).value;
        }

        return result;

    }

    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(BigNumber other) {
        while (this.compareTo(other) != 0) {
            if (this.compareTo(other) == 1) {
                this.value = this.sub(other).value;
            } else {
                other.value = other.sub(this).value;
            }
        }
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