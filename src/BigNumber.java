import java.util.Stack;

class BigNumber {

    String value = "";

    public BigNumber() {

    }

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
        return this;
    }

    // Resta
    BigNumber sub(BigNumber other) {
        return this;

    }

    // Multiplica
    BigNumber mult(BigNumber other) {
        return this;

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
        if (!(other instanceof BigNumber)){
            return false;
        }
        BigNumber ot = (BigNumber) other;
        if (ot.value.length() > this.value.length()){
            this.value = equalizer( this.value, ot.value.length());
        } else {
            ot.value = equalizer( ot.value, this.value.length());

        }
        return this.value.equals(ot.value);

    }

    public String equalizer(String currentStr, int targetSize) {
        while (currentStr.length() != targetSize) {
            currentStr = "0" + currentStr;
        }
        return currentStr;
    }
}