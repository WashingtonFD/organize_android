package com.organize4event.organize.commons.validations;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.primitives.Chars;
import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;

import java.util.Arrays;
import java.util.List;


public class CpfCnpjRule extends ContextualAnnotationRule<CpfCnpj, String> {

    protected CpfCnpjRule(final ValidationContext validationContext, final CpfCnpj cpfCnpj) {
        super(validationContext, cpfCnpj);
    }

    private static boolean IsValidCNPJ(String cnpj) {
        String cnpjNotFormated = cnpj.replaceAll("[.-]", "");
        if (sequenceVerify(cnpjNotFormated)) {
            return IsValid(cnpjNotFormated, new Integer[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}, 14);
        }

        return false;
    }

    private static boolean IsValidCPF(String cpf) {
        String cpfNotFormated = cpf.replaceAll("[.-]", "");
        if (sequenceVerify(cpfNotFormated)) {
            return IsValid(cpfNotFormated, new Integer[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2}, 11);
        }

        return false;
    }

    private static boolean IsValid(String cpfCnpj, Integer[] mult, int length) {
        if (cpfCnpj.length() != length)
            return false;

        String cpnjWithoutCheckDigit = cpfCnpj.substring(0, length - 2);
        List<Integer> ints = Arrays.asList(mult);
        int sum = Sum(cpnjWithoutCheckDigit, ints.subList(1, ints.size()));

        Integer rest = sum % 11;
        rest = rest < 2 ? 0 : 11 - rest;

        String digits = rest.toString();
        cpnjWithoutCheckDigit += digits;
        sum = Sum(cpnjWithoutCheckDigit, ints);

        rest = sum % 11;
        rest = rest < 2 ? 0 : 11 - rest;
        digits += rest.toString();
        return cpfCnpj.endsWith(digits);
    }

    private static int Sum(String cpnjWithoutCheckDigit, List<Integer> mult) {
        int sum = 0;
        List<Integer> ints = Lists.transform(Chars.asList(cpnjWithoutCheckDigit.toCharArray()), new Function<Character, Integer>() {
            @Override
            public Integer apply(Character input) {
                return Integer.parseInt(input.toString());
            }
        });
        for (int i = 0; i < ints.size(); i++) sum += ints.get(i) * mult.get(i);
        return sum;
    }

    public static boolean sequenceVerify(String data) {
        if (data.equals("00000000000") || data.equals("00000000000000") ||
                data.equals("11111111111") || data.equals("11111111111111") ||
                data.equals("22222222222") || data.equals("22222222222222") ||
                data.equals("33333333333") || data.equals("33333333333333") ||
                data.equals("44444444444") || data.equals("44444444444444") ||
                data.equals("55555555555") || data.equals("55555555555555") ||
                data.equals("66666666666") || data.equals("66666666666666") ||
                data.equals("77777777777") || data.equals("77777777777777") ||
                data.equals("88888888888") || data.equals("88888888888888") ||
                data.equals("99999999999") || data.equals("99999999999999")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(String data) {
        boolean stringIsEmpty = data == null || data.isEmpty();
        if (mRuleAnnotation.isRequired() && stringIsEmpty)
            return false;
        else if (!mRuleAnnotation.isRequired() && stringIsEmpty)
            return true;
        return IsValidCNPJ(data) || IsValidCPF(data);
    }
}
