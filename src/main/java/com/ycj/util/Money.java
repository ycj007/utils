package com.ycj.util;


import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 元转分默认无小数
 * 分转元默认保留两位小数
 *
 * 舍入默认采用四舍五入
 *
 */
public class Money {


    private MoneyUnit moneyUnit;
    private BigDecimal money;


    public Money(String money, MoneyUnit moneyUnit) {

        Objects.requireNonNull(money, "money required not null");
        Objects.requireNonNull(moneyUnit, "moneyUnit required not null");
        this.moneyUnit = moneyUnit;
        this.money = new BigDecimal(money);

    }

    public BigDecimal getMoney() {
        return new BigDecimal(money.toPlainString());
    }

    public MoneyUnit getMoneyUnit() {
        return moneyUnit;
    }


    public float toFloat() {
        return this.money.floatValue();
    }

    public long toLong() {
        return this.money.longValue();
    }

    public String toScaleString(int scale) {

        return this.getMoney().setScale(scale,ROUND_HALF_UP).toPlainString();

    }


    public static Money convertUnit(Money money, MoneyUnit moneyUnit) {

        Objects.requireNonNull(moneyUnit);


        if (moneyUnit.getCode() == money.getMoneyUnit().getCode()) {
            return money;
        }

        if (moneyUnit.getCode() == MoneyUnit.YUAN.getCode() && money.getMoneyUnit().getCode() == MoneyUnit.FEN.getCode()) {
            BigDecimal divisor = new BigDecimal(100);
            return new Money(money.getMoney().divide(divisor).setScale(2,ROUND_HALF_UP).toPlainString(), moneyUnit);

        } else if (moneyUnit.getCode() == MoneyUnit.FEN.getCode() && money.getMoneyUnit().getCode() == MoneyUnit.YUAN.getCode()) {
            BigDecimal multiplicand = new BigDecimal(100);
            return new Money(money.getMoney().multiply(multiplicand).setScale(0,ROUND_HALF_UP).toPlainString(), moneyUnit);
        } else {
            return null;
        }


    }

    @Override
    public String toString() {
        return this.money.toPlainString();
    }

    public enum MoneyUnit {
        YUAN(1, "元"), FEN(2, "分");
        private int code;
        private String unit;

        private MoneyUnit(int code, String unit) {
            this.code = code;
            this.unit = unit;
        }

        public int getCode() {
            return code;
        }


        public String getUnit() {
            return unit;
        }


    }




}
