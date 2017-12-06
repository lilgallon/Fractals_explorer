package fr.gallon_labergere.fractales.utils;

/**
 * Created by lgallon on 06/12/17.
 */
public class Complex {

    private double real;
    private double imag;

    public Complex(double real, double imag){
        this.real = real;
        this.imag = imag;
    }

    /**
     * this + z
     * @param z
     * @return
     */
    public Complex plus(Complex z){
        double new_real = real + z.getReal();
        double new_imag = imag + z.getImag();
        return new Complex(new_real,new_imag);
    }

    /**
     * this + a
     * @param a
     * @return
     */
    public Complex plus(double a){
        double new_real = real + a;
        double new_imag = imag + a;
        return new Complex(new_real,new_imag);
    }

    /**
     * this - z
     * @param z
     * @return
     */
    public Complex minus(Complex z){
        double new_real = real - z.getReal();
        double new_imag = imag - z.getImag();
        return new Complex(new_real,new_imag);
    }

    /**
     * this - a
     * @param a
     * @return
     */
    public Complex minus(double a){
        double new_real = real - a;
        double new_imag = imag - a;
        return new Complex(new_real,new_imag);
    }

    /**
     * this * z
     * @param z
     * @return
     */
    public Complex times(Complex z){
        double new_real = real * z.getReal() - imag * z.getImag();
        double new_imag = real * z.getImag() + imag * z.getReal();
        return new Complex(new_real, new_imag);
    }

    /**
     * this * a
     * @return a
     */
    public Complex scale(double a) {
        return new Complex(real * a, imag * a);
    }

    /**
     * this / z
     * @param z
     * @return
     */
    public Complex divides(Complex z) {
        return z.times(z.reciprocal());
    }

    /**
     *
     * @return the reciprocal
     */
    public Complex reciprocal() {
        double scale = real*real + imag*imag;
        return new Complex(real / scale, -imag / scale);
    }

    /**
     * module(this) => |this|
     * @return
     */
    public double getModule(){
        return Math.sqrt(real*real + imag*imag);
    }

    /**
     * this == z
     * @param o
     * @return
     */
    public boolean equals(Object o){
        if(o==null || !(o instanceof Complex)) return false;
        return ((Complex) o).getReal()==real && ((Complex) o).getImag()==imag;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImag() {
        return imag;
    }

    public void setImag(double imag) {
        this.imag = imag;
    }
}
