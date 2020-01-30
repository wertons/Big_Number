class BigNumber {
    private String numericalValue = "";

    // Constructor 1
    public BigNumber(String s) {
        this.numericalValue = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
        this.numericalValue = b.numericalValue;
    }

    // Addition
    BigNumber add(BigNumber other) {

        //We make sure the numbers are equal in size by calling the equalizer method
        if (other.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, other.numericalValue.length());
        } else {
            other.numericalValue = equalizer(other.numericalValue, this.numericalValue.length());
        }
        //Then we add an extra zero in the case that two numbers add more than the total amount of positions, such as:
        // 5 + 5 = 10
        //Which changes the total size from 1 to 2
        this.numericalValue = "0" + this.numericalValue;
        other.numericalValue = "0" + other.numericalValue;

        int length = this.numericalValue.length();
        boolean carryOver = false;
        StringBuilder result = new StringBuilder();
        int currentOp = 0;

        //We cover the whole string
        for (int i = 0; i < length; i++) {

            //Starting with the end we get two numbers, which we extract as chars and add one another
            currentOp = ((int) (this.numericalValue.charAt(length - i - 1) - 48) + (int) (other.numericalValue.charAt(length - i - 1) - 48));

            //If the last number was larger than nine we add the 1 to the current one
            if (carryOver) {
                currentOp++;
            }
            carryOver = false;

            //If the current result is larger than 9 we remove 10 from it so we are able to add 1 to the next operation
            if (currentOp > 9) {
                carryOver = true;
                currentOp = currentOp - 10;
            }

            //We add the current result to the total one
            result.insert(0, Integer.toString(currentOp));

        }

        //We store the result in a BigNumber object, we can use the original one as we no longer need it
        this.numericalValue = result.toString();
        return this;
    }

    // Subtract
    BigNumber sub(BigNumber other) {

        //We make sure the numbers are equal in size by calling the equalizer method
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

        //We cover the whole string
        for (int i = 0; i < length; i++) {

            //Starting with the end we get two numbers, which we extract as chars and add one another
            currentOp = ((int) (this.numericalValue.charAt(length - i - 1) - 48) - (int) (other.numericalValue.charAt(length - i - 1) - 48));

            //If the last number was smaller than 0 we subtract 1 to the current one
            if (carryOver) {
                currentOp--;
            }
            carryOver = false;

            //If the current result is smaller than 0 we add 10 to it then we will remove one from the next operation
            if (currentOp < 0) {
                carryOver = true;
                currentOp = currentOp + 10;
            }

            //We add the current result to the total one
            result.insert(0, Integer.toString(currentOp));
        }

        //We store the result in a BigNumber object, we can use the original one as we no longer need it
        this.numericalValue = result.toString();
        return this;

    }

    // Multiply
    BigNumber mult(BigNumber other) {
        //We make sure the numbers have no unnecessary zeros as this will affect the operation
        this.numericalValue = reducer(this.numericalValue);
        other.numericalValue = reducer(other.numericalValue);


        BigNumber tmpThis = new BigNumber(this);
        BigNumber tmpNum = new BigNumber("0");
        int currOp = 0;

        //For each position on the multiplier we do the operation once
        for (int i = 0; i < other.numericalValue.length(); i++) {

            //We store the current operator, starting with the last one
            currOp = other.numericalValue.charAt(other.numericalValue.length() - i - 1) - 48;

            //We sum the original number n times where n is the current operator
            for (int j = 0; j < currOp; j++) {
                tmpNum.add(tmpThis);
            }

            //When each position is finished we add a zero to the next one instead of multiplying the then results
            // in order to obtain a faster run time
            tmpThis.numericalValue += "0";
        }
        return tmpNum;
    }

    //Total Division
    BigNumber[] division(BigNumber other) {

        BigNumber rest = new BigNumber("");
        BigNumber result = new BigNumber("");

        BigNumber zero = new BigNumber("0");
        BigNumber[] zeroS = new BigNumber[]{zero, zero};


        //We check if either of the numbers are 0 in which case the operation returns 0
        if (this.compareTo(zero) == 0) {
            return zeroS;
        } else if (other.compareTo(zero) == 0) {
            //This is technically a posible operation but not with basic math
            try {
            } catch (Exception e) {
                System.out.println("A division by 0 is undefined");
            }
        }


        //For each position in the original number
        for (int i = 0; i < this.numericalValue.length(); i++) {

            //We reset the counter from last run of the loop
            int resCount = 0;

            //We add the current operator to the rest
            rest.numericalValue += this.numericalValue.charAt(i);

            //If the number is big enough to be operated on we make subtractions until it is no longer possible
            //Each time we do this we store it in a counter
            if (rest.compareTo(other) != -1) {
                while (rest.compareTo(other) != -1) {
                    resCount++;
                    rest.sub(other);
                }
                rest.numericalValue = reducer(rest.numericalValue);
            }

            //Now we add the counter to the result
            result.numericalValue += resCount;
        }

        return new BigNumber[]{result, rest};
    }

    // Dividend
    BigNumber div(BigNumber other) {

        //We extract the result from a division
        BigNumber[] arr;
        arr = this.division(other);
        return arr[0];
    }

    // Modulus
    BigNumber mod(BigNumber other) {

        //We extract the leftover from a division
        BigNumber[] arr;
        arr = this.division(other);
        return arr[1];
    }

    // Square Root
    BigNumber sqrt() {
        BigNumber result = new BigNumber("");
        BigNumber numTester = new BigNumber("");
        BigNumber rest = new BigNumber("");
        StringBuilder tmpStr = new StringBuilder();

        //Make the number have even amount of spaces by, if need be, adding a zero to the begging of it
        if (this.numericalValue.length() % 2 == 1) {
            this.numericalValue = "0" + this.numericalValue;
        }

        //As we need to structure the operations in sets of 2, we make a loop that runs once for every 2 spaces
        // on the number
        int loop = (this.numericalValue.length()) / 2;
        for (int i = 0; i < loop; i++) {

            //We get the first two characters and add them to the rest
            tmpStr.replace(0, tmpStr.length(), this.numericalValue);
            rest.numericalValue += tmpStr.substring(0, 2);

            //Then remove the two characters we extracted from the total
            tmpStr.replace(0, tmpStr.length(), this.numericalValue);
            tmpStr.replace(0, 2, "");
            this.numericalValue = tmpStr.toString();


            int j = 0;
            for (j = 0; j < 11; j++) {

                //We create the operator for the rest, similar to a division
                numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + j;
                numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;

                if (numTester.compareTo(rest) == 1) {

                    //If the numTester if bigger than the rest it means that the last operations is the desired number,
                    // so we remove one from the counter so we get the correct number
                    j--;
                    numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + (j);
                    numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;
                    numTester.numericalValue = reducer(numTester.numericalValue);

                    break;
                } else if (numTester.compareTo(rest) == 0) {

                    //If the numTester is equal to the rest it means the counter is the desired result
                    numTester.numericalValue = result.mult(new BigNumber("2")).numericalValue + (j);
                    numTester.numericalValue = numTester.mult(new BigNumber(Integer.toString(j))).numericalValue;
                    numTester.numericalValue = reducer(numTester.numericalValue);

                    break;
                }
            }

            //We add the counter to the result
            rest.numericalValue = rest.sub(numTester).numericalValue;
            result.numericalValue += j;


        }


        return result;

    }

    // Power
    BigNumber power(int n) {
        BigNumber result = new BigNumber(this.numericalValue);

        //We multiply a number by itself n times
        for (int i = 0; i < n - 1; i++) {
            result.numericalValue = result.mult(this).numericalValue;
        }
        return result;

    }

    // Factorial
    BigNumber factorial() {
        //We establish the starting number as a copy of the original one to make the loop simpler
        BigNumber result = new BigNumber(this.numericalValue);
        BigNumber counter = new BigNumber(this.numericalValue);

        //We repeat until the number is 0
        while (counter.compareTo(new BigNumber("0")) != 0) {

            //We remove one from the number because the original number is already the first iteration of the loop
            counter.sub(new BigNumber("1"));

            //We check if the number is already zero
            if (counter.compareTo(new BigNumber("0")) == 0) {
                break;
            }

            //We multiply the current result with the current value
            result.numericalValue = result.mult(counter).numericalValue;
        }

        return result;

    }

    // Greater Common Divisor
    BigNumber mcd(BigNumber other) {

        int difference = this.numericalValue.length() - other.numericalValue.length();
        this.numericalValue = reducer(this.numericalValue);
        other.numericalValue = reducer(other.numericalValue);

        BigNumber zero = new BigNumber("0");
        if (this.compareTo(zero) == 0 || other.compareTo(zero) == 0) {
            return zero;
        }

        if (difference < 4) {
            //Case for small differences

            //We use the Euclidean algorithm, which subtracts the smaller number from the bigger one until they are equal
            while (this.compareTo(other) != 0) {

                if (this.compareTo(other) == 1) {
                    this.numericalValue = this.sub(other).numericalValue;
                } else {
                    other.numericalValue = other.sub(this).numericalValue;
                }
            }
            return this;
        } else {
            //Case for big differences

            //Euclidean division algorithm based on divisions
            if (other.compareTo(new BigNumber("0")) == 0) {
                return this;
            }

            return other.mcd(this.mod(other));
        }
    }

    // We compare two BigNumber objects
    //If the first one is bigger we return 1
    //If they are equal we return 0
    //If the second one is bigger we return -1
    public int compareTo(BigNumber other) {

        //First we equalize them so we can compare them
        if (other.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, other.numericalValue.length());
        } else {
            other.numericalValue = equalizer(other.numericalValue, this.numericalValue.length());
        }

        //We first test if they are equal
        if (this.numericalValue.equals(other.numericalValue)) {
            return 0;
        } else {
            //If they are not equal start comparing the numbers at each position starting with the first, once we
            // find two distinct numbers we give the result
            for (int i = 0; i < this.numericalValue.length(); i++) {
                if (this.numericalValue.charAt(i) > other.numericalValue.charAt(i)) {
                    return 1;
                } else if (this.numericalValue.charAt(i) < other.numericalValue.charAt(i)) {
                    return -1;
                }
            }
        }

        //In the case none of the above comparisons worked, and to satisfy Javas willy nilly attitude we make
        // a return of 69420 which has no logic but hopefully is easy to spot in case of error
        return 69420;

    }

    // Torna un String representant el número
    public String toString() {
        return this.numericalValue;

    }


    //Compare two different Objects, in this specific case BigNumbers and check if they are equal
    @Override
    public boolean equals(Object other) {
        //First of we test if they are literally the same object
        if (this == other) {
            return true;
        }
        //Then we test that the Object other is actually a BigNumber
        if (!(other instanceof BigNumber)) {
            return false;
        }
        //If other IS a BigNumber we then make sure that they are equal in size, to do so we call the equalizer method
        BigNumber ot = (BigNumber) other;
        if (ot.numericalValue.length() > this.numericalValue.length()) {
            this.numericalValue = equalizer(this.numericalValue, ot.numericalValue.length());
        } else {
            ot.numericalValue = equalizer(ot.numericalValue, this.numericalValue.length());

        }
        //Then we simply compare the two strings
        return this.numericalValue.equals(ot.numericalValue);
    }

    //Turn String into desired size by adding 0s at the beginning
    public String equalizer(String currentStr, int targetSize) {
        StringBuilder currentStrBuilder = new StringBuilder(currentStr);

        //Check if the number is required size, if not add a zero to the beginning
        while (currentStrBuilder.length() != targetSize) {
            currentStrBuilder.insert(0, "0");
        }
        return currentStrBuilder.toString();
    }


    //Remove all 0s at beginning of a String
    public String reducer(String str) {

        StringBuilder reduction = new StringBuilder(str);

        //Check all characters starting with 0
        for (int i = 0; i < str.length(); i++) {

            //If the character is 0 remove it, if not stop the loop
            if (reduction.charAt(0) == 48) {
                reduction.replace(0, 1, "");
            } else {
                break;
            }
        }
        //If the original number was 0, the result will be null, as such we simply add a zero to equalize it
        if (reduction.toString().isEmpty()) {
            reduction.insert(0, "0");
        }

        return reduction.toString();
    }

}