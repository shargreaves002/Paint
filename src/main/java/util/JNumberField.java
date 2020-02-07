package util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 Swing-component for input and output of numeric values.
 */

public class JNumberField extends JTextField {
    boolean valid = false;

    /** constructor for a util.JNumberField */
    public JNumberField() {
        enableEvents(AWTEvent.KEY_EVENT_MASK);
    }

    /** constructor for a default value */
    public JNumberField(long l) {
        this();
        setLong(l);
        setValid(true);
    }

    /** Gets a double-value from the util.JNumberField. */
    public double getDouble() {
        return getText().equals("") ? 0 : Double.parseDouble(getText());
    }

    /** Gets a float-value from the util.JNumberField. */
    public float getFloat() {
        return getText().equals("") ? 0 : Float.parseFloat(getText());
    }

    /** Gets an int-value from the util.JNumberField. */
    public int getInt() {
        return getText().equals("") ? 0 : Integer.parseInt(getText());
    }

    /** Gets a long-value from the util.JNumberField. */
    public long getLong() {
        return getText().equals("") ? 0 : Long.parseLong(getText());
    }

    /** Checks whether the util.JNumberField contains a valid numeric value. */
    public boolean isNumeric() {
        final String Digits     = "(\\p{Digit}+)";
        final String HexDigits  = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp        = "[eE][+-]?"+Digits;
        final String fpRegex    =
                ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
                        "[+-]?(" + // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from the Java Language Specification, 2nd
                        // edition, section 3.10.2.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.("+Digits+")("+Exp+")?)|"+

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        return java.util.regex.Pattern.matches(fpRegex, getText());
    }

    /** Sets a double-value into the util.JNumberField. */
    public void setDouble(double d) {
        setText(String.valueOf(d));
    }

    /** Sets a double-value with N digits into the util.JNumberField. */
    public void setDouble(double d, int N) {
        setText(String.format("%." + N + "f", d));
    }

    /** Sets a float-value into the util.JNumberField. */
    public void setFloat(float f) {
        setText(String.valueOf(f));
    }

    /** Sets a float-value with N digits into the util.JNumberField. */
    public void setFloat(float f, int N) {
        setText(String.format("%." + N + "f", f));
    }

    /** Sets an int-value into the util.JNumberField. */
    public void setInt(int i) {
        setText(String.valueOf(i));
    }

    /** Sets a long-value into the util.JNumberField. */
    public void setLong(long l) {
        setText(String.valueOf(l));
    }

    /** Clears the util.JNumberField */
    public void clear() {
        setText("");
    }

    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (isNumeric() /*|| getText().equals("-") || getText().equals("") || getText().equals(".")*/) {
            setBackground(Color.white);
            setValid(true);
        } else {
            setBackground(Color.red);
            setValid(false);
        }
    }

    public boolean getIsValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}