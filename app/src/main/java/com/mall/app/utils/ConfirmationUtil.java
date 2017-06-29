package com.mall.app.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ConfirmationUtil {

    public static class InputMoney implements InputFilter {
        EditText editText;

        public InputMoney(EditText editText){
            this.editText = editText;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (source.toString().equals(".") && dstart == 0 && dend == 0) {//判断小数点是否在第一位
                editText.setText(0+""+source+dest);//给小数点前面加0
                editText.setSelection(2);//设置光标
            }

            if (dest.toString().indexOf(".") != -1 && (dest.length() - dest.toString().indexOf(".")) > 2) {//判断小数点是否存在并且小数点后面是否已有两个字符
                if ((dest.length() - dstart) < 3) {//判断现在输入的字符是不是在小数点后面
                    return "";//过滤当前输入的字符
                }
            }

            return null;
        }
    }

    public static boolean isInputMoney(String input){

        if(StringHelper.isEmpty(input)){
            return false;
        }
        Pattern p = Pattern.compile("^[0-9]+$|^[0-9]+\\.[0-9]{1,2}$",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str.trim());
        return match.matches();
    }


    public static int ConvertInteger(EditText editText){
        try{
            return Integer.parseInt(editText.getText().toString());
        }catch (Exception e){}
        return 0;
    }


    public static boolean compare(EditText e1,EditText e2){
        try {
            int p1 = Integer.parseInt(e1.getText().toString());
            int p2 = Integer.parseInt(e2.getText().toString());
            if (p1 >= p2) {
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean isOk(Context mContext,EditText e1, EditText e2, EditText e3){
        if(!StringHelper.isEmpty(e1)){
            if(!StringHelper.isEmpty(e2)){
                if(!ConfirmationUtil.compare(e1,e2)){
                    showToastShort(mContext,"输入第二批发数量不能少于第一个批发数量");
                    e2.requestFocus();
                    return false;
                }
            }
        }else if(!StringHelper.isEmpty(e2)){
            if(StringHelper.isEmpty(e1)){
                showToastShort(mContext,"请输入对应的起始数量");
                e1.requestFocus();
                return false;
            }
        }else if(!StringHelper.isEmpty(e3)){
            if(StringHelper.isEmpty(e1)){
                showToastShort(mContext,"请输入对应的起始数量");
                e1.requestFocus();
                return false;
            }else if(StringHelper.isEmpty(e2)){

                e2.requestFocus();
                return false;
            }
        }

        if(!StringHelper.isEmpty(e1)&&StringHelper.isEmpty(e3)){
            showToastShort(mContext,"请输入对应的价格");
            e3.requestFocus();
            return false;
        }
        return true;
    }
    public static void showToastShort(Context mContext,String msg) {
        if (null != msg && !StringHelper.isEmpty(msg)) {
            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
        }
    }



    public static boolean isFit(Context mContext,EditText a1,EditText a2,EditText b1,EditText b2,EditText c1,EditText c2){
        int min1,min2,min3;
        int max1,max2,max3;
        min1 = ConfirmationUtil.ConvertInteger(a1);
        min2 = ConfirmationUtil.ConvertInteger(b1);
        min3 = ConfirmationUtil.ConvertInteger(c1);
        max1 = ConfirmationUtil.ConvertInteger(a2);
        max2 = ConfirmationUtil.ConvertInteger(b2);
        max3 = ConfirmationUtil.ConvertInteger(c2);

        if(max1==0){
            if(min2 != 0 ||max2 != 0 ||max3 != 0 ||min3 != 0){
                showToastShort(mContext,"输入有误");
                return false;
            }
        }
        if(max2==0){
            if(max3 != 0 ||min3 != 0){
                showToastShort(mContext,"输入有误");
                return false;
            }
        }
        if(min2 != 0 && max1 > min2){
            showToastShort(mContext,"输入有误");
            return false;
        }
        if(min3 != 0 && max2 > min3){
            showToastShort(mContext,"输入有误");
            return false;
        }

        return true;
    }



}
