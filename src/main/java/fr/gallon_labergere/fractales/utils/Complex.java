package fr.gallon_labergere.fractales.utils;

/* This file is part of the JavaFractal project.
 *
 * JavaFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFractal.  If not, see <http://www.gnu.org/licenses/>.
 * Authors : Lilian Gallon, Rémi Labergère
 */

/**
 * Used to simplify the newton fractal calculations
 * Important note : Java does not support operator overloading
 * Some details here: https://stackoverflow.com/questions/77718/why-doesnt-java-offer-operator-overloading
 */
public class Complex {

    private double real;
    private double imag;

    public Complex(double real, double imag){
        this.real = real;
        this.imag = imag;
    }

    /**
     * @param z complex
     * @return this + z
     */
    public Complex plus(Complex z){
        double new_real = real + z.getReal();
        double new_imag = imag + z.getImag();
        return new Complex(new_real,new_imag);
    }

    /**
     * @param a constant
     * @return this + a
     */
    public Complex plus(double a){
        double new_real = real + a;
        double new_imag = imag + a;
        return new Complex(new_real,new_imag);
    }

    /**
     * @param z complex
     * @return this - z
     */
    public Complex minus(Complex z){
        double new_real = real - z.getReal();
        double new_imag = imag - z.getImag();
        return new Complex(new_real,new_imag);
    }

    /**
     * @param a constant
     * @return this - a
     */
    public Complex minus(double a){
        double new_real = real - a;
        double new_imag = imag - a;
        return new Complex(new_real,new_imag);
    }

    /**
     * @param z complex
     * @return this * z
     */
    public Complex times(Complex z){
        double new_real = real * z.getReal() - imag * z.getImag();
        double new_imag = real * z.getImag() + imag * z.getReal();
        return new Complex(new_real, new_imag);
    }

    /**
     * @param a constant
     * @return this * a
     */
    public Complex scale(double a) {
        return new Complex(real * a, imag * a);
    }

    /**
     * @param z complex
     * @return this / z
     */
    public Complex divides(Complex z) {
        return z.times(z.reciprocal());
    }

    /**
     * @return the reciprocal
     */
    private Complex reciprocal() {
        double scale = real*real + imag*imag;
        return new Complex(real / scale, -imag / scale);
    }

    /**
     * @return module(this) => |this|
     */
    public double getModule(){
        return Math.sqrt(real*real + imag*imag);
    }

    /**
     * @param o object
     * @return this == z
     */
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Complex)) && ((Complex) o).getReal() == real && ((Complex) o).getImag() == imag;
    }

    /**
     * @return real part
     */
    public double getReal() {
        return real;
    }

    /**
     * @param real real part
     */
    public void setReal(double real) {
        this.real = real;
    }

    /**
     * @return imaginary part
     */
    public double getImag() {
        return imag;
    }

    /**
     * @param imag imaginary part
     */
    public void setImag(double imag) {
        this.imag = imag;
    }
}
