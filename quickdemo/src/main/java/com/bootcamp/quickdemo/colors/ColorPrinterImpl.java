package com.bootcamp.quickdemo.colors;

import org.springframework.stereotype.Component;

@Component
public class ColorPrinterImpl implements ColorPrinter {

    private final RedPrinter redPrinter = new RedPrinter();
    private final GreenPrinter greenPrinter = new GreenPrinter();
    private final BluePrinter bluePrinter = new BluePrinter();

    @Override
    public String printInColor() {
        return this.redPrinter.printInColor() + "\n" + this.greenPrinter.printInColor() + "\n" + this.bluePrinter.printInColor();
    }
}
